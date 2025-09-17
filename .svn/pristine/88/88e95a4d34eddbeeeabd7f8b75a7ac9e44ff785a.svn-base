package com.wanmi.sbc.crm.api.request.customertagrel;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>会员标签关联列表查询请求参数</p>
 * @author dyt
 * @date 2019-11-12 14:49:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTagRelListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

	/**
	 * 标签id
	 */
	@Schema(description = "标签id", hidden = true)
	private Long tagId;

    /**
     * 显示标签名称
     */
    @Schema(description = "显示标签名称", hidden = true)
	private Boolean showTagName;
}