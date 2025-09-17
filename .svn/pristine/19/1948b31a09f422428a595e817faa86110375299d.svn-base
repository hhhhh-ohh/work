package com.wanmi.sbc.marketing.api.request.appointmentsale;


import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除预约抢购请求参数</p>
 * @author zxd
 * @date 2020-05-21 10:32:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleDelByIdListRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-idList
	 */
	@Schema(description = "批量删除-idList")
	@NotEmpty
	private List<Long> idList;
}
