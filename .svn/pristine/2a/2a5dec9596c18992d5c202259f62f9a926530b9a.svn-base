package com.wanmi.sbc.customer.api.provider.wechatvideo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.wechatvideo.WechatVideoStoreAuditByStoreIdRequest;
import com.wanmi.sbc.customer.api.request.wechatvideo.WechatVideoStoreAuditCountRequest;
import com.wanmi.sbc.customer.api.request.wechatvideo.WechatVideoStoreAuditListRequest;
import com.wanmi.sbc.customer.api.request.wechatvideo.WechatVideoStoreAuditPageRequest;
import com.wanmi.sbc.customer.api.response.wechatvideo.WechatVideoStoreAuditByIdResponse;
import com.wanmi.sbc.customer.api.response.wechatvideo.WechatVideoStoreAuditCountResponse;
import com.wanmi.sbc.customer.api.response.wechatvideo.WechatVideoStoreAuditListResponse;
import com.wanmi.sbc.customer.api.response.wechatvideo.WechatVideoStoreAuditPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>视频带货申请查询服务Provider</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@FeignClient(value = "${application.customer.name}", contextId = "WechatVideoStoreAuditQueryProvider")
public interface WechatVideoStoreAuditQueryProvider {

	/**
	 * 分页查询视频带货申请API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoStoreAuditPageReq 分页请求参数和筛选对象 {@link WechatVideoStoreAuditPageRequest}
	 * @return 视频带货申请分页列表信息 {@link WechatVideoStoreAuditPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/wechatvideostoreaudit/page")
	BaseResponse<WechatVideoStoreAuditPageResponse> page(@RequestBody @Valid WechatVideoStoreAuditPageRequest wechatVideoStoreAuditPageReq);

	/**
	 * 列表查询视频带货申请API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoStoreAuditListReq 列表请求参数和筛选对象 {@link WechatVideoStoreAuditListRequest}
	 * @return 视频带货申请的列表信息 {@link WechatVideoStoreAuditListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/wechatvideostoreaudit/list")
	BaseResponse<WechatVideoStoreAuditListResponse> list(@RequestBody @Valid WechatVideoStoreAuditListRequest wechatVideoStoreAuditListReq);

	/**
	 * 单个查询视频带货申请API
	 *
	 * @author zhaiqiankun
	 * @param request 单个查询视频带货申请请求参数 {@link WechatVideoStoreAuditByStoreIdRequest}
	 * @return 视频带货申请详情 {@link WechatVideoStoreAuditByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/wechatvideostoreaudit/get-by-store-Id")
	BaseResponse<WechatVideoStoreAuditByIdResponse> getByStoreId(@RequestBody @Valid WechatVideoStoreAuditByStoreIdRequest request);

	/**
	 * 查询视频带货申请数量API
	 *
	 * @author zhaiqiankun
	 * @param wechatVideoStoreAuditCountRequest 查询视频带货申请数量请求参数 {@link WechatVideoStoreAuditCountRequest}
	 * @return 视频带货申请结果 {@link WechatVideoStoreAuditCountResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/wechatvideostoreaudit/count")
	BaseResponse<WechatVideoStoreAuditCountResponse> getCount(@RequestBody @Valid WechatVideoStoreAuditCountRequest wechatVideoStoreAuditCountRequest);

}

