package com.wanmi.sbc.empower.api.request.ledgercontent;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询拉卡拉经营内容表请求参数</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContentByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 经营内容id
	 */
	@Schema(description = "经营内容id")
	@NotNull
	private Long contentId;

}