package com.wanmi.sbc.goods.api.request.goodsevaluate;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>批量删除商品评价请求参数</p>
 * @author liutao
 * @date 2019-02-25 15:17:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsEvaluateDelByIdListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-评价idList
	 */
	@NotEmpty
	private List<String> evaluateIdList;
}