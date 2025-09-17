package com.wanmi.sbc.goods.api.request.ares;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: wanggang
 * @createDate: 2018/11/5 10:52
 * @version: 1.0
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsAresListByGoodsInfoIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -8998236875625293585L;

    @Schema(description = "商品Id集合")
    @NotNull
    private List<String> goodsInfoIds;
}
