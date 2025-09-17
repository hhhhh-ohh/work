package com.wanmi.sbc.empower.provider.impl.logisticssetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.logisticssetting.ExpressQueryProvider;
import com.wanmi.sbc.empower.api.request.logisticssetting.DeliveryQueryRequest;
import com.wanmi.sbc.empower.api.response.logisticssetting.DeliveryQueryResponse;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import com.wanmi.sbc.empower.logisticssetting.service.LogisticsQueryBaseService;
import com.wanmi.sbc.empower.logisticssetting.service.LogisticsQueryServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author: songhanlin
 * @Date: Created In 下午4:11 2021/4/12
 * @Description: 查询物流信息
 */
@RestController
@Validated
public class ExpressQueryController implements ExpressQueryProvider {

    @Autowired
    private LogisticsQueryServiceFactory logisticsQueryServiceFactory;

    @Override
    public BaseResponse<DeliveryQueryResponse> expressInfoQuery(@RequestBody @Valid DeliveryQueryRequest request) {
        LogisticsQueryBaseService logisticsQueryBaseService =
                logisticsQueryServiceFactory.create(LogisticsType.KUAI_DI_100);

        DeliveryQueryResponse response = new DeliveryQueryResponse();
        response.setOrderList(logisticsQueryBaseService.queryExpressInfo(request));
        return BaseResponse.success(response);
    }

}
