package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 病历提醒对象 tb_record_reminder
 *
 * @author yy
 * @date 2025-04-12
 */
@TableName("tb_record_reminder")
@Data
public class RecordReminder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 提醒编号
     */
    @Excel(name = "提醒编号")
    @TableId(value = "reminder_id", type = IdType.ASSIGN_ID)
    private Long reminderId;

    /**
     * 关联病历
     */
    @Excel(name = "关联病历")
    private String recordName;
    private Long recordId;

    /**
     * 提醒时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提醒时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date remindTime;

    /**
     * 提醒内容
     */
    @Excel(name = "提醒内容")
    private String message;

    /**
     * 用户
     */
    @Excel(name = "用户")
    private String userName;
    private Long userId;

    /**
     * 已读（0否 1是）
     */
    @Excel(name = "已读", readConverterExp = "0=否,1=是")
    private String isRead;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
