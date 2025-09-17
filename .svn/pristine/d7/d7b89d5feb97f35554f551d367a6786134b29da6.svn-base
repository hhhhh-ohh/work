package com.wanmi.sbc.popupadministration;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.setting.api.provider.popupadministration.PopupAdministrationQueryProvider;
import com.wanmi.sbc.setting.api.request.popupadministration.PageManagementRequest;
import com.wanmi.sbc.setting.api.response.popupadministration.PageManagementResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * <p>弹窗管理服务API</p>
 *
 * @author weiwenhao
 * @date 2020-04-23
 */
@RestController
@Validated
@Tag(name =  "弹窗管理查询服务API", description =  "PopupAdministrationQueryController")
@RequestMapping("/popup_administration")
public class PopupAdministrationQueryController {


    @Autowired
    private PopupAdministrationQueryProvider popupAdministrationQueryProvider;


    /**
     * 弹窗管理&页面管理列表查询
     *
     * @param request
     * @return
     */
    @Operation(summary = "弹窗管理&页面管理列表查询")
    @PostMapping("/page_management_popup_administration")
    public BaseResponse<PageManagementResponse> pageManagementAndPopupAdministrationList(@RequestBody @Valid PageManagementRequest request) {
        return popupAdministrationQueryProvider.pageManagementAndPopupAdministrationList(request);
    }

}
