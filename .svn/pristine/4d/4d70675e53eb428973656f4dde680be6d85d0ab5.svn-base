package com.wanmi.sbc.marketing.api.request.bookingsalegoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>预售商品信息通用查询请求参数</p>
 * @author dany
 * @date 2020-06-05 10:51:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleGoodsQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 预售id
	 */
	@Schema(description = "预售id")
	private Long bookingSaleId;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	private Long storeId;

	/**
	 * skuID
	 */
	@Schema(description = "skuID")
	private String goodsInfoId;

	/**
	 * skuID List
	 */
	@Schema(description = "skuID-List")
	private List<String> goodsInfoIdList;

	/**
	 * 预售id List
	 */
	@Schema(description = "预售id List")
	private List<Long> bookingSaleIdList;

	/**
	 * spuID
	 */
	@Schema(description = "spuID")
	private String goodsId;

	/**
	 * 定金
	 */
	@Schema(description = "定金")
	private BigDecimal handSelPrice;

	/**
	 * 膨胀价格
	 */
	@Schema(description = "膨胀价格")
	private BigDecimal inflationPrice;

	/**
	 * 预售价
	 */
	@Schema(description = "预售价")
	private BigDecimal bookingPrice;

	/**
	 * 预售数量
	 */
	@Schema(description = "预售数量")
	private Integer bookingCount;

	/**
	 * 定金支付数量
	 */
	@Schema(description = "定金支付数量")
	private Integer handSelCount;

	/**
	 * 尾款支付数量
	 */
	@Schema(description = "尾款支付数量")
	private Integer tailCount;

	/**
	 * 全款支付数量
	 */
	@Schema(description = "全款支付数量")
	private Integer payCount;

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
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

}