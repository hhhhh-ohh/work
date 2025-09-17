package com.wanmi.sbc.customer.api.request.storesharerecord;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除商城分享请求参数</p>
 * @author zhangwenchang
 * @date 2020-03-06 13:48:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreShareRecordDelByIdRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * shareId
	 */
	@Schema(description = "shareId")
	@NotNull
	private Long shareId;
}