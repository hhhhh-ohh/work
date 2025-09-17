package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.standard.StandardImportGoodsResponse
 * 商品库导入商品响应对象
 * @author lipeng
 * @dateTime 2018/11/9 下午2:48
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardImportGoodsResponse extends BasicResponse {

    @Schema(description = "sku Id")
    private List<String> skuIdList;

    @Schema(description = "spu Id")
    private List<String> spuIdList;

    @Schema(description = "导入的商品名称对象")
    private List<String> goodsNameList;
}
