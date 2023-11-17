package com.teamc6.chatSystem.service;

import com.teamc6.chatSystem.entity.Relationship;
import com.teamc6.chatSystem.entity.User;

import java.util.Map;
import java.util.Set;

public interface RelationshipService {
    Set<User> listFriend(Long ID);
    Relationship addFriend(Long ID1, Long ID2);

    Map<Long, Long> mutualFriend(Long ID);
}
