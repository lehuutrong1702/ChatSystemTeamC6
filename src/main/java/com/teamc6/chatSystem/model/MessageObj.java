package com.teamc6.chatSystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public record MessageObj(LocalDateTime creationDateTime, String userName, String message ) implements Serializable { }