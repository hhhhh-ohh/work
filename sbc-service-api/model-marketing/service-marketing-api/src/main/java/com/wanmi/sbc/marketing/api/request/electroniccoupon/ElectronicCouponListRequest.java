package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>电子卡券表列表查询请求参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-电子卡券idList
	 */
	@Schema(description = "批量查询-电子卡券idList")
	private List<Long> idList;

	/**
	 * 电子卡券id
	 */
	@Schema(description = "电子卡券id")
	private Long id;

	/**
	 * 电子卡券名称
	 */
	@Schema(description = "电子卡券名称")
	private String couponName;

	/**
	 * 是否删除 0 否  1 是
	 */
	@Schema(description = "是否删除 0 否  1 是")
	private DeleteFlag delFlag;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 销售状态
	 */
	@Schema(description = "销售状态 0、未过销售期 1、已过销售期")
	private Integer saleType;

	/**
	 * 包含的卡券id
	 */
	@Schema(description = "包含的卡券id")
	private List<Long> includeIds;

	/**
	 * 需排除的商品
	 */
	@Schema(description = "需排除的商品")
	private String goodsId;

	/**
	 * 绑定标识
	 */
	@Schema(description = "绑定标识")
	private Boolean bindingFlag;
}