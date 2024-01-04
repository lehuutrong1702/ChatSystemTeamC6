package com.teamc6.chatSystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "user")
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


    @ManyToMany(mappedBy = "blockers", cascade = {
            //  CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    }, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> blocking;

    @ManyToMany(cascade = {
            //  CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(name = "Block",
            joinColumns = {@JoinColumn(name = "user_blocked_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_block_id")}
    )
    @JsonIgnore
    private Set<User> blockers;


    @ManyToMany(mappedBy = "members",cascade = {
          //  CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
        //    CascadeType.REMOVE
    })
    @JsonIgnore
    private Set<GroupChat> groups;

    @ManyToMany(mappedBy = "admins",cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
        //    CascadeType.REMOVE
    })
    @JsonIgnore
    private Set<GroupChat> groupAdmins;

    @ManyToMany(mappedBy = "users", cascade=CascadeType.REMOVE)
    @JsonIgnore
    private Set<Relationship> relationships;


    @OneToMany(mappedBy = "reportUser",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ReportSpam> reportSpams;

    @OneToMany(mappedBy = "sessionUser",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserActiveSession> userActiveSessions;

    @Column(name = "role")
    private String role;

    @Column(name = "active")
    private boolean active = true;
}

