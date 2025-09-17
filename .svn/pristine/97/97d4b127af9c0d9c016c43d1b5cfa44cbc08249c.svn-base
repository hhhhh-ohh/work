package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>卡密发放记录表VO</p>
 * @author 许云鹏
 * @date 2022-01-26 17:37:31
 */
@Schema
@Data
public class ElectronicSendRecordVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录id
	 */
	@Schema(description = "记录id")
	private String id;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;

	/**
	 * sku编码
	 */
	@Schema(description = "sku编码")
	private String skuNo;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String skuName;

	/**
	 * 收货人
	 */
	@Schema(description = "收货人")
	private String account;

	/**
	 * 发放时间
	 */
	@Schema(description = "发放时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

	/**
	 * 卡券id
	 */
	@Schema(description = "卡券id")
	private Long couponId;

	/**
	 * 卡券名称
	 */
	@Schema(description = "卡券名称")
	private String couponName;

	/**
	 * 卡密id
	 */
	@Schema(description = "卡密id")
	private String cardId;

	/**
	 * 卡券内容
	 */
	@Schema(description = "卡券内容")
	private String cardContent;

	/**
	 * 发放状态  0、发放成功 1、发送中 2、发送失败
	 */
	@Schema(description = "发放状态  0、发放成功 1、发送中 2、发送失败")
	private Integer sendState;

	/**
	 * 发放失败原因  0、库存不足1、已过销售期 2、其他原因
	 */
	@Schema(description = "发放失败原因  0、库存不足1、已过销售期 2、其他原因")
	private Integer failReason;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 注销状态 0:正常 1:注销中 2:已注销
	 */
	@Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
	private LogOutStatus logOutStatus;

}