package com.wanmi.sbc.setting.provider.impl.stockWarning;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.stockwarning.StockWarningProvider;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningAddRequest;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningDeleteBySkuIdRequest;
import com.wanmi.sbc.setting.api.response.stockwarning.StockWarningAddResponse;
import com.wanmi.sbc.setting.stockWarning.model.root.StockWarning;
import com.wanmi.sbc.setting.stockWarning.service.StockWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@Validated
public class StockWarningController implements StockWarningProvider {

    @Autowired
    private StockWarningService stockWarningService;

    @Override
    public BaseResponse<StockWarningAddResponse> add(@RequestBody @Valid StockWarningAddRequest stockWarningAddRequest) {
        StockWarning stockWarning = KsBeanUtil.convert(stockWarningAddRequest, StockWarning.class);
        return BaseResponse.success(new StockWarningAddResponse(
                stockWarningService.wrapperVo(stockWarningService.add(stockWarning))));
    }

    @Override
    public BaseResponse delete(@RequestBody @Valid StockWarningDeleteBySkuIdRequest stockWarningDeleteBySkuIdRequest) {
        stockWarningService.delete(stockWarningDeleteBySkuIdRequest.getSkuId());
        return BaseResponse.SUCCESSFUL();
    }
}
