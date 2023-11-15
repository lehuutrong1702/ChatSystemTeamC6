package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.User;

import java.util.List;

public interface UserService {
    User save(User u);
    List<User> findAll();
    User updatePassWord(User u, String passWord);
    User findById(Long ID);
    User findByUserName(String userName);
    Boolean delete(User u);

}
