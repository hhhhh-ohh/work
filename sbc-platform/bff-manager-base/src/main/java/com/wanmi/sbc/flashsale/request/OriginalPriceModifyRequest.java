package com.wanmi.sbc.flashsale.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author 黄昭
 * @className PosterModifyRequest
 * @description TODO
 * @date 2022/2/10 15:23
 **/
@Data
@Schema
public class OriginalPriceModifyRequest extends BaseRequest {

    /**
     * 限时抢购库存抢完之后是否允许原价购买
     */
    @Schema(description = "限时抢购库存抢完之后是否允许原价购买")
    @NotNull
    private BoolFlag originalPriceStatus;
}