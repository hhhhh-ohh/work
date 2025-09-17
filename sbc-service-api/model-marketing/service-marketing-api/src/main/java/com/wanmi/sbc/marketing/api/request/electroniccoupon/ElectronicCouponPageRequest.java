package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>电子卡券表分页查询请求参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponPageRequest extends BaseQueryRequest {
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
}