package com.wanmi.sbc.empower.api.request.logisticslog;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * <p>物流记录修改参数</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogModifyEndFlagRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	@Length(max=45)
	private String orderNo;

}
