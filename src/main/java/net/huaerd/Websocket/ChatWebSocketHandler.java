package net.huaerd.Websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nullable;
import net.huaerd.model.DTO.ChatMessage;
import net.huaerd.model.entity.ChannelMessage;
import net.huaerd.service.dao.ChannelService;
import net.huaerd.service.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Map<Long, WebSocketSession> users =  new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    //频道服务类
    @Autowired
    private ChannelService channelService;
    //用户服务类
    @Autowired
    private UserService userService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 示例：你已将 userId 和 channelId 通过拦截器存入 session attributes
        Long userId = (Long) session.getAttributes().get("sender");
        Long channelId = (Long) session.getAttributes().get("channel_id");

        users.put(userId, session);
        ChannelSessionManager.addSession(channelId, session);

        System.out.println("用户 " + userId + " 加入频道 " + channelId);
    }

    /**
     * @param session 当前发送消息的用户的 WebSocketSession
     * @param message 当前发送的消息
     */
    @Override
    public void handleMessage(@Nullable WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ChatMessage chatMessage = mapper.readValue(message.getPayload().toString(), ChatMessage.class);
        ChannelMessage res = new ChannelMessage();
        // 转化为ChannelMessage
        res.setChannelId(chatMessage.getChannelId());
        res.setSender(chatMessage.getSender());
        res.setContent(chatMessage.getContent());
        res.setSendTime(LocalDateTime.now());
        // 存储到数据库
        channelService.sendMessage(res);

        // 向当前频道的连接用户广播
        Set<WebSocketSession> sessions = ChannelSessionManager.getSessions(chatMessage.getChannelId());
        String broadcastJson = mapper.writeValueAsString(res);

        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(broadcastJson));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = (Long) session.getAttributes().get("sender");
        Long channelId = (Long) session.getAttributes().get("channel_id");

        users.remove(userId);
        ChannelSessionManager.removeSession(channelId, session);

        System.out.println("用户 " + userId + " 断开频道 " + channelId);
    }
}