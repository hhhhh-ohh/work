package com.wanmi.sbc.goods.api.response.groupongoodsinfo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据拼团活动ID、SPU编号集合查询拼团价格最小的拼团SKU信息</p>
 * @author dyt
 * @date 2019-05-15 14:49:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponCountByActivityIdsAndSkuIdsResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "商品skuId")
	private Long count;
}