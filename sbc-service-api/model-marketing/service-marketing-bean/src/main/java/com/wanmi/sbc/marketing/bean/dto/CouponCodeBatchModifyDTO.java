package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 批量更新券码使用状态信息结构
 * @Author: gaomuwei
 * @Date: Created In 下午7:47 2018/10/9
 * @Description:
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeBatchModifyDTO implements Serializable {

    private static final long serialVersionUID = -6864391608042983101L;

    /**
     * 优惠券码id
     */
    @Schema(description = "优惠券码id")
    private String couponCodeId;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 使用状态
     */
    @Schema(description = "优惠券是否已使用")
    private DefaultFlag useStatus;

    /**
     * 使用的订单号
     */
    @Schema(description = "使用的订单号")
    private String orderCode;

}
