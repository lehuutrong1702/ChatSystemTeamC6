package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;


public interface GroupChatService {
    Page<GroupChat>  findAll(Pageable pageable);
    Page<GroupChat> filterName(String name, Pageable pageable);
    Page<GroupChat> sortByName(Pageable pageable);
    Page<GroupChat> sortByCreateDay(Pageable pageable);
    Set<User> findAllMember(Long id);
    Set<User> findAllAdmin(Long id);

    GroupChat findByName(String name);
    GroupChat save(GroupChat g);
    GroupChat findById(Long ID);
    void updateNameGroup(GroupChat groupChat, String name);

    GroupChat addMember(Long groupID, Long memberId);

    GroupChat addAdmin(Long groupID, Long adminId);

    boolean delete(Long groupID);
    // Socket server
    Page<Message> findAllMessage(Long groupID, Pageable pageable);
}
