package com.wanmi.sbc.marketing.api.request.drawactivity;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>单个删除抽奖活动表请求参数</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawActivityDelByIdRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Long id;
}