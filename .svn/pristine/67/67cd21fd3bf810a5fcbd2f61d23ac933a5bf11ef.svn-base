package com.wanmi.sbc.empower.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>物流记录VO</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Schema
@Data
public class LogisticsLogVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

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

	/**
	 * 快递单号
	 */
	@Schema(description = "快递单号")
	private String logisticNo;

	/**
	 * 购买人编号
	 */
	@Schema(description = "购买人编号")
	private String customerId;

	/**
	 * 是否结束
	 */
	@Schema(description = "是否结束")
	private BoolFlag endFlag;

	/**
	 * 监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。status=shutdown快递单为已签收时status= abort message为“3天查询无记录”或“60天无变化时”对于status=abort需要增加额外的处理逻辑
	 */
	@Schema(description = "监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。status=shutdown快递单为已签收时status= abort message为“3天查询无记录”或“60天无变化时”对于status=abort需要增加额外的处理逻辑")
	private String status;

	/**
	 * 快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态
	 */
	@Schema(description = "快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态")
	private String state;

	/**
	 * 监控状态相关消息，如:3天查询无记录，60天无变化
	 */
	@Schema(description = "监控状态相关消息，如:3天查询无记录，60天无变化")
	private String message;

	/**
	 * 快递公司编码是否出过错
	 */
	@Schema(description = "快递公司编码是否出过错")
	private String autoCheck;

	/**
	 * 本地物流公司标准编码
	 */
	@Schema(description = "本地物流公司标准编码")
	private String comOld;

	/**
	 * 快递纠正新编码
	 */
	@Schema(description = "快递纠正新编码")
	private String comNew;

	/**
	 * 是否签收标记
	 */
	@Schema(description = "是否签收标记")
	private String isCheck;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String phone;

	/**
	 * 出发地城市
	 */
	@Schema(description = "出发地城市")
	private String from;

	/**
	 * 目的地城市
	 */
	@Schema(description = "目的地城市")
	private String to;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	private String goodsImg;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsName;

	/**
	 * 订阅申请状态
	 */
	@Schema(description = "订阅申请状态")
	private BoolFlag successFlag;

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
	private String deliverId;

	/**
	 * 物流记录明细
	 */
	@Schema(description = "物流记录明细")
	private List<LogisticsLogDetailVO> logisticsLogDetails;

}
