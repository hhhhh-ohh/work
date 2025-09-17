package com.wanmi.sbc.goods.api.request.customergoodsevaluatepraise;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>单个删除会员商品评价点赞关联表请求参数</p>
 *
 * @author lvzhenwei
 * @date 2019-05-07 14:25:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGoodsEvaluatePraiseDelByIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private String id;
}