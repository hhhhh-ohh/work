package com.wanmi.sbc.elastic.provider.impl.customer;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailPageRequest;
import com.wanmi.sbc.elastic.api.response.customer.EsCustomerDetailPageResponse;
import com.wanmi.sbc.elastic.bean.vo.customer.EsCustomerDetailVO;
import com.wanmi.sbc.elastic.customer.model.root.EsCustomerDetail;
import com.wanmi.sbc.elastic.customer.service.EsCustomerDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@Validated
public class EsCustomerDetailQueryController implements EsCustomerDetailQueryProvider {

    @Autowired
    private EsCustomerDetailService esCustomerDetailService;

    @Override
    public BaseResponse<EsCustomerDetailPageResponse> page(@RequestBody @Valid EsCustomerDetailPageRequest request) {
        Page<EsCustomerDetail> page = esCustomerDetailService.page(request);
        List<EsCustomerDetail> esCustomerDetailList = page.getContent();
        if (CollectionUtils.isEmpty(esCustomerDetailList)){
           return BaseResponse.success(new EsCustomerDetailPageResponse(Lists.newArrayList(),page.getTotalElements(),request.getPageNum()));
        }
        List<EsCustomerDetailVO>  esCustomerDetailVOList = esCustomerDetailService.wrapperEsCustomerDetailVO(esCustomerDetailList,request);
        if (request.getFillArea()) {
            esCustomerDetailService.fillArea(esCustomerDetailVOList);
        }
        return BaseResponse.success(new EsCustomerDetailPageResponse(esCustomerDetailVOList,page.getTotalElements(),request.getPageNum()));
    }
}
