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
import com.lz.manage.model.domain.MedicalRecord;
import com.lz.manage.model.vo.medicalRecord.MedicalRecordVo;
import com.lz.manage.model.dto.medicalRecord.MedicalRecordQuery;
import com.lz.manage.model.dto.medicalRecord.MedicalRecordInsert;
import com.lz.manage.model.dto.medicalRecord.MedicalRecordEdit;
import com.lz.manage.service.IMedicalRecordService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 病历信息Controller
 *
 * @author yy
 * @date 2025-04-12
 */
@RestController
@RequestMapping("/manage/medicalRecord")
public class MedicalRecordController extends BaseController
{
    @Resource
    private IMedicalRecordService medicalRecordService;

    /**
     * 查询病历信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:medicalRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(MedicalRecordQuery medicalRecordQuery)
    {
        MedicalRecord medicalRecord = MedicalRecordQuery.queryToObj(medicalRecordQuery);
        startPage();
        List<MedicalRecord> list = medicalRecordService.selectMedicalRecordList(medicalRecord);
        List<MedicalRecordVo> listVo= list.stream().map(MedicalRecordVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出病历信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:medicalRecord:export')")
    @Log(title = "病历信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MedicalRecordQuery medicalRecordQuery)
    {
        MedicalRecord medicalRecord = MedicalRecordQuery.queryToObj(medicalRecordQuery);
        List<MedicalRecord> list = medicalRecordService.selectMedicalRecordList(medicalRecord);
        ExcelUtil<MedicalRecord> util = new ExcelUtil<MedicalRecord>(MedicalRecord.class);
        util.exportExcel(response, list, "病历信息数据");
    }

    /**
     * 获取病历信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:medicalRecord:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        MedicalRecord medicalRecord = medicalRecordService.selectMedicalRecordByRecordId(recordId);
        return success(MedicalRecordVo.objToVo(medicalRecord));
    }

    /**
     * 新增病历信息
     */
    @PreAuthorize("@ss.hasPermi('manage:medicalRecord:add')")
    @Log(title = "病历信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MedicalRecordInsert medicalRecordInsert)
    {
        MedicalRecord medicalRecord = MedicalRecordInsert.insertToObj(medicalRecordInsert);
        return toAjax(medicalRecordService.insertMedicalRecord(medicalRecord));
    }

    /**
     * 修改病历信息
     */
    @PreAuthorize("@ss.hasPermi('manage:medicalRecord:edit')")
    @Log(title = "病历信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MedicalRecordEdit medicalRecordEdit)
    {
        MedicalRecord medicalRecord = MedicalRecordEdit.editToObj(medicalRecordEdit);
        return toAjax(medicalRecordService.updateMedicalRecord(medicalRecord));
    }

    /**
     * 删除病历信息
     */
    @PreAuthorize("@ss.hasPermi('manage:medicalRecord:remove')")
    @Log(title = "病历信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(medicalRecordService.deleteMedicalRecordByRecordIds(recordIds));
    }

    @PreAuthorize("@ss.hasPermi('manage:medicalRecord:import')")
    @Log(title = "导入病历", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<MedicalRecord> util = new ExcelUtil<MedicalRecord>(MedicalRecord.class);
        List<MedicalRecord> storeInfoList = util.importExcel(file.getInputStream());
        String message = medicalRecordService.importMedicalRecord(storeInfoList);
        return success(message);
    }

    @PreAuthorize("@ss.hasPermi('manage:medicalRecord:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<MedicalRecord> util = new ExcelUtil<MedicalRecord>(MedicalRecord.class);
        util.importTemplateExcel(response, "病历数据");
    }
}
