package com.wanmi.sbc.customer.api.request.invoice;

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
public class CustomerInvoicePageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;
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

    /**
     * 批量查询-增票资质表主键List
     */
    @Schema(description = "增票资质主键List")
    private List<Long> customerInvoiceIdList;

    @Schema(description = "批量增票资质id")
    private List<Long> idList;

    private InvoiceStyle invoiceStyle;
}
