package com.wanmi.sbc.marketing.api.request.giftcard;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className UserPickupCardRequest
 * @description TODO
 * @date 2023/7/3 16:33
 **/
@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPickupCardRequest implements Serializable {
    @Schema(description = "用户礼品卡ID")
    @NotNull
    private Long userGiftCardId;

    @Schema(description = "订单ID")
    @NotEmpty
    private List<String> tIds;
}
