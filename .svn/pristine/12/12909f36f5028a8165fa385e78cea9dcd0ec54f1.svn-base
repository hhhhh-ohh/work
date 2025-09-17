package com.wanmi.sbc.goods.api.request.goodstobeevaluate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>订单商品待评价通用查询请求参数</p>
 * @author lzw
 * @date 2019-03-20 14:47:38
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTobeEvaluateQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

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
	 * 商品id(spuId)
	 */
	@Schema(description = "商品id(spuId)")
	private String goodsId;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	private String goodsImg;

	/**
	 * 货品id(skuId)
	 */
	@Schema(description = "货品id(skuId)")
	private String goodsInfoId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsInfoName;

	/**
	 * 规格值名称
	 */
	@Schema(description = "规格值名称")
	private String goodsSpecDetail;

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
	 * 是否评价 0：未评价，1：已评价
	 */
	@Schema(description = "是否评价 0：未评价，1：已评价")
	private Integer evaluateStatus;

	/**
	 * 是否晒单 0：未晒单，1：已晒单
	 */
	@Schema(description = "是否晒单 0：未晒单，1：已晒单")
	private Integer evaluateImgStatus;

	/**
	 * 商品自动评价日期
	 */
	@Schema(description = "商品自动评价日期")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate autoGoodsEvaluateDate;

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

}