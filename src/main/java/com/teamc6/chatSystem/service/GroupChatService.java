package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.record.Connection;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
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

    // Socket server

}
