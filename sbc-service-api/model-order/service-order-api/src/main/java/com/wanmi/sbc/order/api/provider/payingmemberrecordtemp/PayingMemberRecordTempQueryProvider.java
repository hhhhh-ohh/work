package com.wanmi.sbc.order.api.provider.payingmemberrecordtemp;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempPageRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempPageResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempListRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempListResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempByIdRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempByIdResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempExportRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>付费记录临时表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@FeignClient(value = "${application.order.name}", contextId = "PayingMemberRecordTempQueryProvider")
public interface PayingMemberRecordTempQueryProvider {

	/**
	 * 分页查询付费记录临时表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordTempPageReq 分页请求参数和筛选对象 {@link PayingMemberRecordTempPageRequest}
	 * @return 付费记录临时表分页列表信息 {@link PayingMemberRecordTempPageResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecordtemp/page")
	BaseResponse<PayingMemberRecordTempPageResponse> page(@RequestBody @Valid PayingMemberRecordTempPageRequest payingMemberRecordTempPageReq);

	/**
	 * 列表查询付费记录临时表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordTempListReq 列表请求参数和筛选对象 {@link PayingMemberRecordTempListRequest}
	 * @return 付费记录临时表的列表信息 {@link PayingMemberRecordTempListResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecordtemp/list")
	BaseResponse<PayingMemberRecordTempListResponse> list(@RequestBody @Valid PayingMemberRecordTempListRequest payingMemberRecordTempListReq);

	/**
	 * 单个查询付费记录临时表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordTempByIdRequest 单个查询付费记录临时表请求参数 {@link PayingMemberRecordTempByIdRequest}
	 * @return 付费记录临时表详情 {@link PayingMemberRecordTempByIdResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecordtemp/get-by-id")
	BaseResponse<PayingMemberRecordTempByIdResponse> getById(@RequestBody @Valid PayingMemberRecordTempByIdRequest payingMemberRecordTempByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link PayingMemberRecordTempExportRequest}
	 * @return 付费记录临时表数量 {@link Long}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecordtemp/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid PayingMemberRecordTempExportRequest request);

	/**
	 * {tableDesc}导出列表查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出列表查询请求 {@link PayingMemberRecordTempExportRequest}
	 * @return 付费记录临时表列表 {@link PayingMemberRecordTempExportResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecordtemp/export/page")
	BaseResponse<PayingMemberRecordTempExportResponse> exportPayingMemberRecordTempRecord(@RequestBody @Valid PayingMemberRecordTempExportRequest request);

}

