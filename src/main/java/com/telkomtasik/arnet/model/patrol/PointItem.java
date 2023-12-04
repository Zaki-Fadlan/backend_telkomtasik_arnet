package com.telkomtasik.arnet.model.patrol;

import com.telkomtasik.arnet.model.AbstractDate;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "p_point_item")
@Where(clause = "deleted_date is null")
public class PointItem extends AbstractDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @ManyToOne
    private Point point;

    @ManyToOne
    private Item item;
}
