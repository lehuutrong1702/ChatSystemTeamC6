package com.teamc6.chatSystem.controller;

import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.entity.UserActiveSession;
import com.teamc6.chatSystem.exception.ResourceNotAcceptableExecption;
import com.teamc6.chatSystem.service.UserActiveSessionService;
import com.teamc6.chatSystem.utils.EmailUtils;
import com.teamc6.chatSystem.utils.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user-active-sessions")
@AllArgsConstructor
public class UserActiveSessionController {

    UserActiveSessionService userActiveSessionService;
    @PostMapping("{id}")
    public ResponseEntity<UserActiveSession> addSession(@PathVariable ("id") long userId
                                                        ){

        System.out.println("api");
        return new ResponseEntity<UserActiveSession>(userActiveSessionService.save(userId), HttpStatus.CREATED);
    }
    @PatchMapping("{id}")
    public ResponseEntity<UserActiveSession> updateSession(@PathVariable ("id") long sessionId
                                                        ){

        System.out.println("api");
        return new ResponseEntity<UserActiveSession>(userActiveSessionService.update(sessionId), HttpStatus.CREATED);
    }

//    @GetMapping()
//    public List<UserActiveSession> getAll(){
//        return userActiveSessionService.getAll();
//    }
    @GetMapping()
    public List<UserActiveSession> getByTime(
            @RequestParam(value = "start" ) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date start,
            @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date end) {
        return userActiveSessionService.getByTime(start,end);
    }



    @GetMapping("/users")
    public List<User> getUserByActiveTime(@RequestParam(value = "start" )  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date start,
                                          @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date end){
        return userActiveSessionService.findUserActiveByTime(start,end);
    }
}
