package com.wanmi.sbc.goods.api.request.buycyclegoodsinfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>周期购sku表通用查询请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsInfoQueryRequest extends BaseQueryRequest {

	private static final long serialVersionUID = 7567328114719220578L;
	/**
	 * 批量查询-skuIdList
	 */
	@Schema(description = "批量查询-skuIdList")
	private List<String> goodsInfoIdList;

	/**
	 * skuId
	 */
	@Schema(description = "skuId")
	private String goodsInfoId;

	/**
	 * spuId
	 */
	@Schema(description = "spuId")
	private String goodsId;

	/**
	 * 周期购id
	 */
	@Schema(description = "周期购id")
	private Long buyCycleId;

	/**
	 * 批量周期购id
	 */
	@Schema(description = "批量周期购id")
	private List<Long> buyCycleIdList;

	/**
	 * 最低期数
	 */
	@Schema(description = "最低期数")
	private Integer minCycleNum;

	/**
	 * 每期价格
	 */
	@Schema(description = "每期价格")
	private BigDecimal cyclePrice;

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
	 * createPerson
	 */
	@Schema(description = "createPerson")
	private String createPerson;

	/**
	 * 搜索条件:updateTime开始
	 */
	@Schema(description = "搜索条件:updateTime开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:updateTime截止
	 */
	@Schema(description = "搜索条件:updateTime截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson")
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除")
	private DeleteFlag delFlag;


	/**
	 * 周期购商品状态 0：生效；1：失效
	 */
	@Schema(description = "周期购商品状态 0：生效；1：失效")
	private Integer cycleState;

}