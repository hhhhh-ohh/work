package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class GoodsCheckBindRequest extends BaseRequest {

    /**
     * 电子卡券id
     */
    @NotNull
    @Schema(description = "电子卡券id")
    private Long electronicCouponsId;

    /**
     * SKUid
     */
    @CanEmpty
    @Schema(description = "SKUid")
    private String goodsInfoId;
}
