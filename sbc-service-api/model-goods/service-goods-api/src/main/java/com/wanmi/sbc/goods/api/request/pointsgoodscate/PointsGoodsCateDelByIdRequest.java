package com.wanmi.sbc.goods.api.request.pointsgoodscate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>单个删除积分商品分类表请求参数</p>
 *
 * @author yang
 * @date 2019-05-13 09:50:07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsCateDelByIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 积分商品分类主键
     */
    @Schema(description = "积分商品分类主键")
    @NotNull
    private Integer cateId;
}