package com.teamc6.chatSystem.model;

import com.teamc6.chatSystem.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public record InitObj(LocalDateTime creationDateTime, long userId, String userName) implements Serializable { }