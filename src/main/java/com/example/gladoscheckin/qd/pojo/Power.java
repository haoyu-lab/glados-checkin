package com.example.gladoscheckin.qd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("power")
public class Power {
    /** 邮箱 */
    @TableId("EMAIL")
    private String email;
    /** cookie */
    @TableField("COOKIE")
    private String cookie;
    /** 推送加微信推送token */
    @TableField("PUSH_PLUS_TOKEN")
    private String pushPlusToken;
    /** 今日是否成功 */
    @TableField("IS_SUCCESS")
    private String isSuccess;
}
