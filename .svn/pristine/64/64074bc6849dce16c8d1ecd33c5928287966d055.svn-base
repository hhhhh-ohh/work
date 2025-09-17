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
 * 根据商品SKU编号删除请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoDeleteByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 8549936173571185068L;

    /**
     * SKU编号
     */
    @NotEmpty
    @Schema(description = "SKU编号")
    private List<String> goodsInfoIds;
}
