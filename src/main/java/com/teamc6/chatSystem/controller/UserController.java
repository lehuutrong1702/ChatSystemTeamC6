package com.teamc6.chatSystem.controller;


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
@RequestMapping("/api/v1/users")
@AllArgsConstructor

public class UserController {

    private  UserService userService;
    private PasswordEncoder passwordEncoder;
    private RelationshipService relationshipService;
    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User u){
           u.setPassword(passwordEncoder.encode(u.getPassword()));
        return new ResponseEntity<User>(userService.save(u), HttpStatus.CREATED);
    }


    @GetMapping()
    public Page<User> findAllUsers(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                   @RequestParam(value = "size",defaultValue = "5") int perPage) {

        Pageable pageable = PageRequest.of(page,perPage);
        return userService.findAll(pageable);
    }
    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") long id){
        System.out.println(id);
        return new ResponseEntity<User>(userService.findById(id),HttpStatus.OK);
    }



    @GetMapping("/search")
    public ResponseEntity<User> findByUsername(
            @RequestParam(value = "username",defaultValue = "") String username){
       // userService.findByUserName(username).getUserActiveSessions();

        return new ResponseEntity<User>(userService.findByUserName(username),HttpStatus.OK);
    }

    @GetMapping("/filter/{username}")
    public Page<User> filterByUsername(
            @PathVariable("username") String username,
            @RequestParam(value = "page" ,defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "5") int perPage){
        System.out.println(username);
        Pageable pageable = PageRequest.of(page,perPage);
        return userService.filterByName(username,pageable);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id){
        return new ResponseEntity<Boolean>(userService.deleteById(id),HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<User> update( @PathVariable("id")Long id,
                                               @RequestBody User u){
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return new ResponseEntity<User>(userService.update(u,id),HttpStatus.OK);
    }

    @GetMapping("{id}/groups")
    public Set<GroupChat> findAllGroup(@PathVariable ("id") Long id){
        return userService.findAllGroups(id);
    }

    @GetMapping("{id}/friends")
    public Set<User> findAllFriends(@PathVariable("id") Long id){

        return userService.findAllFriends(id);
    }

    @GetMapping("{id}/user-active-sessions")
    public Set<UserActiveSession> findAllUserActiveSessions(@PathVariable("id") Long id){
        return userService.findAllUserActiveSessions(id);
    }

    @PostMapping("{id1}/friends/{id2}")
    public ResponseEntity<Relationship> addFriend(@PathVariable ("id1") Long id1,
                                                  @PathVariable("id2") Long id2){
        return new ResponseEntity<Relationship>(relationshipService.addFriend(id1,id2),HttpStatus.CREATED);
    }
}
