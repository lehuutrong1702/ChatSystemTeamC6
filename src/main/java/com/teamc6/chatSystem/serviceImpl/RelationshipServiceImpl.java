package com.teamc6.chatSystem.serviceImpl;

import com.teamc6.chatSystem.entity.Relationship;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.exception.ResourceNotFoundException;
import com.teamc6.chatSystem.repository.UserRepository;
import com.teamc6.chatSystem.service.RelationshipService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RelationshipServiceImpl implements RelationshipService {
    private UserRepository userRepository;
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

    @Override
    public Relationship addFriend(Long ID1, Long ID2) {
        System.out.println(ID1);
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

}
