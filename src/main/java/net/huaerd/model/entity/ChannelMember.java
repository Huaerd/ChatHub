package net.huaerd.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "channel_members")
@Data
public class ChannelMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long channelId;
    private long userId;
    private LocalDateTime joinTime;
}
