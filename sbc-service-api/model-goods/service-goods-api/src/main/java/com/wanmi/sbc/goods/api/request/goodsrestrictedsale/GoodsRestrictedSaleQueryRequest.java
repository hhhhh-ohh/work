package com.wanmi.sbc.goods.api.request.goodsrestrictedsale;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.*;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>限售配置通用查询请求参数</p>
 * @author baijz
 * @date 2020-04-08 11:20:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedSaleQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-限售主键List
	 */
	@Schema(description = "批量查询-限售主键List")
	private List<Long> restrictedIdList;

	/**
	 * 限售主键
	 */
	@Schema(description = "限售主键")
	private Long restrictedId;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	private Long storeId;

	/**
	 * 货品的skuId
	 */
	@Schema(description = "货品的skuId")
	private String goodsInfoId;

	/**
	 * 货品的名称
	 */
	@Schema(description = "货品的名称")
	private String goodsInfoName;

	/**
	 * 货品的编码
	 */
	@Schema(description = "货品的编码")
	private String goodsInfoNo;

	/**
	 * 货品的skuids
	 */
	@Schema(description = "货品的skuids")
	private List<String> goodsInfoIds;

	/**
	 * 限售方式 0: 按订单 1：按会员
	 */
	@Schema(description = "限售方式 0: 按订单 1：按会员")
	private RestrictedType restrictedType;

	/**
	 * 是否每人限售标识 
	 */
	@Schema(description = "是否每人限售标识 ")
	private DefaultFlag restrictedPrePersonFlag;

	/**
	 * 是否每单限售的标识
	 */
	@Schema(description = "是否每单限售的标识")
	private DefaultFlag restrictedPreOrderFlag;

	/**
	 * 是否指定会员限售的标识
	 */
	@Schema(description = "是否指定会员限售的标识")
	private DefaultFlag restrictedAssignFlag;

	/**
	 * 个人限售的方式(  0:终生限售  1:周期限售)
	 */
	@Schema(description = "个人限售的方式(  0:终生限售  1:周期限售)")
	private PersonRestrictedType personRestrictedType;

	/**
	 * 个人限售的周期 (0:周   1:月  2:年)
	 */
	@Schema(description = "个人限售的周期 (0:周   1:月  2:年)")
	private PersonRestrictedCycle personRestrictedCycle;


	/**
	 * 特定会员的限售类型 0: 会员等级  1：指定会员
	 */
	@Schema(description = "特定会员的限售类型 0: 会员等级  1：指定会员")
	private AssignPersonRestrictedType assignPersonRestrictedType;

	/**
	 * 限售数量
	 */
	@Schema(description = "限售数量")
	private Long restrictedNum;

	/**
	 * 起售数量
	 */
	@Schema(description = "起售数量")
	private Long startSaleNum;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 删除标识
	 */
	@Schema(description = "删除标识")
	private DeleteFlag delFlag;

	/**
	 * 商品类型 0、实物商品 1、虚拟商品 2、电子卡券
	 */
	@Schema(description = "商品类型 0、实物商品 1、虚拟商品 2、电子卡券")
	private GoodsType goodsType;

}