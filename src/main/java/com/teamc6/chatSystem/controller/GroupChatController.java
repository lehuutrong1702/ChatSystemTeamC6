package com.teamc6.chatSystem.controller;


import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.record.Connection;
import com.teamc6.chatSystem.service.GroupChatService;
import com.teamc6.chatSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/groups")
@AllArgsConstructor
public class GroupChatController {
    private GroupChatService groupChatService;

    @GetMapping("{id}")
    public ResponseEntity<GroupChat> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(groupChatService.findById(id),HttpStatus.OK);
    }


    @GetMapping("{id}/connection")
    public Connection getConnection(@PathVariable("id") Long id){ return groupChatService.getConnection(id); }


    @GetMapping("{id}/members")
    public Set<User> findAllMembers(@PathVariable("id") Long id){
        return groupChatService.findAllMember(id);
    }

    @GetMapping("{id}/admins")
    public Set<User> findAllAdmins(@PathVariable("id") Long id){
        return groupChatService.findAllMember(id);
    }

    @PostMapping()
    public ResponseEntity<GroupChat> addGroup(@RequestBody GroupChat g){
        return new ResponseEntity<GroupChat>(groupChatService.save(g), HttpStatus.CREATED);
    }

   @PostMapping("/{id}/members/{member_id}")
   public   ResponseEntity<GroupChat> addMembers(@PathVariable("id") long groupID, @PathVariable("member_id") long memberId) {
        return new ResponseEntity<GroupChat>(groupChatService.addMember(groupID,memberId),HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/admins/{admin_id}")
    public  ResponseEntity<GroupChat> addAdmins(@PathVariable("id") long groupID, @PathVariable("admin_id") long adminId){
        return new ResponseEntity<GroupChat>(groupChatService.addAdmin(groupID,adminId),HttpStatus.ACCEPTED);
    }
}
