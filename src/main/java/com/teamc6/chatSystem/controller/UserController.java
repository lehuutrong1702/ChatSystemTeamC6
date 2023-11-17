package com.teamc6.chatSystem.controller;


import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;
import com.teamc6.chatSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor

public class UserController {

    private  UserService userService;

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User u){
        return new ResponseEntity<User>(userService.save(u), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") long id){
        return new ResponseEntity<User>(userService.findById(id),HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<User> findByUsername(@RequestParam(value = "username",defaultValue = "") String username){
       // userService.findByUserName(username).getUserActiveSessions();
        return new ResponseEntity<User>(userService.findByUserName(username),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id){
        return new ResponseEntity<Boolean>(userService.deleteById(id),HttpStatus.OK);

    }

    @PutMapping("{id}")
    public ResponseEntity<User> updatePassword( @PathVariable("id")Long id,
                                               @RequestBody User u){

        return new ResponseEntity<User>(userService.update(u,id),HttpStatus.OK);
    }


}
