package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardIdsByGoodsIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -106581752361927458L;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private List<String> goodsIds;
}
