package com.wanmi.sbc.elastic.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>积分商品表通用修改状态请求参数</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsPointsGoodsModifyStatusRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;


    @Schema(description = "积分商品id")
    @NotBlank
    private String pointsGoodsId;

    @Schema(description = "积分商品状态")
    @NotNull
    private Integer status;
}