package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.repository.MessageRepository;
import com.teamc6.chatSystem.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository ;
    @Override
    public Page<Message> filterByGroupChatID(long groupChatID, Pageable pageable) {
        return messageRepository.findByGroupChatId(groupChatID, pageable);
    }
    @Override
    public List<Message> searchMessageInChat(long groupChatID, String search) {
        return messageRepository.searchMessagesInChat(groupChatID, search);
    }

    public Message save(Message message) {
        var optional = messageRepository.findById(message.getId());

        if(optional.isPresent())
        {
            throw new ResourceNotAcceptableExecption("Message", "messageID", message.getId());
        }
        return messageRepository.saveAndFlush(message);
    }
}
