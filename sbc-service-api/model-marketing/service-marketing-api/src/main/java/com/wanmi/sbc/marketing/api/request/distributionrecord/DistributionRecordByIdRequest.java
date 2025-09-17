package com.wanmi.sbc.marketing.api.request.distributionrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询DistributionRecord请求参数</p>
 * @author baijz
 * @date 2019-02-27 18:56:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionRecordByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 分销记录表主键
	 */
	@NotNull
	private String recordId;
}