package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.record.Connection;
import com.teamc6.chatSystem.repository.GroupChatRepository;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.GroupChatService;
import com.teamc6.serverSocket.ChatServer;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class GroupChatServiceImpl implements GroupChatService {
    private GroupChatRepository groupChatRepository;
    private UserRepository userRepository;
    @Override
    public List<GroupChat> filterName(String name) {
        return groupChatRepository.filterName(name);
    }

    @Override
    public List<GroupChat> sortByName() {
        return groupChatRepository.sortOrderByNameAsc();
        //return null;
    }

    @Override
    public List<GroupChat> sortByCreateDay() {
        return groupChatRepository.sortOrderByCreateDateAsc();
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
    public Connection getConnection(Long id) {
        Optional<GroupChat> optional = groupChatRepository.findById(id);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("GroupChat", "Group chat connection", id);
        }
        ChatServer chatServer = optional.get().server;
        if(chatServer == null){ // First connection
            Thread thread = null;
            try {
                chatServer = new ChatServer(new ServerSocket(0));
                thread = new Thread(chatServer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            thread.start();
        }
        return chatServer.getConnection();
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
