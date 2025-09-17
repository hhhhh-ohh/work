package com.wanmi.sbc.order.bean.dto;


import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


/**
 * 退款单
 * Created by zhangjin on 2017/4/21.
 */
@Data
@Schema
public class RefundOrderRequestDTO extends BaseQueryRequest {

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 退单编号
     */
    @Schema(description = "退单编号")
    private String returnOrderCode;

    /**
     * 退单编号列表
     */
    @Schema(description = "退单编号列表")
    private List<String> returnOrderCodes;

    /**
     * 收款流水号
     */
    @Schema(description = "收款流水号")
    private String refundBillCode;

    /**
     * 支付渠道id
     */
    @Schema(description = "支付渠道id")
    private Integer payChannelId;

    /**
     * 0 在线支付 1线下支付
     */
    @Schema(description = "支付方式",contentSchema = com.wanmi.sbc.account.bean.enums.PayType.class)
    private Integer payType;

    /**
     * 退款单状态
     */
    @Schema(description = "退款单状态")
    private RefundStatus refundStatus;

    /**
     * 排除退款单状态
     * 处理平台不可见：退款，拒绝退款的订单
     */
    @Schema(description = "排除退款单状态")
    private Boolean excludeRefundStatus;

    /**
     * 账号id
     */
    @Schema(description = "账号id")
    private String accountId;

    /**
     * 退款单主键
     */
    @Schema(description = "退款单主键")
    private List<String> refundIds;

    /**
     * token
     */
    @Schema(description = "token")
    private String token;

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
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家编码
     */
    @Schema(description = "商家编码")
    private Long supplierId;

    /**
     * 多个商家ids
     */
    @Schema(description = "多个商家ids")
    private List<Long> companyInfoIds;

    /**
     * 多个会员详细ids
     */
    @Schema(description = "多个会员详细ids")
    private List<String> customerDetailIds;


    /**
     * 门店/店铺名称
     */
    @Schema(description = "门店/店铺名称")
    private String storeName;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型")
    private StoreType storeType;

}
