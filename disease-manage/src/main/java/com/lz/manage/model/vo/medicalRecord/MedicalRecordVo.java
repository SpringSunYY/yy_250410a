package com.lz.manage.model.vo.medicalRecord;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.MedicalRecord;

/**
 * 病历信息Vo对象 tb_medical_record
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class MedicalRecordVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 病历编号
     */
    @Excel(name = "病历编号")
    private Long recordId;

    /**
     * 所属用户
     */
    @Excel(name = "所属用户")
    private String userName;
    private Long userId;

    /**
     * 使用模板
     */
    @Excel(name = "使用模板")
    private String templateName;
    private Long templateId;

    /**
     * 病历标题
     */
    @Excel(name = "病历标题")
    private String recordTitle;

    /**
     * 病历图片
     */
    @Excel(name = "病历图片")
    private String recordImage;

    /**
     * 病历内容
     */
    @Excel(name = "病历内容")
    private String content;

    /**
     * 严重程度
     */
    @Excel(name = "严重程度")
    private Long level;

    /**
     * 所属部门
     */
    @Excel(name = "所属部门")
    private String deptName;
    private Long deptId;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 是否共享（0否 1是）
     */
    @Excel(name = "是否共享", readConverterExp = "0=否,1=是")
    private String isShared;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


    /**
     * 对象转封装类
     *
     * @param medicalRecord MedicalRecord实体对象
     * @return MedicalRecordVo
     */
    public static MedicalRecordVo objToVo(MedicalRecord medicalRecord) {
        if (medicalRecord == null) {
            return null;
        }
        MedicalRecordVo medicalRecordVo = new MedicalRecordVo();
        BeanUtils.copyProperties(medicalRecord, medicalRecordVo);
        return medicalRecordVo;
    }
}
