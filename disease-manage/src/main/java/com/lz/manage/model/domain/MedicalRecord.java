package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 病历信息对象 tb_medical_record
 *
 * @author yy
 * @date 2025-04-12
 */
@TableName("tb_medical_record")
@Data
public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 病历编号
     */
    @Excel(name = "病历编号", type = Excel.Type.EXPORT)
    @TableId(value = "record_id", type = IdType.ASSIGN_ID)
    private Long recordId;

    /**
     * 所属用户
     */
    @Excel(name = "所属用户", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String userName;
    @Excel(name = "所属用户", type = Excel.Type.IMPORT, prompt = "用户编号")
    private Long userId;

    /**
     * 使用模板
     */
    @Excel(name = "使用模板", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String templateName;
    @Excel(name = "使用模板", prompt = "模版编号", type = Excel.Type.IMPORT)
    private Long templateId;

    /**
     * 病历标题
     */
    @Excel(name = "病历标题")
    private String recordTitle;

    /**
     * 病历图片
     */
//    @Excel(name = "病历图片")
    private String recordImage;

    /**
     * 病历内容
     */
    @Excel(name = "病历内容")
    private String content;

    /**
     * 严重程度
     */
    @Excel(name = "严重程度", readConverterExp = "0=轻微,1=一般,2=严重,3=病危")
    private Long level;

    /**
     * 所属部门
     */
    @Excel(name = "所属部门", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String deptName;
    private Long deptId;

    /**
     * 创建人
     */
    @Excel(name = "创建人", type = Excel.Type.EXPORT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date updateTime;

    /**
     * 是否共享（0否 1是）
     */
    @Excel(name = "是否共享", readConverterExp = "0=否,1=是")
    private String isShared;
    @TableField(exist = false)
    private String orShared;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
