package com.lz.manage.model.dto.dataBackup;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.DataBackup;
/**
 * 数据备份记录Query对象 tb_data_backup
 *
 * @author yy
 * @date 2025-04-12
 */
@Data
public class DataBackupQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 备份编号 */
    private Long backupId;

    /** 备份时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date backupTime;

    /** 创建人 */
    private String createBy;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param dataBackupQuery 查询对象
     * @return DataBackup
     */
    public static DataBackup queryToObj(DataBackupQuery dataBackupQuery) {
        if (dataBackupQuery == null) {
            return null;
        }
        DataBackup dataBackup = new DataBackup();
        BeanUtils.copyProperties(dataBackupQuery, dataBackup);
        return dataBackup;
    }
}
