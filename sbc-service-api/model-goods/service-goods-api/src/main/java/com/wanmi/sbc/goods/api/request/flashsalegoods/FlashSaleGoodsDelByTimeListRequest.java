package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除抢购商品表请求参数</p>
 * @author yxz
 * @date 2019-06-14 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsDelByTimeListRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-activityTimeList
	 */
	@Schema(description = "批量删除-activityTimeList")
	@NotEmpty
	private List<String> activityTimeList;
}