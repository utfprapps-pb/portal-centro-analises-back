package com.portal.centro.API.model;

import com.portal.centro.API.generic.base.IModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "project")
@EntityListeners(AuditingEntityListener.class)
public class Project extends IModel {

    @NotNull()
    @Size(min = 4, max = 255)
    private String description;

    @NotNull()
    @Size(min = 4, max = 255)
    private String subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", updatable = false)
    private User teacher;

    @ManyToMany
    @JoinTable(name = "project_student",
            joinColumns = @JoinColumn(
                    name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private List<User> students;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;
}
