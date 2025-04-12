package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.RecordReminder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 病历提醒Mapper接口
 * 
 * @author yy
 * @date 2025-04-12
 */
public interface RecordReminderMapper extends BaseMapper<RecordReminder>
{
    /**
     * 查询病历提醒
     * 
     * @param reminderId 病历提醒主键
     * @return 病历提醒
     */
    public RecordReminder selectRecordReminderByReminderId(Long reminderId);

    /**
     * 查询病历提醒列表
     * 
     * @param recordReminder 病历提醒
     * @return 病历提醒集合
     */
    public List<RecordReminder> selectRecordReminderList(RecordReminder recordReminder);

    /**
     * 新增病历提醒
     * 
     * @param recordReminder 病历提醒
     * @return 结果
     */
    public int insertRecordReminder(RecordReminder recordReminder);

    /**
     * 修改病历提醒
     * 
     * @param recordReminder 病历提醒
     * @return 结果
     */
    public int updateRecordReminder(RecordReminder recordReminder);

    /**
     * 删除病历提醒
     * 
     * @param reminderId 病历提醒主键
     * @return 结果
     */
    public int deleteRecordReminderByReminderId(Long reminderId);

    /**
     * 批量删除病历提醒
     * 
     * @param reminderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRecordReminderByReminderIds(Long[] reminderIds);
}
