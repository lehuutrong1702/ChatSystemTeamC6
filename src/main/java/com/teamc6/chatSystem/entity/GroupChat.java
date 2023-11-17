package com.teamc6.chatSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="group_chat")
public class GroupChat {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="group_name")
    private String groupName;

    @Column(name="time_create")
    Date timeCreate;

<<<<<<< HEAD
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_group",
            joinColumns = {@JoinColumn(name="group_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    private Set<User> members ;


    public void addMember(User u){
        members.add(u);
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "groupAdmins",fetch = FetchType.LAZY)
=======
    @ManyToMany(mappedBy = "groups",fetch = FetchType.EAGER)
    private Set<User> users ;

    @ManyToMany(mappedBy = "groupAdmins",fetch = FetchType.EAGER)
>>>>>>> 9c0df8543a39c9d5f1c9f1f4db83debd03c5181e
    private Set<User> admins;
}