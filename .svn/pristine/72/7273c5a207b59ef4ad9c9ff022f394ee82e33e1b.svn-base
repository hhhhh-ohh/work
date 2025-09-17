package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.marketing.bean.enums.GiftCardStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvzhenwei
 * @className UserGiftCardBalanceQueryRequest
 * @description 用户礼品卡余额查询 request
 * @date 2022/12/12 9:59 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardBalanceQueryRequest implements Serializable {
    private static final long serialVersionUID = -6384334099714387896L;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    @NotBlank
    private String customerId;

    @Schema(description = "礼品卡状态")
    @NotNull
    private GiftCardStatus giftCardStatus;
}
