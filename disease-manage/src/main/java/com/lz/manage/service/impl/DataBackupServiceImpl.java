package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.DataBackupMapper;
import com.lz.manage.model.domain.DataBackup;
import com.lz.manage.service.IDataBackupService;
import com.lz.manage.model.dto.dataBackup.DataBackupQuery;
import com.lz.manage.model.vo.dataBackup.DataBackupVo;

/**
 * 数据备份记录Service业务层处理
 * 
 * @author yy
 * @date 2025-04-12
 */
@Service
public class DataBackupServiceImpl extends ServiceImpl<DataBackupMapper, DataBackup> implements IDataBackupService
{
    @Resource
    private DataBackupMapper dataBackupMapper;

    //region mybatis代码
    /**
     * 查询数据备份记录
     * 
     * @param backupId 数据备份记录主键
     * @return 数据备份记录
     */
    @Override
    public DataBackup selectDataBackupByBackupId(Long backupId)
    {
        return dataBackupMapper.selectDataBackupByBackupId(backupId);
    }

    /**
     * 查询数据备份记录列表
     * 
     * @param dataBackup 数据备份记录
     * @return 数据备份记录
     */
    @Override
    public List<DataBackup> selectDataBackupList(DataBackup dataBackup)
    {
        return dataBackupMapper.selectDataBackupList(dataBackup);
    }

    /**
     * 新增数据备份记录
     * 
     * @param dataBackup 数据备份记录
     * @return 结果
     */
    @Override
    public int insertDataBackup(DataBackup dataBackup)
    {
        return dataBackupMapper.insertDataBackup(dataBackup);
    }

    /**
     * 修改数据备份记录
     * 
     * @param dataBackup 数据备份记录
     * @return 结果
     */
    @Override
    public int updateDataBackup(DataBackup dataBackup)
    {
        return dataBackupMapper.updateDataBackup(dataBackup);
    }

    /**
     * 批量删除数据备份记录
     * 
     * @param backupIds 需要删除的数据备份记录主键
     * @return 结果
     */
    @Override
    public int deleteDataBackupByBackupIds(Long[] backupIds)
    {
        return dataBackupMapper.deleteDataBackupByBackupIds(backupIds);
    }

    /**
     * 删除数据备份记录信息
     * 
     * @param backupId 数据备份记录主键
     * @return 结果
     */
    @Override
    public int deleteDataBackupByBackupId(Long backupId)
    {
        return dataBackupMapper.deleteDataBackupByBackupId(backupId);
    }
    //endregion
    @Override
    public QueryWrapper<DataBackup> getQueryWrapper(DataBackupQuery dataBackupQuery){
        QueryWrapper<DataBackup> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = dataBackupQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long backupId = dataBackupQuery.getBackupId();
        queryWrapper.eq( StringUtils.isNotNull(backupId),"backup_id",backupId);

        Date backupTime = dataBackupQuery.getBackupTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginBackupTime"))&&StringUtils.isNotNull(params.get("endBackupTime")),"backup_time",params.get("beginBackupTime"),params.get("endBackupTime"));

        String createBy = dataBackupQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        return queryWrapper;
    }

    @Override
    public List<DataBackupVo> convertVoList(List<DataBackup> dataBackupList) {
        if (StringUtils.isEmpty(dataBackupList)) {
            return Collections.emptyList();
        }
        return dataBackupList.stream().map(DataBackupVo::objToVo).collect(Collectors.toList());
    }

}
