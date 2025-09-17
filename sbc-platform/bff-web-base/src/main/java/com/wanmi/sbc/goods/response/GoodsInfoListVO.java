package com.wanmi.sbc.goods.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsLabelNestVO;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsInfoListVO
 * @description TODO
 * @date 2021/8/26 8:09 下午
 **/
@Data
public class GoodsInfoListVO extends BasicResponse{

    /**
     * 商品SKU编号
     */
    @Schema(description = "商品SKU编号")
    private String goodsInfoId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * 商品SKU名称
     */
    @Schema(description = "商品SKU名称")
    private String goodsInfoName;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String goodsInfoImg;

    /**
     * 商品主图图片
     */
    @Schema(description = "商品主图图片")
    private String goodsInfoMainImg;

    /**
     * 商品库存
     */
    @Schema(description = "商品库存")
    private Long stock;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    private BigDecimal marketPrice;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型")
    private BoolFlag companyType;

    /**
     * 设价类型 0:客户,1:订货
     */
    @Schema(description = "设价类型")
    private Integer priceType;


    /**
     * 购买量
     */
    @Schema(description = "购买量")
    private Long buyCount = 0L;

    /**
     * 最新计算的起订量
     * 为空，则不限
     */
    @Schema(description = "最新计算的起订量")
    private Long count;

    /**
     * 起售数量
     */
    @Schema(description = "起售数量")
    private Long startSaleNum;

    /**
     * 规格名称规格值 颜色:红色;大小:16G
     */
    @Schema(description = "规格名称规格值")
    private String specText;

    /**
     * 最小区间价
     */
    @Schema(description = "最小区间价")
    private BigDecimal intervalMinPrice;




    /**
     * 商品详情小程序码
     */
    @Schema(description = "商品详情小程序码")
    private String  smallProgramCode;


    /**
     * 商品评论数
     */
    @Schema(description = "商品评论数")
    private Long goodsEvaluateNum;

    /**
     * 商品收藏量
     */
    @Schema(description = "商品收藏量")
    private Long goodsCollectNum;

    /**
     * 商品销量
     */
    @Schema(description = "商品销量")
    private Long goodsSalesNum;

    /**
     * 商品好评数量
     */
    @Schema(description = "商品好评数量")
    private Long goodsFavorableCommentNum;

    /**
     * 商品好评率
     */
    private Long goodsFeedbackRate;

    /**
     * 购买积分
     */
    @Schema(description = "购买积分")
    private Long buyPoint;

    /**
     * 促销标签
     */
    @Schema(description = "促销标签")
    private List<MarketingPluginSimpleLabelVO> marketingPluginLabels = new ArrayList<>();
    /**
     * 商品标签集合
     */
    @Schema(description = "商品标签集合")
    private List<GoodsLabelNestVO> goodsLabelList;

    /**
     * 商品副标题
     */
    @Schema(description = "商品副标题")
    private String goodsSubtitle;

    /**
     * 商品状态 0：正常 1：缺货 2：失效
     */
    @Schema(description = "商品状态")
    private GoodsStatus goodsStatus = GoodsStatus.OK;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商品类型，普通商品/跨境商品
     */
    @Schema(description = "商品类型")
    private PluginType pluginType;

    /**
     * 计算单位
     */
    @Schema(description = "计算单位")
    private String goodsUnit;

    /**
     * 秒杀库存
     */
    @Schema(description = "秒杀库存")
    private Long flashStock;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 分销员 分账绑定标志
     */
    @Schema(description = "分销员 分账绑定标志")
    private Boolean ledgerBindFlag;

    /**
     * 是否参与周期购
     * 0 否， 1 是
     */
    @Schema(description = "是否参与周期购，0 否， 1 是")
    private Integer isBuyCycle;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    private BigDecimal linePrice;

    /**
     * 订货号
     */
    @Schema(description = "订货号")
    private String quickOrderNo;

    /**
     * 供应商订货号
     */
    @Schema(description = "供应商订货号")
    private String providerQuickOrderNo;
    @Schema(description = "属性尺码")
    private String attributeSize;

    /**
     * 0-春秋装 1-夏装 2-冬装
     */
    @Schema(description = "款式季节")
    private Integer attributeSeason;

    /**
     * 0-校服服饰 1-非校服服饰 2-自营产品
     */
    @Schema(description = "是否校服")
    private Integer attributeGoodsType;

    /**
     * 0-老款 1-新款
     */
    @Schema(description = "新老款")
    private Integer attributeSaleType;

    /**
     * 售卖地区
     */
    @Schema(description = "售卖地区")
    private String attributeSaleRegion;

    /**
     * 学段
     */
    @Schema(description = "学段")
    private String attributeSchoolSection;

    /**
     * 银卡价格
     */
    @Schema(description = "银卡价格")
    private BigDecimal attributePriceSilver;

    /**
     * 金卡价格
     */
    @Schema(description = "金卡价格")
    private BigDecimal attributePriceGold;

    /**
     * 钻石卡价格
     */
    @Schema(description = "钻石卡价格")
    private BigDecimal attributePriceDiamond;
    @Schema(description = "折扣价格")
    private BigDecimal attributePriceDiscount;
    /**
     * 佣金比例
     */
    @Schema(description = "佣金比例")
    private BigDecimal communityCommissionRate;

    /**
     * 商品价格列表
     */
    @Schema(description = "商品价格列表")
    private List<GoodsIntervalPriceVO> intervalPriceList;
}
