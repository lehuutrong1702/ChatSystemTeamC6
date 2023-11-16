package com.teamc6.chatSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name="full_name")
    private String fullName;

    @Column(name="birthday")
    private Date birthDay;

    @Column()
    private boolean gender;

    @Column()
    private String email;

    @Column(name="time_register")
    private Date timeRegister;

    @Column()
    private String userName;

    @Column()
    private String password;

    @ManyToMany(mappedBy = "blockers" , cascade = CascadeType.ALL)
    private Set<User> blocking ;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name= "Block",
            joinColumns = {@JoinColumn(name="user_block_id")},
            inverseJoinColumns = {@JoinColumn(name="user_blocked_id")}
    )
    private Set<User> blockers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_group",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="group_id")}
    )
    private Set<GroupChat> groups ;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="admin_group",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="group_id")}
    )
    private Set<GroupChat> groupAdmins;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_relationship",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="relationship_id")}
    )
    private Set<Relationship> relationships ;


    @OneToMany(mappedBy = "reportUser")
    private Set<ReportSpam> reportSpams ;
    @OneToMany(mappedBy = "sessionUser")
    private Set<UserActiveSession> userActiveSessions;

}
