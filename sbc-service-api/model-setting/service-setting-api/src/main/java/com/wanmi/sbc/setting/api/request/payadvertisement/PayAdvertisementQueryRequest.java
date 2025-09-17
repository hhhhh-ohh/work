package com.wanmi.sbc.setting.api.request.payadvertisement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>支付广告页配置通用查询请求参数</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayAdvertisementQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-支付广告idList
	 */
	@Schema(description = "批量查询-支付广告idList")
	private List<Long> idList;

	/**
	 * 支付广告id
	 */
	@Schema(description = "支付广告id")
	private Long id;

	/**
	 * 广告名称
	 */
	@Schema(description = "广告名称")
	private String advertisementName;

	/**
	 * 搜索条件:投放开始时间开始
	 */
	@Schema(description = "搜索条件:投放开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 搜索条件:投放结束时间截止
	 */
	@Schema(description = "搜索条件:投放结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 1:全部店铺  2:部分店铺
	 */
	@Schema(description = "1:全部店铺  2:部分店铺")
	private Integer storeType;

	/**
	 * 订单金额
	 */
	@Schema(description = "订单金额")
	private BigDecimal orderPrice;

	/**
	 * 广告图片
	 */
	@Schema(description = "广告图片")
	private String advertisementImg;

	/**
	 * 目标客户 0:全平台客户 other:部分客户
	 */
	@Schema(description = "目标客户 0:全平台客户 other:部分客户")
	private String joinLevel;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Schema(description = "删除标记  0：正常，1：删除")
	private DeleteFlag delFlag;

	/**
	 * 是否暂停（1：暂停，0：正常）
	 */
	@Schema(description = "是否暂停（1：暂停，0：正常）")
	private Integer isPause;

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
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson")
	private String updatePerson;

	/**
	 * 活动状态 (1:进行中 2:暂停中 3.未开始 4.已结束)
	 */
	@Schema(description = "活动状态 (1:进行中 2:暂停中 3.未开始 4.已结束)")
	private Integer queryTab;

}