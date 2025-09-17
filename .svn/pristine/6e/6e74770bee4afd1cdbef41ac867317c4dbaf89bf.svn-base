package com.wanmi.sbc.message.api.request.storemessagedetail;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>批量删除商家消息/公告请求参数</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageDetailDelByIdListRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键idList
	 */
	@Schema(description = "批量删除-主键idList")
	@NotEmpty
	private List<String> idList;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id", hidden = true)
	@NotNull
	private Long storeId;
}
