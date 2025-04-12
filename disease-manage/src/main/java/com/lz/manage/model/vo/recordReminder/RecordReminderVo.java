package com.lz.manage.model.vo.recordReminder;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.RecordReminder;

/**
 * 病历提醒Vo对象 tb_record_reminder
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class RecordReminderVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 提醒编号
     */
    @Excel(name = "提醒编号")
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
     * 对象转封装类
     *
     * @param recordReminder RecordReminder实体对象
     * @return RecordReminderVo
     */
    public static RecordReminderVo objToVo(RecordReminder recordReminder) {
        if (recordReminder == null) {
            return null;
        }
        RecordReminderVo recordReminderVo = new RecordReminderVo();
        BeanUtils.copyProperties(recordReminder, recordReminderVo);
        return recordReminderVo;
    }
}
