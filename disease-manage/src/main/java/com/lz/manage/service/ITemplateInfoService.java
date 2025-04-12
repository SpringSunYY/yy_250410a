package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.TemplateInfo;
import com.lz.manage.model.vo.templateInfo.TemplateInfoVo;
import com.lz.manage.model.dto.templateInfo.TemplateInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 病历模板Service接口
 * 
 * @author yy
 * @date 2025-04-12
 */
public interface ITemplateInfoService extends IService<TemplateInfo>
{
    //region mybatis代码
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
     * 批量删除病历模板
     * 
     * @param templateIds 需要删除的病历模板主键集合
     * @return 结果
     */
    public int deleteTemplateInfoByTemplateIds(Long[] templateIds);

    /**
     * 删除病历模板信息
     * 
     * @param templateId 病历模板主键
     * @return 结果
     */
    public int deleteTemplateInfoByTemplateId(Long templateId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param templateInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<TemplateInfo> getQueryWrapper(TemplateInfoQuery templateInfoQuery);

    /**
     * 转换vo
     *
     * @param templateInfoList TemplateInfo集合
     * @return TemplateInfoVO集合
     */
    List<TemplateInfoVo> convertVoList(List<TemplateInfo> templateInfoList);
}
