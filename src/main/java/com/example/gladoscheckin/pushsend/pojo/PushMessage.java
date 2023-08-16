package com.example.gladoscheckin.pushsend.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.pushsend.pojo
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/10/2616:07
 */
@Data
@Builder
public class PushMessage {
    /** 发送的消息 */
    private String message;
    /** 发送的标题 */
    private String title;

    /** 对应的服务 glados/metro */
    private String service;

    /** 对应的用户（邮箱或电话） */
    private String user;
}
