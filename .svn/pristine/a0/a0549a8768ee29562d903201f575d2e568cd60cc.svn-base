package com.wanmi.sbc.marketing.api.request.bookingsale;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.dto.BookingSaleGoodsDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>预售信息修改参数</p>
 * @author dany
 * @date 2020-06-05 10:47:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	@NotBlank
	@Length(max=100)
	private String activityName;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	@NotNull
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 预售类型 0：全款预售  1：定金预售
	 */
	@Schema(description = "预售类型 0：全款预售  1：定金预售")
	@NotNull
	@Max(127)
	private Integer bookingType;

	/**
	 * 定金支付开始时间
	 */
	@Schema(description = "定金支付开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime handSelStartTime;

	/**
	 * 定金支付结束时间
	 */
	@Schema(description = "定金支付结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime handSelEndTime;

	/**
	 * 尾款支付开始时间
	 */
	@Schema(description = "尾款支付开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tailStartTime;

	/**
	 * 尾款支付结束时间
	 */
	@Schema(description = "尾款支付结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tailEndTime;

	/**
	 * 预售开始时间
	 */
	@Schema(description = "预售开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bookingStartTime;

	/**
	 * 预售结束时间
	 */
	@Schema(description = "预售结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bookingEndTime;

	/**
	 * 发货日期 2020-01-10
	 */
	@Schema(description = "发货日期 2020-01-10")
	@NotBlank
	@Length(max=10)
	private String deliverTime;

	/**
	 * 参加会员  -1:全部客户 0:全部等级 other:其他等级
	 */
	@Schema(description = "参加会员  -1:全部客户 0:全部等级 other:其他等级")
	@NotBlank
	@Length(max=500)
	private String joinLevel;

	/**
	 * 是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）
	 */
	@Schema(description = "是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）")
	@NotNull
	private DefaultFlag joinLevelType;

	/**
	 * 是否暂停 0:否 1:是
	 */
	@Schema(description = "是否暂停 0:否 1:是")
	@Max(127)
	private Integer pauseFlag;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	/**
	 * 预售活动商品信息
	 */
	@Valid
	@NotEmpty(message = "预售活动商品不能为空")
	@Schema(description = "预售活动商品信息")
	private List<BookingSaleGoodsDTO> bookingSaleGoodsList;

	/**
	 * 预售活动总开始时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Schema(description = "预售活动总开始时间")
	private LocalDateTime startTime;

	/**
	 * 预售活动总结束时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Schema(description = "预售活动总结束时间")
	private LocalDateTime endTime;
}
