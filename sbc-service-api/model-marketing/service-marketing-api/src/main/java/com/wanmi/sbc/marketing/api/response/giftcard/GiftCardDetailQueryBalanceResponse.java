package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>礼品卡详情余额结果</p>
 * @author 马连峰
 * @date 2022-12-09 14:08:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailQueryBalanceResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 礼品卡余额
     */
    @Schema(description = "礼品卡余额")
    private BigDecimal balance;
}
