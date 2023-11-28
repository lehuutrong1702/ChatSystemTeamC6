package com.teamc6.chatSystem.controller;


import com.teamc6.chatSystem.api.GroupAPI;
import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.service.GroupChatService;
import com.teamc6.chatSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(GroupAPI.PATH)
@AllArgsConstructor
public class GroupChatController {
    private GroupChatService groupChatService;

    @GetMapping(GroupAPI.GET)
    public ResponseEntity<GroupChat> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(groupChatService.findById(id),HttpStatus.OK);
    }

    @GetMapping(GroupAPI.ALL_MEMBERS)
    public Set<User> findAllMembers(@PathVariable("id") Long id){
        return groupChatService.findAllMember(id);
    }

    @GetMapping(GroupAPI.ALL_ADMINS)
    public Set<User> findAllAdmins(@PathVariable("id") Long id){
        return groupChatService.findAllMember(id);
    }

    @PostMapping(GroupAPI.ADD)
    public ResponseEntity<GroupChat> addGroup(@RequestBody GroupChat g){
        return new ResponseEntity<GroupChat>(groupChatService.save(g), HttpStatus.CREATED);
    }

   @PutMapping(GroupAPI.ADD_MEMBER)
   public   ResponseEntity<GroupChat> addMembers(@PathVariable("id") long groupID, @PathVariable("member_id") long memberId) {
        return new ResponseEntity<GroupChat>(groupChatService.addMember(groupID,memberId),HttpStatus.ACCEPTED);
    }
}
