package com.wanmi.sbc.order.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.account.bean.enums.InvoiceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-03 10:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class OrderInvoiceModifyRequest extends BaseRequest {

    /**
     * 订单开票ID
     */
    @Schema(description = "订单开票ID")
    private String orderInvoiceId;

    /**
     * 发票类型
     */
    @Schema(description = "发票类型")
    private InvoiceType invoiceType;

    /**
     * 开票项id
     */
    @Schema(description = "开票项id")
    private String projectId;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String customerId;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderNo;

    /**
     * 发票title
     */
    @Schema(description = "发票title")
    private String invoiceTitle;

    /**
     * 发票地址
     */
    @Schema(description = "发票地址")
    private String invoiceAddress;

    /**
     * 开票时间
     */
    @Schema(description = "开票时间")
    private String invoiceTime;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    private String contacts;

    /**
     * 收货人联系号码
     */
    @Schema(description = "收货人联系号码")
    private String phone;

    /**
     * 收货地址
     */
    @Schema(description = "收货地址")
    private String address;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId = 0L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId = 0L;

    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
    private String taxpayerNumber;

    /**
     * 订单开票收货地址
     */
    @Schema(description = "订单开票收货地址")
    private String addressInfoId;


}
