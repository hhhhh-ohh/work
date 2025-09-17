package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>批量商品库图片 dto</p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchStandardImageDTO extends StandardImageDTO {

    private static final long serialVersionUID = 7611568374261655593L;

    /**
     * 模拟商品编号
     */
    @Schema(description = "模拟商品编号")
    private String mockGoodsId;
}
