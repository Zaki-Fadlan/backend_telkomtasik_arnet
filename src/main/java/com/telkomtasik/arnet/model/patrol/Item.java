package com.telkomtasik.arnet.model.patrol;

import com.telkomtasik.arnet.model.AbstractDate;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "p_item")
@Where(clause = "deleted_date is null")
public class Item extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    
    private String code;

}
