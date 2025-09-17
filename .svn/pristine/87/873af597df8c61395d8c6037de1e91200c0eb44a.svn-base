package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author houshuai
 * @date 2022/2/11 14:18
 * @description <p> 秒杀商品导出 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
public class ExcelFlashSaleGoodsInfoVO extends GoodsInfoVO{

    /**
     * 抢购价
     */
    @Schema(description = "抢购价")
    private BigDecimal price;

    /**
     * 上限数量
     */
    @Schema(description = "上限数量")
    private Long flashStock;

    /**
     * 限购数量
     */
    @Schema(description = "限购数量")
    private Long maxNum;

    /**
     * 起售数量
     */
    @Schema(description = "起售数量")
    private Long minNum;

    /**
     * 包邮标志，0：不包邮 1:包邮
     */
    @Schema(description = "包邮标志，0：不包邮 1:包邮")
    private DefaultFlag postage;

    /**
     * 秒杀分类id
     */
    @Schema(description = "秒杀分类id")
    private Long flashCateId;


}
