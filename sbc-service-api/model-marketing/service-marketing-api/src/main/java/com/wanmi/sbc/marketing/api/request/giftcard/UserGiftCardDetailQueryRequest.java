package com.wanmi.sbc.marketing.api.request.giftcard;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvzhenwei
 * @className UserGiftCardDetailQueryRequest
 * @description 获取会员礼品卡详情request
 * @date 2022/12/10 2:46 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardDetailQueryRequest implements Serializable {
    private static final long serialVersionUID = -2965239443020578322L;

    /**
     * 用户礼品卡id
     */
    @Schema(description = "用户礼品卡id")
    @NotNull
    private Long userGiftCardId;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

}
