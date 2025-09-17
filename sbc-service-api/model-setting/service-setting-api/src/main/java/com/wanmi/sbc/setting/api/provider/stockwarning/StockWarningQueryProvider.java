package com.wanmi.sbc.setting.api.provider.stockwarning;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningByIdRequest;
import com.wanmi.sbc.setting.api.response.stockwarning.StockWarningByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(value = "${application.setting.name}", contextId = "stockWarningQueryProvider")
public interface StockWarningQueryProvider {

    /**
     * 查询当前商家的预警状态
     */
    @PostMapping("/setting/${application.setting.version}/stockwarning/iswarning")
    BaseResponse<StockWarningByIdResponse> findIsWarning(@RequestBody @Valid StockWarningByIdRequest request);
}
