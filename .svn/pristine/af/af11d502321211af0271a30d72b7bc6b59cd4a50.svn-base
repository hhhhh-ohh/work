package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

/**
 * <p>批量删除视频带货申请请求参数</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditDelByIdListRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-idList
	 */
	@Schema(description = "批量删除-idList")
	@NotEmpty
	private List<Integer> idList;
}
