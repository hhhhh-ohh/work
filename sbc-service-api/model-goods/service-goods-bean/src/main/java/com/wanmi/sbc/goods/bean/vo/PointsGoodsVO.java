package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.bean.enums.PointsGoodsStatus;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>积分商品表VO</p>
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@Data
public class PointsGoodsVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 积分商品id
	 */
	@Schema(description = "积分商品id")
	private String pointsGoodsId;

	/**
	 * SpuId
	 */
	@Schema(description = "SpuId")
	private String goodsId;

	/**
	 * SkuId
	 */
	@Schema(description = "SkuId")
	private String goodsInfoId;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	private Integer cateId;

	/**
	 * 字段更改，不代表积分商品库存，改为积分商品可兑换上限
	 */
	@Schema(description = "字段更改，不代表积分商品库存，改为积分商品可兑换上限")
	private Long stock;

	/**
	 * 可兑换的最大库存
	 */
	@Schema(description = "可兑换的最大库存")
	private Long maxStock;

	/**
	 * 销量
	 */
	@Schema(description = "销量")
	private Long sales;

	/**
	 * 结算价格
	 */
	@Schema(description = "结算价格")
	private BigDecimal settlementPrice;

	/**
	 * 兑换积分
	 */
	@Schema(description = "兑换积分")
	private Long points;

	/**
	 * 是否启用 0：停用，1：启用
	 */
	@Schema(description = "是否启用 0：停用，1：启用")
	private EnableStatus status;

	/**
	 * 兑换状态 1: 进行中, 2: 暂停中,3: 未开始,4: 已结束
	 */
	@Schema(description = "兑换状态 1: 进行中, 2: 暂停中,3: 未开始,4: 已结束")
	private PointsGoodsStatus pointsGoodsStatus;

	/**
	 * 推荐标价, 0: 未推荐 1: 已推荐
	 */
	@Schema(description = "推荐标价, 0: 未推荐 1: 已推荐")
	private BoolFlag recommendFlag;

	/**
	 * 分类信息
	 */
	@Schema(description = "分类信息")
	private PointsGoodsCateVO pointsGoodsCate;

	/**
	 * SPU信息
	 */
	@Schema(description = "SPU信息")
	private GoodsVO goods;

	/**
	 * SKU信息
	 */
	@Schema(description = "SKU信息")
	private GoodsInfoVO goodsInfo;

	/**
	 * 规格信息
	 */
	@Schema(description = "规格信息")
	private String specText;

	/**
	 * 兑换开始时间
	 */
	@Schema(description = "兑换开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime beginTime;

	/**
	 * 兑换结束时间
	 */
	@Schema(description = "兑换结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 删除标识,0: 未删除 1: 已删除
	 */
	@Schema(description = "删除标识,0: 未删除 1: 已删除")
	private DeleteFlag delFlag;

}