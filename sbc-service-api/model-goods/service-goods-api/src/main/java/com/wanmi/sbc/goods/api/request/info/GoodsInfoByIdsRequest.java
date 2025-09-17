package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description   根据SPU查询商品基础信息
 * @author  wur
 * @date: 2022/8/29 10:05
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -1635175303035960354L;

    /**
     * SPU 编号
     */
    @Schema(description = "SPU 编号")
    private List<String> goodsIds;

    /**
     * SKU 编号
     */
    @Schema(description = "SKU 编号")
    private List<String> goodsInfoIds;

    @Schema(description = "供应商标识")
    private Boolean isProvide;

}
