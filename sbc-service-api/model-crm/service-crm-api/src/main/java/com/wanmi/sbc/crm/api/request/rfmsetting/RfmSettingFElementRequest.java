package com.wanmi.sbc.crm.api.request.rfmsetting;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

/**
 * <p>rfm参数配置新增参数</p>
 * @author zhanglingke
 * @date 2019-10-14 14:33:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
public class RfmSettingFElementRequest extends RfmSettingRFMElementBaseRequest {
	private static final long serialVersionUID = 1L;

}