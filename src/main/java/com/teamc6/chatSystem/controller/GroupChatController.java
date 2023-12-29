package com.teamc6.chatSystem.controller;


import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.model.Connection;
import com.teamc6.chatSystem.model.MessageObj;
import com.teamc6.chatSystem.service.ConnectionService;
import com.teamc6.chatSystem.service.GroupChatService;
import com.teamc6.chatSystem.service.MessageService;
import com.teamc6.chatSystem.serviceImpl.MessageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/groups")
@AllArgsConstructor
public class GroupChatController {
    private GroupChatService groupChatService;
    private ConnectionService connectionService;
    private MessageService messageService;
    @GetMapping("/search/{group_name}")
    public Page<GroupChat> filterByGroupName(@PathVariable("group_name") String group_name,
                                             @RequestParam(value = "page" ,defaultValue = "0") int page,
                                             @RequestParam(value = "size",defaultValue = "5") int perPage){
        Pageable pageable = PageRequest.of(page,perPage);
        System.out.println(group_name);
        return groupChatService.filterName(group_name, pageable);
    }

    @GetMapping()
    public Page<GroupChat> findAll( @RequestParam(value = "page" ,defaultValue = "0") int page,
                                             @RequestParam(value = "size",defaultValue = "5") int perPage){
        Pageable pageable = PageRequest.of(page,perPage);
        return groupChatService.findAll(pageable);
    }

    @GetMapping("/sortname")
    public Page<GroupChat> sortName(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                    @RequestParam(value = "size",defaultValue = "5") int perPage){
        Pageable pageable = PageRequest.of(page,perPage);
        return groupChatService.sortByName(pageable);
    }
    @GetMapping("/sorttimecreate")
    public Page<GroupChat> sortTimeCreate(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                    @RequestParam(value = "size",defaultValue = "5") int perPage){
        Pageable pageable = PageRequest.of(page,perPage);
        return groupChatService.sortByCreateDay(pageable);
    }
    @GetMapping("{id}")
    public ResponseEntity<GroupChat> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(groupChatService.findById(id),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
        return new ResponseEntity<>(groupChatService.delete(id),HttpStatus.OK);
    }

    @GetMapping("{id}/connection")
    public Connection getConnection(@PathVariable("id") Long id){ return connectionService.getConnection(id); }


    @GetMapping("{id}/members")
    public Set<User> findAllMembers(@PathVariable("id") Long id){
        System.out.println(id);return groupChatService.findAllMember(id);
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

    @GetMapping("/{id}/messages")
    public Page<Message> getOldMessages(@PathVariable("id") Long groupID,
                                                     @RequestParam(value = "page" ,defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int perPage){
        Pageable pageable = PageRequest.of(page,perPage);
        System.out.println(groupID);
        return groupChatService.findAllMessage(groupID,pageable);
    }
    @DeleteMapping("/{id}/messages")
    public void clearMessageHistory(@PathVariable("id") Long groupID){
        messageService.clearAll(groupID);
    }

    @GetMapping("/{id}/text-search")
    public List<Message> searchInChat(@PathVariable("id") long groupID,
                                      @RequestParam(value = "search", defaultValue = "") String search){
        return messageService.searchMessageInChat(groupID,search);
    }


    @PutMapping("/{id}/{name}")
    public ResponseEntity<GroupChat> rename(@PathVariable("id") long groupID, @PathVariable("name") String name){
        return new ResponseEntity<GroupChat>(groupChatService.rename(groupID, name), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}/members/{member_id}")
    public   ResponseEntity<GroupChat> deleteMembers(@PathVariable("id") long groupID, @PathVariable("member_id") long memberId) {
        return new ResponseEntity<GroupChat>(groupChatService.deleteMember(groupID,memberId),HttpStatus.ACCEPTED);
    }
}
