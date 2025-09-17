package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
*
 * @description
 * @author  wur
 * @date: 2022/8/25 14:01
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoIdsBySpuIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -1635175303035960354L;

    /**
     * SKU编号
     */
    @Schema(description = "SPU编号")
    @NotEmpty
    private List<String> goodsIds;

}
