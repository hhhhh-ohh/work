package com.wanmi.sbc.empower.api.provider.ledgermcc;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccPageRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccPageResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccListRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccListResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccByIdRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccByIdResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccExportRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>拉卡拉mcc表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@FeignClient(value = "${application.empower.name}", contextId = "LedgerMccQueryProvider")
public interface LedgerMccQueryProvider {

	/**
	 * 分页查询拉卡拉mcc表API
	 *
	 * @author zhanghao
	 * @param ledgerMccPageReq 分页请求参数和筛选对象 {@link LedgerMccPageRequest}
	 * @return 拉卡拉mcc表分页列表信息 {@link LedgerMccPageResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgermcc/page")
	BaseResponse<LedgerMccPageResponse> page(@RequestBody @Valid LedgerMccPageRequest ledgerMccPageReq);

	/**
	 * 列表查询拉卡拉mcc表API
	 *
	 * @author zhanghao
	 * @param ledgerMccListReq 列表请求参数和筛选对象 {@link LedgerMccListRequest}
	 * @return 拉卡拉mcc表的列表信息 {@link LedgerMccListResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgermcc/list")
	BaseResponse<LedgerMccListResponse> list(@RequestBody @Valid LedgerMccListRequest ledgerMccListReq);

	/**
	 * 单个查询拉卡拉mcc表API
	 *
	 * @author zhanghao
	 * @param ledgerMccByIdRequest 单个查询拉卡拉mcc表请求参数 {@link LedgerMccByIdRequest}
	 * @return 拉卡拉mcc表详情 {@link LedgerMccByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgermcc/get-by-id")
	BaseResponse<LedgerMccByIdResponse> getById(@RequestBody @Valid LedgerMccByIdRequest ledgerMccByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link LedgerMccExportRequest}
	 * @return 拉卡拉mcc表数量 {@link Long}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgermcc/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid LedgerMccExportRequest request);



}

