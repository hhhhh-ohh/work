package com.wanmi.sbc.goods.api.request.buycyclegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>周期购spu表修改参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsModifyStateRequest extends BaseRequest {


	private static final long serialVersionUID = 5343751757964978411L;
	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Long id;

	/**
	 * 周期购商品状态 0：生效；1：失效
	 */
	@Schema(description = "周期购商品状态 0：生效；1：失效")
	@Min(0)
	@Max(1)
	@NotNull
	private Integer cycleState;


}
