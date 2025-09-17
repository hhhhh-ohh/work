package com.wanmi.sbc.customer.api.request.storesharerecord;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除商城分享请求参数</p>
 * @author zhangwenchang
 * @date 2020-03-06 13:48:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreShareRecordDelByIdListRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-shareIdList
	 */
	@Schema(description = "批量删除-shareIdList")
	@NotEmpty
	private List<Long> shareIdList;
}