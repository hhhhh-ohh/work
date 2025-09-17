package com.wanmi.sbc.marketing.api.response.giftcard;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lvzhenwei
 * @className UserGiftCardBalanceQueryResponse
 * @description 会员礼品卡余额 response
 * @date 2022/12/12 10:03 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardBalanceQueryResponse implements Serializable {
    private static final long serialVersionUID = -1945025905690136141L;

    @Schema(description = "会员id")
    private String customerId;

    @Schema(description = "会员礼品卡余额")
    private BigDecimal giftCardBalance;

}
