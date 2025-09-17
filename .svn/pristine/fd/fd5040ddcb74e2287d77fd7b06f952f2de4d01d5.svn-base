package com.wanmi.sbc.account.api.request.credit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>授信订单信息通用查询请求参数</p>
 * @author chenli
 * @date 2021-03-09 16:21:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditRepayQueryRequest extends BaseQueryRequest {
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
	 * 客户id
	 */
	@Schema(description = "客户id")
	private String customerId;

	/**
	 * 批量查询-客户IdList
	 */
	@Schema(description = "批量查询-客户IdList")
	private List<String> CustomerIdList;

	/**
	 * 还款单号
	 */
	@Schema(description = "还款单号")
	private String repayOrderCode;

	/**
	 * 授信额度
	 */
	@Schema(description = "授信额度")
	private BigDecimal creditAmount;

	/**
	 * 还款金额
	 */
	@Schema(description = "还款金额")
	private BigDecimal repayAmount;

	/**
	 * 还款说明
	 */
	@Schema(description = "还款说明")
	private String repayNotes;

	/**
	 * 还款状态 0待还款 1还款成功 2 已作废
	 */
	@Schema(description = "还款状态 0待还款 1还款成功 2已作废")
	private CreditRepayStatus repayStatus;

	/**
	 * 还款方式 0银联，1微信，2支付宝
	 */
	@Schema(description = "还款方式 0银联，1微信，2支付宝")
	private CreditRepayTypeEnum repayType;

	/**
	 * 搜索条件:还款时间开始
	 */
	@Schema(description = "搜索条件:还款时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime repayTimeBegin;
	/**
	 * 搜索条件:还款时间截止
	 */
	@Schema(description = "搜索条件:还款时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime repayTimeEnd;

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
	 * 创建人id
	 */
	@Schema(description = "创建人id")
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
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是")
	private DeleteFlag delFlag;

}