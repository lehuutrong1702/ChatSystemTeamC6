package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.record.Connection;

import java.util.List;
import java.util.Set;

public interface GroupChatService {
    List<GroupChat> filterName(String name);
    List<GroupChat> sortByName();
    List<GroupChat> sortByCreateDay();
    Set<User> findAllMember(Long id);
    Set<User> findAllAdmin(Long id);

    GroupChat findByName(String name);
    GroupChat save(GroupChat g);
    GroupChat findById(Long ID);
    void updateNameGroup(GroupChat groupChat, String name);

    GroupChat addMember(Long groupID, Long memberId);

    GroupChat addAdmin(Long groupID, Long adminId);

    // Socket server
    Connection getConnection(Long id);
}
