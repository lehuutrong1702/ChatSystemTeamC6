package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.Relationship;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.repository.GroupChatRepository;
import com.teamc6.chatSystem.repository.RelationshipRepository;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.RelationshipService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class RelationshipServiceImpl implements RelationshipService {
    private UserRepository userRepository;
    private RelationshipRepository relationshipRepository;
    private GroupChatRepository groupChatRepository;
    @Override
    public Set<User> listFriend(Long ID) {
        var optional = userRepository.findById(ID);
        if(!optional.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", ID);
        }
        Set<Relationship> relationshipSet = optional.get().getRelationships();
        Set<User> friendList = new HashSet<User>();
        for(Relationship row: relationshipSet)
        {
            if(row.getName() == "friend"){
                friendList.addAll(row.getUsers());
            }
        }
        friendList.remove(optional.get());
        return friendList;
    }

    public Relationship getRelationShip(Long ID1, Long ID2){
        var optional1 = userRepository.findById(ID1);
        var optional2 = userRepository.findById(ID2);
        if(!optional1.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", ID1);
        }
        if(!optional2.isPresent())
        {
            throw new ResourceNotFoundException("User", "user", ID2);
        }

        Set<Relationship> relationships = optional1.get().getRelationships();
  //     System.out.println(relationships);
        for(Relationship relationship: relationships){
            System.out.println(relationship.toString());
           if(relationship.getUsers().contains(optional2.get())){
                return relationship;
            }
        }
        return null;
    }

    @Override
    public Relationship addFriend(Long ID1, Long ID2) {
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
        Set<User> friendSet = new HashSet<User>();
        friendSet.add(optional1.get());
        friendSet.add(optional2.get());
        Relationship relationship = new Relationship("friend",friendSet);

        GroupChat groupChat = new GroupChat();
        groupChat.setGroupName(optional1.get().getUserName() + " & " +optional2.get().getUserName());
        relationshipRepository.save(relationship);
       relationship.setGroupChat(groupChat);
      groupChatRepository.save(groupChat);

        return relationship;
    }

    @Override
    public Map<Long, Long> mutualFriend(Long ID) {
        Map<Long, Long> mutualSet = null;
        Set<User> myFriend = this.listFriend(ID);
        for(User row: myFriend)
        {
            Long count = Long.valueOf(0);
            count = this.listFriend(row.getUserId()).stream().count();
            mutualSet.put(row.getUserId(), count);
        }
        return mutualSet;
    }

    @Override
    public Relationship deleteFriend(Long ID1, Long ID2) {
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

        Set<Relationship> user1Relationship = user1.getRelationships();
        Set<Relationship> user2Relationship = user2.getRelationships();

        Relationship friendList = null;

        for (Relationship relationship : user1Relationship)
        {
            if (relationship.getName().equals("friend") && relationship.getUsers().contains(user2))
            {
                friendList = relationship;
                break;
            }
        }
        System.out.println(friendList);

        if (friendList != null)
        {
            user1Relationship.remove(friendList);
            user2Relationship.remove(friendList);

            userRepository.saveAndFlush(user1);
            userRepository.saveAndFlush(user2);

            relationshipRepository.delete(friendList);
        }
        return friendList   ;
    }

}
