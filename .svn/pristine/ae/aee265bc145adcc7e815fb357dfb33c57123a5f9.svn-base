package com.wanmi.sbc.empower.api.provider.ledgermcc;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccAddRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccAddResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccModifyRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccModifyResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccDelByIdRequest;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>拉卡拉mcc表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@FeignClient(value = "${application.empower.name}", contextId = "LedgerMccProvider")
public interface LedgerMccProvider {

	/**
	 * 新增拉卡拉mcc表API
	 *
	 * @author zhanghao
	 * @param ledgerMccAddRequest 拉卡拉mcc表新增参数结构 {@link LedgerMccAddRequest}
	 * @return 新增的拉卡拉mcc表信息 {@link LedgerMccAddResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgermcc/add")
	BaseResponse<LedgerMccAddResponse> add(@RequestBody @Valid LedgerMccAddRequest ledgerMccAddRequest);

	/**
	 * 修改拉卡拉mcc表API
	 *
	 * @author zhanghao
	 * @param ledgerMccModifyRequest 拉卡拉mcc表修改参数结构 {@link LedgerMccModifyRequest}
	 * @return 修改的拉卡拉mcc表信息 {@link LedgerMccModifyResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgermcc/modify")
	BaseResponse<LedgerMccModifyResponse> modify(@RequestBody @Valid LedgerMccModifyRequest ledgerMccModifyRequest);

	/**
	 * 单个删除拉卡拉mcc表API
	 *
	 * @author zhanghao
	 * @param ledgerMccDelByIdRequest 单个删除参数结构 {@link LedgerMccDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgermcc/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid LedgerMccDelByIdRequest ledgerMccDelByIdRequest);

	/**
	 * 批量删除拉卡拉mcc表API
	 *
	 * @author zhanghao
	 * @param ledgerMccDelByIdListRequest 批量删除参数结构 {@link LedgerMccDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/ledgermcc/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid LedgerMccDelByIdListRequest ledgerMccDelByIdListRequest);

}

