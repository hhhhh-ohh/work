package com.wanmi.sbc.empower.api.provider.ledgercontent;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentPageRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentPageResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentListRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentListResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentByIdRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentByIdResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentExportRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentExportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>拉卡拉经营内容表查询服务Provider</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@FeignClient(value = "${application.empower.name}", contextId = "LedgerContentQueryProvider")
public interface LedgerContentQueryProvider {

	/**
	 * 分页查询拉卡拉经营内容表API
	 *
	 * @author zhanghao
	 * @param ledgerContentPageReq 分页请求参数和筛选对象 {@link LedgerContentPageRequest}
	 * @return 拉卡拉经营内容表分页列表信息 {@link LedgerContentPageResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgercontent/page")
	BaseResponse<LedgerContentPageResponse> page(@RequestBody @Valid LedgerContentPageRequest ledgerContentPageReq);

	/**
	 * 列表查询拉卡拉经营内容表API
	 *
	 * @author zhanghao
	 * @param ledgerContentListReq 列表请求参数和筛选对象 {@link LedgerContentListRequest}
	 * @return 拉卡拉经营内容表的列表信息 {@link LedgerContentListResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgercontent/list")
	BaseResponse<LedgerContentListResponse> list(@RequestBody @Valid LedgerContentListRequest ledgerContentListReq);

	/**
	 * 单个查询拉卡拉经营内容表API
	 *
	 * @author zhanghao
	 * @param ledgerContentByIdRequest 单个查询拉卡拉经营内容表请求参数 {@link LedgerContentByIdRequest}
	 * @return 拉卡拉经营内容表详情 {@link LedgerContentByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgercontent/get-by-id")
	BaseResponse<LedgerContentByIdResponse> getById(@RequestBody @Valid LedgerContentByIdRequest ledgerContentByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link LedgerContentExportRequest}
	 * @return 拉卡拉经营内容表数量 {@link Long}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgercontent/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid LedgerContentExportRequest request);


}

