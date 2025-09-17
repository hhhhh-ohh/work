package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>电子卡密表VO</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Schema
@Data
public class ElectronicCardVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 卡密Id
	 */
	@Schema(description = "卡密Id")
	private String id;

	/**
	 * 卡券id
	 */
	@Schema(description = "卡券id")
	private Long couponId;

	/**
	 * 卡号
	 */
	@Schema(description = "卡号")
	private String cardNumber;

	/**
	 * 卡密
	 */
	@Schema(description = "卡密")
	private String cardPassword;

	/**
	 * 优惠码
	 */
	@Schema(description = "优惠码")
	private String cardPromoCode;

	/**
	 * 卡密状态  0、未发送 1、已发送 2、已失效
	 */
	@Schema(description = "卡密状态  0、未发送 1、已发送 2、已失效")
	private Integer cardState;

	/**
	 * 卡密销售开始时间
	 */
	@Schema(description = "卡密销售开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleStartTime;

	/**
	 * 卡密销售结束时间
	 */
	@Schema(description = "卡密销售结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleEndTime;

	/**
	 * 批次id
	 */
	@Schema(description = "批次id")
	private String recordId;

	/**
	 * 导入日期
	 */
	@Schema(description = "导入日期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime importTime;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;
}