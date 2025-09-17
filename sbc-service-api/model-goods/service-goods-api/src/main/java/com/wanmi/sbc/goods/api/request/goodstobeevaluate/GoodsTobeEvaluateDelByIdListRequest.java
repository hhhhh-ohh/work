package com.wanmi.sbc.goods.api.request.goodstobeevaluate;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除订单商品待评价请求参数</p>
 * @author lzw
 * @date 2019-03-20 14:47:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTobeEvaluateDelByIdListRequest extends BaseRequest {

	private static final long serialVersionUID = -8511008317856946256L;

	/**
	 * 批量删除-idList
	 */
	@Schema(description = "id")
	@NotEmpty
	private List<String> idList;
}