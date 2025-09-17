package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @description 供应商商品sku更新上下架请求
 * @author  daiyitian
 * @date 2021/4/13 15:57
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoModifyAddedStatusByProviderRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 批量商品skuId
     */
    @Schema(description = "批量供应商商品skuId")
    @NotEmpty
    private List<String> providerGoodsInfoIds;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态")
    @NotNull
    private AddedFlag addedFlag;
}
