package com.teamc6.chatSystem.controller;


import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.service.GroupChatService;
import com.teamc6.chatSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@AllArgsConstructor
public class GroupChatController {
    private GroupChatService groupChatService;
    @PostMapping()
    public ResponseEntity<GroupChat> addUser(@RequestBody GroupChat g){
        return new ResponseEntity<GroupChat>(groupChatService.save(g), HttpStatus.CREATED);
    }


   @PutMapping("/{group_id}/members/{member_id}")
   public   ResponseEntity<GroupChat> addMembers(@PathVariable("group_id") long groupID, @PathVariable("member_id") long memberId) {

        return new ResponseEntity<GroupChat>(groupChatService.addMember(groupID,memberId),HttpStatus.ACCEPTED);

    }
}
