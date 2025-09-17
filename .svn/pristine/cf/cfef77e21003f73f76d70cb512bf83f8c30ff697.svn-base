package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>拼团活动商品信息表列表查询请求参数</p>
 * @author dyt
 * @date 2019-05-15 14:49:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponCountByActivityIdsAndSkuIdsRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-拼团活动IDList
	 */
	@NotEmpty
	@Schema(description = "拼团活动IDList")
	private List<String> grouponActivityIdList;

	/**
	 * 批量查询-拼团活动IDList
	 */
	@NotEmpty
	@Schema(description = "拼团活动商品idList")
	private List<String> skuIds;
}