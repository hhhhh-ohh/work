package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className PayingMemberInfoVO
 * @description 付费会员信息
 * @date 2022/5/17 2:23 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayingMemberInfoVO extends BasicResponse {
    private static final long serialVersionUID = -3614341831512044323L;

    /**
     * 付费会员等级id
     */
    @Schema(description = "付费会员等级id")
    private Integer levelId;

    /**
     * 付费记录id
     */
    @Schema(description = "付费记录id")
    private String recordId;

    /**
     * 商品优惠金额
     */
    private BigDecimal goodsDiscount;

    /**
     * 优惠券优惠金额
     */
    private BigDecimal couponDiscount;

    /**
     * 优惠金额：商品折扣优惠金额+优惠券优惠金额
     */
    @Schema(description = "优惠金额：商品折扣优惠金额+优惠券优惠金额")
    private BigDecimal totalDiscount;
}
