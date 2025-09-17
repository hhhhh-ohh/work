package com.wanmi.sbc.message.api.request.storenoticesend;

import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>商家公告状态修改参数</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreNoticeSendModifyScanFlagRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 公告ID列表
	 */
	@Schema(description = "公告ID列表")
	@NotEmpty
	private List<Long> noticeIds;

	/**
	 * 定时任务扫描标识
	 */
	@NotNull
	private BoolFlag scanFlag;
}
