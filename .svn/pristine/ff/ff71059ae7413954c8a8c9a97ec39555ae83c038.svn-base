package com.wanmi.sbc.setting.api.request.storeexpresscompanyrela;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>店铺快递公司关联表分页查询请求参数</p>
 * @author lq
 * @date 2019-11-05 16:12:13
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreExpressCompanyRelaPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键UUIDList
	 */
	@Schema(description = "批量查询-主键UUIDList")
	private List<Long> idList;

	/**
	 * 主键UUID
	 */
	@Schema(description = "主键UUID")
	private Long id;

	/**
	 * 主键ID,自增
	 */
	@Schema(description = "主键ID,自增")
	private Long expressCompanyId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	private Long storeId;

	/**
	 * 商家标识
	 */
	@Schema(description = "商家标识")
	private Integer companyInfoId;
}