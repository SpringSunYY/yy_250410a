package com.lz.manage.model.dto.templateInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.TemplateInfo;
/**
 * 病历模板Vo对象 tb_template_info
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class TemplateInfoInsert implements Serializable
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

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param templateInfoInsert 插入对象
     * @return TemplateInfoInsert
     */
    public static TemplateInfo insertToObj(TemplateInfoInsert templateInfoInsert) {
        if (templateInfoInsert == null) {
            return null;
        }
        TemplateInfo templateInfo = new TemplateInfo();
        BeanUtils.copyProperties(templateInfoInsert, templateInfo);
        return templateInfo;
    }
}
