package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>根据活动ID、SKU编号更新已成团人数</p>
 * @author groupon
 * @date 2019-05-15 14:49:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsInfoModifyAlreadyGrouponNumRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 拼团活动ID
	 */
	@Schema(description = "拼团活动ID")
	@NotBlank
	private String grouponActivityId;

	/**
	 * 拼团SKU编号集合
	 */
	@Schema(description = "拼团SKU编号集合")
	@NotBlank
	private List<String> goodsInfoIds;

	/**
	 * 已成团人数
	 */
	@Schema(description = "已成团人数")
	@NotNull
	private Integer alreadyGrouponNum;
}