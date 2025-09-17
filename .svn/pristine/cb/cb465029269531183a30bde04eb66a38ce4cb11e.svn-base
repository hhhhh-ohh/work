package com.wanmi.sbc.order.api.request.payingmemberrecordtemp;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除付费记录临时表请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecordTempDelByIdListRequest extends OrderBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-记录idList
	 */
	@Schema(description = "批量删除-记录idList")
	@NotEmpty
	private List<String> recordIdList;
}
