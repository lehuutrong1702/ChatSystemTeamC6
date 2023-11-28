package com.teamc6.chatSystem.controller;


import com.teamc6.chatSystem.api.UserAPI;
import com.teamc6.chatSystem.entity.GroupChat;
import com.teamc6.chatSystem.entity.Relationship;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;
import com.teamc6.chatSystem.service.RelationshipService;
import com.teamc6.chatSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(UserAPI.PATH)
@AllArgsConstructor

public class UserController {

    private  UserService userService;
    private PasswordEncoder passwordEncoder;
    private RelationshipService relationshipService;
    @PostMapping(UserAPI.ADD)
    public ResponseEntity<User> addUser(@RequestBody User u){
           u.setPassword(passwordEncoder.encode(u.getPassword()));
        return new ResponseEntity<User>(userService.save(u), HttpStatus.CREATED);
    }

//    @GetMapping()
//    public List<User> findAll(){
//        return userService.findAll();
//    }
    @GetMapping(UserAPI.ALL)
    public Page<User> findAllUsers(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                   @RequestParam(value = "size",defaultValue = "5") int perPage) {

        Pageable pageable = PageRequest.of(page,perPage);
        return userService.findAll(pageable);
    }
    @GetMapping(UserAPI.GET)
    public ResponseEntity<User> findById(@PathVariable("id") long id){
        return new ResponseEntity<User>(userService.findById(id),HttpStatus.OK);
    }
    @GetMapping(UserAPI.SEARCH)
    public ResponseEntity<User> findByUsername(
            @RequestParam(value = "username" ,defaultValue = "") String username){
       // userService.findByUserName(username).getUserActiveSessions();
        return new ResponseEntity<User>(userService.findByUserName(username),HttpStatus.OK);
    }

    @GetMapping(UserAPI.FILTER_USERNAME)
    public Page<User> filterByUsername(
            @RequestParam(value = "username" ,defaultValue = "") String username,
            @RequestParam(value = "page" ,defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "5") int perPage){

        Pageable pageable = PageRequest.of(page,perPage);
        return userService.filterByName(username,pageable);
    }

    @DeleteMapping(UserAPI.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id){
        return new ResponseEntity<Boolean>(userService.deleteById(id),HttpStatus.OK);
    }
    @PutMapping(UserAPI.UPDATE)
    public ResponseEntity<User> update( @PathVariable("id")Long id,
                                               @RequestBody User u){
        return new ResponseEntity<User>(userService.update(u,id),HttpStatus.OK);
    }

    @GetMapping(UserAPI.GROUPS)
    public Set<GroupChat> findAllGroup(@PathVariable ("id") Long id){
        return userService.findAllGroups(id);
    }

    @GetMapping(UserAPI.FRIENDS)
    public Set<User> findAllFriends(@PathVariable("id") Long id){
        return userService.findAllFriends(id);
    }

    @GetMapping(UserAPI.SESSIONS)
    public Set<UserActiveSession> findAllUserActiveSessions(@PathVariable("id") Long id){
        return userService.findAllUserActiveSessions(id);
    }

    @PostMapping(UserAPI.ADD_FRIENDS)
    public ResponseEntity<Relationship> addFriend(@PathVariable ("id1") Long id1,
                                                  @PathVariable("id2") Long id2){
        return new ResponseEntity<Relationship>(relationshipService.addFriend(id1,id2),HttpStatus.CREATED);
    }
}
