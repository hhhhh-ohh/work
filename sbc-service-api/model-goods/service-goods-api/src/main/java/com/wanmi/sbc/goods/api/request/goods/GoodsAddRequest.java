package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.vo.GoodsTabRelaVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsAddRequest
 * 新增商品请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午10:02
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsAddRequest extends BaseRequest {

    private static final long serialVersionUID = -8933154540285476077L;

    /**
     * 商品信息
     */
    @Valid
    @Schema(description = "商品信息")
    private GoodsDTO goods;

    /**
     * 商品相关图片
     */
    @Valid
    @Schema(description = "商品相关图片")
    private List<GoodsImageDTO> images;
    /**
     * 商品展示主图
     */
    @Schema(description = "商品展示主图")
    private List<GoodsMainImageDTO> mainImage;
    /**
     * 商品属性列表
     */
    @Schema(description = "商品属性列表")
    private List<GoodsPropDetailRelDTO> goodsPropDetailRels;

    /**
     * 商品规格列表
     */
    @Schema(description = "商品规格列表")
    private List<GoodsSpecDTO> goodsSpecs;

    /**
     * 商品规格值列表
     */
    @Schema(description = "商品规格值列表")
    private List<GoodsSpecDetailDTO> goodsSpecDetails;

    /**
     * 商品SKU列表
     */
    @Valid
    @Schema(description = "商品SKU列表")
    private List<GoodsInfoDTO> goodsInfos;

    /**
     * 商品等级价格列表
     */
    @Schema(description = "商品等级价格列表")
    private List<GoodsLevelPriceDTO> goodsLevelPrices;

    /**
     * 商品客户价格列表
     */
    @Schema(description = "商品客户价格列表")
    private List<GoodsCustomerPriceDTO> goodsCustomerPrices;

    /**
     * 商品订货区间价格列表
     */
    @Schema(description = "商品订货区间价格列表")
    private List<GoodsIntervalPriceDTO> goodsIntervalPrices;

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
     * 商品属性信息
     */
    @Schema(description = "商品属性信息")
    private List<GoodsPropertyDetailRelDTO> goodsDetailRel;
    
    /**
     * 商品插件类型
     */
    private PluginType pluginType = PluginType.NORMAL;

    /**
     * 重写敏感词，用于验证
     * @return 拼凑关键内容
     */
    @Override
    public String checkSensitiveWord(){
        StringBuilder sensitiveWord = new StringBuilder();
        if(goods != null) {
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
                    sensitiveWord.append(goodsSpecs.stream().map(GoodsSpecDTO::getSpecName).collect(Collectors.joining()));
                }
                if (CollectionUtils.isNotEmpty(goodsSpecDetails)) {
                    sensitiveWord.append(goodsSpecDetails.stream().map(GoodsSpecDetailDTO::getDetailName).collect(Collectors.joining()));
                }
            }
        }
        return sensitiveWord.toString();
    }
}
