package com.example.gladoscheckin.timingtask.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author hhy
 * @since 2023-02-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("CRON_TASK")
@ApiModel(value = "CronTask对象", description = "")
public class CronTask extends Model<CronTask> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")

    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")

    @TableField("TASK_NAME")
    private String taskName;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态")

    @TableField("TASK_STATUS")
    private String taskStatus;

    /**
     * cron表达式
     */
    @ApiModelProperty(value = "cron表达式")

    @TableField("CRON_EXPRESSION")
    private String cronExpression;

    /**
     * 标题关键字
     */
    @ApiModelProperty(value = "标题关键字")

    @TableField("SEARCH_KEY")
    private String searchKey;

    /**
     * 任务分组
     */
    @ApiModelProperty(value = "任务分组")

    @TableField("TASK_GROUP")
    private String taskGroup;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
