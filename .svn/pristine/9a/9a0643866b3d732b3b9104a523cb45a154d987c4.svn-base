package com.wanmi.sbc.marketing.api.request.giftcard;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.io.Serializable;

/**
 * @author lvzhenwei
 * @className ExchangeGiftCardRequest
 * @description 礼品卡兑换
 * @date 2022/12/10 9:45 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeGiftCardRequest implements Serializable {

    private static final long serialVersionUID = -6625811352545261818L;

    /**
     * 礼品卡卡号
     */
    @Schema(description = "礼品卡卡号")
    @NotBlank
    private String giftCardNo;

    /**
     * 礼品卡兑换码
     */
    @Schema(description = "礼品卡兑换码")
    @NotBlank
    private String exchangeCode;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;
}
