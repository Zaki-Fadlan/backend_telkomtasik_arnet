package com.telkom.psb_tools.model.datamaster;

import com.telkom.psb_tools.model.AbstractDate;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Data
@Entity
@Table(name = "dm_sto")
@Where(clause = "deleted_date is null")
public class Sto extends AbstractDate implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "sto_name")
    private String stoName;

    @ManyToOne(targetEntity = Datel.class)
    private Datel datel;
}
