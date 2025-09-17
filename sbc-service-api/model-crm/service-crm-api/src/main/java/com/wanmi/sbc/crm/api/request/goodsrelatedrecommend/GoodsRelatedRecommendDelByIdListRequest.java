package com.wanmi.sbc.crm.api.request.goodsrelatedrecommend;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除商品相关性推荐请求参数</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendDelByIdListRequest extends CrmBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键idList
	 */
	@Schema(description = "批量删除-主键idList")
	@NotEmpty
	private List<Long> idList;
}
