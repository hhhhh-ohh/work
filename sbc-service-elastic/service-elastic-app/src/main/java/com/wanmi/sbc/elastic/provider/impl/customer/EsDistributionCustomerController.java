package com.wanmi.sbc.elastic.provider.impl.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerModifyRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerPageRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerAddRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerModifyRequest;
import com.wanmi.sbc.elastic.api.response.customer.EsDistributionCustomerAddResponse;
import com.wanmi.sbc.elastic.customer.service.EsDistributionCustomerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author: HouShuai
 * @date: 2020/12/8 17:03
 * @description: 分销员Api
 */
@RestController
public class EsDistributionCustomerController implements EsDistributionCustomerProvider {

    @Autowired
    private EsDistributionCustomerService esDistributionCustomerService;

    /**
     * 新增分销员
     * @param request
     * @return
     */
    @Override
    public BaseResponse<EsDistributionCustomerAddResponse> add(@RequestBody @Valid EsDistributionCustomerAddRequest request) {
        return esDistributionCustomerService.add(request);
    }

    /**
     * 初始化分销员信息
     * @param request
     * @return
     */
    @Override
    public BaseResponse init(@RequestBody EsDistributionCustomerInitRequest request) {
        esDistributionCustomerService.init(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 编辑分销员
     * @param request
     * @return
     */
    @Override
    public BaseResponse<EsDistributionCustomerAddResponse> modify(@RequestBody @Valid EsDistributionCustomerModifyRequest request) {
        esDistributionCustomerService.modify(request.getCustomerId(),request.getCustomerName());
        return BaseResponse.SUCCESSFUL();
    }
}
