package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;

import java.util.List;
import java.util.Set;

public interface GroupChatService {
    List<GroupChat> filterName(String name);
    List<GroupChat> sortByName();
    List<GroupChat> sortByCreateDay();
    Set<User> listMember(GroupChat groupChat);
    Set<User> listAdmin(GroupChat groupChat);

    GroupChat findByName(String name);

    GroupChat findById(Long ID);
    void updateNameGroup(GroupChat groupChat, String name);
}
