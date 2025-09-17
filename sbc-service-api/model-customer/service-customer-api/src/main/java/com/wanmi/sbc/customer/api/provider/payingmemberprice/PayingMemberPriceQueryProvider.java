package com.wanmi.sbc.customer.api.provider.payingmemberprice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPricePageRequest;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPricePageResponse;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceListRequest;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceListResponse;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceByIdResponse;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceExportRequest;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>付费设置表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberPriceQueryProvider")
public interface PayingMemberPriceQueryProvider {

	/**
	 * 分页查询付费设置表API
	 *
	 * @author zhanghao
	 * @param payingMemberPricePageReq 分页请求参数和筛选对象 {@link PayingMemberPricePageRequest}
	 * @return 付费设置表分页列表信息 {@link PayingMemberPricePageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberprice/page")
	BaseResponse<PayingMemberPricePageResponse> page(@RequestBody @Valid PayingMemberPricePageRequest payingMemberPricePageReq);

	/**
	 * 列表查询付费设置表API
	 *
	 * @author zhanghao
	 * @param payingMemberPriceListReq 列表请求参数和筛选对象 {@link PayingMemberPriceListRequest}
	 * @return 付费设置表的列表信息 {@link PayingMemberPriceListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberprice/list")
	BaseResponse<PayingMemberPriceListResponse> list(@RequestBody @Valid PayingMemberPriceListRequest payingMemberPriceListReq);

	/**
	 * 单个查询付费设置表API
	 *
	 * @author zhanghao
	 * @param payingMemberPriceByIdRequest 单个查询付费设置表请求参数 {@link PayingMemberPriceByIdRequest}
	 * @return 付费设置表详情 {@link PayingMemberPriceByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberprice/get-by-id")
	BaseResponse<PayingMemberPriceByIdResponse> getById(@RequestBody @Valid PayingMemberPriceByIdRequest payingMemberPriceByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberPriceExportRequest}
	 * @return 付费设置表数量 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberprice/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberPriceExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberPriceExportRequest}
	 * @return 付费设置表列表 {@link PayingMemberPriceExportResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberprice/export/page")
	BaseResponse<PayingMemberPriceExportResponse> exportPayingMemberPriceRecord(@RequestBody @Valid PayingMemberPriceExportRequest request);

}

