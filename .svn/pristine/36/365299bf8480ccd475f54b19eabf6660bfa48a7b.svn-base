package com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>新人专享设置修改参数</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseConfigModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9999999999L)
	private Integer id;

	/**
	 * 海报设置
	 */
	@Schema(description = "海报设置")
	@Length(max=2048)
	@NotEmpty
	private String poster;

	/**
	 * 优惠券样式布局
	 */
	@Schema(description = "优惠券样式布局")
	@Max(2)
	@Min(0)
	@NotNull
	private Integer couponLayout;

	/**
	 * 商品样式布局
	 */
	@Schema(description = "商品样式布局")
	@Max(2)
	@Min(0)
	@NotNull
	private Integer goodsLayout;

	/**
	 * 活动规则详细
	 */
	@Schema(description = "活动规则详细")
	@NotEmpty
	private String ruleDetail;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;


	/**
	 * 活动开始时间
	 */
	@Schema(description = "活动开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@NotNull
	private LocalDateTime startTime;

	/**
	 * 活动结束时间
	 */
	@Schema(description = "活动结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@NotNull
	private LocalDateTime endTime;


	@Schema(description = "商品id集合")
	private List<String> goodsInfoIds;

}
