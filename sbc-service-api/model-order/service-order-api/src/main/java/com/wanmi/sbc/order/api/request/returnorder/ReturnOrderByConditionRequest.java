package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.enums.ReturnOrderType;
import com.wanmi.sbc.order.bean.vo.CrossGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据动态条件查询退单列表请求结构
 * Created by jinwei on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ReturnOrderByConditionRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "退单编号")
    private String rid;

    @Schema(description = "订单编号")
    private String tid;

    @Schema(description = "订单编号List")
    private List<String> tids;

    /**
     * 尾款退单号
     */
    @Schema(description = "尾款退单号")
    private String businessTailId;

    /**
     * 购买人编号
     */
    @Schema(description = "购买人id")
    private String buyerId;

    @Schema(description = "购买人姓名")
    private String buyerName;

    @Schema(description = "购买人账户")
    private String buyerAccount;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    private String consigneeName;

    @Schema(description = "手机号")
    private String consigneePhone;
    /**
     * 跨境商品信息
     */
    @Schema(description = "跨境商品信息")
    private CrossGoodsInfoVO crossGoodsInfo;
    /**
     * 退单流程状态
     */
    @Schema(description = "退单流程状态")
    private ReturnFlowState returnFlowState;
    /**
     * 退单类型 0 是普通退单 1是跨境退单
     */
    @Schema(description = "退单类型 0 是普通退单 1是跨境退单")
    private ReturnOrderType returnOrderType;
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

    /**
     * 供应商公司id
     */
    @Schema(description = "供应商公司id")
    private Long providerCompanyInfoId;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

    /**
     * 供应商编码
     */
    @Schema(description = "供应商编码")
    private String providerCode;

    /**
     * 业务员id集合
     */
    private List<String> employeeIds;

    /**
     * 子订单号
     */
    @Schema(description = "子订单号")
    private String ptid;

}
