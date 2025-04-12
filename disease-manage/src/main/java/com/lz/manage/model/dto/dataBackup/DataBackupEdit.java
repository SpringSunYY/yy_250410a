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
public class DataBackupEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 备份编号 */
    private Long backupId;

    /** 备份内容 */
    private String backupContent;

    /**
     * 对象转封装类
     *
     * @param dataBackupEdit 编辑对象
     * @return DataBackup
     */
    public static DataBackup editToObj(DataBackupEdit dataBackupEdit) {
        if (dataBackupEdit == null) {
            return null;
        }
        DataBackup dataBackup = new DataBackup();
        BeanUtils.copyProperties(dataBackupEdit, dataBackup);
        return dataBackup;
    }
}
