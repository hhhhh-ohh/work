package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author lvzhenwei
 * @className GiftCardCancelRequest
 * @description 礼品卡销卡request
 * @date 2022/12/14 4:46 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardCancelRequest implements Serializable {
    private static final long serialVersionUID = 5036585577605234040L;

    /**
     * 礼品卡卡号
     */
    @Schema(description = "礼品卡卡号")
    @NotEmpty
    private List<String> giftCardNoList;

    /**
     * 销卡人
     */
    @Schema(description = "销卡人")
    private String cancelPerson;

    /**
     * 销卡原因
     */
    @Schema(description = "销卡原因")
    private String cancelDesc;

    /**
     * 交易人类型
     */
    @Schema(description = "交易人类型")
    DefaultFlag tradePersonType;
}
