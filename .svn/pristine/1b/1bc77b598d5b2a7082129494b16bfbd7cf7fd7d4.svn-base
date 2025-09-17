package com.wanmi.sbc.customer.api.provider.payingmembercustomerrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.*;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>客户与付费会员等级关联表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberCustomerRelQueryProvider")
public interface PayingMemberCustomerRelQueryProvider {

	/**
	 * 分页查询客户与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberCustomerRelPageReq 分页请求参数和筛选对象 {@link PayingMemberCustomerRelPageRequest}
	 * @return 客户与付费会员等级关联表分页列表信息 {@link PayingMemberCustomerRelPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/page")
	BaseResponse<PayingMemberCustomerRelPageResponse> page(@RequestBody @Valid PayingMemberCustomerRelPageRequest payingMemberCustomerRelPageReq);

	/**
	 * 列表查询客户与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberCustomerRelListReq 列表请求参数和筛选对象 {@link PayingMemberCustomerRelListRequest}
	 * @return 客户与付费会员等级关联表的列表信息 {@link PayingMemberCustomerRelListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/list")
	BaseResponse<PayingMemberCustomerRelListResponse> list(@RequestBody @Valid PayingMemberCustomerRelListRequest payingMemberCustomerRelListReq);

	/**
	 * 单个查询客户与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberCustomerRelByIdRequest 单个查询客户与付费会员等级关联表请求参数 {@link PayingMemberCustomerRelByIdRequest}
	 * @return 客户与付费会员等级关联表详情 {@link PayingMemberCustomerRelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/get-by-id")
	BaseResponse<PayingMemberCustomerRelByIdResponse> getById(@RequestBody @Valid PayingMemberCustomerRelByIdRequest payingMemberCustomerRelByIdRequest);


	/**
	 * 单个查询客户与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberCustomerRelByIdRequest 单个查询客户与付费会员等级关联表请求参数 {@link PayingMemberCustomerRelByIdRequest}
	 * @return 客户与付费会员等级关联表详情 {@link PayingMemberCustomerRelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/find-by-customerId")
	BaseResponse<PayingMemberCustomerRelByIdResponse> findByCustomerId(@RequestBody @Valid PayingMemberCustomerRelQueryRequest payingMemberCustomerRelByIdRequest);


	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberCustomerRelExportRequest}
	 * @return 客户与付费会员等级关联表数量 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberCustomerRelExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberCustomerRelExportRequest}
	 * @return 客户与付费会员等级关联表列表 {@link PayingMemberCustomerRelExportResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/export/page")
	BaseResponse<PayingMemberCustomerRelExportResponse> exportPayingMemberCustomerRelRecord(@RequestBody @Valid PayingMemberCustomerRelExportRequest request);

	/**
	 * 个人中心-付费会员入口-查询结果
	 *
	 * @param payingMemberCustomerByCustomerIdRequest 个人中心-付费会员入口-查询参数 {@link PayingMemberCustomerByCustomerIdRequest}
	 * @return 个人中心-付费会员入口-查询结果 {@link PayingMemberCustomerResponse
	 * }
	 * @author chenli
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomer/setting")
	BaseResponse<PayingMemberCustomerResponse> getPayingMemberCustomerSetting(@RequestBody PayingMemberCustomerByCustomerIdRequest payingMemberCustomerByCustomerIdRequest);

	/**
	 * 根据等级id查询总优惠金额
	 *
	 * @param payingMemberQueryDiscountRequest 优惠金额-查询参数 {@link PayingMemberQueryDiscountRequest}
	 * @return 优惠金额-查询结果 {@link PayingMemberQueryDiscountResponse}
	 * @author xuyunpeng
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomer/discount-by-level-id")
	BaseResponse<PayingMemberQueryDiscountResponse> findDiscountByLevelId(@RequestBody @Valid PayingMemberQueryDiscountRequest payingMemberQueryDiscountRequest);

	/**
	 * 单个查询客户与付费会员等级关联表API(付费等级最大)
	 *
	 * @author shy
	 * @param payingMemberCustomerRelByIdRequest 单个查询客户与付费会员等级关联表请求参数 {@link PayingMemberCustomerRelByIdRequest}
	 * @return 客户与付费会员等级关联表详情 {@link PayingMemberCustomerRelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/find-by-levelId")
	BaseResponse<PayingMemberCustomerRelByIdResponse> findMostByCustomerId(@RequestBody @Valid PayingMemberCustomerRelQueryRequest payingMemberCustomerRelByIdRequest);

	/**
	 * 根据指定时间查询有效会员
	 *
	 * @author xuyunpeng
	 * @param request 请求参数 {@link PayingMemberCustomerIdByWeekRequest}
	 * @return 查询结果 {@link PayingMemberCustomerIdByWeekResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/find-active-customer-id-by-week")
	BaseResponse<PayingMemberCustomerIdByWeekResponse> getActiveCustomerIdByWeek(@RequestBody @Valid PayingMemberCustomerIdByWeekRequest request);

	/**
	 * 检查当前用户是否为付费会员
	 *
	 * @author xuyunpeng
	 * @param request 请求参数 {@link PayingMemberCheckRequest}
	 * @return 查询结果 {@link PayingMemberCheckResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/check-pay-member")
	BaseResponse<PayingMemberCheckResponse> checkPayMember(@RequestBody @Valid PayingMemberCheckRequest request);

	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/find-expiration")
	BaseResponse<PayingMemberCustomerRelExpiraResponse> findExpiration();

}

