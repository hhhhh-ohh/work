package com.wanmi.sbc.elastic.api.provider.customerInvoice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.customerInvoice.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


@FeignClient(value = "${application.elastic.name}", contextId = "EsCustomerInvoiceProvider")
public interface EsCustomerInvoiceProvider {

    /**
     * Es init数据
     *
     * @param esCustomerInvoicePageRequest {@link EsCustomerInvoicePageRequest}
     * @return 员工列表信息（带分页）{@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/esCustomerInvoice/init")
    BaseResponse init(@RequestBody @Valid EsCustomerInvoicePageRequest esCustomerInvoicePageRequest);


    /**
     * 单条 / 批量审核 增专票信息
     *
     * @param esCustomerInvoiceAuditingRequest {@link EsCustomerInvoiceAuditingRequest}
     * @return 审核专票结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/esCustomerInvoice/auditing")
    BaseResponse auditing(@RequestBody @Valid EsCustomerInvoiceAuditingRequest esCustomerInvoiceAuditingRequest);


    /**
     * 驳回增专票信息
     *
     * @param esCustomerInvoiceRejectRequest {@link EsCustomerInvoiceRejectRequest}
     * @return 驳回专票结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/esCustomerInvoice/reject")
    BaseResponse reject(@RequestBody @Valid EsCustomerInvoiceRejectRequest esCustomerInvoiceRejectRequest);


    /**
     * 作废 增专票信息
     *
     * @param esCustomerInvoiceInvalidRequest {@link EsCustomerInvoiceInvalidRequest}
     * @return 作废专票结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/esCustomerInvoice/invalid")
    BaseResponse invalid(@RequestBody @Valid EsCustomerInvoiceInvalidRequest esCustomerInvoiceInvalidRequest);


    /**
     * 删除 增专票信息
     *
     * @param esCustomerInvoiceDeleteRequest {@link EsCustomerInvoiceDeleteRequest}
     * @return 删除专票结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/esCustomerInvoice/delete")
    BaseResponse delete(@RequestBody @Valid EsCustomerInvoiceDeleteRequest esCustomerInvoiceDeleteRequest);


    /**
     * 保存客户的增专票信息
     * boss端新增增票信息，增票状态都是已审核
     * 客户端新增增票信息，增票状态都是待审核
     * @param esCustomerInvoiceAddRequest {@link EsCustomerInvoiceAddRequest}
     * @return 新增专票结果
     */
    @PostMapping("/elastic/${application.elastic.version}/esCustomerInvoice/add")
    BaseResponse add(@RequestBody @Valid EsCustomerInvoiceAddRequest esCustomerInvoiceAddRequest);

    /**
     * 修改客户的增专票信息
     * boss端修改增票信息，增票状态都是已审核
     * 客户端修改增票信息，增票状态都是待审核
     *
     * @param esCustomerInvoiceModifyRequest {@link EsCustomerInvoiceModifyRequest}
     * @return 修改专票结果
     */
    @PostMapping("/customer/${application.customer.version}/invoice/modify")
    BaseResponse modify(@RequestBody @Valid EsCustomerInvoiceModifyRequest esCustomerInvoiceModifyRequest);

}
