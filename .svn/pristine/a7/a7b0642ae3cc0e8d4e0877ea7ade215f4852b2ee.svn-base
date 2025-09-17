package com.wanmi.sbc.customer.api.request.payingmemberprice;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除付费设置表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPriceDelByIdListRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-付费设置idList
	 */
	@Schema(description = "批量删除-付费设置idList")
	@NotEmpty
	private List<Integer> priceIdList;
}
