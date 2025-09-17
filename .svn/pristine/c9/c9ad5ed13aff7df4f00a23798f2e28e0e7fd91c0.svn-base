package com.wanmi.sbc.goods.api.request.goodsrestrictedcustomerrela;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除限售配置会员关系表请求参数</p>
 * @author baijz
 * @date 2020-04-08 11:32:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedCustomerRelaDelByIdListRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-限售会员的关系主键List
	 */
	@Schema(description = "批量删除-限售会员的关系主键List")
	@NotEmpty
	private List<Long> relaIdList;
}