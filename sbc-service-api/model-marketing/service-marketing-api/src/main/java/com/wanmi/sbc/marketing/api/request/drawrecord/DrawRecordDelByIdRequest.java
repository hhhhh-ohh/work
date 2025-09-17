package com.wanmi.sbc.marketing.api.request.drawrecord;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>单个删除抽奖记录表请求参数</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordDelByIdRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 抽奖记录主键
	 */
	@Schema(description = "抽奖记录主键")
	@NotNull
	private Long id;
}