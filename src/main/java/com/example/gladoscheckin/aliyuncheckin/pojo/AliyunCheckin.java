package com.example.gladoscheckin.aliyuncheckin.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author hhy
 * @since 2023-10-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AliyunCheckin对象", description = "")
@TableName("ALIYUN_CHECKIN")
public class AliyunCheckin extends Model<AliyunCheckin> {
    @TableId("ALIYUN_TOKEN")
    @ApiModelProperty(value = "阿里云token")
    private String aliyunToken;

    @TableField("PUSH_PLUS_TOKEN")
    @ApiModelProperty(value = "推送通知token")
    private String pushPlusToken;

    @TableField("NAME")
    @ApiModelProperty(value = "姓名")
    private String name;


    @TableField(exist = false)
    private String AddTimeStr;

    @Override
    protected Serializable pkVal() {
        return this.aliyunToken;
    }

}
