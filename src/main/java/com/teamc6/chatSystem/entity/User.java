package com.teamc6.chatSystem.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;




@Entity
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private Date birthDay;

    @Column()
    private boolean gender;

    @Column()
    private String email;

    @Column(name = "time_register")
    private Date timeRegister;

    @Column(unique = true)
    private String userName;

    @Column()
    private String password;


    @ManyToMany(mappedBy = "blockers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> blocking;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Block",
            joinColumns = {@JoinColumn(name = "user_block_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_blocked_id")}
    )
    @JsonIgnore
    private Set<User> blockers;


    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private Set<GroupChat> groups;

    @ManyToMany(mappedBy = "admins")
    @JsonIgnore
    private Set<GroupChat> groupAdmins;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Relationship> relationships;


    @OneToMany(mappedBy = "reportUser")
    @JsonIgnore
    private Set<ReportSpam> reportSpams;

    @OneToMany(mappedBy = "sessionUser")
    @JsonIgnore
    private Set<UserActiveSession> userActiveSessions;

    @Column(name = "role")
    private String role;

    @Column(name = "active")
    private boolean active = true;
}

