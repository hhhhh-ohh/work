package com.wanmi.sbc.setting.api.request.pickupemployeerela;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xufeng
 * @date 2021-09-26 11:01:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupSettingRelaDelByEmployeesRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 自提点员工id
	 */
	@Schema(description = "自提点员工id")
	private List<String> employeeIds;

}
