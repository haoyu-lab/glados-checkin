package com.example.gladoscheckin.pushsend.service;

import com.example.gladoscheckin.pushsend.pojo.PushMessage;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.pushsend.service
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/9/814:43
 */
public interface PushService {

    int pushWeChat(PushMessage pushMessage) throws Exception;

    int pushWeChatHtml(PushMessage pushMessage) throws Exception;
}
