package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 物流信息
 * @author wumeng[OF2627]
 *         company qianmi.com
 *         Date 2017-04-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LogisticsVO extends BasicResponse {
    /**
     * 物流配送方式编号
     */
    @Schema(description = "物流配送方式编号")
    private String shipMethodId;

    /**
     * 物流配送方式名称
     */
    @Schema(description = "物流配送方式名称")
    private String shipMethodName;

    /**
     * 物流号
     */
    @Schema(description = "物流号")
    private String logisticNo;

    /**
     * 物流费
     */
    @Schema(description = "物流费")
    private BigDecimal logisticFee;

    /**
     * 物流公司编号
     */
    @Schema(description = "物流公司编号")
    private String logisticCompanyId;

    /**
     * 物流公司名称
     */
    @Schema(description = "物流公司名称")
    private String logisticCompanyName;

    /**
     * 物流公司标准编码
     */
    @Schema(description = "物流公司标准编码")
    private String logisticStandardCode;

    /**
     * 第三方平台物流对应的订单id
     */
    @Schema(description = "第三方平台物流对应的订单id")
    private String thirdPlatformOrderId;

    /**
     * 第三方平台外部订单id
     * linkedmall --> 淘宝订单号
     */
    @Schema(description = "第三方平台外部订单id，linkedmall --> 淘宝订单号")
    private String outOrderId;

    /**
     * 购买用户id
     */
    @Schema(description = "购买用户id")
    private String buyerId;

    /**
     * 扩展信息
     */
    @Schema(description = "扩展信息 跨境订单：tradeCreateResult（SUCCESS 成功  其他失败原因）电子面单结果 tradeCreateTime：电子面单下单时间")
    private Object extendedAttributes;

    /**
     * 微信物流信息token
     */
    @Schema(description = "微信物流信息token", hidden = true)
    private String waybillToken;

    /**
     * 微信物流信息状态:  0: 运单不存在或者未揽收、1: 已揽件、2: 运输中、3: 派件中、4: 已签收、5: 异常、6: 代签收
     */
    @Schema(description = "微信物流信息状态:  0: 运单不存在或者未揽收、1: 已揽件、2: 运输中、3: 派件中、4: 已签收、5: 异常、6: 代签收", hidden = true)
    private String waybillStatus;

}
