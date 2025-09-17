package com.wanmi.sbc.goods.api.request.goodspropertydetailrel;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除商品与属性值关联请求参数</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailRelDelByIdListRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-detailRelIdList
	 */
	@Schema(description = "批量删除-detailRelIdList")
	@NotEmpty
	private List<Long> detailRelIdList;
}
