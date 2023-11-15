package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.User;

import java.util.Date;
import java.util.List;

public interface NewRegisterService {
    List<User> findAllUser(Date dateStart, Date dateFinish);
    List<User> sortOrderByName(Date dateStart, Date dateFinish);
    List<User> sortOrderByCreateDate(Date dateStart, Date dateFinish);
    List<User> filterByName(Date dateStart, Date dateFinish, String name);
}
