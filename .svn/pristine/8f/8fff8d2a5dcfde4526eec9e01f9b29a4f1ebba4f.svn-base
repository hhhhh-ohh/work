package com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

/**
 * @description 商品智能推荐查询参数
 * @author  lvzhenwei
 * @date 2021/4/14 1:39 下午
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelligentRecommendationRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;
	/**
	 * 推荐策略类型，0：热门推荐；1：基于商品相关性推荐
	 */
	@Schema(description = "推荐策略类型，0：热门推荐；1：基于商品相关性推荐")
	private Integer recommendType;

	/**
	 * 查询类目还是商品 商品为0，类目为1
	 */
	@Schema(description = "查询类目还是商品 商品为0，类目为1")
	private Integer item = NumberUtils.INTEGER_ZERO;

	/**
	 * 坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方
	 */
	@Schema(description = "坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方")
	private PositionType type;

	/**
	 * 坑位（大数据用）
	 */
	@Schema(description = "坑位")
	private Integer location;

	/**
	 * 客户id
	 */
	@Schema(description = "客户id")
	private String customerId;

	/**
	 * 相关性推荐需要查询的商品集合
	 */
	@Schema(description = "相关性推荐需要查询的商品集合")
	private List<String> relationGoodsIdList;

	/**
	 * 相关性推荐需要查询的品类集合
	 */
	@Schema(description = "相关性推荐需要查询的品类集合")
	private List<Long> relationCateIdList;

	/**
	 * 一页展示多少条
	 */
	@Schema(description = "一页展示多少条")
	private Integer pageSize;

	/**
	 * 从第几条开始展示(后台取存储每个用户的row_number)
	 */
	@Schema(description = "从第几条开始展示(后台取存储每个用户的row_number)")
	private Integer pageIndex;

	/**
	 * 兴趣推荐 类目和品牌
	 */
	@Schema(description = "兴趣推荐 类目和品牌")
	private String interestItem;

	/** 终端 */
	@Schema(description = "终端类型，PC：1，H5：2， APP：3，小程序：4")
	private Integer terminalType;

}