package com.teamc6.chatSystem.controller;

import com.teamc6.chatSystem.entity.Relationship;
import com.teamc6.chatSystem.service.RelationshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/relationships")
@AllArgsConstructor
public class RelationshipController {
    private RelationshipService relationshipService;

    @PostMapping
    public ResponseEntity<Relationship> addFriend(@RequestParam(value = "id1" ,defaultValue = "") long Id1, @RequestParam(value = "id2",defaultValue = "") long Id2){

        return new ResponseEntity<Relationship>(relationshipService.addFriend(Id1,Id2), HttpStatus.CREATED);
    }

    // delete ...
}
