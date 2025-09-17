package com.wanmi.sbc.order.api.request.payingmemberpayrecord;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除付费会员支付记录表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPayRecordDelByIdListRequest extends OrderBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<String> idList;
}
