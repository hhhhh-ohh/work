package com.wanmi.sbc.marketing.api.request.giftcard;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvzhenwei
 * @className ExchangeGiftCardRequest
 * @description 礼品卡激活
 * @date 2022/12/10 9:45 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivateGiftCardRequest implements Serializable {
    private static final long serialVersionUID = -4836721070618148061L;

    /**
     * 礼品卡卡号
     */
    @Schema(description = "礼品卡卡号")
    @NotBlank
    private String giftCardNo;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;
}
