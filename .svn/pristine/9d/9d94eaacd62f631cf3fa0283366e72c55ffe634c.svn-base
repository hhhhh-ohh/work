package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据动态条件统计退单请求结构
 * Created by jinwei on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ReturnCountByConditionRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "退单编号")
    private String rid;

    @Schema(description = "订单编号")
    private String tid;

    /**
     * 购买人编号
     */
    @Schema(description = "购买人id")
    private String buyerId;

    /**
     * 邀请人id
     */
    @Schema(description = "邀请人id，用于查询从店铺精选下的单")
    private String inviteeId;

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
    @Schema(description = "退单流程状态")
    private ReturnFlowState returnFlowState;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String skuName;

    @Schema(description = "skuNo")
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
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

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
     * 供应商编码
     */
    @Schema(description = "供应商编码")
    private String providerCode;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

    @Schema(description = "供应商公司id")
    private Long providerCompanyInfoId;

    /**
     * 业务员id集合
     */
    private List<String> employeeIds;

    /**
     * 子订单号
     */
    @Schema(description = "子订单号")
    private String ptid;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型")
    private StoreType storeType;

    /**
     * 平台类型
     */
    @Schema(description = "平台类型")
    private Platform platform;

    /**
     * 带货视频号
     */
    @Schema(description = "带货视频号")
    private String videoName;

    /**
     * 场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）
     */
    @Schema(description = "场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）")
    private Integer sceneGroup;

    /**
     * 代销平台标识
     */
    @Schema(description = "代销平台标识")
    private SellPlatformType sellPlatformType;
}
