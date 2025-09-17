package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>卡密发放记录表分页查询请求参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:37:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicSendRecordPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-记录idList
	 */
	@Schema(description = "批量查询-记录idList")
	private List<String> idList;

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
	 * 搜索条件:发放时间开始
	 */
	@Schema(description = "搜索条件:发放时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTimeBegin;
	/**
	 * 搜索条件:发放时间截止
	 */
	@Schema(description = "搜索条件:发放时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTimeEnd;

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
	 * 是否删除 0 否  1 是
	 */
	@Schema(description = "是否删除 0 否  1 是")
	private DeleteFlag delFlag;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

}