package com.wanmi.sbc.order.api.provider.settlement;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.api.request.settlement.*;
import com.wanmi.sbc.order.api.response.settlement.LakalaLedgerStatusResponse;
import com.wanmi.sbc.order.api.response.settlement.LakalaSettlementDetailByIdResp;
import com.wanmi.sbc.order.api.response.settlement.SettlementDetailByParamResponse;
import com.wanmi.sbc.order.api.response.settlement.SettlementDetailListBySettleUuidResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.bean.vo.LakalaSettlementDetailVO;
import com.wanmi.sbc.order.bean.vo.SettlementDetailVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对账结算明细查询接口</p>
 * Created by of628-wenzhi on 2018-10-13-下午6:23.
 */
@FeignClient(value = "${application.order.name}", contextId = "SettlementDetailQueryProvider")
public interface SettlementDetailQueryProvider {

    /**
     * 根据条件查询单条结算明细
     *
     * @param settlementDetailByParamRequest 查询条件 {@link SettlementDetailByParamRequest}
     * @return 结算明细返回结构 {@link SettlementDetailByParamResponse}
     */
    @PostMapping("/account/${application.order.version}/finance/record/settlement/detail/get-by-param")
    BaseResponse<SettlementDetailByParamResponse> getByParam(@RequestBody @Valid SettlementDetailByParamRequest
                                                                     settlementDetailByParamRequest);

    /**
     * 根据结算单id查询结算明细列表
     *
     * @param settlementDetailListBySettleUuidRequest 包含结算单id的查询条件 {@link SettlementDetailListBySettleUuidRequest}
     * @return 返回的计算明细列表 {@link SettlementDetailListBySettleUuidResponse}
     */
    @PostMapping("/account/${application.order.version}/finance/record/settlement/detail/list-by-settle-uuid")
    BaseResponse<SettlementDetailListBySettleUuidResponse> listBySettleUuid(@RequestBody @Valid
                                                                                    SettlementDetailListBySettleUuidRequest
                                                                                    settlementDetailListBySettleUuidRequest);

    @PostMapping("/account/${application.order.version}/finance/record/settlement/detail/list-by-lakala-settle-uuid")
    BaseResponse<SettlementDetailListBySettleUuidResponse> listByLakalaIds(@RequestBody @Valid SettlementDetailListByIdsRequest settlementDetailListByIdsRequest);

    @PostMapping("/account/${application.order.version}/finance/record/settlement/detail/lakala-settle-uuid")
    BaseResponse<LakalaSettlementDetailByIdResp> LakalaSettlementDetailById(
            @RequestBody @Valid LakalaSettlementDetailByIdReq lakalaSettlementDetailByIdReq);

    @PostMapping("/account/${application.order.version}/finance/record/settlement/detail/find-settlement-detail-page")
    BaseResponse<MicroServicePage<SettlementDetailVO>> findSettlementDetailPage(@RequestBody @Valid SettlementDetailPageRequest request);

    @PostMapping("/account/${application.order.version}/finance/record/settlement/detail/find-lakala-settlement-detail-page")
    BaseResponse<MicroServicePage<LakalaSettlementDetailVO>> findLakalaSettlementDetailPage(@RequestBody @Valid LakalaSettlementDetailPageRequest request);

    @PostMapping("/account/${application.order.version}/finance/record/settlement/detail/get-page-uuid")
    BaseResponse<MicroServicePage<LakalaSettlementDetailVO>> getLakalaSettlementDetailsPageByUuid(@RequestBody @Valid SettlementDetailListBySettleUuidsRequest request);

    @PostMapping("/account/${application.order.version}/finance/record/settlement/detail/get-list-uuid")
    BaseResponse<LakalaLedgerStatusResponse> listByLakalaSettleUuid(
            @RequestBody @Valid SettlementDetailListBySettleUuidsRequest request);
}
