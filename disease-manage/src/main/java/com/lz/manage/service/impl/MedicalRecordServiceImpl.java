package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.MedicalRecordMapper;
import com.lz.manage.model.domain.MedicalRecord;
import com.lz.manage.service.IMedicalRecordService;
import com.lz.manage.model.dto.medicalRecord.MedicalRecordQuery;
import com.lz.manage.model.vo.medicalRecord.MedicalRecordVo;

/**
 * 病历信息Service业务层处理
 * 
 * @author yy
 * @date 2025-04-12
 */
@Service
public class MedicalRecordServiceImpl extends ServiceImpl<MedicalRecordMapper, MedicalRecord> implements IMedicalRecordService
{
    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    //region mybatis代码
    /**
     * 查询病历信息
     * 
     * @param recordId 病历信息主键
     * @return 病历信息
     */
    @Override
    public MedicalRecord selectMedicalRecordByRecordId(Long recordId)
    {
        return medicalRecordMapper.selectMedicalRecordByRecordId(recordId);
    }

    /**
     * 查询病历信息列表
     * 
     * @param medicalRecord 病历信息
     * @return 病历信息
     */
    @Override
    public List<MedicalRecord> selectMedicalRecordList(MedicalRecord medicalRecord)
    {
        return medicalRecordMapper.selectMedicalRecordList(medicalRecord);
    }

    /**
     * 新增病历信息
     * 
     * @param medicalRecord 病历信息
     * @return 结果
     */
    @Override
    public int insertMedicalRecord(MedicalRecord medicalRecord)
    {
        medicalRecord.setCreateTime(DateUtils.getNowDate());
        return medicalRecordMapper.insertMedicalRecord(medicalRecord);
    }

    /**
     * 修改病历信息
     * 
     * @param medicalRecord 病历信息
     * @return 结果
     */
    @Override
    public int updateMedicalRecord(MedicalRecord medicalRecord)
    {
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
    public int deleteMedicalRecordByRecordIds(Long[] recordIds)
    {
        return medicalRecordMapper.deleteMedicalRecordByRecordIds(recordIds);
    }

    /**
     * 删除病历信息信息
     * 
     * @param recordId 病历信息主键
     * @return 结果
     */
    @Override
    public int deleteMedicalRecordByRecordId(Long recordId)
    {
        return medicalRecordMapper.deleteMedicalRecordByRecordId(recordId);
    }
    //endregion
    @Override
    public QueryWrapper<MedicalRecord> getQueryWrapper(MedicalRecordQuery medicalRecordQuery){
        QueryWrapper<MedicalRecord> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = medicalRecordQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long recordId = medicalRecordQuery.getRecordId();
        queryWrapper.eq( StringUtils.isNotNull(recordId),"record_id",recordId);

        Long userId = medicalRecordQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Long templateId = medicalRecordQuery.getTemplateId();
        queryWrapper.eq( StringUtils.isNotNull(templateId),"template_id",templateId);

        String recordTitle = medicalRecordQuery.getRecordTitle();
        queryWrapper.like(StringUtils.isNotEmpty(recordTitle) ,"record_title",recordTitle);

        String recordImage = medicalRecordQuery.getRecordImage();
        queryWrapper.eq(StringUtils.isNotEmpty(recordImage) ,"record_image",recordImage);

        String content = medicalRecordQuery.getContent();
        queryWrapper.eq(StringUtils.isNotEmpty(content) ,"content",content);

        Long level = medicalRecordQuery.getLevel();
        queryWrapper.eq( StringUtils.isNotNull(level),"level",level);

        Long deptId = medicalRecordQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        Date createTime = medicalRecordQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        Date updateTime = medicalRecordQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        String isShared = medicalRecordQuery.getIsShared();
        queryWrapper.eq(StringUtils.isNotEmpty(isShared) ,"is_shared",isShared);

        return queryWrapper;
    }

    @Override
    public List<MedicalRecordVo> convertVoList(List<MedicalRecord> medicalRecordList) {
        if (StringUtils.isEmpty(medicalRecordList)) {
            return Collections.emptyList();
        }
        return medicalRecordList.stream().map(MedicalRecordVo::objToVo).collect(Collectors.toList());
    }

}
