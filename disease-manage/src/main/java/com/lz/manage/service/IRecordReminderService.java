package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.RecordReminder;
import com.lz.manage.model.vo.recordReminder.RecordReminderVo;
import com.lz.manage.model.dto.recordReminder.RecordReminderQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 病历提醒Service接口
 * 
 * @author yy
 * @date 2025-04-12
 */
public interface IRecordReminderService extends IService<RecordReminder>
{
    //region mybatis代码
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
     * 批量删除病历提醒
     * 
     * @param reminderIds 需要删除的病历提醒主键集合
     * @return 结果
     */
    public int deleteRecordReminderByReminderIds(Long[] reminderIds);

    /**
     * 删除病历提醒信息
     * 
     * @param reminderId 病历提醒主键
     * @return 结果
     */
    public int deleteRecordReminderByReminderId(Long reminderId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param recordReminderQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<RecordReminder> getQueryWrapper(RecordReminderQuery recordReminderQuery);

    /**
     * 转换vo
     *
     * @param recordReminderList RecordReminder集合
     * @return RecordReminderVO集合
     */
    List<RecordReminderVo> convertVoList(List<RecordReminder> recordReminderList);
}
