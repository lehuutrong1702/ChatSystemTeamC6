package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface UserService {
    User save(User u);
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    User update(User u, Long id);
    User findById(Long ID);
    User findByUserName(String userName);

    Page<User> filterByName(String name,Pageable pageable);

    Boolean delete(User u);

    Set<GroupChat> findAllGroups(Long ID);

    Set<UserActiveSession> findAllUserActiveSessions(Long ID);

    Boolean deleteById(Long ID);

    Set<User> findAllFriends(Long Id);

    Page<GroupChat> filterByGroupName(Long userId, String groupName, Pageable pageable);

}
