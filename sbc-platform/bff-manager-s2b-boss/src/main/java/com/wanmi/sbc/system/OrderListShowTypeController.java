package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.provider.systemconfig.OrderListShowTypeProvider;
import com.wanmi.sbc.setting.api.request.systemconfig.OrderListShowTypeModifyRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.OrderListShowTypeResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * S2B 平台端-订单列表展示设置
 * Created by dyt on 2017/12/06.
 */
@Tag(name = "OrderListShowTypeController", description = "S2B 平台端-订单列表展示设置配置API")
@RestController
@Validated
@RequestMapping("/config/orderListShowType")
public class OrderListShowTypeController {

    @Resource
    private OrderListShowTypeProvider orderListShowTypeProvider;


    @Operation(summary = "查询订单列表展示设置")
    @GetMapping
    public BaseResponse<OrderListShowTypeResponse> query() {
        return orderListShowTypeProvider.query();
    }

    @Operation(summary = "修改订单列表展示设置")
    @PutMapping
    public BaseResponse modify(@RequestBody @Valid OrderListShowTypeModifyRequest request) {
        if (!Constants.no.equals(request.getStatus()) && !Constants.yes.equals(request.getStatus())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return orderListShowTypeProvider.modify(request);
    }
}
