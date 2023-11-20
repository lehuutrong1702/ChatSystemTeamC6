package com.teamc6.chatSystem.repository;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupChatRepository extends JpaRepository<GroupChat,Long> {
    @Query("SELECT g FROM GroupChat g WHERE g.groupName LIKE %:searchName%")
    List<GroupChat> filterName(@Param("searchName") String searchName);

    @Query("SELECT g FROM GroupChat g ORDER BY g.groupName ASC")
    List<GroupChat> sortOrderByNameAsc();

    @Query("SELECT g FROM GroupChat g ORDER BY g.timeCreate ASC")
    List<GroupChat> sortOrderByCreateDateAsc();

    @Query("SELECT g FROM GroupChat g WHERE g.groupName = :name")
    Optional<GroupChat> findByName(@Param("name") String username);
}
