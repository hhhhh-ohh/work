package com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>商品相关性推荐新增参数</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	@Length(max=32)
	private String goodsId;

	/**
	 * 关联商品id
	 */
	@Schema(description = "关联商品id")
	@Length(max=32)
	private String relatedGoodsId;

	/**
	 * 提升度
	 */
	@Schema(description = "提升度")
	@Max(9999999999L)
	private Integer lift;

	/**
	 * 权重
	 */
	@Schema(description = "权重")
	private BigDecimal weight;

	/**
	 * 类型，0：关联分析，1：手动关联
	 */
	@Schema(description = "类型，0：关联分析，1：手动关联")
	@Max(127)
	private Integer type;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;



}