package com.wanmi.sbc.crm.api.request.IntelligentRecommendation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.bean.enums.PositionType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDateTime;

/**
 * <p>商品智能推荐-浏览商品埋点参数</p>
 *
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelligentRecommendationBrowseGoodsRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	/**
	 * 商品智能推荐-商品id
	 */
	private String goodsId;

	/**
	 * 商品智能推荐-类目id
	 */
	private Long cateId;

	/**
	 * 推荐类型  热门:0 相关性:1
	 */
	private Integer recommendType;

	/**
	 * 浏览类目还是商品 商品为1，类目为0
	 */
	private Integer item;

	/**
	 * 坑位
	 */
	@NotNull
	private PositionType type;

	/**
	 * 坑位（大数据平台用）
	 */
	private Integer location;

	/**
	 * 客户id
	 */
	private String customerId;

	/**
	 事件类型 0：浏览，1：点击
	 */
	 private Integer eventType = NumberUtils.INTEGER_ZERO;

	/**
	 发生时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	 private LocalDateTime createTime;
}