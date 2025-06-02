package net.huaerd.model.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessage {
    private long channelId;
    private long sender;
    private String content;
}
