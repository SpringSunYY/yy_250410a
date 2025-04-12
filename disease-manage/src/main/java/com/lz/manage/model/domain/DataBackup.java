package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 数据备份记录对象 tb_data_backup
 *
 * @author yy
 * @date 2025-04-12
 */
@TableName("tb_data_backup")
@Data
public class DataBackup implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 备份编号 */
    @Excel(name = "备份编号")
    @TableId(value = "backup_id", type = IdType.ASSIGN_ID)
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

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
