package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.print.PrintSettingProvider;
import com.wanmi.sbc.customer.api.provider.print.PrintSettingQueryProvider;
import com.wanmi.sbc.customer.api.request.print.PrintSettingByStoreIdRequest;
import com.wanmi.sbc.customer.api.request.print.PrintSettingSaveRequest;
import com.wanmi.sbc.customer.api.response.print.PrintSettingResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@Tag(name = "StorePrintSettingController", description = "店铺打印服务API")
@RestController
@Validated
@RequestMapping("/print/setting")
public class StorePrintSettingController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PrintSettingQueryProvider printSettingQueryProvider;

    @Autowired
    private PrintSettingProvider printSettingProvider;

    /**
     * 查询打印设置
     *
     * @return
     */
    @Operation(summary = "查询打印设置")
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<PrintSettingResponse> queryPrintSetting() {
        return printSettingQueryProvider.getPrintSettingByStoreId(
                PrintSettingByStoreIdRequest.builder()
                        .storeId(commonUtil.getStoreId())
                .build()
        );
    }

    /**
     * 更新打印设置
     *
     * @param request
     * @return
     */
    @Operation(summary = "更新打印设置")
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse savePrintSetting(@RequestBody PrintSettingSaveRequest request) {
        request.setStoreId(commonUtil.getStoreId());
        return printSettingProvider.modifyPrintSetting(request);
    }
}
