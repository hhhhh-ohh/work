package com.wanmi.sbc.marketing.api.request.marketingsuits;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * <p>单个查询组合商品主表请求参数</p>
 * @author zhk
 * @date 2020-04-01 20:54:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@NotNull
	private Long id;

}