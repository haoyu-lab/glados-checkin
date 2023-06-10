package com.example.gladoscheckin.csdnrefresh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("CSDN_DETAIL")
@ApiModel(value = "CsdnDetail对象", description = "")
public class CsdnDetail extends Model<CsdnDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "链接ID")
    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "url地址")
    @TableField("PATH_URL")
    private String pathUrl;
}
