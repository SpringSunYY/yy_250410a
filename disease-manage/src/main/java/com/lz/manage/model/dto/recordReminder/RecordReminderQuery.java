package com.lz.manage.model.dto.recordReminder;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.RecordReminder;

/**
 * 病历提醒Query对象 tb_record_reminder
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class RecordReminderQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 提醒编号
     */
    private Long reminderId;

    /**
     * 关联病历
     */
    private Long recordId;

    /**
     * 提醒时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date remindTime;

    /**
     * 提醒内容
     */
    private String message;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 已读（0否 1是）
     */
    private String isRead;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param recordReminderQuery 查询对象
     * @return RecordReminder
     */
    public static RecordReminder queryToObj(RecordReminderQuery recordReminderQuery) {
        if (recordReminderQuery == null) {
            return null;
        }
        RecordReminder recordReminder = new RecordReminder();
        BeanUtils.copyProperties(recordReminderQuery, recordReminder);
        return recordReminder;
    }
}
