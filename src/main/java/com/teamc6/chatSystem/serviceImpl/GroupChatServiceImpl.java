package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.repository.GroupChatRepository;
import com.teamc6.chatSystem.service.GroupChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class GroupChatServiceImpl implements GroupChatService {
    private GroupChatRepository groupChatRepository;
    @Override
    public List<GroupChat> filterName(String name) {
        return groupChatRepository.filterName(name);
    }

    @Override
    public List<GroupChat> sortByName() {
    //    return groupChatRepository.findAllByOrderByNameAsc();
        return null;
    }

    @Override
    public List<GroupChat> sortByCreateDay() {
        return null ;//groupChatRepository.findAllByOrderByCreateDateAsc();
    }

    @Override
    public Set<User> listMember(GroupChat groupChat) {
        Optional<GroupChat> optional = groupChatRepository.findById(groupChat.getId());
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat", groupChat.getId());
        }
        return optional.get().getUsers();
    }

    @Override
    public Set<User> listAdmin(GroupChat groupChat) {
        Optional<GroupChat> optional = groupChatRepository.findById(groupChat.getId());
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat", groupChat.getId());
        }
        return optional.get().getAdmins();
    }

    @Override
    public void updateNameGroup(GroupChat groupChat, String name) {
        Optional<GroupChat> optional = groupChatRepository.findById(groupChat.getId());
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat", groupChat.getId());
        }
        optional.get().setGroupName(name);
    }

    @Override
    public GroupChat findByName(String name)
    {
        Optional<GroupChat> optional = groupChatRepository.findByUsername(name);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat name: ", name);
        }
        return optional.get();
    }
    @Override
    public GroupChat findById(Long ID)
    {
        Optional<GroupChat> optional = groupChatRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat name: ", ID);
        }
        return optional.get();
    }
}
