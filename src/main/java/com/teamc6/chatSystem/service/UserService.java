package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User u);
    List<User> findAllUser();
    User updatePassWord(User u, String passWord);
    User findById(Long ID);
    User findByUserName(String userName);
    Boolean deleteUser(User u);

}
