package com.lz.manage.model.vo.templateInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.TemplateInfo;
/**
 * 病历模板Vo对象 tb_template_info
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class TemplateInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 模板编号 */
    @Excel(name = "模板编号")
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


     /**
     * 对象转封装类
     *
     * @param templateInfo TemplateInfo实体对象
     * @return TemplateInfoVo
     */
    public static TemplateInfoVo objToVo(TemplateInfo templateInfo) {
        if (templateInfo == null) {
            return null;
        }
        TemplateInfoVo templateInfoVo = new TemplateInfoVo();
        BeanUtils.copyProperties(templateInfo, templateInfoVo);
        return templateInfoVo;
    }
}
