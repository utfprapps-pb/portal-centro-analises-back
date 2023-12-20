package com.portal.centro.API.service;

import com.portal.centro.API.dto.StudentSolicitationDTO;
import com.portal.centro.API.enums.StudentSolicitationStatus;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.NotFoundException;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.StudentSolicitation;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.StudentSolicitationRepository;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentSolicitationService extends GenericService<StudentSolicitation, Long> {

    private final StudentSolicitationRepository studentSolicitationRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private static final String MESSAGE_VALIDATION_APPROVE_ADVISOR_AND_LAB = "Apenas o orientador e o laboratório podem aprovar ou recusar a solicitação.";

    public StudentSolicitationService(StudentSolicitationRepository studentSolicitationRepository, UserService userService, ModelMapper modelMapper, EntityManager entityManager) {
        super(studentSolicitationRepository);
        this.studentSolicitationRepository = studentSolicitationRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentSolicitation save(StudentSolicitation requestBody) throws Exception {
        setDefaultValuesWhenSave(requestBody);
        return super.save(requestBody);
    }

    private void setDefaultValuesWhenSave(StudentSolicitation requestBody) {
        if (Objects.isNull(requestBody.getId()) || Objects.equals(requestBody.getId(), 0)) {
            requestBody.setStatus(StudentSolicitationStatus.PENDING);
        }
    }

    public StudentSolicitation solicit(StudentSolicitationDTO studentSolicitationDTO) throws Exception {
        User user = userService.findOneById(studentSolicitationDTO.getSolicitatedTo().getId());
        studentSolicitationDTO.setSolicitatedTo(user);
        StudentSolicitation studentSolicitation = modelMapper.map(studentSolicitationDTO, StudentSolicitation.class);
        studentSolicitation.setSolicitatedBy(userService.findSelfUser());
        validateLoggedUserIsSoliciting(studentSolicitation.getSolicitatedBy());
        studentSolicitation.setStatus(StudentSolicitationStatus.PENDING);
        return save(studentSolicitation);
    }

    private void validateLoggedUserIsSoliciting(User user) {
        if (!Objects.equals(user.getRole(), Type.ROLE_STUDENT))
            throw new ValidationException("Apenas o estudante pode realizar a solicitação.");
    }

    public StudentSolicitation approve(Long studentSolicitationId) throws Exception {
        StudentSolicitation studentSolicitation = getStudentSolicitationByIdOrElseThrowNotFoundException(studentSolicitationId);
        StudentSolicitationStatus currentStudentSolicitationStatus = studentSolicitation.getStatus();
        setDefaultValuesWhenApprovingOrRefusing(studentSolicitation, true);
        validateWhenApprovingOrRefusing(
                studentSolicitation.getFinishedBy(),
                studentSolicitation.getSolicitatedTo(),
                currentStudentSolicitationStatus
        );
        return save(studentSolicitation);
    }

    private StudentSolicitationStatus getStudentSolicitationStatusByUserRole(Type userRole, boolean approving) {
        if (Objects.equals(userRole, Type.ROLE_PROFESSOR))
            return approving ? StudentSolicitationStatus.APPROVED_ADVISOR : StudentSolicitationStatus.REFUSED_ADVISOR;

        if (Objects.equals(userRole, Type.ROLE_ADMIN))
            return approving ? StudentSolicitationStatus.APPROVED_LAB : StudentSolicitationStatus.REFUSED_LAB;

        throw new ValidationException(MESSAGE_VALIDATION_APPROVE_ADVISOR_AND_LAB);
    }

    private void validateLoggedUserIsApprovingOrRefusing(User userApprove, User solicitedTo) {
        if ((!Objects.equals(userApprove.getRole(), Type.ROLE_PROFESSOR)) &&
                (!Objects.equals(userApprove.getRole(), Type.ROLE_ADMIN)))
            throw new ValidationException(MESSAGE_VALIDATION_APPROVE_ADVISOR_AND_LAB);

        if ((Objects.equals(userApprove.getRole(), Type.ROLE_PROFESSOR)) &&
                (!Objects.equals(userApprove.getId(), solicitedTo.getId())))
            throw new ValidationException("O Orientador informado não é o mesmo solicitado na solicitação, somente o mesmo pode aprovar ou recusar.");
    }

    private void validateStatusWhenApprovingOrRefusing(StudentSolicitationStatus studentSolicitationStatus) {
        if (!Objects.equals(studentSolicitationStatus, StudentSolicitationStatus.PENDING))
            throw new ValidationException("Solicitação já encontra-se com a situação " + studentSolicitationStatus.getContent() + ". só é possível aprovar ou recusar uma solicitação que está pendente.");
    }

    private void validateWhenApprovingOrRefusing(User userApprove, User solicitedTo, StudentSolicitationStatus currentStudentSolicitationStatus) {
        validateLoggedUserIsApprovingOrRefusing(userApprove, solicitedTo);
        validateStatusWhenApprovingOrRefusing(currentStudentSolicitationStatus);
    }

    public StudentSolicitation refuse(Long studentSolicitationId) throws Exception {
        StudentSolicitation studentSolicitation = getStudentSolicitationByIdOrElseThrowNotFoundException(studentSolicitationId);
        StudentSolicitationStatus currentStudentSolicitationStatus = studentSolicitation.getStatus();
        setDefaultValuesWhenApprovingOrRefusing(studentSolicitation, false);
        validateWhenApprovingOrRefusing(
                studentSolicitation.getFinishedBy(),
                studentSolicitation.getSolicitatedTo(),
                currentStudentSolicitationStatus
        );
        return save(studentSolicitation);
    }

    private StudentSolicitation getStudentSolicitationByIdOrElseThrowNotFoundException(Long studentSolicitationId) {
        Optional<StudentSolicitation> studentSolicitationOptional = studentSolicitationRepository.findById(studentSolicitationId);
        if (studentSolicitationOptional.isEmpty())
            throw new NotFoundException("Solicitação não encontrada.");

        return studentSolicitationOptional.get();
    }

    private void setDefaultValuesWhenApprovingOrRefusing(StudentSolicitation studentSolicitation, boolean approving) {
        studentSolicitation.setFinishedBy(userService.findSelfUser());
        studentSolicitation.setFinishDate(LocalDateTime.now());
        studentSolicitation.setStatus(getStudentSolicitationStatusByUserRole(studentSolicitation.getFinishedBy().getRole(), approving));
    }

}
