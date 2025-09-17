package com.wanmi.sbc.customer.api.provider.payingmemberlevel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.*;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>付费会员等级表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberLevelQueryProvider")
public interface PayingMemberLevelQueryProvider {

	/**
	 * 分页查询付费会员等级表API
	 *
	 * @author zhanghao
	 * @param payingMemberLevelPageReq 分页请求参数和筛选对象 {@link PayingMemberLevelPageRequest}
	 * @return 付费会员等级表分页列表信息 {@link PayingMemberLevelPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/page")
	BaseResponse<PayingMemberLevelPageResponse> page(@RequestBody @Valid PayingMemberLevelPageRequest payingMemberLevelPageReq);

	/**
	 * 列表查询付费会员等级表API
	 *
	 * @author zhanghao
	 * @param payingMemberLevelListReq 列表请求参数和筛选对象 {@link PayingMemberLevelListRequest}
	 * @return 付费会员等级表的列表信息 {@link PayingMemberLevelListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/list")
	BaseResponse<PayingMemberLevelListResponse> list(@RequestBody @Valid PayingMemberLevelListRequest payingMemberLevelListReq);

	/**
	 * 单个查询付费会员等级表API
	 *
	 * @author zhanghao
	 * @param payingMemberLevelByIdRequest 单个查询付费会员等级表请求参数 {@link PayingMemberLevelByIdRequest}
	 * @return 付费会员等级表详情 {@link PayingMemberLevelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/get-by-id")
	BaseResponse<PayingMemberLevelByIdResponse> getById(@RequestBody @Valid PayingMemberLevelByIdRequest payingMemberLevelByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberLevelExportRequest}
	 * @return 付费会员等级表数量 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberLevelExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberLevelExportRequest}
	 * @return 付费会员等级表列表 {@link PayingMemberLevelExportResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/export/page")
	BaseResponse<PayingMemberLevelExportResponse> exportPayingMemberLevelRecord(@RequestBody @Valid PayingMemberLevelExportRequest request);

	/**
	 * 查询付费会员等级数量API
	 *
	 * @author xuyunpeng
	 * @param payingMemberLevelQueryRequest 数量请求参数和筛选对象 {@link PayingMemberLevelQueryRequest}
	 * @return 付费会员等级数量 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/count-levels")
	BaseResponse<Long> countLevels(@RequestBody @Valid PayingMemberLevelQueryRequest payingMemberLevelQueryRequest);

	/**
	 * 根据会员查询等级API
	 *
	 * @author xuyunpeng
	 * @param payingMemberLevelCustomerRequest 请求参数和筛选对象 {@link PayingMemberLevelCustomerRequest}
	 * @return 付费会员等级 {@link PayingMemberLevelListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/list-by-customer-id")
	BaseResponse<PayingMemberLevelListResponse> listByCustomerId(@RequestBody @Valid PayingMemberLevelCustomerRequest payingMemberLevelCustomerRequest);

	/**
	 * 列表查询付费会员等级表API
	 *
	 * @author zhanghao
	 * @param payingMemberLevelListReq 列表请求参数和筛选对象 {@link PayingMemberLevelListRequest}
	 * @return 付费会员等级表的列表信息 {@link PayingMemberLevelListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/list-for-sku")
	BaseResponse<PayingMemberLevelListResponse> listForSku(@RequestBody @Valid PayingMemberLevelListRequest payingMemberLevelListReq);

	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/base-list")
	BaseResponse<PayingMemberLevelListNewResponse> listAllPayingMemberLevelNew();
}

