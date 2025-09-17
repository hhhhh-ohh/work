package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据状态统计退单请求结构
 * Created by jinwei on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ReturnOrderCountByFlowStateRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "退单id")
    private String rid;

    @Schema(description = "订单id")
    private String tid;

    /**
     * 购买人编号
     */
    @Schema(description = "购买人编号")
    private String buyerId;

    @Schema(description = "购买人姓名")
    private String buyerName;

    @Schema(description = "购买人账号")
    private String buyerAccount;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    private String consigneeName;

    @Schema(description = "手机号")
    private String consigneePhone;

    /**
     * 退单流程状态
     */
    @NotNull
    @Schema(description = "退单流程状态")
    private ReturnFlowState returnFlowState;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String skuName;

    @Schema(description = "sku编号")
    private String skuNo;

    /**
     * 退单编号列表
     */
    @Schema(description = "退单编号列表")
    private String[] rids;

    /**
     * 退单创建开始时间，精确到天
     */
    @Schema(description = "退单创建开始时间，精确到天")
    private String beginTime;

    /**
     * 退单创建结束时间，精确到天
     */
    @Schema(description = "退单创建结束时间，精确到天")
    private String endTime;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String supplierCode;

    /**
     * 业务员id
     */
    @Schema(description = "业务员id")
    private String employeeId;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Object[] customerIds;

    /**
     * pc搜索条件
     */
    @Schema(description = "pc搜索条件")
    private String tradeOrSkuName;

}
