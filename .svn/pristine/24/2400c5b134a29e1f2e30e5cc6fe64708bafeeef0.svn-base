package com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>新人专享设置新增参数</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseConfigAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 海报设置
	 */
	@Schema(description = "海报设置")
	@Length(max=2048)
	private String poster;

	/**
	 * 优惠券样式布局
	 */
	@Schema(description = "优惠券样式布局")
	@Max(127)
	private Integer couponLayout;

	/**
	 * 商品样式布局
	 */
	@Schema(description = "商品样式布局")
	@Max(127)
	private Integer goodsLayout;

	/**
	 * 活动规则详细
	 */
	@Schema(description = "活动规则详细")
	private String ruleDetail;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

}