package com.wanmi.sbc.goods.api.request.goodspropcaterel;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除商品类目与属性关联请求参数</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropCateRelDelByIdListRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-关联表主键List
	 */
	@Schema(description = "批量删除-关联表主键List")
	@NotEmpty
	private List<Long> relIdList;
}
