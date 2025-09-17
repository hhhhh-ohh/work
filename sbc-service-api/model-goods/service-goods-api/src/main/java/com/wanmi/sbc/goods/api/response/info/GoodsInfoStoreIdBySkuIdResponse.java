package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * StoreId
 * Created by yangzhen on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoStoreIdBySkuIdResponse extends BasicResponse {

    private static final long serialVersionUID = -192805137868023814L;

    /**
     * StoreId
     */
    @Schema(description = "StoreId")
    private Long StoreId;
}
