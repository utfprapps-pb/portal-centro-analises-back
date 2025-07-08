package br.edu.utfpr.pb.app.labcaapi.model;

import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "tb_analysis")
public class Analysis extends IModel {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

}
