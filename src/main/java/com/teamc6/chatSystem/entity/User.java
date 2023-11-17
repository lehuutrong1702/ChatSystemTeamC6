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

    @Column(unique = true)
    private String userName;

    @Column()
    private String password;

<<<<<<< HEAD
    @ManyToMany(mappedBy = "blockers" , cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
=======
    @ManyToMany(mappedBy = "blockers" , cascade = CascadeType.ALL)
>>>>>>> 9c0df8543a39c9d5f1c9f1f4db83debd03c5181e
    private Set<User> blocking ;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name= "Block",
            joinColumns = {@JoinColumn(name="user_block_id")},
            inverseJoinColumns = {@JoinColumn(name="user_blocked_id")}
    )
    @JsonIgnore
    private Set<User> blockers;

<<<<<<< HEAD


    @ManyToMany(mappedBy = "members")
    @JsonIgnore
=======
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_group",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="group_id")}
    )
>>>>>>> 9c0df8543a39c9d5f1c9f1f4db83debd03c5181e
    private Set<GroupChat> groups ;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="admin_group",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="group_id")}
    )
    @JsonIgnore
    private Set<GroupChat> groupAdmins ;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_relationship",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="relationship_id")}
    )
    @JsonIgnore
    private Set<Relationship> relationships ;


    @OneToMany(mappedBy = "reportUser")
    @JsonIgnore
    private Set<ReportSpam> reportSpams ;

    @OneToMany(mappedBy = "sessionUser")
    @JsonIgnore
    private Set<UserActiveSession> userActiveSessions;

<<<<<<< HEAD


=======
>>>>>>> 9c0df8543a39c9d5f1c9f1f4db83debd03c5181e
}
