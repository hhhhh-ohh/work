package com.wanmi.sbc.customer.api.request.department;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.DepartmentImportDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>部门管理新增参数</p>
 * @author wanggang
 * @date 2020-02-26 19:02:40
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentImportRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private List<DepartmentImportDTO> list;
}