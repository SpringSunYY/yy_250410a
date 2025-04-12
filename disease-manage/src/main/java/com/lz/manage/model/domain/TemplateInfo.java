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
 * 病历模板对象 tb_template_info
 *
 * @author yy
 * @date 2025-04-12
 */
@TableName("tb_template_info")
@Data
public class TemplateInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 模板编号 */
    @Excel(name = "模板编号")
    @TableId(value = "template_id", type = IdType.ASSIGN_ID)
    private Long templateId;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 模版案例 */
    @Excel(name = "模版案例")
    private String templateImage;

    /** 模板内容 */
    @Excel(name = "模板内容")
    private String content;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
