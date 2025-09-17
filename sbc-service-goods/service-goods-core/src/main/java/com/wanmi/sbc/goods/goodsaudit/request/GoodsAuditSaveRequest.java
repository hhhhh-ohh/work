package com.wanmi.sbc.goods.goodsaudit.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.goods.bean.dto.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 黄昭
 * @className GoodsAuditSaveRequest
 * @description TODO
 * @date 2021/12/23 11:41
 **/
@Data
@Schema
public class GoodsAuditSaveRequest extends BaseRequest {
    private static final long serialVersionUID = 60680417292685655L;

    /**
     * 商品信息
     */
    private GoodsAuditSaveDTO goodsAudit;

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
     * 扩展属性
     */
    private Object extendedAttributes;

    @Schema(description = "在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置")
    private EnableStatus isIndependent;


}