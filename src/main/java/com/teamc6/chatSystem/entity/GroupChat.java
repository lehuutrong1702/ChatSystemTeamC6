package com.teamc6.chatSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_group",
            joinColumns = {@JoinColumn(name="group_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    @JsonIgnore
    private Set<User> members ;

    public void addMember(User u){

        if(members == null){
            members = new HashSet<>();
        }
        members.add(u);
    }
    public void removeMember(User user) {
        if (members != null) {
            members.remove(user);
        }
    }
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="admin_group",
            joinColumns = {@JoinColumn(name="group_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    private Set<User> admins;

    public void addAdmin(User u){
        if(admins == null){
           admins = new HashSet<>();
        }
        admins.add(u);}

    @JsonIgnore
    @OneToMany(mappedBy="groupChat", cascade = CascadeType.ALL)
    @OrderBy("creationDateTime desc ")
    private List<Message> items;


    @JsonIgnore
    @OneToOne(mappedBy = "groupChat",fetch = FetchType.LAZY)
    Relationship relationship;

}