package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.AuditStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据活动ID批量更新审核状态</p>
 * @author groupon
 * @date 2019-05-15 14:49:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsInfoModifyAuditStatusRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 拼团活动ID集合
	 */
	@Schema(description = "拼团活动ID集合")
	@NonNull
	private List<String> grouponActivityIds;

	/**
	 * 审核状态
	 */
	@Schema(description = " 审核状态")
	@NonNull
	private AuditStatus auditStatus;
}