package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.function.Predicate;

public interface MessageService {
    Page<Message> filterByGroupChatID(long groupChatID,Pageable pageable);
    Message save(Message u);
}
