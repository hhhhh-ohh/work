package com.wanmi.sbc.buycycle;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.trade.TradeItemProvider;
import com.wanmi.sbc.order.api.request.trade.BuyCyclePlanRequest;
import com.wanmi.sbc.order.api.response.trade.BuyCyclePlanResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author xuyunpeng
 * @className BuyCycleController
 * @description
 * @date 2022/10/19 3:02 PM
 **/
@Tag(name =  "周期购API", description =  "BuyCycleController")
@RestController
@RequestMapping(value = "/buyCycle")
public class BuyCycleController {

    @Autowired
    private TradeItemProvider tradeItemProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "生成送达计划")
    @PostMapping("/create/plan")
    public BaseResponse<BuyCyclePlanResponse> createPlan(@RequestBody @Valid BuyCyclePlanRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        request.setTerminalToken(commonUtil.getTerminalToken());
        return tradeItemProvider.createPlan(request);
    }

}
