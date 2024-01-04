package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.Relationship;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.UserService;
import com.teamc6.chatSystem.utils.EmailUtils;
import com.teamc6.chatSystem.validation.Email.EmailChecking;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private EmailChecking emailChecking;


    public User save(User u) {
        var optional = userRepository.findById(u.getUserId());

        if(optional.isPresent())
        {
            throw new ResourceNotAcceptableExecption("User", "username", u.getUserId());
        }
        //u.setPassword(PasswordGenerator.generatePassword());
        System.out.println("password: " + u.getPassword());
        return userRepository.saveAndFlush(u);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    @Override
    public User update(User u, Long id) {
        var optional = userRepository.findById(id);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", u.getUserId());
        }
        User existUser = optional.get();
        existUser.setFullName(u.getFullName());
        existUser.setPassword(u.getPassword());
        existUser.setUserName(u.getUserName());
        existUser.setEmail(u.getEmail());
        existUser.setBirthDay(u.getBirthDay());

        return userRepository.saveAndFlush(existUser);
    }

    @Override
    public void setActive(Long id, boolean isActive) {
        var optional = userRepository.findById(id);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", id);
        }
        User existUser = optional.get();
        existUser.setActive(isActive);

        userRepository.saveAndFlush(existUser);
    }

    @Override
    public User findById(Long ID) {
        Optional<User> optional = userRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ", ID);
        }
//        System.out.println(optional.get().getRelationships().toString());
        return optional.get();
    }

    @Override

    public User findByUserName(String userName) {
        Optional<User> optional = userRepository.findByUsername(userName);
        if(!optional.isPresent())
        {

            throw new ResourceNotFoundException("User", "User Name: ", userName);
        }

        return optional.get();
    }

    @Override
    public Boolean delete(User u) {
        Optional<User> optional = userRepository.findById(u.getUserId());
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ", u.getUserId());
        }
        userRepository.delete(optional.get());
        return true;
    }

    @Override
    public Boolean deleteById(Long ID) {
        Optional<User> optional = userRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ",ID);
        }
        userRepository.delete(optional.get());
        return true;
    }


    @Override
    public Set<GroupChat> findAllGroups(Long ID) {
        Optional<User> optional = userRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ",ID);
        }
        System.out.println("size= " + optional.get().getGroups().size());
        System.out.println(optional.get().getGroups());
        return  optional.get().getGroups();
    }
    public Page<GroupChat> filterByGroupName(Long userId, String groupName, Pageable pageable){
        Optional<User> optional = userRepository.findById(userId);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ",userId);
        }
        Set<GroupChat> groupChats =  optional.get().getGroups();
        System.out.println(groupChats);
        List<GroupChat> list = new ArrayList<GroupChat>();
        list.addAll(groupChats);
        Page<GroupChat> page = new PageImpl<GroupChat>(list,pageable,groupChats.size());
        return page;
    }

    @Override
    public Set<UserActiveSession> findAllUserActiveSessions(Long ID) {
        Optional<User> optional = userRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user: ",ID);
        }
        return  optional.get().getUserActiveSessions();
    }
    @Override
    public Set<User> findAllFriends(Long ID) {
        var optional = userRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", ID);
        }
        Set<Relationship> relationshipSet = optional.get().getRelationships();
        Set<User> friendList = new HashSet<User>();
        for(Relationship row: relationshipSet)
        {
            System.out.println("group id = " + row.getId());
            if(row.getName().equals("friend")){
                friendList.addAll(row.getUsers());
                System.out.println(friendList.size());
            }

        }
        friendList.remove(optional.get());
        return friendList;
    }

    @Override
    public Page<User> filterFriendByName(Long ID, String Name, Pageable pageable) {
        Set<User> userSet = findAllFriends(ID);
        List<User> filteredFriend = new ArrayList<>();
        for (User user: userSet)
        {
            if(user.getUserName().contains(Name))
            {
                filteredFriend.add(user);
            }
        }
        Page<User> userPage = new PageImpl<>(filteredFriend, pageable, filteredFriend.size());
        return userPage;
    }

    @Override
    public Page<User> filterByName(String name, Pageable pageable) {
//       return userRepository.findByUsername(name,pageable);
        return userRepository.findByUsername(name,pageable);
    }

    @Override
    public Page<User> findAllBlockers(Long userId, Pageable pageable) {
        var optional = userRepository.findById(userId);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", userId);
        }
        Set<User> blockers =  optional.get().getBlockers();
     //   System.out.println(groupChats);
        List<User> list = new ArrayList<User>();
        list.addAll(blockers);
        Page<User> page = new PageImpl<User>(list,pageable,blockers.size());
        return page;
    }

    @Override
    public Page<User> findAllBlocking(Long userId, Pageable pageable) {
        var optional = userRepository.findById(userId);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", userId);
        }
        Set<User> blocking =  optional.get().getBlocking();
        //   System.out.println(groupChats);
        List<User> list = new ArrayList<User>();
        list.addAll(blocking);
        Page<User> page = new PageImpl<User>(list,pageable,blocking.size());
        return page;
    }

    @Override
    public Boolean blockById(Long ID1, Long ID2) {
        var optional1 = userRepository.findById(ID1);
        if(!optional1.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", ID1);
        }
        var optional2 = userRepository.findById(ID2);
        if(!optional2.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", ID2);
        }
        User user1 = optional1.get();
        User user2 = optional2.get();
        Set<User> blocking =  user1.getBlocking();
        blocking.add(user2);
        user1.setBlocking(blocking);

        Set<User> blockers =  user2.getBlockers();
        blockers.add(user1);
        user2.setBlockers(blockers);
        userRepository.saveAndFlush(user2);
        userRepository.saveAndFlush(user1);
        return true;

    }

    @Override
    public List<User> getByTime(Date start, Date end) {
        return  userRepository.listUserByTimeRegister(start,end);
    }
}
