package com.teamc6.chatSystem.controller;


import com.teamc6.chatSystem.entity.*;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.service.RelationshipService;
import com.teamc6.chatSystem.service.ReportSpamService;
import com.teamc6.chatSystem.service.UserActiveSessionService;
import com.teamc6.chatSystem.service.UserService;
import com.teamc6.chatSystem.utils.EmailUtils;
import com.teamc6.chatSystem.utils.PasswordGenerator;
import com.teamc6.chatSystem.validation.Email.EmailChecking;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor

public class UserController {

    private EmailChecking emailChecking;
    private  UserService userService;
    private PasswordEncoder passwordEncoder;
    private RelationshipService relationshipService;
    private ReportSpamService reportSpamService;
    private UserActiveSessionService userActiveSessionService;
    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User u){
        u.setPassword(PasswordGenerator.generatePassword());
        u.setTimeRegister(new Date());
        System.out.println("password: " + u.getPassword());
        if(emailChecking.check(u.getEmail()) == true)
        {
              Thread emailThread = new Thread(() -> EmailUtils.getInstance().sendPassword(u.getEmail(), u.getPassword()));
              emailThread.start();
        }
        else {
            throw new ResourceNotAcceptableExecption("User", "email", u.getEmail());
        }
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
        return new ResponseEntity<User>(userService.findById(id),HttpStatus.OK);
    }



    @GetMapping("/search")
    public ResponseEntity<User> findByUsername(
            @RequestParam(value = "username",defaultValue = "") String username){

        System.out.println("find by username");
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

    @PostMapping("{id}/active/{flag}")
    public void setActive(@PathVariable("id") long id, @PathVariable("flag") boolean isActive){
//        System.out.println(isActive);
        userService.setActive(id, isActive);
    }

    @GetMapping("{id}/groups")
    public Set<GroupChat> findAllGroup(@PathVariable ("id") Long id){
        return userService.findAllGroups(id);
    }

    @GetMapping("{id}/groups/{name}")
    public Page<GroupChat> filterByGroupName(@PathVariable ("id") Long userId,
                                             @PathVariable("name") String groupName,
                                             @RequestParam(value = "page" ,defaultValue = "0") int page,
                                             @RequestParam(value = "size",defaultValue = "5") int perPage)
    {
        Pageable pageable = PageRequest.of(page,perPage);
        return userService.filterByGroupName(userId,groupName,pageable);

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
    @GetMapping("{id1}/friends/{id2}")
    public ResponseEntity<Relationship> getRelationship(@PathVariable ("id1") Long id1,
                                                        @PathVariable("id2") Long id2) {
        return new ResponseEntity<Relationship>(relationshipService.getRelationShip(id1,id2),HttpStatus.OK);
    }
    @GetMapping("{id1}/friends/{id2}/group-chat")
    public ResponseEntity<GroupChat> getP2PGroupChat(@PathVariable ("id1") Long id1,
                                                        @PathVariable("id2") Long id2) {
        return new ResponseEntity<GroupChat>(relationshipService.getRelationShip(id1,id2).getGroupChat(),HttpStatus.OK);
    }

    @DeleteMapping("{id1}/friends/{id2}")
    public void delete(@PathVariable("id1") Long id1,
                                          @PathVariable("id2") Long id2){
        relationshipService.deleteFriend(id1, id2);
    }

    @GetMapping("{id}/blockers")
    public Page<User> getBlockers(
            @PathVariable("id") Long id,
            @RequestParam(value = "page" ,defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "5") int perPage){
     //   System.out.println(username);
        Pageable pageable = PageRequest.of(page,perPage);
        return userService.findAllBlockers(id,pageable);
    }
    @GetMapping("{id}/blocking")
    public Page<User> getBlocking(
            @PathVariable("id") Long id,
            @RequestParam(value = "page" ,defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "5") int perPage){
        //   System.out.println(username);
        Pageable pageable = PageRequest.of(page,perPage);
        return userService.findAllBlocking(id,pageable);
    }

    @PostMapping("{id1}/block/{id2}")
    public ResponseEntity<Boolean> block(@PathVariable("id1") Long id1 ,@PathVariable("id2") long id2 ){
        return new ResponseEntity<Boolean>(userService.blockById(id1,id2),HttpStatus.OK);
    }

    @PostMapping("{id}/spams")
    public ResponseEntity<ReportSpam> spam(@PathVariable ("id") Long id ){
        return new ResponseEntity<ReportSpam>(reportSpamService.save(id),HttpStatus.CREATED);
    }

    @GetMapping("{id}/spams")
    public Set<ReportSpam> getAllSpam(@PathVariable ("id") Long id){
        return reportSpamService.getByUserid(id);
    }
    @GetMapping("/time-register")
    public List<User> getByTimeRegister(
            @RequestParam(value = "start" ) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date start,
            @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date end) {
        return userService.getByTime(start,end);
    }

    @GetMapping("/{id}/online")
    public ResponseEntity<Boolean> isOnline(@PathVariable("id") long id){
        return new ResponseEntity<Boolean>(userActiveSessionService.isOnline(id),HttpStatus.OK);
    }
}
