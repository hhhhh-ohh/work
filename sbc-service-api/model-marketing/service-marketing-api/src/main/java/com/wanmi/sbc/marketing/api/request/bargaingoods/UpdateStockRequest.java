package com.wanmi.sbc.marketing.api.request.bargaingoods;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class UpdateStockRequest {
    private static final long serialVersionUID = 1L;
    @NotNull
    private Long bargainId;
    @NotNull
    private Long stock;

}