package com.wanmi.sbc.empower.api.request.logisticslog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>物流记录新增参数</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	@Length(max=45)
	private String orderNo;

	/**
	 * 快递单号
	 */
	@Schema(description = "快递单号")
	@Length(max=50)
	private String logisticNo;

	/**
	 * 购买人编号
	 */
	@Schema(description = "购买人编号")
	@Length(max=32)
	private String customerId;

	/**
	 * 是否结束
	 */
	@Schema(description = "是否结束")
	@Max(127)
	private Integer endFlag;

	/**
	 * 监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。status=shutdown快递单为已签收时status= abort message为“3天查询无记录”或“60天无变化时”对于status=abort需要增加额外的处理逻辑
	 */
	@Schema(description = "监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。status=shutdown快递单为已签收时status= abort message为“3天查询无记录”或“60天无变化时”对于status=abort需要增加额外的处理逻辑")
	@Length(max=20)
	private String status;

	/**
	 * 快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态
	 */
	@Schema(description = "快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态")
	@Length(max=20)
	private String state;

	/**
	 * 监控状态相关消息，如:3天查询无记录，60天无变化
	 */
	@Schema(description = "监控状态相关消息，如:3天查询无记录，60天无变化")
	@Length(max=128)
	private String message;

	/**
	 * 快递公司编码是否出过错
	 */
	@Schema(description = "快递公司编码是否出过错")
	@Length(max=128)
	private String autoCheck;

	/**
	 * 本地物流公司标准编码
	 */
	@Schema(description = "本地物流公司标准编码")
	@Length(max=50)
	private String comOld;

	/**
	 * 快递纠正新编码
	 */
	@Schema(description = "快递纠正新编码")
	@Length(max=50)
	private String comNew;

	/**
	 * 是否签收标记
	 */
	@Schema(description = "是否签收标记")
	@Length(max=20)
	private String isCheck;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	@Length(max=11)
	private String phone;

	/**
	 * 出发地城市
	 */
	@Schema(description = "出发地城市")
	@Length(max=200)
	private String from;

	/**
	 * 目的地城市
	 */
	@Schema(description = "目的地城市")
	@Length(max=200)
	private String to;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	@Length(max=250)
	private String goodsImg;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	@Length(max=128)
	private String goodsName;

	/**
	 * 订阅申请状态
	 */
	@Schema(description = "订阅申请状态")
	@Max(127)
	private Integer successFlag;

	/**
	 * 签收时间
	 */
	@Schema(description = "签收时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime checkTime;

	/**
	 * 本地发货单号
	 */
	@Schema(description = "本地发货单号")
	@Length(max=50)
	private String deliverId;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	/**
	 * 微信物流信息token
	 */
	@Schema(description = "微信物流信息token", hidden = true)
	private String waybillToken;

	/**
	 * 微信物流信息状态:  0: 运单不存在或者未揽收、1: 已揽件、2: 运输中、3: 派件中、4: 已签收、5: 异常、6: 代签收
	 */
	@Schema(description = "微信物流信息状态:  0: 运单不存在或者未揽收、1: 已揽件、2: 运输中、3: 派件中、4: 已签收、5: 异常、6: 代签收", hidden = true)
	private String waybillStatus;

}