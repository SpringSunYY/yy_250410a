package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.MedicalRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 病历信息Mapper接口
 * 
 * @author yy
 * @date 2025-04-12
 */
public interface MedicalRecordMapper extends BaseMapper<MedicalRecord>
{
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
     * 删除病历信息
     * 
     * @param recordId 病历信息主键
     * @return 结果
     */
    public int deleteMedicalRecordByRecordId(Long recordId);

    /**
     * 批量删除病历信息
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMedicalRecordByRecordIds(Long[] recordIds);
}
