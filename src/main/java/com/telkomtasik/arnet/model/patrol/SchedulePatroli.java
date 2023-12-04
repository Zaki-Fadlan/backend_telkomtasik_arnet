package com.telkomtasik.arnet.model.patrol;

import com.telkomtasik.arnet.model.AbstractDate;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "p_jadwal_patroli")
@Where(clause = "deleted_date is null")
public class SchedulePatroli extends AbstractDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Date startDate;

    private Date endDate;

    @ManyToOne
    private PointItem pointItem;
}
