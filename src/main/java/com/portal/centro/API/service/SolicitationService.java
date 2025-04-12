package com.portal.centro.API.service;

import com.portal.centro.API.enums.SolicitationProjectNature;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.*;
import com.portal.centro.API.repository.SolicitationAmostraAnaliseRepository;
import com.portal.centro.API.repository.SolicitationAmostraRepository;
import com.portal.centro.API.repository.SolicitationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SolicitationService extends GenericService<Solicitation, Long> {

    private final SolicitationHistoricService solicitationHistoricService;
    private final UserService userService;
    private final SolicitationRepository solicitationRepository;
    private final SolicitationAmostraRepository solicitationAmostraRepository;
    private final SolicitationAmostraAnaliseRepository solicitationAmostraAnaliseRepository;
    private final WebsocketService websocketService;

    @PersistenceContext
    private EntityManager entityManager;

    public SolicitationService(SolicitationRepository solicitationRepository, SolicitationHistoricService solicitationHistoricService,
                               UserService userService, SolicitationAmostraAnaliseRepository solicitationAmostraAnaliseRepository,
                               SolicitationAmostraRepository solicitationAmostraRepository, WebsocketService websocketService) {
        super(solicitationRepository);
        this.solicitationRepository = solicitationRepository;
        this.solicitationHistoricService = solicitationHistoricService;
        this.userService = userService;
        this.solicitationAmostraAnaliseRepository = solicitationAmostraAnaliseRepository;
        this.solicitationAmostraRepository = solicitationAmostraRepository;
        this.websocketService = websocketService;
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

        this.websocketService.atualizarSolicitacao(solicitation);
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
            form.setSolicitation(solicitation);
            entityManager.persist(form);
            solicitation.setForm(form);

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
            entityManager.persist(solicitation);
            genericRepository.saveAndFlush(solicitation);
        } else {
            genericRepository.saveAndFlush(solicitation);

        }

        solicitationHistoricService.save(solicitationHistoric);
    }

    @Transactional
    @Override
    public ObjectReturn deleteById(Long id) throws Exception {
        throw new GenericException("Não é possível excluir solicitações.");
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

    public Long salvarAnalise(SolicitationAmostra amostra) throws Exception {
        User user = userService.findSelfUser();
        if (!Objects.equals(user.getRole(), Type.ROLE_ADMIN)) {
            throw new GenericException("Você não pode fazer isso!");
        }
        try {
            solicitationAmostraRepository.saveAndFlush(amostra);
            return amostra.getForm().getSolicitation().getId();
        } catch (Exception e) {
            String message = e.getMessage();
            final String genericException = "GenericException{";
            if (message.contains(genericException)) {
                message = message.substring(message.indexOf(genericException) + genericException.length());
                message = message.substring(0, message.indexOf("}"));
                throw new GenericException(message);
            }

            e.printStackTrace();
            throw e;
        }
    }

    public Long atualizarConclusao(SolicitationAmostra amostra) throws Exception {
        amostra.setConcluida(!amostra.getConcluida());
        return this.salvarAnalise(amostra);
    }
}
