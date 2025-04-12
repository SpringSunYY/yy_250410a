package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.TemplateInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 病历模板Mapper接口
 * 
 * @author yy
 * @date 2025-04-12
 */
public interface TemplateInfoMapper extends BaseMapper<TemplateInfo>
{
    /**
     * 查询病历模板
     * 
     * @param templateId 病历模板主键
     * @return 病历模板
     */
    public TemplateInfo selectTemplateInfoByTemplateId(Long templateId);

    /**
     * 查询病历模板列表
     * 
     * @param templateInfo 病历模板
     * @return 病历模板集合
     */
    public List<TemplateInfo> selectTemplateInfoList(TemplateInfo templateInfo);

    /**
     * 新增病历模板
     * 
     * @param templateInfo 病历模板
     * @return 结果
     */
    public int insertTemplateInfo(TemplateInfo templateInfo);

    /**
     * 修改病历模板
     * 
     * @param templateInfo 病历模板
     * @return 结果
     */
    public int updateTemplateInfo(TemplateInfo templateInfo);

    /**
     * 删除病历模板
     * 
     * @param templateId 病历模板主键
     * @return 结果
     */
    public int deleteTemplateInfoByTemplateId(Long templateId);

    /**
     * 批量删除病历模板
     * 
     * @param templateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTemplateInfoByTemplateIds(Long[] templateIds);
}
