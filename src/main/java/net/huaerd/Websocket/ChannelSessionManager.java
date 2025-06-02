package net.huaerd.Websocket;


import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelSessionManager {
    // channelId -> Set of sessions
    private static final Map<Long, Set<WebSocketSession>> channelSessions = new ConcurrentHashMap<>();

    public static void addSession(Long channelId, WebSocketSession session) {
        channelSessions.computeIfAbsent(channelId, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    public static void removeSession(Long channelId, WebSocketSession session) {
        Set<WebSocketSession> sessions = channelSessions.get(channelId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                channelSessions.remove(channelId);
            }
        }
    }

    public static Set<WebSocketSession> getSessions(Long channelId) {
        return channelSessions.getOrDefault(channelId, Collections.emptySet());
    }
}