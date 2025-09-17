package com.wanmi.sbc.goods.api.request.goodstemplate;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除GoodsTemplate请求参数</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTemplateDelByIdListRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-idList
	 */
	@Schema(description = "批量删除-idList")
	@NotEmpty
	private List<Long> idList;
}
