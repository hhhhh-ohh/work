package com.wanmi.sbc.elastic.api.request.customerInvoice;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 会员增票资质-分页Request
 */
@Schema
@Data
public class EsCustomerInvoicePageRequest extends BaseQueryRequest {

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private List<String> customerIds;

    /**
     * 单位全称
     */
    @Schema(description = "单位全称")
    private String companyName;

    /**
     * 增票资质审核状态  0:待审核 1:已审核通过 2:审核未通过
     */
    @Schema(description = "增票资质审核状态")
    private CheckState checkState;

    /**
     * 负责业务员
     */
    @Schema(description = "负责业务员")
    private String employeeId;

    /**
     * 负责业务员ID集合
     */
    @Schema(description = "负责业务员ID集合")
    private List<String> employeeIds;

    @Schema(description = "批量增票资质id")
    private List<Long> idList;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;

    private InvoiceStyle invoiceStyle;
}
