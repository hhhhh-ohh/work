package com.wanmi.sbc.order.api.request.orderinvoice;

import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.order.bean.enums.FlowState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class OrderInvoiceFindAllRequest extends BaseQueryRequest {

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
     * 付款状态0:已付款 1.未付款 2.待确认
     */
    private PayOrderStatus payOrderStatus;

    /**
     * 流程状态（订单状态）
     */
    @Schema(description = "流程状态（订单状态）")
    private FlowState flowState;

    /**
     * 开票状态 0待开票 1 已开票
     */
    @Schema(description = "开票状态")
    private InvoiceState invoiceState;

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
     * 商家id
     */
    @Schema(description = "商家id")
    private List<Long> companyInfoIds;

    /**
     * 订单开票IDs
     */
    @Schema(description = "订单开票IDs")
    private List<String> idList;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;

    /**
     * 封装公共条件
     * @return
     */
    private static String buildLike(String field) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append('%').append(XssUtils.replaceLikeWildcard(field)).append('%').toString();
    }
}