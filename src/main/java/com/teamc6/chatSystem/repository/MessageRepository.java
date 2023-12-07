package com.teamc6.chatSystem.repository;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query("SELECT m FROM Message m WHERE m.groupChat = :groupId")
    Page<Message> findByGroupChatId(@Param("groupId") Long groupId, Pageable pageable);
}
