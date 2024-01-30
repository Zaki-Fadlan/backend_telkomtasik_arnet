package com.telkom.psb_tools.model.datamaster;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.telkom.psb_tools.model.AbstractDate;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "dm_datel")
@Where(clause = "deleted_date is null")
public class Datel extends AbstractDate implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "datel_name")
    @JsonProperty("datel_name")
    private String datelName;

    @JsonIgnore
    @OneToMany(mappedBy = "datel",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Sto> Sto;
}
