package com.wanmi.sbc.elastic.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author houshuai
 * 订单开票查询入参
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsOrderInvoiceFindAllRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1171756302012537452L;
    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;

    /**
     * 流程状态（订单状态）
     */
    @Schema(description = "流程状态（订单状态）")
    private FlowState flowState;

    /**
     * 流程状态批量（订单状态）
     */
    @Schema(description = "流程状态批量（订单状态）")
    private List<FlowState> flowStates;

    /**
     * 付款状态0:已付款 1.未付款 2.待确认
     */
    @Schema(description = "付款状态0:已付款 1.未付款 2.待确认")
    private Integer payOrderStatus;

    /**
     * 开票状态 0待开票 1 已开票
     */
    @Schema(description = "开票状态")
    private Integer invoiceState;

    /**
     * 订单开票IDs
     */
    @Schema(description = "订单开票IDs")
    private List<String> orderInvoiceIds;

    /**
     * 查询退款开始时间，精确到天
     */
    @Schema(description = "查询退款开始时间，精确到天")
    private String beginTime;

    /**
     * 查询退款结束时间，精确到天
     */
    @Schema(description = "查询退款结束时间，精确到天")
    private String endTime;

    /**
     * token
     */
    @Schema(description = "token")
    private String token;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

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
     * 批量会员Ids
     */
    @Schema(description = "批量会员Ids")
    private List<String> customerIds;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private List<Long> companyInfoIds;

    /**
     * 门店/店铺名称
     */
    @Schema(description = "门店/店铺名称")
    private String storeName;
}