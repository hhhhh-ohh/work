package com.wanmi.sbc.goods.api.response.goodsaudit;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.api.request.goods.GoodsCheckRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsCheckResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品审核修改结果</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAuditModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品审核入参
     */
    @Schema(description = "商品审核入参")
    private GoodsCheckRequest checkRequest;
}
