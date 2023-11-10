package com.teamc6.chatSystem.repository;

import com.teamc6.chatSystem.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipRepository extends JpaRepository<Relationship,Long> {
}
