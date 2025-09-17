package com.wanmi.sbc.customer.api.provider.wechatvideo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.wechatvideo.*;
import com.wanmi.sbc.customer.api.response.wechatvideo.WechatVideoStoreApplyResponse;
import com.wanmi.sbc.customer.api.response.wechatvideo.WechatVideoStoreAuditModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>视频带货申请保存服务Provider</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@FeignClient(value = "${application.customer.name}", contextId = "WechatVideoStoreAuditProvider")
public interface WechatVideoStoreAuditProvider {

	/**
	 * 商家提交视频带货申请
	 * @param applyRequest 视频带货申请
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/wechatvideostoreaudit/apply")
	BaseResponse<WechatVideoStoreApplyResponse> apply(@RequestBody @Valid WechatVideoStoreApplyRequest applyRequest);

	/**
	 * 修改视频带货申请API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoStoreAuditModifyRequest 视频带货申请修改参数结构 {@link WechatVideoStoreAuditModifyRequest}
	 * @return 修改的视频带货申请信息 {@link WechatVideoStoreAuditModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/wechatvideostoreaudit/modify")
	BaseResponse<WechatVideoStoreAuditModifyResponse> modify(@RequestBody @Valid WechatVideoStoreAuditModifyRequest wechatVideoStoreAuditModifyRequest);

	/**
	 * 单个删除视频带货申请API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoStoreAuditDelByIdRequest 单个删除参数结构 {@link WechatVideoStoreAuditDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/wechatvideostoreaudit/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid WechatVideoStoreAuditDelByIdRequest wechatVideoStoreAuditDelByIdRequest);

	/**
	 * 批量删除视频带货申请API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoStoreAuditDelByIdListRequest 批量删除参数结构 {@link WechatVideoStoreAuditDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/wechatvideostoreaudit/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid WechatVideoStoreAuditDelByIdListRequest wechatVideoStoreAuditDelByIdListRequest);

	/**
	 * 修改指定字段视频带货申请API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoStoreAuditModifyFieldRequest 修改 {@link WechatVideoStoreAuditModifyFieldRequest}
	 * @return 修改结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/wechatvideostoreaudit/update-field")
	BaseResponse updateField(@RequestBody @Valid WechatVideoStoreAuditModifyFieldRequest wechatVideoStoreAuditModifyFieldRequest);

}

