package com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>微信小程序支付发货信息通用查询请求参数</p>
 * @author 吕振伟
 * @date 2023-07-24 14:13:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxPayUploadShippingInfoQueryRequest extends BaseQueryRequest {
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
	private Long id;

	/**
	 * 支付类型，0：商品订单支付；1：授信还款
	 */
	@Schema(description = "支付类型，0：商品订单支付；1：授信还款")
	private Integer payType;

	/**
	 * 支付订单id
	 */
	@Schema(description = "支付订单id")
	private String businessId;

	/**
	 * 批量-支付订单id
	 */
	@Schema(description = "批量-支付订单id")
	private List<String> businessIdList;

	/**
	 * 支付流水id
	 */
	@Schema(description = "支付流水id")
	private String transactionId;

	/**
	 * 商户号
	 */
	@Schema(description = "商户号")
	private String mchId;

	/**
	 * 接口调用返回结果内容
	 */
	@Schema(description = "接口调用返回结果内容")
	private String resultContext;

	/**
	 * 结果状态，0：待处理；1：处理成功；2：处理失败
	 */
	@Schema(description = "结果状态，0：待处理；1：处理成功；2：处理失败")
	private Integer resultStatus;

	/**
	 * 处理失败次数
	 */
	@Schema(description = "处理失败次数")
	private Integer errorNum;

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
	 * 创建人
	 */
	@Schema(description = "创建人")
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

}