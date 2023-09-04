package com.example.gladoscheckin.socket;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理收到的WebSocket消息
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);
        // 可以在这里处理消息并发送响应
    }
}