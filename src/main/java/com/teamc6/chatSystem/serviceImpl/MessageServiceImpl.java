package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.repository.MessageRepository;
import com.teamc6.chatSystem.repository.MessageRepository;
import com.teamc6.chatSystem.service.MessageService;
import com.teamc6.chatSystem.utils.EmailUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository ;
    @Override
    public Page<Message> filterByGroupChatID(long groupChatID, Pageable pageable) {
        return messageRepository.findByGroupChatId(groupChatID, pageable);
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
