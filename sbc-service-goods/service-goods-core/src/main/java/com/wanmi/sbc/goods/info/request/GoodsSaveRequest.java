package com.wanmi.sbc.goods.info.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.goods.bean.dto.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品新增/编辑请求
 * Created by daiyitian on 2017/3/24.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSaveRequest extends BaseRequest {

    /**
     * 商品信息
     */
    private GoodsSaveDTO goods;


    /**
     * 商品展示主图
     */
    private List<GoodsMainImageDTO> mainImage;

    /**
     * 商品相关图片
     */
    private List<GoodsImageDTO> images;

    /**
     * 商品属性列表
     */
    private List<GoodsPropDetailRelDTO> goodsPropDetailRels;

    /**
     * 商品规格列表
     */
    private List<GoodsSpecSaveDTO> goodsSpecs;

    /**
     * 商品规格值列表
     */
    private List<GoodsSpecDetailSaveDTO> goodsSpecDetails;

    /**
     * 商品SKU列表
     */
    private List<GoodsInfoSaveDTO> goodsInfos;

    /**
     * 商品等级价格列表
     */
    private List<GoodsLevelPriceDTO> goodsLevelPrices;

    /**
     * 商品客户价格列表
     */
    private List<GoodsCustomerPriceDTO> goodsCustomerPrices;

    /**
     * 商品订货区间价格列表
     */
    private List<GoodsIntervalPriceDTO> goodsIntervalPrices;

    /**
     * 是否修改价格及订货量设置
     */
    private Integer isUpdatePrice;

    /**
     * 商品详情模板关联
     */
    private List<GoodsTabRelaDTO> goodsTabRelas;

    /**
     * 属性信息
     */
    private List<GoodsPropertyDetailRelSaveDTO> goodsPropertyDetailRel;

    /**
     * 商品插件类型
     */
    private PluginType pluginType = PluginType.NORMAL;

    /**
     * sku维度设价
     */
    BoolFlag skuEditPrice = BoolFlag.NO;

    /**
     * 商品SKU编号
     */
    private String oldGoodsInfoId;

    /**
     * key 老的goodsId；value新goodsId
     */
    Map<String, String> goodsIdMap;

    /**
     * key 老的goodsInfoId；value新goodsInfoId
     */
    List<Map<String, String>> goodsInfoMaps;

    /**
     * 扩展属性
     */
    private Object extendedAttributes;

    @Schema(description = "在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置")
    private EnableStatus isIndependent;

    @Schema(description = "是否审核通过")
    private Boolean isChecked = Boolean.FALSE;

    /**
     * 是否批量调价
     */
    @Schema(description = "是否批量调价")
    private BoolFlag isBatchEditPrice = BoolFlag.NO;

    /**
     * 划线价格
     */
    @Schema(description = "划线价格")
    private BigDecimal linePrice;


}
