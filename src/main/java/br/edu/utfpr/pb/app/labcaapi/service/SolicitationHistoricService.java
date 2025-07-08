package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.enums.SolicitationStatus;
import br.edu.utfpr.pb.app.labcaapi.enums.Type;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.Solicitation;
import br.edu.utfpr.pb.app.labcaapi.model.SolicitationHistoric;
import br.edu.utfpr.pb.app.labcaapi.model.User;
import br.edu.utfpr.pb.app.labcaapi.repository.SolicitationHistoricRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SolicitationHistoricService extends GenericService<SolicitationHistoric, Long> {

    private final SolicitationHistoricRepository solicitationHistoricRepository;
    private final UserService userService;
    private final EmailService emailService;

    public SolicitationHistoricService(SolicitationHistoricRepository solicitationHistoricRepository,
                                       UserService userService,
                                       EmailService emailService) {
        super(solicitationHistoricRepository);
        this.solicitationHistoricRepository = solicitationHistoricRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public List<SolicitationHistoric> getAll() {
        User user = userService.findSelfUser();
        switch (user.getRole()) {
            case ROLE_ADMIN:
                return super.getAll();
            default:
                return solicitationHistoricRepository.findAllBySolicitation_CreatedByOrSolicitation_Responsavel(user, user);
        }
    }

    public List<SolicitationHistoric> findHistoryBySolicitationId(Long id) {
        return solicitationHistoricRepository.findAllBySolicitationId(id);
    }

    public void verificaStatusValido(SolicitationHistoric historico) throws Exception {
        User selfUser = userService.findSelfUser();
        if (!Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN)) {
            List<SolicitationStatus> statusPermitidos = List.of(
                    SolicitationStatus.AWAITING_RESPONSIBLE_CONFIRMATION,
                    SolicitationStatus.AWAITING_LAB_CONFIRMATION,
                    SolicitationStatus.AWAITING_CORRECTION,
                    SolicitationStatus.REJECTED);
            if (!statusPermitidos.contains(historico.getStatus())) {
                throw new GenericException("Você não pode fazer isso!");
            }
        }
    }

    @Override
    @Transactional
    public SolicitationHistoric save(SolicitationHistoric requestBody) throws Exception {
        Solicitation solicitation = requestBody.getSolicitation();
        SolicitationStatus oldStatus = solicitation.getStatus();
        SolicitationStatus newStatus = requestBody.getStatus();
        SolicitationHistoric save = super.save(requestBody);
        if (oldStatus != newStatus) {
            emailService.sendUpdateSolicitationStatus(solicitation, newStatus, oldStatus, requestBody.getObservation());
        }

        return save;
    }


}
