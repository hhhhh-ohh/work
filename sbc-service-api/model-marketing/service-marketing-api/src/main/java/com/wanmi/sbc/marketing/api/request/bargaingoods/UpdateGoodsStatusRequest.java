package com.wanmi.sbc.marketing.api.request.bargaingoods;

import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>砍价商品新增参数</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGoodsStatusRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 商品Id
     */
    @NotEmpty
    private List<String> goodsInfoIds;

    /**
     * 商品状态  0： 不可售  1：可售
     */
    @NotNull
    private DeleteFlag goodsStatus;

}