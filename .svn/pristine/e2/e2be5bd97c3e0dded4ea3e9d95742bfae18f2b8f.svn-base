package com.wanmi.sbc.goods.api.request.buycyclegoodsinfo;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除周期购sku表请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsInfoDelByIdListRequest extends GoodsBaseRequest {


	private static final long serialVersionUID = 7148988131272332318L;
	/**
	 * 批量删除-skuIdList
	 */
	@Schema(description = "批量删除-skuIdList")
	@NotEmpty
	private List<Long> ids;
}
