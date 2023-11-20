package com.teamc6.chatSystem.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="relationship")
public class Relationship {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    public Relationship(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    @Column(name="relationship_name")
    private String name;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_relationship",
            joinColumns = {@JoinColumn(name="relationship_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    private Set<User> users;

}
