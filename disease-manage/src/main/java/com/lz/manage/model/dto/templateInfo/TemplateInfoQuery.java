package com.lz.manage.model.dto.templateInfo;

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
import com.lz.manage.model.domain.TemplateInfo;
/**
 * 病历模板Query对象 tb_template_info
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class TemplateInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 模板编号 */
    private Long templateId;

    /** 模板名称 */
    private String templateName;

    /** 模版案例 */
    private String templateImage;

    /** 模板内容 */
    private String content;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param templateInfoQuery 查询对象
     * @return TemplateInfo
     */
    public static TemplateInfo queryToObj(TemplateInfoQuery templateInfoQuery) {
        if (templateInfoQuery == null) {
            return null;
        }
        TemplateInfo templateInfo = new TemplateInfo();
        BeanUtils.copyProperties(templateInfoQuery, templateInfo);
        return templateInfo;
    }
}
