package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>单个查询积分商品表请求参数</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsByIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 积分商品id
     */
    @Schema(description = "积分商品id")
    @NotNull
    private String pointsGoodsId;
}