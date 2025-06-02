package net.huaerd.Websocket;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class PathParamInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest req = servletRequest.getServletRequest();
            String uri = req.getRequestURI(); // 例如：/ws/chat/1
            String[] parts = uri.split("/");
            if (parts.length >= 4) {
                try {
                    long channelId = Long.parseLong(parts[3]);
                    String senderStr = req.getParameter("sender");
                    if (senderStr != null) {
                        long senderId = Long.parseLong(senderStr);
                        attributes.put("channel_id", channelId);
                        attributes.put("sender", senderId);
                        return true;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        // no-op
    }
}