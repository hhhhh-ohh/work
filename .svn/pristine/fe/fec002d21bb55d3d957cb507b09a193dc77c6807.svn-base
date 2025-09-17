package com.wanmi.sbc.goods.api.request.buycyclegoods;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>周期购spu表分页查询请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsPageRequest extends BaseQueryRequest {


	private static final long serialVersionUID = 5866763800393899734L;
	/**
	 * 模糊条件-商品名称
	 */
	@Schema(description = "模糊条件-商品名称")
	private String likeGoodsName;

	/**
	 * 周期购商品状态 0：生效；1：失效
	 */
	@Schema(description = "周期购商品状态 0：生效；1：失效")
	private Integer cycleState;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 批量查询-spuIdList
	 */
	@Schema(description = "批量查询-spuIdList",hidden = true)
	private List<String> goodsIdList;

}