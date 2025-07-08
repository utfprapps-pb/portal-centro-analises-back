package br.edu.utfpr.pb.app.labcaapi.model;

import br.edu.utfpr.pb.app.labcaapi.enums.StatusEmail;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "tb_email_log")
@Table
public class EmailLog implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_ref")
    private String ownerRef;

    @Column(name = "email_from")
    private String emailFrom;

    @Column(name = "email_to")
    private String emailTo;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(value = EnumType.STRING)
    private StatusEmail statusEmail;
}
