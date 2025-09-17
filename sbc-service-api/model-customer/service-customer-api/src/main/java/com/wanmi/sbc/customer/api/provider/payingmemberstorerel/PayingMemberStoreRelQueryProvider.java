package com.wanmi.sbc.customer.api.provider.payingmemberstorerel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelPageRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelPageResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelListRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelListResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelByIdResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelExportRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商家与付费会员等级关联表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberStoreRelQueryProvider")
public interface PayingMemberStoreRelQueryProvider {

	/**
	 * 分页查询商家与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberStoreRelPageReq 分页请求参数和筛选对象 {@link PayingMemberStoreRelPageRequest}
	 * @return 商家与付费会员等级关联表分页列表信息 {@link PayingMemberStoreRelPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberstorerel/page")
	BaseResponse<PayingMemberStoreRelPageResponse> page(@RequestBody @Valid PayingMemberStoreRelPageRequest payingMemberStoreRelPageReq);

	/**
	 * 列表查询商家与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberStoreRelListReq 列表请求参数和筛选对象 {@link PayingMemberStoreRelListRequest}
	 * @return 商家与付费会员等级关联表的列表信息 {@link PayingMemberStoreRelListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberstorerel/list")
	BaseResponse<PayingMemberStoreRelListResponse> list(@RequestBody @Valid PayingMemberStoreRelListRequest payingMemberStoreRelListReq);

	/**
	 * 单个查询商家与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberStoreRelByIdRequest 单个查询商家与付费会员等级关联表请求参数 {@link PayingMemberStoreRelByIdRequest}
	 * @return 商家与付费会员等级关联表详情 {@link PayingMemberStoreRelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberstorerel/get-by-id")
	BaseResponse<PayingMemberStoreRelByIdResponse> getById(@RequestBody @Valid PayingMemberStoreRelByIdRequest payingMemberStoreRelByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberStoreRelExportRequest}
	 * @return 商家与付费会员等级关联表数量 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberstorerel/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberStoreRelExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberStoreRelExportRequest}
	 * @return 商家与付费会员等级关联表列表 {@link PayingMemberStoreRelExportResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberstorerel/export/page")
	BaseResponse<PayingMemberStoreRelExportResponse> exportPayingMemberStoreRelRecord(@RequestBody @Valid PayingMemberStoreRelExportRequest request);

}

