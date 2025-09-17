package com.wanmi.sbc.goods.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author houshuai
 * @date 2022/2/9 18:00
 * @description <p> 拼团导入商品信息 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
public class ExcelSuitGoodsInfoVO extends GoodsInfoVO {
    private static final long serialVersionUID = 1L;

    /**
     * 拼团价格
     */
    @Schema(description = "拼团价格")
    private BigDecimal discountPrice;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Long quantity;


}
