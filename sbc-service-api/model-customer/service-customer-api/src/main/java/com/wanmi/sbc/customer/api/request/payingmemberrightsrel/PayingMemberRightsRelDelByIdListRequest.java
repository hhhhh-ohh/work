package com.wanmi.sbc.customer.api.request.payingmemberrightsrel;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除权益与付费会员等级关联表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRightsRelDelByIdListRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<Long> idList;
}
