package com.wanmi.sbc.order.api.provider.paytimeseries;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesAddRequest;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesAddResponse;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesModifyRequest;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * 支付流水记录保存服务Provider
 *
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@FeignClient(value = "${application.order.name}", contextId = "PayTimeSeriesProvider")
public interface PayTimeSeriesProvider {

    /**
     * 新增支付流水记录API
     *
     * @author zhanggaolei
     * @param payTimeSeriesAddRequest 支付流水记录新增参数结构 {@link PayTimeSeriesAddRequest}
     * @return 新增的支付流水记录信息 {@link PayTimeSeriesAddResponse}
     */
    @PostMapping("/order/${application.order.version}/paytimeseries/add")
    BaseResponse add(
            @RequestBody @Valid PayTimeSeriesAddRequest payTimeSeriesAddRequest);

    /**
     * 修改支付流水记录API
     *
     * @author zhanggaolei
     * @param payTimeSeriesModifyRequest 支付流水记录修改参数结构 {@link PayTimeSeriesModifyRequest}
     * @return 修改的支付流水记录信息 {@link PayTimeSeriesModifyResponse}
     */
    @PostMapping("/order/${application.order.version}/paytimeseries/modify")
    BaseResponse<PayTimeSeriesModifyResponse> modify(
            @RequestBody @Valid PayTimeSeriesModifyRequest payTimeSeriesModifyRequest);
}
