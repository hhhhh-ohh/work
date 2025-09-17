package com.wanmi.sbc.marketing.api.request.communitystockorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.CommunityStockOrderDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>社区团购备货单新增参数</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStockOrderAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	@Valid
	@Schema(description = "批量-活动备货单")
	@NotEmpty
	private List<CommunityStockOrderDTO> communityStockOrderDTOList;
}