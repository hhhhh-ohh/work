package com.wanmi.sbc.goods.api.request.goodspropertydetail;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除商品属性值请求参数</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailDelByIdListRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-属性值idList
	 */
	@Schema(description = "批量删除-属性值idList")
	@NotEmpty
	private List<Long> detailIdList;
}
