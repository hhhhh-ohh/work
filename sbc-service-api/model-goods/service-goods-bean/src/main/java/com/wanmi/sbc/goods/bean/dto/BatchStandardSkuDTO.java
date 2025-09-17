package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 批量商品库SKU DTO
 * Created by dyt on 2017/4/11.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchStandardSkuDTO extends StandardSkuDTO {

    private static final long serialVersionUID = 7237659696160873844L;

    /**
     * 模拟商品编号
     */
    @Schema(description = "模拟商品编号")
    private String mockGoodsId;

}
