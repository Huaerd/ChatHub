package net.huaerd.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "channel_messages")
@Data
public class ChannelMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long channelId;
    private long sender;
    private String content;
    private LocalDateTime sendTime;
}