package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.springframework.validation.annotation.Validated;

/**
 * @author lvzhenwei
 * @className GiftCardBillPageRequest
 * @description 分页查询礼品卡使用记录request
 * @date 2022/12/10 5:45 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class GiftCardBillForUserPageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -1696339390121651047L;

    /**
     * 会员礼品卡Id
     */
    @Schema(description = "会员礼品卡Id")
    @NotNull
    private Long userGiftCardId;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

}
