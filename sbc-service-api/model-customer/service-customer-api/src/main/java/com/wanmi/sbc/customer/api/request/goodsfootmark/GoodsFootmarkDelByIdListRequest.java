package com.wanmi.sbc.customer.api.request.goodsfootmark;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * <p>批量删除我的足迹请求参数</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsFootmarkDelByIdListRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-footmarkIdList
	 */
	@Schema(description = "批量删除-footmarkIdList")
	@NotNull
	private List<Long> footmarkIdList;

	@Schema(description = "客户id")
	private String customerId;
}