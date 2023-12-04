package com.teamc6.chatSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="message")
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="send_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDateTime;
    @PrePersist
    protected void onCreate() {
        creationDateTime = LocalDateTime.now();
    }

    @Column(name="user_name", nullable = false)
    private String userName;

    @Column(name="message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name="group_id", nullable = false)
    private GroupChat groupChat;

    public void setGroupChat(GroupChat groupChat) {
        this.groupChat = groupChat;
    }

    public Message(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }
}
