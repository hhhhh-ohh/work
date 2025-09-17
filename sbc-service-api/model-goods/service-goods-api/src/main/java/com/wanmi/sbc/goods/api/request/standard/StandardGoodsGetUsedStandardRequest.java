package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
public class StandardGoodsGetUsedStandardRequest extends BaseRequest {

    private static final long serialVersionUID = -4997321042769381204L;

    /**
     * 商品库id
     */
    @Schema(description = "商品库id")
    @NotNull
    @Size(min = 1)
    private List<String> standardIds;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    @Size(min = 1)
    private List<Long> storeIds;

    /**
     * 是否需要同步
     */
    @Schema(description = "是否需要同步")
    private BoolFlag needSynchronize;
}
