package com.wanmi.sbc.goods.api.request.storegoodstab;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gaomuwei
 * @date Created In 上午9:32 2018/12/13
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreGoodsTabListByStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = 2899011395901092877L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;
}
