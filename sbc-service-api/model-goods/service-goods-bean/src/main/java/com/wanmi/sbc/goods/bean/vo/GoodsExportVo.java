package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class GoodsExportVo extends BasicResponse {

    /**
     * 商品SKU名称
     */
    @Schema(description = "商品SKU名称")
    private String goodsInfoName;

    /**
     * SPU编码
     */
    @Schema(description = "SPU编码")
    private String goodsNo;

    /**
     * 平台类目
     */
    @Schema(description = "平台类目")
    private String cateName;

    /**
     * 平台一级类目
     */
    @Schema(description = "平台一级类目")
    private String cateNameLevel1;

    /**
     * 平台二级类目
     */
    @Schema(description = "平台二级类目")
    private String cateNameLevel2;

    /**
     * 平台三级类目
     */
    @Schema(description = "平台三级类目")
    private String cateNameLevel3;

    /**
     * 店铺分类
     */
    @Schema(description = "店铺分类")
    private String storeCateName;

    /**
     * 销售类型 0:批发, 1:零售
     */
    @Schema(description = "销售类型", contentSchema = com.wanmi.sbc.goods.bean.enums.SaleType.class)
    private Integer saleType;

    /**
     * 计算单位
     */
    @Schema(description = "计算单位")
    private String goodsUnit;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 商品主图
     */
    @Schema(description = "商品主图")
    @CanEmpty
    private String goodsImg;

    /**
     * 商品视频地址
     */
    @Schema(description = "商品视频地址")
    @CanEmpty
    private String goodsVideo;

    /**
     * 商品详情
     */
    @Schema(description = "商品详情")
    private String goodsDetail;

    /**
     * 商品SKU编码
     */
    @Schema(description = "商品SKU编码")
    private String goodsInfoNo;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String goodsInfoImg;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private List<GoodsInfoSpecExportVO> goodsSpecVOList;

    /**
     * 商品库存
     */
    @Schema(description = "商品库存")
    private Long stock;

    /**
     * 商品条形码
     */
    @Schema(description = "商品条形码")
    private String goodsInfoBarcode;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    private BigDecimal marketPrice;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private Integer addedFlag;

    /**
     * 运费模板名称
     */
    @Schema(description = "运费模板名称")
    private String freightTempName;

    /**
     * 商品重量
     */
    @Schema(description = "商品重量")
    private BigDecimal goodsWeight;

    /**
     * 商品体积 单位：m3
     */
    @Schema(description = "商品体积，单位：m3")
    private BigDecimal goodsCubage;

    /**
     * 购买积分
     */
    @Schema(description = "购买积分")
    private Long buyPoint;

    /**
     * 购买方式 0立即购买,1购物车,内容以,相隔
     */
    @Schema(description = "购买方式 0立即购买 1购物车,内容以,相隔")
    private String goodsBuyTypes;

    /**
     * 设价类型 0按客户 1按订货量 2按市场价
     */
    @Schema(description = "设价类型", contentSchema = com.wanmi.sbc.goods.bean.enums.GoodsPriceType.class)
    private Integer priceType;

    /**
     * 供货价
     */
    @Schema(description = "供货价")
    @CanEmpty
    private BigDecimal supplyPrice;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String supplierName;

    /**
     * 商品销量
     */
    @Schema(description = "商品销量")
    private Long goodsSalesNum;

    /**
     * 注水销量
     */
    @Schema(description = "注水销量")
    private Long shamSalesNum;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Long sortNo;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型")
    private Integer goodsType;

    /**
     * 所属供应商
     */
    @Schema(description = "供应商名称")
    private String providerName;

    /**
     * 所属供应商商品sku编码
     */
    @Schema(description = "所属供应商商品sku编码")
    private String providerGoodsInfoNo;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型")
    private BoolFlag companyType;

    /**
     * 导入状态
     */
    @Schema(description = "导入状态")
    private Integer importState;


    /**
     * 划线价
     */
    @Schema(description = "划线价")
    @CanEmpty
    private BigDecimal linePrice;

    /**
     * 商品标签
     */
    @Schema(description = "商品标签")
    private String goodsLabel;
}
