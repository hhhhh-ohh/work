package com.wanmi.sbc.order.api.provider.thirdplatformtrade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.trade.*;
import com.wanmi.sbc.order.bean.vo.ThirdPlatformTradeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Description: 第三方渠道订单查询
 * @Autho qiaokang
 * @Date：2020-03-27 09:08
 */
@FeignClient(value = "${application.order.name}", contextId = "ThirdPlatformTradeQueryProvider")
public interface ThirdPlatformTradeQueryProvider {

    /**
     * 通过父订单号获取交易单集合
     *
     * @param request 父交易单id {@link ThirdPlatformTradeListByTradeIdsRequest}
     * @return 交易单信息 {@link TradeListByParentIdResponse}
     */
    @PostMapping("/order/${application.order.version}/third-platform-trade/list-by-trade-ids")
    BaseResponse<ThirdTradeListByTradeIdsResponse> listByTradeIds(@RequestBody @Valid ThirdPlatformTradeListByTradeIdsRequest request);

    /**
     * 条件分页查询
     *
     * @param tradePageCriteriaRequest 父交易单id {@link ThirdPlatformTradeListByTradeIdsRequest}
     * @return 交易单信息 {@link TradeListByParentIdResponse}
     */
    @PostMapping("/order/${application.order.version}/third-platform-trade/page-criteria")
    BaseResponse<TradePageCriteriaResponse> pageCriteria(@RequestBody @Valid ThirdPlatformTradePageCriteriaRequest tradePageCriteriaRequest);

    /**
     * 通过主订单号获取交易单集合
     *
     * @param tradeListByOrderCodeRequest 主交易单id {@link TradeListByParentIdRequest}
     * @return 交易单信息 {@link TradeListByParentIdResponse}
     */
    @PostMapping("/order/${application.order.version}/third-platform-trade/list-by-order-code")
    BaseResponse<ThirdTradeListByTradeIdsResponse> listByTradeId(@RequestBody @Valid TradeListByOrderCodeRequest tradeListByOrderCodeRequest);

    /**
     * 条件分页
     *
     * @param tradeCountCriteriaRequest 带参分页参数 {@link TradeCountCriteriaRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/third-platform-trade/count-criteria")
    BaseResponse<ProviderTradeCountCriteriaResponse> countCriteria(@RequestBody @Valid ThirdPlatformTradeCountCriteriaRequest tradeCountCriteriaRequest);

    /**
     * 通过第三方订单号获取订单
     *
     * @param request 主交易单id {@link ThirdPlatformTradeByThirdPlatformOrderIdRequest}
     * @return 交易单信息 {@link ThirdPlatformTradeByThirdPlatformOrderIdResponse}
     */
    @PostMapping("/order/${application.order.version}/third-platform-trade/query-by-third-platform-order-id")
    BaseResponse<ThirdPlatformTradeByThirdPlatformOrderIdResponse> queryByThirdPlatformOrderId(@RequestBody @Valid ThirdPlatformTradeByThirdPlatformOrderIdRequest request);
}
