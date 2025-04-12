package com.lz.manage.model.dto.medicalRecord;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.MedicalRecord;
/**
 * 病历信息Query对象 tb_medical_record
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class MedicalRecordQuery implements Serializable
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

    /** 所属部门 */
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 是否共享（0否 1是） */
    private String isShared;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param medicalRecordQuery 查询对象
     * @return MedicalRecord
     */
    public static MedicalRecord queryToObj(MedicalRecordQuery medicalRecordQuery) {
        if (medicalRecordQuery == null) {
            return null;
        }
        MedicalRecord medicalRecord = new MedicalRecord();
        BeanUtils.copyProperties(medicalRecordQuery, medicalRecord);
        return medicalRecord;
    }
}
