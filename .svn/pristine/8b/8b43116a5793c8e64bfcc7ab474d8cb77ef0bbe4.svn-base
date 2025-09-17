package com.wanmi.sbc.empower.api.request.logisticssetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询物流配置请求参数</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsSettingByLogisticsTypeRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 物流类型
	 */
	@Schema(description = "物流类型")
	@NotNull
	private LogisticsType logisticsType;

}
