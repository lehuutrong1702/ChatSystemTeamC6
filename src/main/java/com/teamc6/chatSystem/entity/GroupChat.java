package com.teamc6.chatSystem.entity;

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

    @ManyToMany(mappedBy = "groups",fetch = FetchType.EAGER)
    private Set<User> users ;

    @ManyToMany(mappedBy = "groupAdmins",fetch = FetchType.EAGER)
    private Set<User> admins;
}