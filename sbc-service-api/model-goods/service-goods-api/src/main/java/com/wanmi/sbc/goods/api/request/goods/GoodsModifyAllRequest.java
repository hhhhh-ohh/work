package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsModifyAllRequest
 * 修改商品基本信息、基价请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午10:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsModifyAllRequest extends BaseRequest {

    /**
     * 商品信息
     */
    @Valid
    @Schema(description = "商品信息")
    private GoodsVO goods;

    /**
     * 商品展示主图
     */
    @Valid
    @Schema(description = "商品展示主图")
    private List<GoodsMainImageVO> mainImage;

    /**
     * 商品相关图片
     */
    @Valid
    @Schema(description = "商品相关图片")
    private List<GoodsImageVO> images;

    /**
     * 商品属性列表
     */
    @Schema(description = "商品属性列表")
    private List<GoodsPropDetailRelVO> goodsPropDetailRels;

    /**
     * 商品规格列表
     */
    @Schema(description = "商品规格列表")
    private List<GoodsSpecVO> goodsSpecs;

    /**
     * 商品规格值列表
     */
    @Schema(description = "商品规格值列表")
    private List<GoodsSpecDetailVO> goodsSpecDetails;

    /**
     * 商品SKU列表
     */
    @Valid
    @Schema(description = "商品SKU列表")
    private List<GoodsInfoVO> goodsInfos;

    /**
     * 商品等级价格列表
     */
    @Schema(description = "商品等级价格列表")
    private List<GoodsLevelPriceVO> goodsLevelPrices;

    /**
     * 商品客户价格列表
     */
    @Schema(description = "商品客户价格列表")
    private List<GoodsCustomerPriceVO> goodsCustomerPrices;

    /**
     * 商品订货区间价格列表
     */
    @Schema(description = "商品订货区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 是否修改价格及订货量设置
     */
    @Schema(description = "是否修改价格及订货量设置", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isUpdatePrice;

    /**
     * 商品详情模板关联
     */
    @Schema(description = "商品详情模板关联")
    private List<GoodsTabRelaVO> goodsTabRelas;

    /**
     * 属性信息
     */
    private List<GoodsPropertyDetailRelDTO> goodsDetailRel;

    @Schema(description = "在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置")
    private EnableStatus isIndependent;
    /**
     * sku维度设价
     */
    BoolFlag skuEditPrice = BoolFlag.NO;

    /**
     * 商品SKU编号
     */
    @Schema(description = "商品SKU编号")
    private String oldGoodsInfoId;

    /**
     * 是否批量调价
     */
    @Schema(description = "是否批量调价")
    private BoolFlag isBatchEditPrice = BoolFlag.NO;

    /**
     * 重写敏感词，用于验证
     * @return 拼凑关键内容
     */
    @Override
    public String checkSensitiveWord() {
        StringBuilder sensitiveWord = new StringBuilder();
        if (goods != null) {
            if (Objects.nonNull(goods.getGoodsName())) {
                sensitiveWord.append(goods.getGoodsName());
            }
            if (Objects.nonNull(goods.getGoodsSubtitle())) {
                sensitiveWord.append(goods.getGoodsSubtitle());
            }
            if (Objects.nonNull(goods.getGoodsDetail())) {
                sensitiveWord.append(goods.getGoodsDetail());
            }
            if (Objects.nonNull(goods.getLabelName())) {
                sensitiveWord.append(goods.getLabelName());
            }
            if (Constants.yes.equals(goods.getMoreSpecFlag())) {
                if (CollectionUtils.isNotEmpty(goodsSpecs)) {
                    sensitiveWord.append(goodsSpecs.stream().map(GoodsSpecVO::getSpecName).collect(Collectors.joining()));
                }
                if (CollectionUtils.isNotEmpty(goodsSpecDetails)) {
                    sensitiveWord.append(goodsSpecDetails.stream().map(GoodsSpecDetailVO::getDetailName).collect(Collectors.joining()));
                }
            }
        }
        return sensitiveWord.toString();
    }
}
