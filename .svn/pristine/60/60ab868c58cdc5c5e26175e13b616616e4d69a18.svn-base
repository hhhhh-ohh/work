package com.wanmi.sbc.empower.api.request.minimsgcustomerrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>查询可实际推送人数</p>
 * @author xufeng
 * @date 2022-08-18 10:26:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgCusRecordByActivityIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	@NotNull
	private Long activityId;

}