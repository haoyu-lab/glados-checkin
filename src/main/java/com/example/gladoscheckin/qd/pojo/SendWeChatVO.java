package com.example.gladoscheckin.qd.pojo;

import lombok.Data;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.qd.pojo
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/9/811:00
 */
@Data
public class SendWeChatVO {
    private String token;
    private String title;
    private String content;
    private String template;

}
