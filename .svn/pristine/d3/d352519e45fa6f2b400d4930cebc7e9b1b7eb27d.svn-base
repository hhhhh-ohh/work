package com.wanmi.sbc.order.api.provider.paytimeseries;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesByBusinessIdRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesListRequest;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesListResponse;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesByIdRequest;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * 支付流水记录查询服务Provider
 *
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@FeignClient(value = "${application.order.name}", contextId = "PayTimeSeriesQueryProvider")
public interface PayTimeSeriesQueryProvider {


    /**
     * 列表查询支付流水记录API
     *
     * @author zhanggaolei
     * @param payTimeSeriesListReq 列表请求参数和筛选对象 {@link PayTimeSeriesListRequest}
     * @return 支付流水记录的列表信息 {@link PayTimeSeriesListResponse}
     */
    @PostMapping("/order/${application.order.version}/paytimeseries/list")
    BaseResponse<PayTimeSeriesListResponse> list(
            @RequestBody @Valid PayTimeSeriesListRequest payTimeSeriesListReq);

    /**
     * 单个查询支付流水记录API
     *
     * @author zhanggaolei
     * @param payTimeSeriesByIdRequest 单个查询支付流水记录请求参数 {@link PayTimeSeriesByIdRequest}
     * @return 支付流水记录详情 {@link PayTimeSeriesByIdResponse}
     */
    @PostMapping("/order/${application.order.version}/paytimeseries/get-by-id")
    BaseResponse<PayTimeSeriesByIdResponse> getById(
            @RequestBody @Valid PayTimeSeriesByIdRequest payTimeSeriesByIdRequest);


    /**
     * 按照订单id查询重复支付的流水
     *
     * @author zhanggaolei
     * @param request 按照订单id查询重复支付的流水记录请求参数 {@link PayTimeSeriesByIdRequest}
     * @return 支付流水记录详情 {@link PayTimeSeriesByIdResponse}
     */
    @PostMapping("/order/${application.order.version}/paytimeseries/get-duplicate-Pay")
    BaseResponse<PayTimeSeriesListResponse> getDuplicatePay(@RequestBody @Valid PayTimeSeriesByBusinessIdRequest request);
}
