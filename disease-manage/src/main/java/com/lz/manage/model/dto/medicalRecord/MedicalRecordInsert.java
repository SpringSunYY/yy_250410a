package com.lz.manage.model.dto.medicalRecord;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.MedicalRecord;
/**
 * 病历信息Vo对象 tb_medical_record
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class MedicalRecordInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 病历编号 */
    private Long recordId;

    /** 所属用户 */
    private Long userId;

    /** 使用模板 */
    private Long templateId;

    /** 病历标题 */
    private String recordTitle;

    /** 病历图片 */
    private String recordImage;

    /** 病历内容 */
    private String content;

    /** 严重程度 */
    private Long level;

    /** 是否共享（0否 1是） */
    private String isShared;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param medicalRecordInsert 插入对象
     * @return MedicalRecordInsert
     */
    public static MedicalRecord insertToObj(MedicalRecordInsert medicalRecordInsert) {
        if (medicalRecordInsert == null) {
            return null;
        }
        MedicalRecord medicalRecord = new MedicalRecord();
        BeanUtils.copyProperties(medicalRecordInsert, medicalRecord);
        return medicalRecord;
    }
}
