package com.wanmi.sbc.elastic.bean.vo.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商品库实体类
 * Created by dyt on 2017/4/11.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsStandardGoodsPageVO extends BasicResponse {

    /**
     * 商品编号，采用UUID
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    private Long brandId;

    /**
     * SPU编码
     */
    @Schema(description = "SPU编码")
    private String goodsNo;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;


    /**
     * 商品主图
     */
    @Schema(description = "商品主图")
    private String goodsImg;


    /**
     * 市场价
     */
    @Schema(description = "市场价")
    private BigDecimal marketPrice;

    /**
     * 供货价
     */
    @Schema(description = "供货价")
    private BigDecimal supplyPrice;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall
     */
    @Schema(description = "商品来源，0供应商，1商家,2 linkedmall")
    private Integer goodsSource;

    /**
     * 上下架状态,0:下架1:上架2:部分上架
     */
    @Schema(description = "上下架状态,0:下架1:上架2:部分上架")
    private Integer addedFlag;

    @Schema(description = "库存")
    private Long stock;

    /**
     * 商品类型 0、实物商品 1、虚拟商品 2、电子卡券
     */
    private Integer goodsType;

    /**
     * 供应商店铺id
     */
    @Schema(description = "供应商店铺id")
    private Long storeId;

    /**
     * 不可导入原因
     */
    @Schema(description = "不可导入原因")
    private String reason;

    /**
     * 导入状态
     *
     * @see com.wanmi.sbc.goods.bean.enums.GoodsImportState
     */
    @Schema(description = "导入状态")
    private Integer importState;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    private BigDecimal linePrice;
}
