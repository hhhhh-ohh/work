package com.wanmi.sbc.order.provider.impl.thirdplatformtrade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeProvider;
import com.wanmi.sbc.order.api.request.thirdplatformtrade.ThirdPlatformTradeAddRequest;
import com.wanmi.sbc.order.api.request.thirdplatformtrade.ThirdPlatformTradeCompensateRequest;
import com.wanmi.sbc.order.api.request.thirdplatformtrade.ThirdPlatformTradeReAddRequest;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeUpdateRequest;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeUpdateStateRequest;
import com.wanmi.sbc.order.thirdplatformtrade.service.LinkedMallTradeService;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import static com.wanmi.sbc.common.constant.RedisKeyConstant.THIRD_PLATFORM_MQ_REPEATED;

/**
 * @Description: 第三方平台订单处理
 * @Autho daiyitian
 * @Date：2020-03-27 09:17
 */
@Slf4j
@Validated
@RestController
public class ThirdPlatformTradeController implements ThirdPlatformTradeProvider {

    @Autowired
    private ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired
    private RedisUtil redisService;

    @Override
    public BaseResponse add(@RequestBody @Valid ThirdPlatformTradeAddRequest request) {
        log.info("渠道订单开始add,businessId="+request.getBusinessId());
        //防止重复消费
        if(redisService.hasKey(THIRD_PLATFORM_MQ_REPEATED.concat(request.getBusinessId()))){
            log.info("重复消费,businessId=",request.getBusinessId());
            return BaseResponse.SUCCESSFUL();
        }
        redisService.setString(THIRD_PLATFORM_MQ_REPEATED.concat(request.getBusinessId()), "1", 60*60L);
        thirdPlatformTradeService.createOrPay(request.getBusinessId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse compensate(@RequestBody @Valid ThirdPlatformTradeCompensateRequest request) {
        thirdPlatformTradeService.compensate();
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 更新订单
     *
     * @param tradeUpdateRequest 订单信息 {@link ThirdPlatformTradeUpdateRequest}
     * @return
     */
    @Override
    public BaseResponse update(@RequestBody @Valid ThirdPlatformTradeUpdateRequest tradeUpdateRequest) {
        thirdPlatformTradeService.updateThirdPlatformTrade(tradeUpdateRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 更新第三方平台订单，同时修改ProviderTrade及Trade状态
     *
     * @param tradeUpdateStateRequest 订单信息 {@link ThirdPlatformTradeUpdateRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse updateTradeState(@RequestBody @Valid ThirdPlatformTradeUpdateStateRequest tradeUpdateStateRequest){
        thirdPlatformTradeService.updateThirdPlatformTradeState(tradeUpdateStateRequest.getTradeUpdateStateDTO());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse reAdd(@RequestBody @Valid ThirdPlatformTradeReAddRequest request) {
        thirdPlatformTradeService.reAdd(request.getTradeIds());
        return BaseResponse.SUCCESSFUL();
    }
}