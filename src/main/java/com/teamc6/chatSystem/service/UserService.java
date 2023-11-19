package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;

import java.util.List;
import java.util.Set;

public interface UserService {
    User save(User u);
    List<User> findAll();
    User update(User u, Long id);
    User findById(Long ID);
    User findByUserName(String userName);
    Boolean delete(User u);

    Boolean deleteById(Long ID);

    List<User> pagination(int page, int perpage);

}
