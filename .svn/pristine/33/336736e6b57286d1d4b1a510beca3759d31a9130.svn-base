package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>限售配置VO</p>
 * @author baijz
 * @date 2020-04-08 11:20:28
 */
@Schema
@Data
public class GoodsRestrictedSaleVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

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
	 * 货品信息
	 */
	@Schema(description = "货品信息")
	private GoodsInfoVO goodsInfo;

	/**
	 * 会员的关系
	 */
	@Schema(description = "限售的会员关系")
	private List<GoodsRestrictedCustomerRelaVO> goodsRestrictedCustomerRelas;

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
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标识
	 */
	@Schema(description = "删除标识")
	private DeleteFlag delFlag;
	/**
	 * 是否打开限售方式开关
	 */
	@Schema(description = "是否打开限售方式开关 ")
	private DefaultFlag restrictedWay;
	/**
	 * 是否打开起售数量开关
	 */
	@Schema(description = "是否打开起售数量开关 ")
	private DefaultFlag restrictedStartNum;

	/**
	 * 商品类型
	 */
	@Schema(description = "商品类型")
	private GoodsType goodsType;
}