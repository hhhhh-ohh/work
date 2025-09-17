package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 商品更新上下架请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoModifyAddedStatusRequest extends BaseRequest {

    private static final long serialVersionUID = 1790637329739254339L;

    /**
     * 批量商品skuId
     */
    @Schema(description = "批量商品skuId")
    @NotEmpty
    private List<String> goodsInfoIds;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    @NotNull
    private Integer addedFlag;

}
