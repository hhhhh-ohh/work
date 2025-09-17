package com.wanmi.sbc.goods.api.request.buycyclegoods;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除周期购spu表请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsDelByIdListRequest extends GoodsBaseRequest {


	private static final long serialVersionUID = 6366882166681259692L;
	/**
	 * 批量删除-spuIdList
	 */
	@Schema(description = "批量删除-spuIdList")
	@NotEmpty
	private List<String> goodsIdList;
}
