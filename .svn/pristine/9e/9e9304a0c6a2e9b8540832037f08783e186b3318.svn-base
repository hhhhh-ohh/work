package com.wanmi.sbc.goods.api.request.buycyclegoodsinfo;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>周期购sku表导出查询请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsInfoExportRequest extends BaseQueryRequest {

	private static final long serialVersionUID = -3686319495751507367L;
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

}
