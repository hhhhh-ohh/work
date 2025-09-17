package com.wanmi.sbc.marketing.api.request.marketingsuits;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除组合商品主表请求参数</p>
 * @author zhk
 * @date 2020-04-01 20:54:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsDelByIdListRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键idList
	 */
	@Schema(description = "批量删除-主键idList")
	@NotEmpty
	private List<Long> idList;
}
