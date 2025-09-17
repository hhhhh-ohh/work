package com.wanmi.sbc.setting.provider.impl.stockWarning;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.setting.api.provider.stockwarning.StockWarningQueryProvider;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningByIdRequest;
import com.wanmi.sbc.setting.api.response.stockwarning.StockWarningByIdResponse;
import com.wanmi.sbc.setting.stockWarning.service.StockWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


@RestController
@Validated
public class StockWarningQueryController implements StockWarningQueryProvider {

    @Autowired
    private StockWarningService stockWarningService;

    @Override
    public BaseResponse<StockWarningByIdResponse> findIsWarning(@RequestBody @Valid StockWarningByIdRequest request) {

        BoolFlag isWarning = stockWarningService.findIsWarning(request.getStoreId(), request.getSkuId());

        return BaseResponse.success(new StockWarningByIdResponse(isWarning));
    }
}
