package com.wanmi.sbc.order.api.provider.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.trade.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Description: 供应商订单查询
 * @Autho qiaokang
 * @Date：2020-03-27 09:08
 */
@FeignClient(value = "${application.order.name}", contextId = "ProviderTradeQueryProvider")
public interface ProviderTradeQueryProvider {

    /**
     * 条件分页
     *
     * @param tradePageCriteriaRequest 带参分页参数
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/provider-page-criteria")
    BaseResponse<TradePageCriteriaResponse> providerPageCriteria(@RequestBody @Valid ProviderTradePageCriteriaRequest tradePageCriteriaRequest);

    /**
     * 通过id获取交易单信息
     *
     * @param tradeGetByIdRequest 交易单id {@link TradeGetByIdRequest}
     * @return 交易单信息 {@link TradeGetByIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/provider-get-by-id")
    BaseResponse<TradeGetByIdResponse> providerGetById(@RequestBody @Valid TradeGetByIdRequest tradeGetByIdRequest);

    /**
     * 通过ids批量获取交易单信息
     *
     * @param providerTradeGetByIdListRequest 交易单id {@link TradeGetByIdRequest}
     * @return 交易单信息 {@link TradeGetByIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/provider-get-by-ids")
    BaseResponse<ProviderTradeGetByIdsResponse> providerGetByIds(@RequestBody @Valid ProviderTradeGetByIdListRequest providerTradeGetByIdListRequest);

    /**
     * 通过父订单号获取交易单集合
     *
     * @param tradeListByParentIdRequest 父交易单id {@link TradeListByParentIdRequest}
     * @return 交易单信息 {@link TradeListByParentIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-provider-list-by-parent-id")
    BaseResponse<TradeListByParentIdResponse> getProviderListByParentId(@RequestBody @Valid TradeListByParentIdRequest tradeListByParentIdRequest);

    /**
     * 查询导出数据
     *
     * @param tradeListExportRequest 查询条件 {@link TradeListExportRequest}
     * @return 验证结果 {@link TradeListExportResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/provider-list-export")
    BaseResponse<TradeListExportResponse> providerListTradeExport(@RequestBody @Valid TradeListExportRequest tradeListExportRequest);

    /**
     * 查询导出数据量
     *
     * @param tradeListExportRequest 查询条件 {@link TradeListExportRequest}
     * @return 验证结果 {@link TradeListExportResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/count-provider-export")
    BaseResponse<Long> countProviderTradeExport(@RequestBody @Valid TradeListExportRequest tradeListExportRequest);

    /**
     * 条件分页
     *
     * @param tradeCountCriteriaRequest 带参分页参数 {@link TradeCountCriteriaRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/provider-count-criteria")
    BaseResponse<ProviderTradeCountCriteriaResponse> countCriteria(@RequestBody @Valid ProviderTradeCountCriteriaRequest tradeCountCriteriaRequest);

}
