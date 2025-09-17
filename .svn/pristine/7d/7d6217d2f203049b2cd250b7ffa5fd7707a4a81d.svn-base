package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Builder
public class StoreGoodsStatusRequest extends BaseRequest {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotEmpty
    private List<Long> storeIds;
}
