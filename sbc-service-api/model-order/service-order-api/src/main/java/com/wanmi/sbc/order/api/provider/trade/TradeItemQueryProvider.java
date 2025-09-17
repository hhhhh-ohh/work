package com.wanmi.sbc.order.api.provider.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.trade.TradeItemByCustomerIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeItemSnapshotByCustomerIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeItemSnapshotRequest;
import com.wanmi.sbc.order.api.response.trade.TradeItemByCustomerIdResponse;
import com.wanmi.sbc.order.api.response.trade.TradeItemSnapshotByCustomerIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>订单商品服务查询接口</p>
 * @Author: daiyitian
 * @Description: 退单服务查询接口
 * @Date: 2018-12-03 15:40
 */
@FeignClient(value = "${application.order.name}", contextId = "TradeItemQueryProvider")
public interface TradeItemQueryProvider {

    /**
     * 根据客户id查询已确认订单商品快照
     *
     * @param request 根据客户id查询已确认订单商品快照请求结构 {@link TradeItemByCustomerIdRequest}
     * @return 订单商品快照列表 {@link TradeItemByCustomerIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/list-by-terminal-token")
    BaseResponse<TradeItemByCustomerIdResponse> listByTerminalToken(@RequestBody @Valid TradeItemByCustomerIdRequest
                                                                         request);


    /**
     * 根据客户id查询已确认订单商品快照
     *
     * @param request 根据客户id查询已确认订单商品快照请求结构 {@link TradeItemSnapshotByCustomerIdRequest}
     * @return 订单快照对象 {@link TradeItemSnapshotByCustomerIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/snapshot/list-by-terminal-token")
    BaseResponse<TradeItemSnapshotByCustomerIdResponse> listByTerminalToken(@RequestBody @Valid TradeItemSnapshotByCustomerIdRequest
                                                                                           request);


    /**
     * 根据客户id查询已确认订单商品快照
     *
     * @param request 根据客户id查询已确认订单商品快照请求结构 {@link TradeItemSnapshotByCustomerIdRequest}
     * @return 订单快照对象 {@link TradeItemSnapshotByCustomerIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/snapshot/list-by-terminal-token-without")
    BaseResponse<TradeItemSnapshotByCustomerIdResponse> listByTerminalTokenWithout(@RequestBody @Valid TradeItemSnapshotByCustomerIdRequest
                                                                                    request);


    /**
     * 组装订单商品快照
     *
     * @param request 保存订单商品快照请求结构 {@link TradeItemSnapshotRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/item/assembleSnapshot")
    BaseResponse<TradeItemSnapshotByCustomerIdResponse> assembleSnapshot(@RequestBody @Valid TradeItemSnapshotRequest request);
}
