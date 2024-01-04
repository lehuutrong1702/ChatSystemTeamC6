package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;

import java.util.Date;
import java.util.List;

public interface UserActiveSessionService {
    List<User> findUserActiveByTime(Date startTime, Date endTime);

    List<User> sortOrderByName(Date startTime, Date endTime);

    List<User> sortOrderByCreateDate(Date startTime, Date endTime);

    List<User> filterByName(Date startTime, Date endTime, String Name);

    UserActiveSession save(Long userId);
    UserActiveSession update(Long sessionId);

    List<UserActiveSession> getAll();
    List<UserActiveSession> getByTime(Date start,Date end);

    boolean isOnline(Long userId);
}
