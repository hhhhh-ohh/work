package com.wanmi.sbc.customer.api.provider.payingmemberdiscountrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelPageRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelPageResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelListRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelListResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelByIdResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelExportRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>折扣商品与付费会员等级关联表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberDiscountRelQueryProvider")
public interface PayingMemberDiscountRelQueryProvider {

	/**
	 * 分页查询折扣商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberDiscountRelPageReq 分页请求参数和筛选对象 {@link PayingMemberDiscountRelPageRequest}
	 * @return 折扣商品与付费会员等级关联表分页列表信息 {@link PayingMemberDiscountRelPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberdiscountrel/page")
	BaseResponse<PayingMemberDiscountRelPageResponse> page(@RequestBody @Valid PayingMemberDiscountRelPageRequest payingMemberDiscountRelPageReq);

	/**
	 * 列表查询折扣商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberDiscountRelListReq 列表请求参数和筛选对象 {@link PayingMemberDiscountRelListRequest}
	 * @return 折扣商品与付费会员等级关联表的列表信息 {@link PayingMemberDiscountRelListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberdiscountrel/list")
	BaseResponse<PayingMemberDiscountRelListResponse> list(@RequestBody @Valid PayingMemberDiscountRelListRequest payingMemberDiscountRelListReq);

	/**
	 * 单个查询折扣商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberDiscountRelByIdRequest 单个查询折扣商品与付费会员等级关联表请求参数 {@link PayingMemberDiscountRelByIdRequest}
	 * @return 折扣商品与付费会员等级关联表详情 {@link PayingMemberDiscountRelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberdiscountrel/get-by-id")
	BaseResponse<PayingMemberDiscountRelByIdResponse> getById(@RequestBody @Valid PayingMemberDiscountRelByIdRequest payingMemberDiscountRelByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberDiscountRelExportRequest}
	 * @return 折扣商品与付费会员等级关联表数量 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberdiscountrel/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberDiscountRelExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberDiscountRelExportRequest}
	 * @return 折扣商品与付费会员等级关联表列表 {@link PayingMemberDiscountRelExportResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberdiscountrel/export/page")
	BaseResponse<PayingMemberDiscountRelExportResponse> exportPayingMemberDiscountRelRecord(@RequestBody @Valid PayingMemberDiscountRelExportRequest request);

}

