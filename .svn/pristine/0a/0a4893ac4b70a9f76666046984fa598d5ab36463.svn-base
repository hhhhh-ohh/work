package com.wanmi.sbc.goods.api.request.grouponsharerecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询拼团分享访问记录请求参数</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponShareRecordByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull
	private Long id;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID", hidden = true)
	private Long storeId;

}