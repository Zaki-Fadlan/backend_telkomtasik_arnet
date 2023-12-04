package com.telkomtasik.arnet.model.patrol;

import com.telkomtasik.arnet.model.AbstractDate;
import com.telkomtasik.arnet.model.Status;
import com.telkomtasik.arnet.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "p_laporan_patroli")
@Where(clause = "deleted_date is null")
public class ReportPatroli extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private SchedulePatroli schedulePatroli;
    
    @ManyToOne
    private Status status;

    private String keterangan;
}
