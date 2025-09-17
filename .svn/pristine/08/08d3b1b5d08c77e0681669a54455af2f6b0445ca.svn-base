package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @author wur
 * @className GiftCardDeleteRequest
 * @description 礼品卡删除
 * @date 2022/12/8 16:29
 **/
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCardDeleteRequest extends BaseRequest {

    private static final long serialVersionUID = -2952406761735818859L;

    /**
     * 礼品卡Id
     */
    @Schema(description = "礼品卡Id")
    @NotNull
    private Long giftCardId;
}