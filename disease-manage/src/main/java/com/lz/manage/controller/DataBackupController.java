package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.DataBackup;
import com.lz.manage.model.vo.dataBackup.DataBackupVo;
import com.lz.manage.model.dto.dataBackup.DataBackupQuery;
import com.lz.manage.model.dto.dataBackup.DataBackupInsert;
import com.lz.manage.model.dto.dataBackup.DataBackupEdit;
import com.lz.manage.service.IDataBackupService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 数据备份记录Controller
 *
 * @author yy
 * @date 2025-04-12
 */
@RestController
@RequestMapping("/manage/dataBackup")
public class DataBackupController extends BaseController {
    @Resource
    private IDataBackupService dataBackupService;

    /**
     * 查询数据备份记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:dataBackup:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataBackupQuery dataBackupQuery) {
        DataBackup dataBackup = DataBackupQuery.queryToObj(dataBackupQuery);
        startPage();
        List<DataBackup> list = dataBackupService.selectDataBackupList(dataBackup);
        List<DataBackupVo> listVo = list.stream().map(DataBackupVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出数据备份记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:dataBackup:export')")
    @Log(title = "数据备份记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataBackupQuery dataBackupQuery) {
        DataBackup dataBackup = DataBackupQuery.queryToObj(dataBackupQuery);
        List<DataBackup> list = dataBackupService.selectDataBackupList(dataBackup);
        ExcelUtil<DataBackup> util = new ExcelUtil<DataBackup>(DataBackup.class);
        util.exportExcel(response, list, "数据备份记录数据");
    }

    /**
     * 获取数据备份记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:dataBackup:query')")
    @GetMapping(value = "/{backupId}")
    public AjaxResult getInfo(@PathVariable("backupId") Long backupId) {
        DataBackup dataBackup = dataBackupService.selectDataBackupByBackupId(backupId);
        return success(DataBackupVo.objToVo(dataBackup));
    }

    /**
     * 新增数据备份记录
     */
    @PreAuthorize("@ss.hasPermi('manage:dataBackup:add')")
    @Log(title = "数据备份记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataBackupInsert dataBackupInsert) {
        DataBackup dataBackup = DataBackupInsert.insertToObj(dataBackupInsert);
        return toAjax(dataBackupService.insertDataBackup(dataBackup));
    }

    /**
     * 修改数据备份记录
     */
    @PreAuthorize("@ss.hasPermi('manage:dataBackup:edit')")
    @Log(title = "数据备份记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataBackupEdit dataBackupEdit) {
        DataBackup dataBackup = DataBackupEdit.editToObj(dataBackupEdit);
        return toAjax(dataBackupService.updateDataBackup(dataBackup));
    }

    /**
     * 删除数据备份记录
     */
    @PreAuthorize("@ss.hasPermi('manage:dataBackup:remove')")
    @Log(title = "数据备份记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{backupIds}")
    public AjaxResult remove(@PathVariable Long[] backupIds) {
        return toAjax(dataBackupService.deleteDataBackupByBackupIds(backupIds));
    }

    @PreAuthorize("@ss.hasPermi('manage:medicalRecord:add')")
    @Log(title = "恢复数据被封", businessType = BusinessType.INSERT)
    @GetMapping("/restore/{backupId}")
    public AjaxResult restore(@PathVariable("backupId") Long backupId) {

        return success(dataBackupService.restore(backupId));
    }
}
