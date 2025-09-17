package com.wanmi.sbc.customer.api.request.storeevaluate;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

/**
 * <p>店铺评价分页查询请求参数</p>
 * @author liutao
 * @date 2019-02-26 10:23:32
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreEvaluatePageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-评价idList
	 */
	@Schema(description = "批量查询-评价idList")
	private List<String> evaluateIdList;

	/**
	 * 评价id
	 */
	@Schema(description = "评价id")
	private String evaluateId;

	/**
	 * 店铺Id
	 */
	@Schema(description = "店铺Id")
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	private String customerName;

	/**
	 * 会员登录账号|手机号
	 */
	@Schema(description = "会员登录账号|手机号")
	private String customerAccount;

	/**
	 * 商品评分
	 */
	@Schema(description = "商品评分")
	private Integer goodsScore;

	/**
	 * 服务评分
	 */
	@Schema(description = "服务评分")
	private Integer serverScore;

	/**
	 * 物流评分
	 */
	@Schema(description = "物流评分")
	private Integer logisticsScore;

	/**
	 * 综合评分（冗余字段看后面怎么做）
	 */
	@Schema(description = "综合评分（冗余字段看后面怎么做）")
	private BigDecimal compositeScore;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是")
	private Integer delFlag;

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
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 搜索条件:删除时间开始
	 */
	@Schema(description = "搜索条件:删除时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime delTimeBegin;
	/**
	 * 搜索条件:删除时间截止
	 */
	@Schema(description = "搜索条件:删除时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime delTimeEnd;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String delPerson;

	/**
	 * 评分周期 0：30天，1：90天，2：180天
	 */
	@Schema(description = "评分周期 0：30天，1：90天，2：180天")
	private Integer scoreCycle;

}