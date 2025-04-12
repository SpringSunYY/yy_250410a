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
import com.lz.manage.model.domain.TemplateInfo;
import com.lz.manage.model.vo.templateInfo.TemplateInfoVo;
import com.lz.manage.model.dto.templateInfo.TemplateInfoQuery;
import com.lz.manage.model.dto.templateInfo.TemplateInfoInsert;
import com.lz.manage.model.dto.templateInfo.TemplateInfoEdit;
import com.lz.manage.service.ITemplateInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 病历模板Controller
 *
 * @author yy
 * @date 2025-04-12
 */
@RestController
@RequestMapping("/manage/templateInfo")
public class TemplateInfoController extends BaseController
{
    @Resource
    private ITemplateInfoService templateInfoService;

    /**
     * 查询病历模板列表
     */
    @PreAuthorize("@ss.hasPermi('manage:templateInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(TemplateInfoQuery templateInfoQuery)
    {
        TemplateInfo templateInfo = TemplateInfoQuery.queryToObj(templateInfoQuery);
        startPage();
        List<TemplateInfo> list = templateInfoService.selectTemplateInfoList(templateInfo);
        List<TemplateInfoVo> listVo= list.stream().map(TemplateInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出病历模板列表
     */
    @PreAuthorize("@ss.hasPermi('manage:templateInfo:export')")
    @Log(title = "病历模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TemplateInfoQuery templateInfoQuery)
    {
        TemplateInfo templateInfo = TemplateInfoQuery.queryToObj(templateInfoQuery);
        List<TemplateInfo> list = templateInfoService.selectTemplateInfoList(templateInfo);
        ExcelUtil<TemplateInfo> util = new ExcelUtil<TemplateInfo>(TemplateInfo.class);
        util.exportExcel(response, list, "病历模板数据");
    }

    /**
     * 获取病历模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:templateInfo:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        TemplateInfo templateInfo = templateInfoService.selectTemplateInfoByTemplateId(templateId);
        return success(TemplateInfoVo.objToVo(templateInfo));
    }

    /**
     * 新增病历模板
     */
    @PreAuthorize("@ss.hasPermi('manage:templateInfo:add')")
    @Log(title = "病历模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TemplateInfoInsert templateInfoInsert)
    {
        TemplateInfo templateInfo = TemplateInfoInsert.insertToObj(templateInfoInsert);
        return toAjax(templateInfoService.insertTemplateInfo(templateInfo));
    }

    /**
     * 修改病历模板
     */
    @PreAuthorize("@ss.hasPermi('manage:templateInfo:edit')")
    @Log(title = "病历模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TemplateInfoEdit templateInfoEdit)
    {
        TemplateInfo templateInfo = TemplateInfoEdit.editToObj(templateInfoEdit);
        return toAjax(templateInfoService.updateTemplateInfo(templateInfo));
    }

    /**
     * 删除病历模板
     */
    @PreAuthorize("@ss.hasPermi('manage:templateInfo:remove')")
    @Log(title = "病历模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(templateInfoService.deleteTemplateInfoByTemplateIds(templateIds));
    }
}
