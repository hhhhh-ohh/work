package com.wanmi.sbc.popupadministration;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.popupadministration.PopupAdministrationQueryProvider;
import com.wanmi.sbc.setting.api.request.popupadministration.PageManagementGetIdRequest;
import com.wanmi.sbc.setting.api.request.popupadministration.PageManagementRequest;
import com.wanmi.sbc.setting.api.request.popupadministration.PopupAdministrationPageRequest;
import com.wanmi.sbc.setting.api.response.popupadministration.PageManagementResponse;
import com.wanmi.sbc.setting.api.response.popupadministration.PopupAdministrationPageResponse;
import com.wanmi.sbc.setting.api.response.popupadministration.PopupAdministrationResponse;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;


    /**
     * 弹窗列表查询
     *
     * @param request
     * @return
     */
    @Operation(summary = "弹窗列表查询")
    @PostMapping("/page")
    public BaseResponse<PopupAdministrationPageResponse> page(@RequestBody @Valid PopupAdministrationPageRequest request) {
        operateLogMQUtil.convertAndSend("弹窗列表查询", "查询弹窗管理列表", "查询弹窗管理列表");
        return popupAdministrationQueryProvider.page(request);
    }

    /**
     * 弹窗详情查询
     *
     * @param request
     * @return
     */
    @Operation(summary = "弹窗详情查询")
    @PostMapping("/popupAdministration_id")
    public BaseResponse<PopupAdministrationResponse> getPopupAdministrationById(@RequestBody @Valid PageManagementGetIdRequest request) {
        operateLogMQUtil.convertAndSend("弹窗详情查询", "弹窗详情查询", "弹窗详情查询：" + request.getPopupId());
        return popupAdministrationQueryProvider.getPopupAdministrationById(request);
    }

    /**
     * 弹窗管理&页面管理列表查询
     *
     * @param request
     * @return
     */
    @Operation(summary = "弹窗管理&页面管理列表查询")
    @PostMapping("/page_management_popup_administration")
    public BaseResponse<PageManagementResponse> pageManagementAndPopupAdministrationList(@RequestBody @Valid PageManagementRequest request) {
        operateLogMQUtil.convertAndSend("弹窗管理&页面管理列表查询", " 弹窗管理&页面管理列表查询", " 弹窗管理&页面管理列表查询：" + request.getApplicationPageName());
        return popupAdministrationQueryProvider.pageManagementAndPopupAdministrationList(request);
    }

}
