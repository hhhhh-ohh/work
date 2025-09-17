package com.wanmi.sbc.goods.api.request.buycyclegoods;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>周期购spu表列表查询请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsListRequest extends BaseRequest {


	private static final long serialVersionUID = 6359343264132771729L;
	/**
	 * 批量查询-spuIdList
	 */
	@Schema(description = "批量查询-spuIdList")
	private List<String> goodsIdList;

	/**
	 * spuId
	 */
	@Schema(description = "spuId")
	private String goodsId;

	/**
	 * 配送周期
	 */
	@Schema(description = "配送周期")
	private Integer deliveryCycle;

	/**
	 * 用户可选送达日期
	 */
	@Schema(description = "用户可选送达日期")
	private String deliveryDate;

	/**
	 * 预留时间 日期
	 */
	@Schema(description = "预留时间 日期")
	private Integer reserveDay;

	/**
	 * 预留时间 时间点
	 */
	@Schema(description = "预留时间 时间点")
	private Integer reserveTime;



	/**
	 * 周期购商品状态 0：生效；1：失效
	 */
	@Schema(description = "周期购商品状态 0：生效；1：失效")
	private Integer cycleState;

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
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

}