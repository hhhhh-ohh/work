package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>批量商品库规格 dto</p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchStandardSpecDTO extends StandardSpecDTO {

    private static final long serialVersionUID = 1229896643569821965L;

    /**
     * 模拟商品编号
     */
    @Schema(description = "模拟商品编号")
    private String mockGoodsId;
}
