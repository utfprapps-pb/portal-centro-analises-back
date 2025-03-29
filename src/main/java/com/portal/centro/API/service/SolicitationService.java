package com.portal.centro.API.service;

import com.portal.centro.API.enums.SolicitationProjectNature;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.*;
import com.portal.centro.API.repository.SolicitationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitationService extends GenericService<Solicitation, Long> {

    private final SolicitationHistoricService solicitationHistoricService;
    private final UserService userService;
    private final SolicitationRepository solicitationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public SolicitationService(SolicitationRepository solicitationRepository, SolicitationHistoricService solicitationHistoricService,
                               UserService userService) {
        super(solicitationRepository);
        this.solicitationRepository = solicitationRepository;
        this.solicitationHistoricService = solicitationHistoricService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Solicitation update(Solicitation solicitation) throws Exception {
        Solicitation oneById = findOneById(solicitation.getId());
        if (!SolicitationStatus.AWAITING_CORRECTION.equals(oneById.getStatus())) {
            throw new GenericException("A Solicitação não pode ser alterada pois não está em correção.");
        }
        super.update(solicitation);

        User loggedUser = userService.findSelfUser();
        SolicitationHistoric solicitationHistoric = new SolicitationHistoric();
        solicitationHistoric.setSolicitation(solicitation);
        if (solicitation.getProject().getUser().getId().equals(loggedUser.getId())) {
            solicitationHistoric.setStatus(SolicitationStatus.AWAITING_LAB_CONFIRMATION);
        } else {
            solicitationHistoric.setStatus(SolicitationStatus.AWAITING_RESPONSIBLE_CONFIRMATION);
        }
        solicitationHistoricService.save(solicitationHistoric);
        solicitation.setStatus(solicitationHistoric.getStatus());

        return solicitation;
    }

    @Override
    @Transactional
    public Solicitation save(Solicitation solicitation) throws Exception {
        if (solicitation.getId() != null) {
            throw new GenericException("Não é possivel alterar solicitações por rota de inclusão.");
        }
        this.completeSolicitation(solicitation);

        return solicitation;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    protected void completeSolicitation(Solicitation solicitation) throws Exception {
        User loggedUser = userService.findSelfUser();
        // Verifica se a Natureza do projeto é outra, se sim, verifica se o campo de outra natureza foi preenchido.
        if (solicitation.getProjectNature().equals(SolicitationProjectNature.OTHER)
                && (solicitation.getOtherProjectNature() == null || solicitation.getOtherProjectNature().isEmpty())) {
            throw new GenericException("O campo 'Outra natureza de projeto' deve ser preenchido quando a natureza do projeto for 'Outro'.");
        }

        // Cria o histório da solicitação
        SolicitationHistoric solicitationHistoric = new SolicitationHistoric();
        solicitationHistoric.setSolicitation(solicitation);

        if (solicitation.getId() == null) {
            if (solicitation.getProject().getUser().getId().equals(loggedUser.getId())) {
                solicitation.setStatus(SolicitationStatus.AWAITING_LAB_CONFIRMATION);
                solicitationHistoric.setStatus(SolicitationStatus.AWAITING_LAB_CONFIRMATION);
            } else {
                solicitation.setStatus(SolicitationStatus.AWAITING_RESPONSIBLE_CONFIRMATION);
                solicitationHistoric.setStatus(SolicitationStatus.AWAITING_RESPONSIBLE_CONFIRMATION);
            }
        }

        SolicitationForm form = solicitation.getForm();
        if (ObjectUtils.isNotEmpty(form)) {
            entityManager.persist(form);

            if (ObjectUtils.isNotEmpty(form.getAmostras())) {
                for (SolicitationAmostra amostra : form.getAmostras()) {
                    amostra.setForm(form);
                    if (ObjectUtils.isNotEmpty(amostra.getFotos())) {
                        for (SolicitationAmostraFoto foto : amostra.getFotos()) {
                            foto.setAmostra(amostra);
                            entityManager.persist(foto);
                        }
                    }
                    if (ObjectUtils.isNotEmpty(amostra.getModeloMicroscopia())) {
                        entityManager.persist(amostra.getModeloMicroscopia());
                    }
                    entityManager.persist(amostra);
                }
            }
        }

//        salvarAnexosMEV(solicitation);
        genericRepository.saveAndFlush(solicitation);
        solicitationHistoricService.save(solicitationHistoric);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void salvarAnexosMEV(Solicitation solicitation) {
        // Verifica se a solicitação é do tipo MEV
//        if (SolicitationFormType.MEV.getContent().equals(solicitation.getSolicitationType().getContent())) {
//            List<SolicitationAmostraAttachment> vinculos = new ArrayList<>();
//            // Supondo que o formulário da solicitação contenha uma amostra registrada "amostras"
//            LinkedHashMap form = (LinkedHashMap) solicitation.getForm();
//            List<LinkedHashMap> amostras = (List<LinkedHashMap>) form.get("amostras");
//
//            if (amostras != null && !amostras.isEmpty()) {
//                for (LinkedHashMap node : amostras) {
//                    // Obtém a lista de anexos (modeloMicroscopia) de cada amostra
//                    List<LinkedHashMap> modeloMicroscopia = (List<LinkedHashMap>) node.get("modeloMicroscopia");
//
//                    if (modeloMicroscopia != null && !modeloMicroscopia.isEmpty()) {
//                        List<Attachment> attachmentsPersistidos = new ArrayList<>();
//
//                        // Persistindo cada anexo para que o ID seja gerado
//                        for (LinkedHashMap attachmentHash : modeloMicroscopia) {
//                            Attachment attachment = modelMapper.map(attachmentHash, Attachment.class);
//                            attachment.setUrl(null);
//                            attachment.setCreatedAt(LocalDateTime.now());
//                            entityManager.persist(attachment);
//
//                            SolicitationAmostraAttachment vinculo = new SolicitationAmostraAttachment();
//                            vinculo.setAttachment(attachment);
//                            vinculos.add(vinculo);
//                            entityManager.persist(vinculo);
//
//                            attachmentsPersistidos.add(attachment);
//                        }
//                        // Atualiza o node da amostra para usar os anexos já persistidos (com ID)
//                        node.put("modeloMicroscopia", attachmentsPersistidos);
//                    }
//                }
//            }
//        }
    }

    @Transactional
    @Override
    public ObjectReturn deleteById(Long id) throws Exception {
        throw new GenericException("Não é possível excluir solicitações.");
//        Solicitation solicitation = this.findOneById(id);
//        // Se não ta Aguardando Confirmação do Responsavel ou Aguardando Confirmação do Lab. Ainda pode Excluir
//        if (!(SolicitationStatus.AWAITING_RESPONSIBLE_CONFIRMATION.equals(solicitation.getStatus()) ||
//                SolicitationStatus.AWAITING_LAB_CONFIRMATION.equals(solicitation.getStatus()))) {
//            throw new GenericException("Solicitações não podem ser excluidas após entrada no laboratório!");
//        }
//
//        if (ObjectUtils.isNotEmpty(solicitation)) {
//            this.excluirAnexosMEV(solicitation);
//        }
//        return super.deleteById(id);
    }

    @Transactional
    public void excluirAnexosMEV(Solicitation solicitation) {
//        if (SolicitationFormType.MEV.getContent().equals(solicitation.getSolicitationType().getContent())) {
//            LinkedHashMap form = (LinkedHashMap) solicitation.getForm();
//            List<LinkedHashMap> amostras = (List<LinkedHashMap>) form.get("amostras");
//            if (amostras != null && !amostras.isEmpty()) {
//                for (LinkedHashMap node : amostras) {
//                    List<LinkedHashMap> modeloMicroscopia = (List<LinkedHashMap>) node.get("modeloMicroscopia");
//                    if (modeloMicroscopia != null && !modeloMicroscopia.isEmpty()) {
//                        for (LinkedHashMap attachmentHash : modeloMicroscopia) {
//                            Attachment attachment = modelMapper.map(attachmentHash, Attachment.class);
//                            SolicitationAmostraAttachment bind = solicitationAttachmentRepository.findByAttachment_Id(attachment.getId());
//                            if (ObjectUtils.isNotEmpty(bind)) {
//                                entityManager.remove(bind);
//                            }
//                            entityManager.remove(attachment);
//                        }
//                    }
//                }
//            }
//        }
    }

    @Override
    public List<Solicitation> getAll() {
        User user = userService.findSelfUser();
        switch (user.getRole()) {
            case ROLE_ADMIN:
                return super.getAll();
            default:
                return solicitationRepository.findAllByCreatedByOrResponsavel(user, user);
        }
    }
}
