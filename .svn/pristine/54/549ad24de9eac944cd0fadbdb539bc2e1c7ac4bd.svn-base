package com.wanmi.sbc.empower.api.provider.ledgercontent;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentAddRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentAddResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentModifyRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentModifyResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentDelByIdRequest;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>拉卡拉经营内容表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@FeignClient(value = "${application.empower.name}", contextId = "LedgerContentProvider")
public interface LedgerContentProvider {

	/**
	 * 新增拉卡拉经营内容表API
	 *
	 * @author zhanghao
	 * @param ledgerContentAddRequest 拉卡拉经营内容表新增参数结构 {@link LedgerContentAddRequest}
	 * @return 新增的拉卡拉经营内容表信息 {@link LedgerContentAddResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgercontent/add")
	BaseResponse<LedgerContentAddResponse> add(@RequestBody @Valid LedgerContentAddRequest ledgerContentAddRequest);

	/**
	 * 修改拉卡拉经营内容表API
	 *
	 * @author zhanghao
	 * @param ledgerContentModifyRequest 拉卡拉经营内容表修改参数结构 {@link LedgerContentModifyRequest}
	 * @return 修改的拉卡拉经营内容表信息 {@link LedgerContentModifyResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgercontent/modify")
	BaseResponse<LedgerContentModifyResponse> modify(@RequestBody @Valid LedgerContentModifyRequest ledgerContentModifyRequest);

	/**
	 * 单个删除拉卡拉经营内容表API
	 *
	 * @author zhanghao
	 * @param ledgerContentDelByIdRequest 单个删除参数结构 {@link LedgerContentDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgercontent/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid LedgerContentDelByIdRequest ledgerContentDelByIdRequest);

	/**
	 * 批量删除拉卡拉经营内容表API
	 *
	 * @author zhanghao
	 * @param ledgerContentDelByIdListRequest 批量删除参数结构 {@link LedgerContentDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgercontent/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid LedgerContentDelByIdListRequest ledgerContentDelByIdListRequest);

}

