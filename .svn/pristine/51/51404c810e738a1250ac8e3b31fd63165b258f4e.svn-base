package com.wanmi.sbc.order.provider.impl.paytimeseries;

import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesByBusinessIdRequest;
import com.wanmi.sbc.order.util.mapper.PayTimeSeriesMapper;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesQueryProvider;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesQueryRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesListRequest;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesListResponse;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesByIdRequest;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesByIdResponse;
import com.wanmi.sbc.order.bean.vo.PayTimeSeriesVO;
import com.wanmi.sbc.order.paytimeseries.service.PayTimeSeriesService;
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 支付流水记录查询服务接口实现
 *
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@RestController
@Validated
public class PayTimeSeriesQueryController implements PayTimeSeriesQueryProvider {
    @Autowired private PayTimeSeriesService payTimeSeriesService;

    @Autowired private PayTimeSeriesMapper payTimeSeriesMapper;

    @Override
    public BaseResponse<PayTimeSeriesListResponse> list(
            @RequestBody @Valid PayTimeSeriesListRequest payTimeSeriesListReq) {
        PayTimeSeriesQueryRequest queryReq =
                KsBeanUtil.convert(payTimeSeriesListReq, PayTimeSeriesQueryRequest.class);
        List<PayTimeSeries> payTimeSeriesList = payTimeSeriesService.list(queryReq);
        List<PayTimeSeriesVO> newList =
                payTimeSeriesMapper.payTimeSeriesListToPayTimeSeriesVOList(payTimeSeriesList);
        return BaseResponse.success(new PayTimeSeriesListResponse(newList));
    }

    @Override
    public BaseResponse<PayTimeSeriesByIdResponse> getById(
            @RequestBody @Valid PayTimeSeriesByIdRequest payTimeSeriesByIdRequest) {
        PayTimeSeries payTimeSeries =
                payTimeSeriesService.getOne(payTimeSeriesByIdRequest.getPayNo());
        return BaseResponse.success(
                new PayTimeSeriesByIdResponse(payTimeSeriesMapper.payTimeSeriesToPayTimeSeriesVO(payTimeSeries)));
    }

    public BaseResponse<PayTimeSeriesListResponse> getDuplicatePay(@RequestBody @Valid PayTimeSeriesByBusinessIdRequest request){
        List<PayTimeSeries> payTimeSeriesList = payTimeSeriesService.getDuplicatePay(request.getBusinessId());
        List<PayTimeSeriesVO> newList =
                payTimeSeriesMapper.payTimeSeriesListToPayTimeSeriesVOList(payTimeSeriesList);
        return BaseResponse.success(new PayTimeSeriesListResponse(newList));
    }

}
