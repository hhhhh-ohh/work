package com.wanmi.sbc.customer.api.provider.payingmemberrightsrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelPageRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelPageResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelListRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelListResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelByIdResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelExportRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>权益与付费会员等级关联表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberRightsRelQueryProvider")
public interface PayingMemberRightsRelQueryProvider {

	/**
	 * 分页查询权益与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRightsRelPageReq 分页请求参数和筛选对象 {@link PayingMemberRightsRelPageRequest}
	 * @return 权益与付费会员等级关联表分页列表信息 {@link PayingMemberRightsRelPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrightsrel/page")
	BaseResponse<PayingMemberRightsRelPageResponse> page(@RequestBody @Valid PayingMemberRightsRelPageRequest payingMemberRightsRelPageReq);

	/**
	 * 列表查询权益与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRightsRelListReq 列表请求参数和筛选对象 {@link PayingMemberRightsRelListRequest}
	 * @return 权益与付费会员等级关联表的列表信息 {@link PayingMemberRightsRelListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrightsrel/list")
	BaseResponse<PayingMemberRightsRelListResponse> list(@RequestBody @Valid PayingMemberRightsRelListRequest payingMemberRightsRelListReq);

	/**
	 * 单个查询权益与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRightsRelByIdRequest 单个查询权益与付费会员等级关联表请求参数 {@link PayingMemberRightsRelByIdRequest}
	 * @return 权益与付费会员等级关联表详情 {@link PayingMemberRightsRelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrightsrel/get-by-id")
	BaseResponse<PayingMemberRightsRelByIdResponse> getById(@RequestBody @Valid PayingMemberRightsRelByIdRequest payingMemberRightsRelByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberRightsRelExportRequest}
	 * @return 权益与付费会员等级关联表数量 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrightsrel/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberRightsRelExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberRightsRelExportRequest}
	 * @return 权益与付费会员等级关联表列表 {@link PayingMemberRightsRelExportResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrightsrel/export/page")
	BaseResponse<PayingMemberRightsRelExportResponse> exportPayingMemberRightsRelRecord(@RequestBody @Valid PayingMemberRightsRelExportRequest request);

}

