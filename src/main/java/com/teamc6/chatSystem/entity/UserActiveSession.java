package com.teamc6.chatSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name="user_active_session")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserActiveSession {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Column(name="time_active")
    private Date timeActive;

    @Column(name="time_logout")
    private Date timeLogout;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User sessionUser ;
}
