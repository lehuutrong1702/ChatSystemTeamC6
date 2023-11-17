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

<<<<<<< HEAD
    @ManyToMany(mappedBy = ("relationships"))
    private Set<User> users;
=======
    @ManyToMany(mappedBy = ("relationships"), fetch = FetchType.EAGER)
    private Set<User>users ;
>>>>>>> 9c0df8543a39c9d5f1c9f1f4db83debd03c5181e
}
