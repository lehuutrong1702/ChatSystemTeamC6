package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.repository.GroupChatRepository;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.GroupChatService;

import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Service

public class GroupChatServiceImpl implements GroupChatService {
    private GroupChatRepository groupChatRepository;
    private UserRepository userRepository;


    public GroupChatServiceImpl(GroupChatRepository groupChatRepository, UserRepository userRepository) {
        this.groupChatRepository = groupChatRepository;
        this.userRepository = userRepository;

    }

    @Override
    public boolean delete(Long ID) {
        Optional<GroupChat> optional = groupChatRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat name: ", ID);
        }
        groupChatRepository.delete(optional.get());
        return true;
    }

    @Override
    public Page<GroupChat> findAll(Pageable pageable) {
        return groupChatRepository.findAll(pageable);
    }

    @Override
    public Page<GroupChat> filterName(String name, Pageable pageable) {
        return groupChatRepository.filterName(name, pageable);
    }

    @Override
    public Page<GroupChat> sortByName(Pageable pageable) {
        return groupChatRepository.sortOrderByNameAsc(pageable);
    }

    @Override
    public Page<GroupChat> sortByCreateDay(Pageable pageable) {
        return groupChatRepository.sortOrderByCreateDateAsc(pageable);
    }

    @Override
    public Set<User> findAllMember(Long id) {
        Optional<GroupChat> optional = groupChatRepository.findById(id);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat", id);
        }
        return optional.get().getMembers();
    }

    @Override
    public Set<User> findAllAdmin(Long id) {
        Optional<GroupChat> optional = groupChatRepository.findById(id);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat", id);
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
    public GroupChat addMember(Long groupID, Long memberId) {
        Optional<GroupChat> g = groupChatRepository.findById(groupID);
        if(!(g.isPresent())){
            throw new ResourceNotFoundException("Group chat" , "GroupID",groupID);

        }
        GroupChat groupChat = g.get();

        Optional<User> u = userRepository.findById(memberId);

        if(!(u.isPresent())){
            throw new ResourceNotFoundException("User","user id",groupID);
        }

        User user = u.get();

       groupChat.addMember(user);
        return  groupChatRepository.save(groupChat);

    }

    @Override
    public GroupChat addAdmin(Long groupID, Long adminId) {
        Optional<GroupChat> g = groupChatRepository.findById(groupID);
        if(!(g.isPresent())){
            throw new ResourceNotFoundException("Group chat" , "GroupID",groupID);

        }
        GroupChat groupChat = g.get();

        Optional<User> u = userRepository.findById(adminId);

        if(!(u.isPresent())){
            throw new ResourceNotFoundException("User","Admin id",groupID);
        }

        User user = u.get();

        groupChat.addAdmin(user);
        return  groupChatRepository.save(groupChat);
    }

    @Override
    public Page<Message> findAllMessage(Long groupID, Pageable pageable) {
        Optional<GroupChat> g = groupChatRepository.findById(groupID);
        if(g.isEmpty()){
            throw new ResourceNotFoundException("Group chat" , "GroupID",groupID);
        }
        GroupChat groupChat = g.get();
        List<Message> list = groupChat.getItems();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<Message> pageContent;
        try {
            pageContent = list.subList(start, end);
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
            pageContent = new ArrayList<>();
        }

        return new PageImpl<>(pageContent, pageable, list.size());
    }
    @Override
    public GroupChat rename(Long groupID, String name) {
        Optional<GroupChat> g = groupChatRepository.findById(groupID);
        if(!(g.isPresent())){
            throw new ResourceNotFoundException("Group chat" , "GroupID",groupID);

        }
        GroupChat groupChat = g.get();
        groupChat.setGroupName(name);
        return groupChatRepository.saveAndFlush(groupChat);
    }

    @Override
    public GroupChat deleteMember(Long groupID, Long memberId) {
        Optional<GroupChat> g = groupChatRepository.findById(groupID);
        if(!(g.isPresent())){
            throw new ResourceNotFoundException("Group chat" , "GroupID",groupID);

        }
        GroupChat groupChat = g.get();

        Optional<User> u = userRepository.findById(memberId);

        if(!(u.isPresent())){
            throw new ResourceNotFoundException("User","user id",groupID);
        }

        User user = u.get();
        groupChat.removeMember(user);

        // Lưu thay đổi vào cơ sở dữ liệu
        return groupChatRepository.saveAndFlush(groupChat);
    }


    @Override
    public GroupChat findByName(String name)
    {
        Optional<GroupChat> optional = groupChatRepository.findByName(name);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat name: ", name);
        }
        return optional.get();
    }

    @Override
    public GroupChat save(GroupChat g) {
        var optional = groupChatRepository.findById(g.getId());

        if(optional.isPresent())
        {
            throw new ResourceNotAcceptableExecption("Groupchat", "groupID", g.getId());
        }
        g.setTimeCreate(new Date());
        return groupChatRepository.saveAndFlush(g);
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
