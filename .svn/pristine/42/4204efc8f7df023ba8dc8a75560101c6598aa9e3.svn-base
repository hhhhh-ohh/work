package com.wanmi.sbc.order.util.mapper;

import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesAddRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesModifyRequest;
import com.wanmi.sbc.order.bean.vo.PayTimeSeriesVO;
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author zhanggaolei
 * @type PayTimeSeriesMapper.java
 * @desc
 * @date 2022/12/8 16:16
 */
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface PayTimeSeriesMapper {

    @Mappings({})
    PayTimeSeries payTimeSeriesAddRequestToPayTimeSeries(PayTimeSeriesAddRequest bean);

    @Mappings({})
    PayTimeSeries payTimeSeriesModifyRequestToPayTimeSeries(PayTimeSeriesModifyRequest bean);

    @Mappings({})
    PayTradeRecord payTimeSeriesToPayTradeRecord(PayTimeSeries bean);

    @Mappings({})
    PayTimeSeriesVO payTimeSeriesToPayTimeSeriesVO(PayTimeSeries bean);

    @Mappings({})
    List<PayTimeSeriesVO> payTimeSeriesListToPayTimeSeriesVOList(List<PayTimeSeries> bean);

}
