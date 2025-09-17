package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description  提交订单 礼品卡使用信息
 * @author  wur
 * @date: 2022/12/13 16:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class GiftCardTradeCommitVO extends BasicResponse {
    /**
     * 用户礼品卡Id
     */
    @Schema(description = "用户礼品卡Id")
    private Long userGiftCardId;

    /**
     * 预估使用金额
     */
    @Schema(description = "预估使用金额")
    private BigDecimal usePrice;
}
