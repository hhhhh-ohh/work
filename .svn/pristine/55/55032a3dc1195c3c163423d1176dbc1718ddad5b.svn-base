package com.wanmi.sbc.elastic.provider.impl.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerBatchModifyRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerPageRequest;
import com.wanmi.sbc.elastic.api.response.customer.DistributionCustomerExportResponse;
import com.wanmi.sbc.elastic.api.response.customer.EsDistributionCustomerListResponse;
import com.wanmi.sbc.elastic.api.response.customer.EsDistributionCustomerPageResponse;
import com.wanmi.sbc.elastic.customer.service.EsDistributionCustomerQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author: HouShuai
 * @date: 2020/12/7 11:26
 * @description: 分销员查询接口
 */
@RestController
public class EsDistributionCustomerPageController implements EsDistributionCustomerQueryProvider {


    @Autowired
    private EsDistributionCustomerQueryService esDistributionCustomerService;


    /**
     * 分页查询分销员信息
     * @param request
     * @return
     */
    @Override
    public BaseResponse<EsDistributionCustomerPageResponse> page(@RequestBody @Valid EsDistributionCustomerPageRequest request) {
        EsDistributionCustomerPageResponse page = esDistributionCustomerService.page(request);
        return BaseResponse.success(page);
    }

    /**
     * 根据id查询分销员信息
     * @param queryRequest
     * @return
     */
    @Override
    public BaseResponse<EsDistributionCustomerListResponse> listByIds(@RequestBody @Valid EsDistributionCustomerBatchModifyRequest queryRequest) {

        return esDistributionCustomerService.listByIds(queryRequest);
    }

    /**
     * 导出分销员信息
     * @param request
     * @return
     */
    @Override
    public BaseResponse<DistributionCustomerExportResponse> export(@RequestBody @Valid EsDistributionCustomerPageRequest request) {
        return esDistributionCustomerService.export(request);
    }

    @Override
    public BaseResponse<Long> countForCount(@RequestBody @Valid EsDistributionCustomerPageRequest request) {
        Long total = esDistributionCustomerService.count(request);
        return BaseResponse.success(total);
    }
}
