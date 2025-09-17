package com.wanmi.sbc.order.api.provider.payingmemberrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecord.*;
import com.wanmi.sbc.order.api.response.payingmemberrecord.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>付费记录表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@FeignClient(value = "${application.order.name}", contextId = "PayingMemberRecordQueryProvider")
public interface PayingMemberRecordQueryProvider {

	/**
	 * 分页查询付费记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordPageReq 分页请求参数和筛选对象 {@link PayingMemberRecordPageRequest}
	 * @return 付费记录表分页列表信息 {@link PayingMemberRecordPageResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/page")
	BaseResponse<PayingMemberRecordPageResponse> page(@RequestBody @Valid PayingMemberRecordPageRequest payingMemberRecordPageReq);

	/**
	 * 列表查询付费记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordListReq 列表请求参数和筛选对象 {@link PayingMemberRecordListRequest}
	 * @return 付费记录表的列表信息 {@link PayingMemberRecordListResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/list")
	BaseResponse<PayingMemberRecordListResponse> list(@RequestBody @Valid PayingMemberRecordListRequest payingMemberRecordListReq);

	/**
	 * 单个查询付费记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordByIdRequest 单个查询付费记录表请求参数 {@link PayingMemberRecordByIdRequest}
	 * @return 付费记录表详情 {@link PayingMemberRecordByIdResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/get-by-id")
	BaseResponse<PayingMemberRecordByIdResponse> getById(@RequestBody @Valid PayingMemberRecordByIdRequest payingMemberRecordByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberRecordExportRequest}
	 * @return 付费记录表数量 {@link Long}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberRecordExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberRecordExportRequest}
	 * @return 付费记录表列表 {@link PayingMemberRecordExportResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/export/page")
	BaseResponse<PayingMemberRecordExportResponse> exportPayingMemberRecordRecord(@RequestBody @Valid PayingMemberRecordExportRequest request);

	/**
	 * 权益记录查询API
	 *
	 * @author xuyunpeng
	 * @param request 权益记录查询请求 {@link PayingMemberRecordRightsRequest}
	 * @return 付费记录表列表 {@link PayingMemberRecordCustomerResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/customer")
	BaseResponse<PayingMemberRecordCustomerResponse> findByRights(@RequestBody @Valid PayingMemberRecordRightsRequest request);

	/**
	 * 单个查询付费记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordByIdRequest 单个查询付费记录表请求参数 {@link PayingMemberRecordByIdRequest}
	 * @return 付费记录表详情 {@link PayingMemberRecordByIdResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/find-by-id")
	BaseResponse<PayingMemberRecordByIdResponse> findById(@RequestBody @Valid PayingMemberRecordByIdRequest payingMemberRecordByIdRequest);

}

