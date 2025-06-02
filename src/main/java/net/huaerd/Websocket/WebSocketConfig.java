package net.huaerd.Websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler handler;
    private final PathParamInterceptor pathParamInterceptor;

    public WebSocketConfig(ChatWebSocketHandler handler, PathParamInterceptor interceptor) {
        this.handler = handler;
        this.pathParamInterceptor = interceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/ws/chat/{channelId}")
                .addInterceptors(pathParamInterceptor)
                .setAllowedOrigins("*");
    }
}