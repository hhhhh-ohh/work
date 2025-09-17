package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.PriceType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商品客户价格实体
 * Created by dyt on 2017/4/17.
 */
@Schema
@Data
public class GoodsCustomerPriceVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 客户价格ID
     */
    @Schema(description = "客户价格ID")
    private Long customerPriceId;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String goodsId;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 订货价
     */
    @Schema(description = "订货价")
    private BigDecimal price;

    /**
     * 起订量
     */
    @Schema(description = "起订量")
    private Long count;

    /**
     * 限订量
     */
    @Schema(description = "限订量")
    private Long maxCount;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String goodsInfoId;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private PriceType type;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    private String customerAccount;

    /**
     * 客户等级
     */
    @Schema(description = "客户等级")
    private Long customerLevelId;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    private String customerLevelName;

    /**
     * 老商品ID
     */
    @Schema(description = "老商品ID")
    private String oldGoodsInfoId;

}
