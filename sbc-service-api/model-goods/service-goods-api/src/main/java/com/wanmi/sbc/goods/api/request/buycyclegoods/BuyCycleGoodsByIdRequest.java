package com.wanmi.sbc.goods.api.request.buycyclegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询周期购spu表请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsByIdRequest extends BaseRequest {


	private static final long serialVersionUID = -4435579116391789476L;
	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Long id;

}