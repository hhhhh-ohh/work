package com.wanmi.sbc.customer.api.request.enterpriseinfo;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除企业信息表请求参数</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoDelByIdListRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-企业IdList
	 */
	@Schema(description = "批量删除-企业IdList")
	@NotEmpty
	private List<String> enterpriseIdList;
}
