package com.teamc6.chatSystem.repository;

import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserActiveSessionRepository extends JpaRepository<UserActiveSession,Long> {
    @Query("SELECT uas.sessionUser FROM UserActiveSession uas WHERE uas.timeActive >= :startTime AND uas.timeActive <= :endTime")
    List<User> listUserActive(Date startTime, Date endTime);

    @Query("SELECT uas FROM UserActiveSession uas WHERE uas.timeActive >= :startTime AND uas.timeActive <= :endTime")
    List<UserActiveSession> listSession(Date startTime, Date endTime);

}
