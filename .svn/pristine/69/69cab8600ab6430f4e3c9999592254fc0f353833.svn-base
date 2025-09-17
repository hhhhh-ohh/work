package com.wanmi.sbc.marketing.api.request.bookingsale;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.PresellSaleStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>预售信息通用查询请求参数</p>
 * @author dany
 * @date 2020-06-05 10:47:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	private String activityName;

	/**
	 * 店铺storeIdList
	 */
	@Schema(description = "店铺storeIdList")
	private List<Long> storeIds;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	private Long storeId;

	/**
	 * 预售类型 0：全款预售  1：定金预售
	 */
	@Schema(description = "预售类型 0：全款预售  1：定金预售")
	private Integer bookingType;

	/**
	 * 搜索条件:定金支付开始时间开始
	 */
	@Schema(description = "搜索条件:定金支付开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime handSelStartTimeBegin;
	/**
	 * 搜索条件:定金支付开始时间截止
	 */
	@Schema(description = "搜索条件:定金支付开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime handSelStartTimeEnd;

	/**
	 * 搜索条件:定金支付结束时间开始
	 */
	@Schema(description = "搜索条件:定金支付结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime handSelEndTimeBegin;
	/**
	 * 搜索条件:定金支付结束时间截止
	 */
	@Schema(description = "搜索条件:定金支付结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime handSelEndTimeEnd;

	/**
	 * 搜索条件:尾款支付开始时间开始
	 */
	@Schema(description = "搜索条件:尾款支付开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tailStartTimeBegin;
	/**
	 * 搜索条件:尾款支付开始时间截止
	 */
	@Schema(description = "搜索条件:尾款支付开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tailStartTimeEnd;

	/**
	 * 搜索条件:尾款支付结束时间开始
	 */
	@Schema(description = "搜索条件:尾款支付结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tailEndTimeBegin;
	/**
	 * 搜索条件:尾款支付结束时间截止
	 */
	@Schema(description = "搜索条件:尾款支付结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tailEndTimeEnd;

	/**
	 * 搜索条件:预售开始时间开始
	 */
	@Schema(description = "搜索条件:预售开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bookingStartTimeBegin;
	/**
	 * 搜索条件:预售开始时间截止
	 */
	@Schema(description = "搜索条件:预售开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bookingStartTimeEnd;

	/**
	 * 搜索条件:预售结束时间开始
	 */
	@Schema(description = "搜索条件:预售结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bookingEndTimeBegin;
	/**
	 * 搜索条件:预售结束时间截止
	 */
	@Schema(description = "搜索条件:预售结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bookingEndTimeEnd;

	/**
	 * 搜索条件:总开始时间开始
	 */
	@Schema(description = "搜索条件:总开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeBegin;
	/**
	 * 搜索条件:总开始时间截止
	 */
	@Schema(description = "搜索条件:总开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeEnd;

	/**
	 * 搜索条件:总结束时间开始
	 */
	@Schema(description = "搜索条件:总结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeBegin;
	/**
	 * 搜索条件:总结束时间截止
	 */
	@Schema(description = "搜索条件:总结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeEnd;

	/**
	 * 发货日期 2020-01-10
	 */
	@Schema(description = "发货日期 2020-01-10")
	private String deliverTime;

	/**
	 * 发货开始日期 2020-01-10
	 */
	@Schema(description = "发货开始日期 2020-01-10")
	private String deliverStartTime;

	/**
	 * 发货结束日期 2020-01-10
	 */
	@Schema(description = "发货结束日期 2020-01-10")
	private String deliverEndTime;

	/**
	 * 参加会员  -1:全部客户 0:全部等级 other:其他等级
	 */
	@Schema(description = "参加会员  -1:全部客户 0:全部等级 other:其他等级")
	private String joinLevel;

	/**
	 * 是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）
	 */
	@Schema(description = "是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）")
	private DefaultFlag joinLevelType;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是")
	private DeleteFlag delFlag;

	/**
	 * 是否暂停 0:否 1:是
	 */
	@Schema(description = "是否暂停 0:否 1:是")
	private Integer pauseFlag;

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
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

	/**
	 * 查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束
	 */
	@Schema(description = "查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束")
	private PresellSaleStatus queryTab;

	/**
	 * 查询平台类型
	 */
	@Schema(description = "查询平台类型")
	private Platform platform;

	/**
	 * notId
	 */
	@Schema(description = "notId")
	private Long notId;
}