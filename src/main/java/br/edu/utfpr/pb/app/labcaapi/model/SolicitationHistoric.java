package br.edu.utfpr.pb.app.labcaapi.model;

import br.edu.utfpr.pb.app.labcaapi.enums.SolicitationStatus;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity(name = "tb_solicitation_historic")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitationHistoric extends IModel {

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "solicitation_id")
    private Solicitation solicitation;

    @Enumerated
    private SolicitationStatus status;

    @Column(name = "observation")
    private String observation;

}


