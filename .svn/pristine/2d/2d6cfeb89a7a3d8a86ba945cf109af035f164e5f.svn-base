package com.wanmi.sbc.customer.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>付费会员等级表VO</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@Schema
@Data
public class PayingMemberLevelVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	private Integer levelId;

	/**
	 * 付费会员等级名称
	 */
	@Schema(description = "付费会员等级名称")
	private String levelName;

	/**
	 * 付费会员等级昵称
	 */
	@Schema(description = "付费会员等级昵称")
	private String levelNickName;

	/**
	 * 付费会员等级状态 0.开启 1.暂停
	 */
	@Schema(description = "付费会员等级状态 0.开启 1.暂停")
	private Integer levelState;

	/**
	 * 付费会员等级背景类型：0.背景色 1.背景图
	 */
	@Schema(description = "付费会员等级背景类型：0.背景色 1.背景图")
	private Integer levelBackGroundType;

	/**
	 * 付费会员等级背景详情
	 */
	@Schema(description = "付费会员等级背景详情")
	private String levelBackGroundDetail;

	/**
	 * 付费会员等级字体颜色
	 */
	@Schema(description = "付费会员等级字体颜色")
	private String levelFontColor;

	/**
	 * 付费会员等级商家范围：0.自营商家 1.自定义选择
	 */
	@Schema(description = "付费会员等级商家范围：0.自营商家 1.自定义选择")
	private Integer levelStoreRange;

	/**
	 * 付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置
	 */
	@Schema(description = "付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置")
	private Integer levelDiscountType;

	/**
	 * 付费会员等级所有商品统一设置 折扣
	 */
	@Schema(description = "付费会员等级所有商品统一设置 折扣")
	private BigDecimal levelAllDiscount;


	/**
	 * 付费设置
	 */
	@Schema(description = "付费设置")
	private List<PayingMemberPriceVO> payingMemberPriceVOS;

	/**
	 * 商家与付费会员等级关联
	 */
	@Schema(description = "商家与付费会员等级关联")
	private List<PayingMemberStoreRelVO> payingMemberStoreRelVOS;


	/**
	 * 折扣商品与付费会员等级关联
	 */
	@Schema(description = "折扣商品与付费会员等级关联")
	private List<PayingMemberDiscountRelVO> payingMemberDiscountRelVOS;


	/**
	 * 推荐商品与付费会员等级关联
	 */
	@Schema(description = "推荐商品与付费会员等级关联")
	private List<PayingMemberRecommendRelVO> payingMemberRecommendRelVOS;


	/**
	 * 付费会员等级付费描述
	 */
	@Schema(description = "付费会员等级付费描述")
	private String priceDesc;


	/**
	 * 付费会员等级权益描述
	 */
	@Schema(description = "付费会员等级权益描述")
	private String rightsDesc;

	/**
	 * 付费会员人数
	 */
	@Schema(description = "付费会员人数")
	private Long payingMemberCount;

	/**
	 * 付费会标签
	 */
	@Schema(description = "付费会员标签")
	private String label;

	/**
	 * 会员是否拥有
	 */
	@Schema(description = "会员是否拥有")
	private Boolean ownFlag;

}