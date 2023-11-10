package com.teamc6.chatSystem.repository;

import com.teamc6.chatSystem.entity.UserActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActiveSessionRepository extends JpaRepository<UserActiveSession,Long> {
}
