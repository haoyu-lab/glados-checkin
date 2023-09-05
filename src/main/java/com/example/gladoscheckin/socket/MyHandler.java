package com.example.gladoscheckin.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;

@Slf4j
public class MyHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 连接建立后执行的操作
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 处理收到的消息
        log.info(message.toString());

        // 创建要发送的消息
        String responseMessage = "收到收到！";
        TextMessage textMessage = new TextMessage(responseMessage);

        // 使用WebSocketSession发送消息给客户端
        session.sendMessage(textMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 处理传输错误
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 连接关闭后执行的操作
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}