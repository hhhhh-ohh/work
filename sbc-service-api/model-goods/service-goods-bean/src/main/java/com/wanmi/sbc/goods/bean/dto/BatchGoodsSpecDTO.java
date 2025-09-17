package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 商品Sku规格批量导入参数
 *
 * @author lipeng
 * @dateTime 2018/11/8 下午1:58
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchGoodsSpecDTO extends GoodsSpecDTO implements Serializable {

    private static final long serialVersionUID = 8860618050455414805L;

    /**
     * 模拟商品编号
     */
    @Schema(description = "模拟商品编号")
    private String mockGoodsId;
}
