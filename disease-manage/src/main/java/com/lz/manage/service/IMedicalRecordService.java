package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.MedicalRecord;
import com.lz.manage.model.vo.medicalRecord.MedicalRecordVo;
import com.lz.manage.model.dto.medicalRecord.MedicalRecordQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 病历信息Service接口
 * 
 * @author yy
 * @date 2025-04-12
 */
public interface IMedicalRecordService extends IService<MedicalRecord>
{
    //region mybatis代码
    /**
     * 查询病历信息
     * 
     * @param recordId 病历信息主键
     * @return 病历信息
     */
    public MedicalRecord selectMedicalRecordByRecordId(Long recordId);

    /**
     * 查询病历信息列表
     * 
     * @param medicalRecord 病历信息
     * @return 病历信息集合
     */
    public List<MedicalRecord> selectMedicalRecordList(MedicalRecord medicalRecord);

    /**
     * 新增病历信息
     * 
     * @param medicalRecord 病历信息
     * @return 结果
     */
    public int insertMedicalRecord(MedicalRecord medicalRecord);

    /**
     * 修改病历信息
     * 
     * @param medicalRecord 病历信息
     * @return 结果
     */
    public int updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * 批量删除病历信息
     * 
     * @param recordIds 需要删除的病历信息主键集合
     * @return 结果
     */
    public int deleteMedicalRecordByRecordIds(Long[] recordIds);

    /**
     * 删除病历信息信息
     * 
     * @param recordId 病历信息主键
     * @return 结果
     */
    public int deleteMedicalRecordByRecordId(Long recordId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param medicalRecordQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<MedicalRecord> getQueryWrapper(MedicalRecordQuery medicalRecordQuery);

    /**
     * 转换vo
     *
     * @param medicalRecordList MedicalRecord集合
     * @return MedicalRecordVO集合
     */
    List<MedicalRecordVo> convertVoList(List<MedicalRecord> medicalRecordList);
}
