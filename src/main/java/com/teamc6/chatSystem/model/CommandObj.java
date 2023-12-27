package com.teamc6.chatSystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CommandObj(LocalDateTime creationDateTime, long userId, String command ) implements Serializable { }