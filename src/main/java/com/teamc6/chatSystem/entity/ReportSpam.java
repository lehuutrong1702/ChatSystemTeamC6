package com.teamc6.chatSystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name="report_spam")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportSpam {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Column(name="time_report")
    private Date timeReport;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User reportUser;
}
