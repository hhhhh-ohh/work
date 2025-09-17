package com.wanmi.sbc.account.api.response.credit;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author houshuai
 * @date 2021/3/3 10:27
 * @description <p>  </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditRepayDetailResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderNo;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String skuName;

    /**
     * 商品图片
     */
    @Schema(description = "图片")
    private List<String> urlList;

    /**
     * 订单应还款金额
     */
    @Schema(description = "订单应还款金额")
    private BigDecimal orderPrice;

    /**
     * 订单原始金额
     */
    @Schema(description = "订单原始金额")
    private BigDecimal orderOriginPrice;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态")
    private String payOrderStatus;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private String flowState;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Integer goodsNum;
}
