package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品SKU同步SPU请求
 *
 * @author daiyitian
 * @date 2017/3/24
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoSyncSpuRequest extends BaseRequest {

    private static final long serialVersionUID = 946149820871022491L;

    /**
     * 批量SKU编号
     */
    @NotEmpty
    @Schema(description = "批量SKU编号")
    private List<String> goodsInfoIds;
}
