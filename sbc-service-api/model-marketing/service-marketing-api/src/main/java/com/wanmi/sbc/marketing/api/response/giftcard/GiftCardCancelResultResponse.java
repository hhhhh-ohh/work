package com.wanmi.sbc.marketing.api.response.giftcard;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lvzhenwei
 * @className GiftCardCancelResultResponse
 * @description 礼品卡销卡结果
 * @date 2022/12/15 10:35 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardCancelResultResponse implements Serializable {
    private static final long serialVersionUID = -7906964895679519252L;

    /**
     * 礼品卡销卡失败卡号list
     */
    @Schema(description = "礼品卡销卡失败卡号list")
    private List<String> cancelErrorGiftCardNoList;
}
