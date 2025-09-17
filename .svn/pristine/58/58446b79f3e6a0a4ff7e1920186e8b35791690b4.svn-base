package com.wanmi.sbc.order.provider.impl.paytimeseries;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesProvider;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesAddRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesModifyRequest;
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import com.wanmi.sbc.order.paytimeseries.service.PayTimeSeriesService;
import com.wanmi.sbc.order.util.mapper.PayTimeSeriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * 支付流水记录保存服务接口实现
 *
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@RestController
@Validated
public class PayTimeSeriesController implements PayTimeSeriesProvider {
    @Autowired private PayTimeSeriesService payTimeSeriesService;

    @Autowired private PayTimeSeriesMapper payTimeSeriesMapper;

    @Override
    public BaseResponse add(
            @RequestBody @Valid PayTimeSeriesAddRequest payTimeSeriesAddRequest) {
        PayTimeSeries payTimeSeries =
                payTimeSeriesMapper.payTimeSeriesAddRequestToPayTimeSeries(payTimeSeriesAddRequest);
        payTimeSeriesService.add(payTimeSeries);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modify(
            @RequestBody @Valid PayTimeSeriesModifyRequest payTimeSeriesModifyRequest) {
        PayTimeSeries payTimeSeries =
                payTimeSeriesMapper.payTimeSeriesModifyRequestToPayTimeSeries(payTimeSeriesModifyRequest);
        payTimeSeriesService.modify(payTimeSeries);
        return BaseResponse.SUCCESSFUL();
    }
}
