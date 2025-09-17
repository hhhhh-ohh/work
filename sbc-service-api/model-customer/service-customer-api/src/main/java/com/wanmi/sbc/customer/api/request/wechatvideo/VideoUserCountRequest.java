package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.ValidateUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * <p>视频号数量查询请求参数</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUserCountRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 分页查询条件 - 查询数量
	 */
	@Schema(description = "分页查询条件 - 查询数量")
	private VideoUserPageRequest videoUserPageRequest;

	/**
	 * 列表查询条件 - 查询数量
	 */
	@Schema(description = "列表查询条件 - 查询数量")
	private VideoUserListRequest videoUserListRequest;

	/**
	 * 分页数量
	 */
	@Schema(description = "分页数量")
	private Integer pageSize;

	@Override
	public void checkParam() {
		Validate.isTrue((Objects.nonNull(videoUserPageRequest) || Objects.nonNull(videoUserListRequest)), ValidateUtil.NULL_EX_MESSAGE,
		"videoUserPageRequest | videoUserListRequest");
	}

}