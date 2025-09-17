package com.wanmi.sbc.goods.api.request.storetobeevaluate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除店铺服务待评价请求参数</p>
 * @author lzw
 * @date 2019-03-20 17:01:46
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreTobeEvaluateDelByIdRequest extends BaseRequest {

	private static final long serialVersionUID = 5278196680180531188L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull
	private String id;
}