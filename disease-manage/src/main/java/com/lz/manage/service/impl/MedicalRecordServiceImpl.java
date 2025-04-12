package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.DataBackup;
import com.lz.manage.model.domain.RecordReminder;
import com.lz.manage.model.domain.TemplateInfo;
import com.lz.manage.service.IDataBackupService;
import com.lz.manage.service.IRecordReminderService;
import com.lz.manage.service.ITemplateInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.MedicalRecordMapper;
import com.lz.manage.model.domain.MedicalRecord;
import com.lz.manage.service.IMedicalRecordService;
import com.lz.manage.model.dto.medicalRecord.MedicalRecordQuery;
import com.lz.manage.model.vo.medicalRecord.MedicalRecordVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 病历信息Service业务层处理
 *
 * @author yy
 * @date 2025-04-12
 */
@Service
public class MedicalRecordServiceImpl extends ServiceImpl<MedicalRecordMapper, MedicalRecord> implements IMedicalRecordService {
    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private ITemplateInfoService templateInfoService;

    @Resource
    @Lazy
    private IRecordReminderService recordReminderService;

    @Resource
    private IDataBackupService dataBackupService;
    //region mybatis代码

    /**
     * 查询病历信息
     *
     * @param recordId 病历信息主键
     * @return 病历信息
     */
    @Override
    public MedicalRecord selectMedicalRecordByRecordId(Long recordId) {
        return medicalRecordMapper.selectMedicalRecordByRecordId(recordId);
    }

    /**
     * 查询病历信息列表
     *
     * @param medicalRecord 病历信息
     * @return 病历信息
     */
    @Override
    public List<MedicalRecord> selectMedicalRecordList(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = medicalRecordMapper.selectMedicalRecordList(medicalRecord);
        for (MedicalRecord info : medicalRecords) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
            TemplateInfo templateInfo = templateInfoService.selectTemplateInfoByTemplateId(info.getTemplateId());
            if (StringUtils.isNotNull(templateInfo)) {
                info.setTemplateName(templateInfo.getTemplateName());
            }
        }
        return medicalRecords;
    }

    /**
     * 新增病历信息
     *
     * @param medicalRecord 病历信息
     * @return 结果
     */
    @Override
    public int insertMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecord.setCreateBy(SecurityUtils.getUsername());
        medicalRecord.setDeptId(SecurityUtils.getDeptId());
        Date nowDate = DateUtils.getNowDate();
        medicalRecord.setCreateTime(nowDate);

        medicalRecordMapper.insertMedicalRecord(medicalRecord);
        //创建提醒
        RecordReminder recordReminder = new RecordReminder();
        recordReminder.setRecordId(medicalRecord.getRecordId());
        recordReminder.setRemindTime(nowDate);
        recordReminder.setMessage(StringUtils.format("您有新的病历--{}，请及时查看！", medicalRecord.getRecordTitle()));
        recordReminder.setUserId(medicalRecord.getUserId());
        recordReminder.setIsRead("0");
        return recordReminderService.insertRecordReminder(recordReminder);
    }

    /**
     * 修改病历信息
     *
     * @param medicalRecord 病历信息
     * @return 结果
     */
    @Override
    public int updateMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecord.setUpdateTime(DateUtils.getNowDate());
        return medicalRecordMapper.updateMedicalRecord(medicalRecord);
    }

    /**
     * 批量删除病历信息
     *
     * @param recordIds 需要删除的病历信息主键
     * @return 结果
     */
    @Override
    public int deleteMedicalRecordByRecordIds(Long[] recordIds) {
        List<MedicalRecord> list = this.list(new LambdaQueryWrapper<MedicalRecord>().in(MedicalRecord::getRecordId, (Object[]) recordIds));
        if (StringUtils.isNotEmpty(list)) {
            ArrayList<DataBackup> dataBackups = new ArrayList<>();
            Date nowDate = DateUtils.getNowDate();
            String username = SecurityUtils.getUsername();
            list.forEach(info -> {
                String jsonString = JSONObject.toJSONString(info);
                DataBackup dataBackup = new DataBackup();
                dataBackup.setBackupTime(nowDate);
                dataBackup.setBackupContent(jsonString);
                dataBackup.setCreateBy(username);
                dataBackups.add(dataBackup);
            });
            dataBackupService.saveBatch(dataBackups);
        }
        return medicalRecordMapper.deleteMedicalRecordByRecordIds(recordIds);
    }

    /**
     * 删除病历信息信息
     *
     * @param recordId 病历信息主键
     * @return 结果
     */
    @Override
    public int deleteMedicalRecordByRecordId(Long recordId) {
        return medicalRecordMapper.deleteMedicalRecordByRecordId(recordId);
    }

    //endregion
    @Override
    public QueryWrapper<MedicalRecord> getQueryWrapper(MedicalRecordQuery medicalRecordQuery) {
        QueryWrapper<MedicalRecord> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = medicalRecordQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long recordId = medicalRecordQuery.getRecordId();
        queryWrapper.eq(StringUtils.isNotNull(recordId), "record_id", recordId);

        Long userId = medicalRecordQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long templateId = medicalRecordQuery.getTemplateId();
        queryWrapper.eq(StringUtils.isNotNull(templateId), "template_id", templateId);

        String recordTitle = medicalRecordQuery.getRecordTitle();
        queryWrapper.like(StringUtils.isNotEmpty(recordTitle), "record_title", recordTitle);

        String recordImage = medicalRecordQuery.getRecordImage();
        queryWrapper.eq(StringUtils.isNotEmpty(recordImage), "record_image", recordImage);

        String content = medicalRecordQuery.getContent();
        queryWrapper.eq(StringUtils.isNotEmpty(content), "content", content);

        Long level = medicalRecordQuery.getLevel();
        queryWrapper.eq(StringUtils.isNotNull(level), "level", level);

        Long deptId = medicalRecordQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = medicalRecordQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        Date updateTime = medicalRecordQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        String isShared = medicalRecordQuery.getIsShared();
        queryWrapper.eq(StringUtils.isNotEmpty(isShared), "is_shared", isShared);

        return queryWrapper;
    }

    @Override
    public List<MedicalRecordVo> convertVoList(List<MedicalRecord> medicalRecordList) {
        if (StringUtils.isEmpty(medicalRecordList)) {
            return Collections.emptyList();
        }
        return medicalRecordList.stream().map(MedicalRecordVo::objToVo).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String importMedicalRecord(List<MedicalRecord> medicalRecordList) {
        //数据校验
        if (StringUtils.isEmpty(medicalRecordList)) {
            return "数据不能为空";
        }
        String username = SecurityUtils.getUsername();
        Date nowDate = DateUtils.getNowDate();
        Long deptId = SecurityUtils.getDeptId();
        for (int i = 0; i < medicalRecordList.size(); i++) {
            int index = i + 1;
            MedicalRecord info = medicalRecordList.get(i);
            if (StringUtils.isNull(info.getUserId())) {
                return StringUtils.format("第{}行，所属用户不能为空", index);
            }
            if (StringUtils.isNull(info.getTemplateId())) {
                return StringUtils.format("第{}行，所属模板不能为空", index);
            }
            if (StringUtils.isNull(info.getRecordTitle())) {
                return StringUtils.format("第{}行，病历标题不能为空", index);
            }
            if (StringUtils.isNull(info.getLevel())) {
                return StringUtils.format("第{}行，严重程度不能为空", index);
            }
            if (StringUtils.isNull(info.getIsShared())) {
                return StringUtils.format("第{}行，是否共享不能为空", index);
            }
            info.setCreateBy(username);
            info.setCreateTime(nowDate);
            info.setDeptId(deptId);
        }
        this.saveBatch(medicalRecordList);
        return StringUtils.format("成功导入{}条数据", medicalRecordList.size());
    }
}
