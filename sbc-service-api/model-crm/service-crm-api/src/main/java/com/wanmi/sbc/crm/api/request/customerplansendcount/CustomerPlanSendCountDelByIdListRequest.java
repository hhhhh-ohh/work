package com.wanmi.sbc.crm.api.request.customerplansendcount;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除权益礼包优惠券发放统计表请求参数</p>
 * @author zhanghao
 * @date 2020-02-04 13:29:18
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanSendCountDelByIdListRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-礼包优惠券发放统计idList
	 */
	@Schema(description = "批量删除-礼包优惠券发放统计idList")
	@NotEmpty
	private List<Long> idList;
}