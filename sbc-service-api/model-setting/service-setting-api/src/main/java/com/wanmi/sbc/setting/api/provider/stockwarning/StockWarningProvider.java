package com.wanmi.sbc.setting.api.provider.stockwarning;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningAddRequest;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningDeleteBySkuIdRequest;
import com.wanmi.sbc.setting.api.response.stockwarning.StockWarningAddResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(value = "${application.setting.name}", contextId = "StockWarningProvider")
public interface StockWarningProvider {

    /**
     * 新增
     */
    @PostMapping("/setting/${application.setting.version}/stockwarning/add")
    BaseResponse<StockWarningAddResponse> add(@RequestBody @Valid StockWarningAddRequest stockWarningAddRequest);

    /**
     * 删除
     */
    @PostMapping("/setting/${application.setting.version}/stockwarning/delete")
    BaseResponse<StockWarningAddResponse> delete(@RequestBody @Valid StockWarningDeleteBySkuIdRequest stockWarningDeleteBySkuIdRequest);
}
