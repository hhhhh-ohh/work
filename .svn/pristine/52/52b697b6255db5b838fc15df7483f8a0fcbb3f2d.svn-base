package com.wanmi.sbc.goods.api.request.goodsaudit;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除商品审核请求参数</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAuditDelByIdListRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-goodsIdList
	 */
	@Schema(description = "批量删除-goodsIdList")
	private List<String> goodsIdList;

	/**
	 * 商品id类型 0:goodsId 1:oldGoodsId
	 */
	@Schema(description = "商品id类型 0:goodsId 1:oldGoodsId")
	private Integer goodsIdType;
}
