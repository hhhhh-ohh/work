package com.wanmi.sbc.order.api.request.payingmemberpayrecord;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费会员支付记录表分页查询请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPayRecordPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<String> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

	/**
	 * 业务id
	 */
	@Schema(description = "业务id")
	private String businessId;

	/**
	 * chargeId
	 */
	@Schema(description = "chargeId")
	private String chargeId;

	/**
	 * 申请价格
	 */
	@Schema(description = "申请价格")
	private BigDecimal applyPrice;

	/**
	 * 实际成功交易价格
	 */
	@Schema(description = "实际成功交易价格")
	private BigDecimal practicalPrice;

	/**
	 * 状态:0处理中(退款状态)/未支付(支付状态) 1成功 2失败
	 */
	@Schema(description = "状态:0处理中(退款状态)/未支付(支付状态) 1成功 2失败")
	private Integer status;

	/**
	 * 支付渠道项id
	 */
	@Schema(description = "支付渠道项id")
	private Integer channelItemId;

	/**
	 * 搜索条件:回调时间开始
	 */
	@Schema(description = "搜索条件:回调时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime callbackTimeBegin;
	/**
	 * 搜索条件:回调时间截止
	 */
	@Schema(description = "搜索条件:回调时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime callbackTimeEnd;

	/**
	 * 搜索条件:交易完成时间开始
	 */
	@Schema(description = "搜索条件:交易完成时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime finishTimeBegin;
	/**
	 * 搜索条件:交易完成时间截止
	 */
	@Schema(description = "搜索条件:交易完成时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime finishTimeEnd;

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
	 * updatePerson
	 */
	@Schema(description = "updatePerson")
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除")
	private DeleteFlag delFlag;

}