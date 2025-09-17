package com.wanmi.sbc.order.api.provider.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.trade.BuyCycleInfoResponse;
import com.wanmi.sbc.order.api.response.trade.BuyCyclePlanResponse;
import com.wanmi.sbc.order.api.response.trade.TradeConfirmResponse;
import com.wanmi.sbc.order.api.response.trade.TradeItemModifyGoodsNumResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>订单商品服务操作接口</p>
 * @Author: daiyitian
 * @Description: 退单服务操作接口
 * @Date: 2018-12-03 15:40
 */
@FeignClient(value = "${application.order.name}", contextId = "TradeItemProvider")
public interface TradeItemProvider {

    /**
     * 保存订单商品快照
     *
     * @param request 保存订单商品快照请求结构 {@link TradeItemSnapshotRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/snapshot")
    BaseResponse snapshot(@RequestBody @Valid TradeItemSnapshotRequest request);

    @PostMapping("/order/${application.order.version}/trade/new/snapshot")
    BaseResponse snapshotNew(@RequestBody @Valid TradeBuyRequest request);

    /**
     * 修改订单商品快照的商品数量
     *
     * @param request 修改订单商品快照的商品数量请求结构 {@link TradeItemModifyGoodsNumRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/modify-goods-num")
    BaseResponse<TradeItemModifyGoodsNumResponse> modifyGoodsNum(@RequestBody @Valid TradeItemModifyGoodsNumRequest request);

    /**
     * 根据customerId删除订单商品快照
     *
     * @param request 根据customerId删除订单商品快照请求结构 {@link TradeItemDeleteByCustomerIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/delete-by-customer-id")
    BaseResponse deleteByCustomerId(@RequestBody @Valid TradeItemDeleteByCustomerIdRequest request);

    /**
     * 保存订单赠品快照
     *
     * @param request 保存订单商品快照请求结构 {@link TradeItemSnapshotGiftRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/snapshot-gift")
    BaseResponse snapshotGift(@RequestBody @Valid TradeItemSnapshotGiftRequest request);

    /**
     * 确认结算
     *
     * @param request 保存订单商品快照请求结构 {@link TradeItemConfirmSettlementRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/confirm-settlement")
    BaseResponse confirmSettlement(@RequestBody @Valid TradeItemConfirmSettlementRequest request);

    /**
     * 拼团购买
     *
     * @param request 拼团购买 {@link TradeBuyRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/group-buy/snapshot")
    BaseResponse groupBuySnapshot(@RequestBody @Valid TradeBuyRequest request);

    /**
     * 购物车结算
     *
     * @param request 保存订单商品快照请求结构 {@link TradeItemConfirmSettlementRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/confirm-snapshot")
    BaseResponse confirmSnapshot(@RequestBody @Valid TradeBuyRequest request);

    /**
     * 查询订单快照
     *
     * @param request 查询订单快照返回结果 {@link FindTradeSnapshotRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/find-trade-snapshot")
    BaseResponse<TradeConfirmResponse> findTradeSnapshot(@RequestBody @Valid FindTradeSnapshotRequest request);

    /**
     * 查询周期计划
     *
     * @param request 返回结果 {@link BuyCycleInfoRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/buy-cycle-info")
    BaseResponse<BuyCycleInfoResponse> buyCycleInfo(@RequestBody @Valid BuyCycleInfoRequest request);

    /**
     * 生成送达计划
     *
     * @param request 返回结果 {@link BuyCyclePlanRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/cycle/deliveryPlan/create")
    BaseResponse<BuyCyclePlanResponse> createPlan(@RequestBody @Valid BuyCyclePlanRequest request);


    /**
     * 快速下单结算
     *
     * @param request 保存订单商品快照请求结构 {@link TradeItemConfirmSettlementRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/quick-order-snapshot")
    BaseResponse quickOrderSnapshot(@RequestBody @Valid TradeBuyRequest request);
}
