package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.TemplateInfoMapper;
import com.lz.manage.model.domain.TemplateInfo;
import com.lz.manage.service.ITemplateInfoService;
import com.lz.manage.model.dto.templateInfo.TemplateInfoQuery;
import com.lz.manage.model.vo.templateInfo.TemplateInfoVo;

/**
 * 病历模板Service业务层处理
 * 
 * @author yy
 * @date 2025-04-12
 */
@Service
public class TemplateInfoServiceImpl extends ServiceImpl<TemplateInfoMapper, TemplateInfo> implements ITemplateInfoService
{
    @Resource
    private TemplateInfoMapper templateInfoMapper;

    //region mybatis代码
    /**
     * 查询病历模板
     * 
     * @param templateId 病历模板主键
     * @return 病历模板
     */
    @Override
    public TemplateInfo selectTemplateInfoByTemplateId(Long templateId)
    {
        return templateInfoMapper.selectTemplateInfoByTemplateId(templateId);
    }

    /**
     * 查询病历模板列表
     * 
     * @param templateInfo 病历模板
     * @return 病历模板
     */
    @Override
    public List<TemplateInfo> selectTemplateInfoList(TemplateInfo templateInfo)
    {
        return templateInfoMapper.selectTemplateInfoList(templateInfo);
    }

    /**
     * 新增病历模板
     * 
     * @param templateInfo 病历模板
     * @return 结果
     */
    @Override
    public int insertTemplateInfo(TemplateInfo templateInfo)
    {
        templateInfo.setCreateTime(DateUtils.getNowDate());
        return templateInfoMapper.insertTemplateInfo(templateInfo);
    }

    /**
     * 修改病历模板
     * 
     * @param templateInfo 病历模板
     * @return 结果
     */
    @Override
    public int updateTemplateInfo(TemplateInfo templateInfo)
    {
        templateInfo.setUpdateTime(DateUtils.getNowDate());
        return templateInfoMapper.updateTemplateInfo(templateInfo);
    }

    /**
     * 批量删除病历模板
     * 
     * @param templateIds 需要删除的病历模板主键
     * @return 结果
     */
    @Override
    public int deleteTemplateInfoByTemplateIds(Long[] templateIds)
    {
        return templateInfoMapper.deleteTemplateInfoByTemplateIds(templateIds);
    }

    /**
     * 删除病历模板信息
     * 
     * @param templateId 病历模板主键
     * @return 结果
     */
    @Override
    public int deleteTemplateInfoByTemplateId(Long templateId)
    {
        return templateInfoMapper.deleteTemplateInfoByTemplateId(templateId);
    }
    //endregion
    @Override
    public QueryWrapper<TemplateInfo> getQueryWrapper(TemplateInfoQuery templateInfoQuery){
        QueryWrapper<TemplateInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = templateInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long templateId = templateInfoQuery.getTemplateId();
        queryWrapper.eq( StringUtils.isNotNull(templateId),"template_id",templateId);

        String templateName = templateInfoQuery.getTemplateName();
        queryWrapper.like(StringUtils.isNotEmpty(templateName) ,"template_name",templateName);

        String templateImage = templateInfoQuery.getTemplateImage();
        queryWrapper.eq(StringUtils.isNotEmpty(templateImage) ,"template_image",templateImage);

        String content = templateInfoQuery.getContent();
        queryWrapper.eq(StringUtils.isNotEmpty(content) ,"content",content);

        String createBy = templateInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = templateInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        Date updateTime = templateInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<TemplateInfoVo> convertVoList(List<TemplateInfo> templateInfoList) {
        if (StringUtils.isEmpty(templateInfoList)) {
            return Collections.emptyList();
        }
        return templateInfoList.stream().map(TemplateInfoVo::objToVo).collect(Collectors.toList());
    }

}
