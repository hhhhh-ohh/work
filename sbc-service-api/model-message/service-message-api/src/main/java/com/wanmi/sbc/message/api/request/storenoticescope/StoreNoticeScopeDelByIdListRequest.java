package com.wanmi.sbc.message.api.request.storenoticescope;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除商家公告发送范围请求参数</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeScopeDelByIdListRequest implements Serializable {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键idList
	 */
	@Schema(description = "批量删除-主键idList")
	@NotEmpty
	private List<Long> idList;
}
