package com.wanmi.sbc.order.api.request.refundcallbackresult;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>退款回调结果通用查询请求参数</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundCallBackResultQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String businessId;

	/**
	 * 订单号list
	 */
	@Schema(description = "订单号list")
	private List<String> businessIds;

	/**
	 * 回调结果内容
	 */
	@Schema(description = "回调结果内容")
	private String resultContext;

	/**
	 * 结果状态，0：待处理；1:处理中 2：处理成功；3：处理失败
	 */
	@Schema(description = "结果状态，0：待处理；1:处理中 2：处理成功；3：处理失败")
	private PayCallBackResultStatus resultStatus;

	/**
	 * 处理失败次数
	 */
	@Schema(description = "处理失败次数")
	private Integer errorNum;

	/**
	 * 支付方式，0：微信；1：支付宝；2：银联
	 */
	@Schema(description = "支付方式，0：微信；1：支付宝；2：银联")
	private Integer payType;

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

}