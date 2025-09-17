package com.wanmi.sbc.order.provider.impl.settlement;

import com.wanmi.sbc.account.api.provider.finance.record.SettlementProvider;
import com.wanmi.sbc.account.api.request.finance.record.LakalaSettlementBatchUpdateStatusRequest;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.settlement.SettlementDetailQueryProvider;
import com.wanmi.sbc.order.api.request.settlement.*;
import com.wanmi.sbc.order.api.response.settlement.*;
import com.wanmi.sbc.order.bean.vo.LakalaSettlementDetailVO;
import com.wanmi.sbc.order.bean.vo.SettlementDetailVO;
import com.wanmi.sbc.order.settlement.model.root.LakalaSettlementDetail;
import com.wanmi.sbc.order.settlement.model.root.SettlementDetail;
import com.wanmi.sbc.order.settlement.service.SettlementDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * 结算明细
 */
@RestController
@Validated
public class SettlementDetailQueryController implements SettlementDetailQueryProvider {

    @Autowired
    private SettlementDetailService settlementDetailService;

    /**
     * 根据条件查询单条结算明细
     *
     * @param settlementDetailByParamRequest 查询条件 {@link SettlementDetailByParamRequest}
     * @return 结算明细返回结构 {@link SettlementDetailByParamResponse}
     */

    @Override
    public BaseResponse<SettlementDetailByParamResponse> getByParam(@RequestBody @Valid SettlementDetailByParamRequest settlementDetailByParamRequest) {
        SettlementDetail settlementDetail = settlementDetailService.getByTradeId(settlementDetailByParamRequest.getTradeId(),
                settlementDetailByParamRequest.getStartDate(),settlementDetailByParamRequest.getEndDate()).orElse( null);
        SettlementDetailVO settlementDetailVO = null;
        if(null != settlementDetail){
            settlementDetailVO  =  KsBeanUtil.convert(settlementDetail,SettlementDetailVO.class);
        }
        return BaseResponse.success(new SettlementDetailByParamResponse(settlementDetailVO));
    }

    /**
     * 根据结算单id查询结算明细列表
     *
     * @param settlementDetailListBySettleUuidRequest 包含结算单id的查询条件 {@link SettlementDetailListBySettleUuidRequest}
     * @return 返回的计算明细列表 {@link SettlementDetailListBySettleUuidResponse}
     */

    @Override
    public BaseResponse<SettlementDetailListBySettleUuidResponse> listBySettleUuid(@RequestBody @Valid SettlementDetailListBySettleUuidRequest settlementDetailListBySettleUuidRequest) {
        List<SettlementDetail> settlementDetailList = settlementDetailService.getSettlementDetail(settlementDetailListBySettleUuidRequest.getSettleUuid());
        List<SettlementDetailVO> settlementDetailVOList = KsBeanUtil.convert(settlementDetailList,SettlementDetailVO.class);
        SettlementDetailListBySettleUuidResponse response = new SettlementDetailListBySettleUuidResponse();
        response.setSettlementDetailVOList(settlementDetailVOList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<SettlementDetailListBySettleUuidResponse> listByLakalaIds(
            @RequestBody @Valid SettlementDetailListByIdsRequest settlementDetailListByIdsRequest) {
        List<LakalaSettlementDetail> settlementDetailList =
                settlementDetailService.getLakalaSettlementDetail(
                        settlementDetailListByIdsRequest.getIds());
        List<LakalaSettlementDetailVO> settlementDetailVOList =
                KsBeanUtil.convert(settlementDetailList, LakalaSettlementDetailVO.class);
        SettlementDetailListBySettleUuidResponse response =
                new SettlementDetailListBySettleUuidResponse();
        response.setLakalaSettlementDetailVOList(settlementDetailVOList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<LakalaSettlementDetailByIdResp> LakalaSettlementDetailById(
            @RequestBody @Valid LakalaSettlementDetailByIdReq lakalaSettlementDetailByIdReq) {
        LakalaSettlementDetail settlementDetail =
                settlementDetailService.getLakalaSettlementDetail(
                        lakalaSettlementDetailByIdReq.getId());
        LakalaSettlementDetailVO settlementDetailVO =
                KsBeanUtil.convert(settlementDetail, LakalaSettlementDetailVO.class);
        return BaseResponse.success(new LakalaSettlementDetailByIdResp(settlementDetailVO));
    }

    /**
     * 根据结算单id查询结算明细列表
     *
     * @param
     * @return
     */

    @Override
    public BaseResponse<MicroServicePage<SettlementDetailVO>> findSettlementDetailPage(@RequestBody @Valid SettlementDetailPageRequest request) {
        Page<SettlementDetail> settlementDetailPage = settlementDetailService.findSettlementDetailPage(request);
        MicroServicePage<SettlementDetailVO> newPage= KsBeanUtil.convertPage(settlementDetailPage, SettlementDetailVO.class);
        return BaseResponse.success(newPage);
    }

    @Override
    public BaseResponse<MicroServicePage<LakalaSettlementDetailVO>> findLakalaSettlementDetailPage(
            @RequestBody @Valid LakalaSettlementDetailPageRequest request) {
        Page<LakalaSettlementDetail> settlementDetailPage =
                settlementDetailService.findLakalaSettlementDetailPage(request);
        MicroServicePage<LakalaSettlementDetailVO> newPage =
                KsBeanUtil.convertPage(settlementDetailPage, LakalaSettlementDetailVO.class);
        return BaseResponse.success(newPage);
    }

    @Override
    public BaseResponse<MicroServicePage<LakalaSettlementDetailVO>>
            getLakalaSettlementDetailsPageByUuid(
                    @RequestBody @Valid SettlementDetailListBySettleUuidsRequest request) {
        Page<LakalaSettlementDetail> settlementDetailPage =
                settlementDetailService.getLakalaSettlementDetailsPageByUuid(request);
        MicroServicePage<LakalaSettlementDetailVO> newPage =
                KsBeanUtil.convertPage(settlementDetailPage, LakalaSettlementDetailVO.class);
        return BaseResponse.success(newPage);
    }

    @Override
    public BaseResponse<LakalaLedgerStatusResponse> listByLakalaSettleUuid(
            @RequestBody @Valid SettlementDetailListBySettleUuidsRequest request) {
        return BaseResponse.success(settlementDetailService.getLakalaSettlementStatus(request.getSettleUuids()));
    }
}
