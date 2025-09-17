package com.wanmi.sbc.goods.api.request.goodsaudit;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.*;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>商品审核修改参数</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAuditModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	@Schema(description = "审核商品信息")
	private GoodsAuditVO goodsAudit;

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
	 * 商品属性信息
	 */
	@Schema(description = "商品属性信息")
	private List<GoodsPropertyDetailRelDTO> goodsDetailRel;

	@Schema(description = "智能设价下的加价比例")
	private List<CommissionPriceTargetVO> commissionPriceTargetVOS;

	/**
	 * 商品插件类型
	 */
	private PluginType pluginType = PluginType.NORMAL;

	/**
	 * 商品SKU列表
	 */
	@Valid
	@Schema(description = "商品SKU列表")
	private List<GoodsInfoVO> goodsInfos;

	/**
	 * 在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置
	 */
	@Schema(description = "在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置")
	private EnableStatus isIndependent;

	/**
	 * 重写敏感词，用于验证
	 * @return 拼凑关键内容
	 */
	@Override
	public String checkSensitiveWord() {
		StringBuilder sensitiveWord = new StringBuilder();
		if (goodsAudit != null) {
			if (Objects.nonNull(goodsAudit.getGoodsName())) {
				sensitiveWord.append(goodsAudit.getGoodsName());
			}
			if (Objects.nonNull(goodsAudit.getGoodsSubtitle())) {
				sensitiveWord.append(goodsAudit.getGoodsSubtitle());
			}
			if (Objects.nonNull(goodsAudit.getGoodsDetail())) {
				sensitiveWord.append(goodsAudit.getGoodsDetail());
			}
			if (Objects.nonNull(goodsAudit.getLabelName())) {
				sensitiveWord.append(goodsAudit.getLabelName());
			}
			if (Constants.yes.equals(goodsAudit.getMoreSpecFlag())) {
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
