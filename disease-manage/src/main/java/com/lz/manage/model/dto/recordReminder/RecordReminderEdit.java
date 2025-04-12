package com.lz.manage.model.dto.recordReminder;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.RecordReminder;

/**
 * 病历提醒Vo对象 tb_record_reminder
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class RecordReminderEdit implements Serializable {
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
     * 备注
     */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param recordReminderEdit 编辑对象
     * @return RecordReminder
     */
    public static RecordReminder editToObj(RecordReminderEdit recordReminderEdit) {
        if (recordReminderEdit == null) {
            return null;
        }
        RecordReminder recordReminder = new RecordReminder();
        BeanUtils.copyProperties(recordReminderEdit, recordReminder);
        return recordReminder;
    }
}
