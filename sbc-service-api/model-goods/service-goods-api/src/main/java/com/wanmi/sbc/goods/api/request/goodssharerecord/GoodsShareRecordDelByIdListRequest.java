package com.wanmi.sbc.goods.api.request.goodssharerecord;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除商品分享请求参数</p>
 * @author zhangwenchang
 * @date 2020-03-06 13:46:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsShareRecordDelByIdListRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-shareIdList
	 */
	@Schema(description = "批量删除-shareIdList")
	@NotEmpty
	private List<Long> shareIdList;
}