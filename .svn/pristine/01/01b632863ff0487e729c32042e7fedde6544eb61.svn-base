package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.ValidateUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * <p>视频带货申请数量查询请求参数</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditCountRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 分页查询条件 - 查询数量
	 */
	@Schema(description = "分页查询条件 - 查询数量")
	private WechatVideoStoreAuditPageRequest wechatVideoStoreAuditPageRequest;

	/**
	 * 列表查询条件 - 查询数量
	 */
	@Schema(description = "列表查询条件 - 查询数量")
	private WechatVideoStoreAuditListRequest wechatVideoStoreAuditListRequest;

	/**
	 * 分页数量
	 */
	@Schema(description = "分页数量")
	private Integer pageSize;

	@Override
	public void checkParam() {
		Validate.isTrue((Objects.nonNull(wechatVideoStoreAuditPageRequest) || Objects.nonNull(wechatVideoStoreAuditListRequest)), ValidateUtil.NULL_EX_MESSAGE,
		"wechatVideoStoreAuditPageRequest | wechatVideoStoreAuditListRequest");
	}

}