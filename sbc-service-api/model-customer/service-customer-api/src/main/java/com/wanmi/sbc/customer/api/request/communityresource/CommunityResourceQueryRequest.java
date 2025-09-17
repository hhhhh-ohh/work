package com.wanmi.sbc.customer.api.request.communityresource;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.customer.bean.enums.CommunityResourceServiceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>社区拼团资源库表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-21 17:12:01
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityResourceQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 资源类型 0:图片 1:视频
	 */
	@Schema(description = "资源类型 0:图片 1:视频")
	private ResourceType type;

	/**
	 * 业务id
	 */
	@Schema(description = "业务id")
	private String serviceId;

	/**
	 * 业务类型
	 */
	@Schema(description = "业务类型")
	private CommunityResourceServiceType serviceType;
}