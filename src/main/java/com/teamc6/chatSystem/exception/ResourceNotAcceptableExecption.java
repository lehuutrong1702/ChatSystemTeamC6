package com.teamc6.chatSystem.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
@Getter
@Setter
@AllArgsConstructor
public class ResourceNotAcceptableExecption extends RuntimeException{
    private String resourceName ;
    private String fieldName;
    private Object fieldValue;
}
