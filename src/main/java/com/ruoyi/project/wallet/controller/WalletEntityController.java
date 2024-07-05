package com.ruoyi.project.wallet.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.project.wallet.domain.dto.WalletAddDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.wallet.domain.WalletEntity;
import com.ruoyi.project.wallet.service.IWalletEntityService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 钱包管理Controller
 *
 * @author junqiao.li
 * @date 2024-06-11
 */
@RestController
@RequestMapping("/wallet/wallet")
public class WalletEntityController extends BaseController {
    @Autowired
    private IWalletEntityService walletEntityService;

    /**
     * 查询钱包管理列表
     */
    @PreAuthorize("@ss.hasPermi('wallet:wallet:list')")
    @GetMapping("/list")
    public TableDataInfo list(WalletEntity walletEntity) {
        startPage();
        List<WalletEntity> list = walletEntityService.selectWalletEntityList(walletEntity);
        return getDataTable(list);
    }

    /**
     * 导出钱包管理列表
     */
    @PreAuthorize("@ss.hasPermi('wallet:wallet:export')")
    @Log(title = "钱包管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WalletEntity walletEntity) {
        List<WalletEntity> list = walletEntityService.selectWalletEntityList(walletEntity);
        ExcelUtil<WalletEntity> util = new ExcelUtil<WalletEntity>(WalletEntity.class);
        util.exportExcel(response, list, "钱包管理数据");
    }

    /**
     * 获取钱包管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('wallet:wallet:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(walletEntityService.selectWalletEntityById(id));
    }

    /**
     * 新增钱包管理
     */
    @PreAuthorize("@ss.hasPermi('wallet:wallet:add')")
    @Log(title = "钱包管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WalletAddDto walletAddDto) {
        return toAjax(walletEntityService.insertWalletEntity(walletAddDto));
    }

    /**
     * 修改钱包管理
     */
    @PreAuthorize("@ss.hasPermi('wallet:wallet:edit')")
    @Log(title = "钱包管理", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody WalletEntity walletEntity) {
        return toAjax(walletEntityService.updateWalletEntity(walletEntity));
    }

    /**
     * 删除钱包管理
     */
    @PreAuthorize("@ss.hasPermi('wallet:wallet:remove')")
    @Log(title = "钱包管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(walletEntityService.deleteWalletEntityByIds(ids));
    }
}
