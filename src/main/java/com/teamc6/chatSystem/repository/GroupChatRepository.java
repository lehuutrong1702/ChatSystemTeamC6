package com.teamc6.chatSystem.repository;

import com.teamc6.chatSystem.entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupChatRepository extends JpaRepository<GroupChat,Long> {
}
