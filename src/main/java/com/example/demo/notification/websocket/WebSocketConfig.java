package com.example.demo.notification.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 WebSocket 엔드포인트
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*") .withSockJS(); // SockJS는 선택
        // .setAllowedOrigins("http://localhost:3000")

    }
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독(서버 → 클라이언트) 경로
        registry.enableSimpleBroker("/topic");
        // 발행(클라이언트 → 서버) 경로
        registry.setApplicationDestinationPrefixes("/app");
	}

}
