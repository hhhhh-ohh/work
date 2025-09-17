package com.wanmi.sbc.empower.api.request.minimsgcustomerrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>客户订阅消息信息表修改参数</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgCusRecordModifyByActivityIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 推送活动id
	 */
	@Schema(description = "推送活动id")
	@Max(9223372036854775807L)
	private Long activityId;

	/**
	 * 主键IDs
	 */
	@Schema(description = "主键IDs")
	private List<Long> idList;

}
