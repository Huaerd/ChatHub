package net.huaerd.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "channels")
@Data
public class Channel {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String name;

    private LocalDateTime createTime = LocalDateTime.now();
    private Integer memberCount;
    private String avatar;
    private String description;
    private long owner;
}
