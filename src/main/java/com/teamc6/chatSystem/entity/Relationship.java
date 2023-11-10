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

    @Column(name="relationship_name")
    private String name;

    @ManyToMany(mappedBy = ("relationships"), fetch = FetchType.EAGER)
    private Set<User> users;
}
