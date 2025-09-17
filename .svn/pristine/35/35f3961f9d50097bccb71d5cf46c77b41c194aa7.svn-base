package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.enums.MarketingGoodsStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>拼团活动信息表VO</p>
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class GrouponActivityForManagerVO extends GrouponActivityVO implements Serializable{


	private static final long serialVersionUID = -8243289883922464684L;
	/**
	 * 拼团价格--最低拼团价格
	 */
	@Schema(description = "拼团价格")
	private BigDecimal grouponPrice;


	/**
	 * 商家名称
	 */
	@Schema(description = "商家名称")
	private String supplierName;


	/**
	 * skuId
	 */
	@Schema(description = "skuId")
	private String goodsInfoId;

	/**
	 * spu编码
	 */
	@Schema(description = "spu编码")
	private String goodsNo;

	/**
	 * 拼团分类名称
	 */
	@Schema(description = "拼团分类名称")
	private String grouponCateName;

	/**
	 * 营销商品状态
	 */
	@Schema(description = "营销商品状态")
	private MarketingGoodsStatus marketingGoodsStatus;


}