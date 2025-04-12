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
import com.lz.manage.model.domain.RecordReminder;
import com.lz.manage.model.vo.recordReminder.RecordReminderVo;
import com.lz.manage.model.dto.recordReminder.RecordReminderQuery;
import com.lz.manage.model.dto.recordReminder.RecordReminderInsert;
import com.lz.manage.model.dto.recordReminder.RecordReminderEdit;
import com.lz.manage.service.IRecordReminderService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 病历提醒Controller
 *
 * @author yy
 * @date 2025-04-12
 */
@RestController
@RequestMapping("/manage/recordReminder")
public class RecordReminderController extends BaseController
{
    @Resource
    private IRecordReminderService recordReminderService;

    /**
     * 查询病历提醒列表
     */
    @PreAuthorize("@ss.hasPermi('manage:recordReminder:list')")
    @GetMapping("/list")
    public TableDataInfo list(RecordReminderQuery recordReminderQuery)
    {
        RecordReminder recordReminder = RecordReminderQuery.queryToObj(recordReminderQuery);
        startPage();
        List<RecordReminder> list = recordReminderService.selectRecordReminderList(recordReminder);
        List<RecordReminderVo> listVo= list.stream().map(RecordReminderVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出病历提醒列表
     */
    @PreAuthorize("@ss.hasPermi('manage:recordReminder:export')")
    @Log(title = "病历提醒", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RecordReminderQuery recordReminderQuery)
    {
        RecordReminder recordReminder = RecordReminderQuery.queryToObj(recordReminderQuery);
        List<RecordReminder> list = recordReminderService.selectRecordReminderList(recordReminder);
        ExcelUtil<RecordReminder> util = new ExcelUtil<RecordReminder>(RecordReminder.class);
        util.exportExcel(response, list, "病历提醒数据");
    }

    /**
     * 获取病历提醒详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:recordReminder:query')")
    @GetMapping(value = "/{reminderId}")
    public AjaxResult getInfo(@PathVariable("reminderId") Long reminderId)
    {
        RecordReminder recordReminder = recordReminderService.selectRecordReminderByReminderId(reminderId);
        return success(RecordReminderVo.objToVo(recordReminder));
    }

    /**
     * 新增病历提醒
     */
    @PreAuthorize("@ss.hasPermi('manage:recordReminder:add')")
    @Log(title = "病历提醒", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RecordReminderInsert recordReminderInsert)
    {
        RecordReminder recordReminder = RecordReminderInsert.insertToObj(recordReminderInsert);
        return toAjax(recordReminderService.insertRecordReminder(recordReminder));
    }

    /**
     * 修改病历提醒
     */
    @PreAuthorize("@ss.hasPermi('manage:recordReminder:edit')")
    @Log(title = "病历提醒", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RecordReminderEdit recordReminderEdit)
    {
        RecordReminder recordReminder = RecordReminderEdit.editToObj(recordReminderEdit);
        return toAjax(recordReminderService.updateRecordReminder(recordReminder));
    }

    /**
     * 删除病历提醒
     */
    @PreAuthorize("@ss.hasPermi('manage:recordReminder:remove')")
    @Log(title = "病历提醒", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reminderIds}")
    public AjaxResult remove(@PathVariable Long[] reminderIds)
    {
        return toAjax(recordReminderService.deleteRecordReminderByReminderIds(reminderIds));
    }
}
