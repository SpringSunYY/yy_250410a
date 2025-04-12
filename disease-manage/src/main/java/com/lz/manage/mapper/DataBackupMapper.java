package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.DataBackup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 数据备份记录Mapper接口
 * 
 * @author yy
 * @date 2025-04-12
 */
public interface DataBackupMapper extends BaseMapper<DataBackup>
{
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
     * 删除数据备份记录
     * 
     * @param backupId 数据备份记录主键
     * @return 结果
     */
    public int deleteDataBackupByBackupId(Long backupId);

    /**
     * 批量删除数据备份记录
     * 
     * @param backupIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDataBackupByBackupIds(Long[] backupIds);
}
