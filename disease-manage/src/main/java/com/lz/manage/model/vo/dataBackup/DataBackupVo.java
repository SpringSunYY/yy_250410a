package com.lz.manage.model.vo.dataBackup;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.DataBackup;
/**
 * 数据备份记录Vo对象 tb_data_backup
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class DataBackupVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 备份编号 */
    @Excel(name = "备份编号")
    private Long backupId;

    /** 备份时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "备份时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date backupTime;

    /** 备份内容 */
    @Excel(name = "备份内容")
    private String backupContent;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;


     /**
     * 对象转封装类
     *
     * @param dataBackup DataBackup实体对象
     * @return DataBackupVo
     */
    public static DataBackupVo objToVo(DataBackup dataBackup) {
        if (dataBackup == null) {
            return null;
        }
        DataBackupVo dataBackupVo = new DataBackupVo();
        BeanUtils.copyProperties(dataBackup, dataBackupVo);
        return dataBackupVo;
    }
}
