package com.wanmi.sbc.elastic.provider.impl.customerInvoice;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoicePageRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;
import com.wanmi.sbc.elastic.api.provider.customerInvoice.EsCustomerInvoiceProvider;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceAddRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceAuditingRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceDeleteRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceInvalidRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceModifyRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoicePageRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceRejectRequest;
import com.wanmi.sbc.elastic.customerInvoice.mapper.EsCustomerInvoiceMapper;
import com.wanmi.sbc.elastic.customerInvoice.model.root.EsCustomerInvoice;
import com.wanmi.sbc.elastic.customerInvoice.service.EsCustomerInvoiceService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
public class EsCustomerInvoiceController implements EsCustomerInvoiceProvider {
    private static final Integer DEFAULT_SIZE = 2000;

    @Autowired
    private EsCustomerInvoiceService esCustomerInvoiceService;

    @Autowired
    private EsCustomerInvoiceMapper esCustomerInvoiceMapper;

    @Override
    public BaseResponse init(@RequestBody @Valid EsCustomerInvoicePageRequest esCustomerInvoicePageRequest) {
        CustomerInvoicePageRequest pageRequest = esCustomerInvoiceMapper.esCustomerInvoicePageRequestToCustomerInvoicePageRequest(esCustomerInvoicePageRequest);
        //设置为2000
        pageRequest.setPageSize(DEFAULT_SIZE);
        //后端只展示增值税发票
//        pageRequest.setInvoiceStyle(InvoiceStyle.SPECIAL);
        List<Long> idList = esCustomerInvoicePageRequest.getIdList();
        if (CollectionUtils.isNotEmpty(idList)) {
            pageRequest.setCustomerInvoiceIdList(idList);
        }
        esCustomerInvoiceService.init(pageRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse auditing(@RequestBody @Valid EsCustomerInvoiceAuditingRequest esCustomerInvoiceAuditingRequest) {
        esCustomerInvoiceService.auditing(esCustomerInvoiceAuditingRequest.getCheckState(),esCustomerInvoiceAuditingRequest.getCustomerInvoiceIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse reject(@RequestBody @Valid EsCustomerInvoiceRejectRequest esCustomerInvoiceRejectRequest) {
        esCustomerInvoiceService.reject(esCustomerInvoiceRejectRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse invalid(@RequestBody @Valid EsCustomerInvoiceInvalidRequest esCustomerInvoiceInvalidRequest) {
        esCustomerInvoiceService.invalid(esCustomerInvoiceInvalidRequest.getCustomerInvoiceIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse delete(@RequestBody @Valid EsCustomerInvoiceDeleteRequest esCustomerInvoiceDeleteRequest) {
        esCustomerInvoiceService.delete(esCustomerInvoiceDeleteRequest.getCustomerInvoiceIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse add(@RequestBody @Valid EsCustomerInvoiceAddRequest esCustomerInvoiceAddRequest) {
        EsCustomerInvoice esCustomerInvoice = esCustomerInvoiceMapper.esCustomerInvoiceAddRequestToEsCustomerInvoice(esCustomerInvoiceAddRequest);
        esCustomerInvoice.setCheckState(CheckState.CHECKED);
        esCustomerInvoice.setInvalidFlag(InvalidFlag.NO);
        esCustomerInvoice.setDelFlag(DeleteFlag.NO);
        esCustomerInvoice.setCreateTime(LocalDateTime.now());
        esCustomerInvoiceService.add(esCustomerInvoice,esCustomerInvoiceAddRequest.getEmployeeId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modify(@RequestBody @Valid EsCustomerInvoiceModifyRequest esCustomerInvoiceModifyRequest) {
        EsCustomerInvoice esCustomerInvoice = esCustomerInvoiceMapper.esCustomerInvoiceModifyRequestToEsCustomerInvoice(esCustomerInvoiceModifyRequest);
        esCustomerInvoiceService.modify(esCustomerInvoice,esCustomerInvoiceModifyRequest.getEmployeeId());
        return BaseResponse.SUCCESSFUL();
    }
}


