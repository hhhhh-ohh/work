package com.wanmi.sbc.customer.api.provider.payingmemberrecommendrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelPageRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelPageResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelListRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelListResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelByIdResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelExportRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>推荐商品与付费会员等级关联表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberRecommendRelQueryProvider")
public interface PayingMemberRecommendRelQueryProvider {

	/**
	 * 分页查询推荐商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecommendRelPageReq 分页请求参数和筛选对象 {@link PayingMemberRecommendRelPageRequest}
	 * @return 推荐商品与付费会员等级关联表分页列表信息 {@link PayingMemberRecommendRelPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrecommendrel/page")
	BaseResponse<PayingMemberRecommendRelPageResponse> page(@RequestBody @Valid PayingMemberRecommendRelPageRequest payingMemberRecommendRelPageReq);

	/**
	 * 列表查询推荐商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecommendRelListReq 列表请求参数和筛选对象 {@link PayingMemberRecommendRelListRequest}
	 * @return 推荐商品与付费会员等级关联表的列表信息 {@link PayingMemberRecommendRelListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrecommendrel/list")
	BaseResponse<PayingMemberRecommendRelListResponse> list(@RequestBody @Valid PayingMemberRecommendRelListRequest payingMemberRecommendRelListReq);

	/**
	 * 单个查询推荐商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecommendRelByIdRequest 单个查询推荐商品与付费会员等级关联表请求参数 {@link PayingMemberRecommendRelByIdRequest}
	 * @return 推荐商品与付费会员等级关联表详情 {@link PayingMemberRecommendRelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrecommendrel/get-by-id")
	BaseResponse<PayingMemberRecommendRelByIdResponse> getById(@RequestBody @Valid PayingMemberRecommendRelByIdRequest payingMemberRecommendRelByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberRecommendRelExportRequest}
	 * @return 推荐商品与付费会员等级关联表数量 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrecommendrel/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberRecommendRelExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberRecommendRelExportRequest}
	 * @return 推荐商品与付费会员等级关联表列表 {@link PayingMemberRecommendRelExportResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrecommendrel/export/page")
	BaseResponse<PayingMemberRecommendRelExportResponse> exportPayingMemberRecommendRelRecord(@RequestBody @Valid PayingMemberRecommendRelExportRequest request);

}

