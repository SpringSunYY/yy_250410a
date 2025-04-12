package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.RecordReminderMapper;
import com.lz.manage.model.domain.RecordReminder;
import com.lz.manage.service.IRecordReminderService;
import com.lz.manage.model.dto.recordReminder.RecordReminderQuery;
import com.lz.manage.model.vo.recordReminder.RecordReminderVo;

/**
 * 病历提醒Service业务层处理
 * 
 * @author yy
 * @date 2025-04-12
 */
@Service
public class RecordReminderServiceImpl extends ServiceImpl<RecordReminderMapper, RecordReminder> implements IRecordReminderService
{
    @Resource
    private RecordReminderMapper recordReminderMapper;

    //region mybatis代码
    /**
     * 查询病历提醒
     * 
     * @param reminderId 病历提醒主键
     * @return 病历提醒
     */
    @Override
    public RecordReminder selectRecordReminderByReminderId(Long reminderId)
    {
        return recordReminderMapper.selectRecordReminderByReminderId(reminderId);
    }

    /**
     * 查询病历提醒列表
     * 
     * @param recordReminder 病历提醒
     * @return 病历提醒
     */
    @Override
    public List<RecordReminder> selectRecordReminderList(RecordReminder recordReminder)
    {
        return recordReminderMapper.selectRecordReminderList(recordReminder);
    }

    /**
     * 新增病历提醒
     * 
     * @param recordReminder 病历提醒
     * @return 结果
     */
    @Override
    public int insertRecordReminder(RecordReminder recordReminder)
    {
        return recordReminderMapper.insertRecordReminder(recordReminder);
    }

    /**
     * 修改病历提醒
     * 
     * @param recordReminder 病历提醒
     * @return 结果
     */
    @Override
    public int updateRecordReminder(RecordReminder recordReminder)
    {
        return recordReminderMapper.updateRecordReminder(recordReminder);
    }

    /**
     * 批量删除病历提醒
     * 
     * @param reminderIds 需要删除的病历提醒主键
     * @return 结果
     */
    @Override
    public int deleteRecordReminderByReminderIds(Long[] reminderIds)
    {
        return recordReminderMapper.deleteRecordReminderByReminderIds(reminderIds);
    }

    /**
     * 删除病历提醒信息
     * 
     * @param reminderId 病历提醒主键
     * @return 结果
     */
    @Override
    public int deleteRecordReminderByReminderId(Long reminderId)
    {
        return recordReminderMapper.deleteRecordReminderByReminderId(reminderId);
    }
    //endregion
    @Override
    public QueryWrapper<RecordReminder> getQueryWrapper(RecordReminderQuery recordReminderQuery){
        QueryWrapper<RecordReminder> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = recordReminderQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long reminderId = recordReminderQuery.getReminderId();
        queryWrapper.eq( StringUtils.isNotNull(reminderId),"reminder_id",reminderId);

        Long recordId = recordReminderQuery.getRecordId();
        queryWrapper.eq( StringUtils.isNotNull(recordId),"record_id",recordId);

        Date remindTime = recordReminderQuery.getRemindTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginRemindTime"))&&StringUtils.isNotNull(params.get("endRemindTime")),"remind_time",params.get("beginRemindTime"),params.get("endRemindTime"));

        String message = recordReminderQuery.getMessage();
        queryWrapper.eq(StringUtils.isNotEmpty(message) ,"message",message);

        String isRead = recordReminderQuery.getIsRead();
        queryWrapper.eq(StringUtils.isNotEmpty(isRead) ,"is_read",isRead);

        return queryWrapper;
    }

    @Override
    public List<RecordReminderVo> convertVoList(List<RecordReminder> recordReminderList) {
        if (StringUtils.isEmpty(recordReminderList)) {
            return Collections.emptyList();
        }
        return recordReminderList.stream().map(RecordReminderVo::objToVo).collect(Collectors.toList());
    }

}
