package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.DataBackup;
import com.lz.manage.model.vo.dataBackup.DataBackupVo;
import com.lz.manage.model.dto.dataBackup.DataBackupQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 数据备份记录Service接口
 * 
 * @author yy
 * @date 2025-04-12
 */
public interface IDataBackupService extends IService<DataBackup>
{
    //region mybatis代码
    /**
     * 查询数据备份记录
     * 
     * @param backupId 数据备份记录主键
     * @return 数据备份记录
     */
    public DataBackup selectDataBackupByBackupId(Long backupId);

    /**
     * 查询数据备份记录列表
     * 
     * @param dataBackup 数据备份记录
     * @return 数据备份记录集合
     */
    public List<DataBackup> selectDataBackupList(DataBackup dataBackup);

    /**
     * 新增数据备份记录
     * 
     * @param dataBackup 数据备份记录
     * @return 结果
     */
    public int insertDataBackup(DataBackup dataBackup);

    /**
     * 修改数据备份记录
     * 
     * @param dataBackup 数据备份记录
     * @return 结果
     */
    public int updateDataBackup(DataBackup dataBackup);

    /**
     * 批量删除数据备份记录
     * 
     * @param backupIds 需要删除的数据备份记录主键集合
     * @return 结果
     */
    public int deleteDataBackupByBackupIds(Long[] backupIds);

    /**
     * 删除数据备份记录信息
     * 
     * @param backupId 数据备份记录主键
     * @return 结果
     */
    public int deleteDataBackupByBackupId(Long backupId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param dataBackupQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<DataBackup> getQueryWrapper(DataBackupQuery dataBackupQuery);

    /**
     * 转换vo
     *
     * @param dataBackupList DataBackup集合
     * @return DataBackupVO集合
     */
    List<DataBackupVo> convertVoList(List<DataBackup> dataBackupList);
}
