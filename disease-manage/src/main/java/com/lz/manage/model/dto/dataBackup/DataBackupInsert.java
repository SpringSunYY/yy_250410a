package com.lz.manage.model.dto.dataBackup;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.DataBackup;
/**
 * 数据备份记录Vo对象 tb_data_backup
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class DataBackupInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 备份编号 */
    private Long backupId;

    /** 备份内容 */
    private String backupContent;

    /**
     * 对象转封装类
     *
     * @param dataBackupInsert 插入对象
     * @return DataBackupInsert
     */
    public static DataBackup insertToObj(DataBackupInsert dataBackupInsert) {
        if (dataBackupInsert == null) {
            return null;
        }
        DataBackup dataBackup = new DataBackup();
        BeanUtils.copyProperties(dataBackupInsert, dataBackup);
        return dataBackup;
    }
}
