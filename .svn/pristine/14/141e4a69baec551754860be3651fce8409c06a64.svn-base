package com.wanmi.sbc.finance;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementProvider;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.*;
import com.wanmi.sbc.account.api.response.finance.record.LakalaSettlementGetViewResponse;
import com.wanmi.sbc.account.api.response.finance.record.SettlementGetViewResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.account.bean.vo.*;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.enums.storemessage.SupplierMessageNode;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementQueryProvider;
import com.wanmi.sbc.elastic.api.request.settlement.EsSettlementPageRequest;
import com.wanmi.sbc.elastic.bean.vo.settlement.LakalaEsSettlementVO;
import com.wanmi.sbc.empower.api.provider.pay.Lakala.LakalaShareProfitProvider;
import com.wanmi.sbc.empower.api.request.settlement.SettlementRequest;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.order.api.provider.settlement.SettlementDetailProvider;
import com.wanmi.sbc.order.api.provider.settlement.SettlementDetailQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.settlement.*;
import com.wanmi.sbc.order.api.request.trade.FindByTailOrderNoInRequest;
import com.wanmi.sbc.order.api.response.settlement.LakalaLedgerStatusResponse;
import com.wanmi.sbc.order.api.response.settlement.SettlementDetailListBySettleUuidResponse;
import com.wanmi.sbc.order.api.response.trade.FindByTailOrderNoInResponse;
import com.wanmi.sbc.order.bean.vo.LakalaSettlementDetailVO;
import com.wanmi.sbc.order.bean.vo.LakalaSettlementDetailViewVO;
import com.wanmi.sbc.order.bean.vo.SettlementDetailVO;
import com.wanmi.sbc.order.bean.vo.SettlementDetailViewVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Tag(name = "SettlementController", description = "结算单 Api")
@RestController
@Validated
@RequestMapping("/finance/settlement")
@Slf4j
public class SettlementController {


    @Autowired
    private SettlementProvider settlementProvider;

    @Autowired
    private SettlementQueryProvider settlementQueryProvider;

    @Autowired
    private SettlementDetailQueryProvider settlementDetailQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    @Autowired
    private LakalaShareProfitProvider lakalaShareProfitProvider;

    @Autowired
    private SettlementDetailProvider settlementDetailProvider;

    @Autowired
    private EsSettlementQueryProvider esSettlementQueryProvider;

    @Autowired private TradeQueryProvider tradeQueryProvider;

    /**
     * 分页查询结算单(商家)
     *
     * @param request
     * @return
     */
    @Operation(summary = "分页查询结算单")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<SettlementViewVO>> pageBasePageRequest(@RequestBody SettlementPageRequest request) {
        Long storeId = commonUtil.getStoreId();
        if (!ObjectUtils.isEmpty(storeId)) {
            request.setStoreId(storeId);
        }
        request.setStoreType(StoreType.SUPPLIER);
        return BaseResponse.success(settlementQueryProvider.page(request).getContext().getSettlementViewVOPage());
    }

    @Operation(summary = "分页查询拉卡拉结算单")
    @RequestMapping(value = "/lakala/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<LakalaEsSettlementVO>> lakalaSettlementPage(
            @RequestBody EsSettlementPageRequest request) {
        Long storeId = commonUtil.getStoreId();
        if (!ObjectUtils.isEmpty(storeId)) {
            request.setStoreId(storeId);
        }
        request.setStoreType(StoreType.SUPPLIER);

        if (StringUtils.isEmpty(request.getSortColumn())) {
            request.setSortColumn("createTime");
            request.setSortRole(SortType.DESC.toValue());
        }

        MicroServicePage<LakalaEsSettlementVO> page = esSettlementQueryProvider
                .lakalaEsSettlementPage(request).getContext().getLakalaEsSettlementVOPage();
        List<LakalaEsSettlementVO> esSettlementVOS = page.getContent();
        List<String> uuids =
                esSettlementVOS.stream().map(LakalaEsSettlementVO::getSettleUuid).collect(Collectors.toList());
        LakalaLedgerStatusResponse lakalaLedgerStatusResponse = settlementDetailQueryProvider.listByLakalaSettleUuid(
                SettlementDetailListBySettleUuidsRequest.builder().settleUuids(uuids).build()).getContext();
        Map<String, LakalaLedgerStatus> map = lakalaLedgerStatusResponse.getOperateMap();
        Map<String, LakalaLedgerStatus> statusMapmap = lakalaLedgerStatusResponse.getStatusMap();
        esSettlementVOS.forEach(g -> {
            LakalaLedgerStatus operateStatus = map.get(g.getSettleUuid());
            LakalaLedgerStatus status = statusMapmap.get(g.getSettleUuid());
            if (status != null){
                g.setLakalaLedgerStatus(status);
            }
            if (operateStatus != null){
                g.setOperateStatus(operateStatus);
            } else {
                g.setOperateStatus(g.getLakalaLedgerStatus());
            }
        });
        return BaseResponse.success(page);
    }

    /**
     * 分页查询结算单(商家) 从es查找
     *
     * @param request
     * @return
     */
//    @Operation(summary = "分页查询结算单")
//    @RequestMapping(value = "/page", method = RequestMethod.POST)
//    public BaseResponse<MicroServicePage<EsSettlementVO>> pageBasePageRequestFromEs(@RequestBody EsSettlementPageRequest request) {
//        if (commonUtil.getStoreId() != null) {
//            request.setStoreId(commonUtil.getStoreId());
//        }
//        request.setStoreType(StoreType.SUPPLIER);
//        BaseResponse<EsSettlementResponse> response = esSettlementQueryProvider.esSettlementPage(request);
//        if(Objects.nonNull(response.getContext())){
//            return BaseResponse.success(response.getContext().getEsSettlementVOPage());
//        }
//        return BaseResponse.SUCCESSFUL();
//    }

    /**
     * 更改结算单状态
     *
     * @param request
     * @return
     */
    @Operation(summary = "更改结算单状态")
    @RequestMapping(value = "/status", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse changeSettlementStatus(@RequestBody SettlementBatchModifyStatusRequest request) {
        List<Long> settleIdList = request.getSettleIdList();

        SettleStatus status = request.getStatus();
        settlementProvider.batchModifyStatus(request);
        //操作日志记录
        if (SettleStatus.SETTLE_LATER.equals(status)) {
            if (CollectionUtils.size(settleIdList) == 1) {
                operateLogMQUtil.convertAndSend("财务", "暂不处理",
                        "暂不处理：结算单号" + String.format("S%07d", settleIdList.get(0)));
            } else {
                operateLogMQUtil.convertAndSend("财务", "批量暂不处理", "批量暂不处理");
            }
        } else if (SettleStatus.SETTLED.equals(status)) {
            if (CollectionUtils.size(settleIdList) == 1) {
                operateLogMQUtil.convertAndSend("财务", "设为已结算",
                        "设为已结算：结算单号" + String.format("S%07d", settleIdList.get(0)));
            } else {
                operateLogMQUtil.convertAndSend("财务", "批量设为已结算", "批量设为已结算");
            }
        }
//        esSettlementProvider.updateSettlementStatus(SettlementQueryRequest.builder().settleIdLists(settleIdList).status(status.toValue()).build());

        // ============= 处理商家的消息发送：待结算单结算提醒 START =============
        storeMessageBizService.handleForSettlementSettled(SupplierMessageNode.SETTLEMENT_SETTLED.getCode(), request);
        // ============= 处理商家的消息发送：待结算单结算提醒 END =============

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询结算明细
     *
     * @param settleId
     * @return
     */
    @Operation(summary = "查询结算明细")
    @Parameter(name = "settleId", description = "结算Id", required = true)
    @RequestMapping(value = "/detail/list/{settleId}", method = RequestMethod.GET)
    public BaseResponse<List<SettlementDetailViewVO>> getSettlementDetailList(@PathVariable("settleId") Long settleId) {
        SettlementVO settlement = settlementQueryProvider.getById(
                SettlementGetByIdRequest.builder().settleId(settleId).build()
        ).getContext();
        if (settlement != null) {
            commonUtil.checkStoreId(settlement.getStoreId());
            BaseResponse<SettlementDetailListBySettleUuidResponse> baseResponse =
                    settlementDetailQueryProvider.listBySettleUuid(new SettlementDetailListBySettleUuidRequest(settlement.getSettleUuid()));
            SettlementDetailListBySettleUuidResponse response = baseResponse.getContext();
            if (Objects.nonNull(response)) {
                List<SettlementDetailVO> settlementDetailVOList = response.getSettlementDetailVOList();
                return BaseResponse.success(SettlementDetailViewVO.renderSettlementDetailForView(settlementDetailVOList, false));
            }
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        } else {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        }
    }

    /**
     * 查询结算明细 分页
     *
     * @param
     * @return
     */
    @Operation(summary = "查询结算明细分页")
    @RequestMapping(value = "/detail/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<SettlementDetailViewVO>> getSettlementDetailPage(@RequestBody SettlementDetailPageRequest settlementDetailPageRequest) {
        SettlementVO settlement = settlementQueryProvider.getById(
                SettlementGetByIdRequest.builder().settleId(settlementDetailPageRequest.getSettleId()).build()
        ).getContext();
        if (settlement != null) {
            commonUtil.checkStoreId(settlement.getStoreId());
            settlementDetailPageRequest.setSettleUuid(settlement.getSettleUuid());
            BaseResponse<MicroServicePage<SettlementDetailVO>> response = settlementDetailQueryProvider.findSettlementDetailPage(settlementDetailPageRequest);
            MicroServicePage<SettlementDetailVO> page = response.getContext();
            if (Objects.nonNull(page)) {
                List<SettlementDetailVO> settlementDetailVOList = page.getContent();
                List<SettlementDetailViewVO> settlementDetailViewVOS = SettlementDetailViewVO.renderSettlementDetailForView(settlementDetailVOList, false);
                return BaseResponse.success(new MicroServicePage(settlementDetailViewVOS,settlementDetailPageRequest.getPageable(), page.getTotal()));
            }
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        } else {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        }
    }

    @Operation(summary = "查询拉卡拉结算明细分页")
    @RequestMapping(value = "/lakala/detail/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<LakalaSettlementDetailViewVO>>
            getLakalaSettlementDetailPage(
                    @RequestBody LakalaSettlementDetailPageRequest settlementDetailPageRequest) {
        LakalaSettlementVO settlement =
                settlementQueryProvider
                        .getLakalaById(
                                SettlementGetByIdRequest.builder()
                                        .settleId(settlementDetailPageRequest.getSettleId())
                                        .build())
                        .getContext();
        if (settlement != null) {
            commonUtil.checkStoreId(settlement.getStoreId());
            settlementDetailPageRequest.setSettleUuid(settlement.getSettleUuid());
            BaseResponse<MicroServicePage<LakalaSettlementDetailVO>> response =
                    settlementDetailQueryProvider.findLakalaSettlementDetailPage(
                            settlementDetailPageRequest);
            MicroServicePage<LakalaSettlementDetailVO> page = response.getContext();
            if (Objects.nonNull(page)) {
                List<LakalaSettlementDetailVO> settlementDetailVOList = page.getContent();
                List<LakalaSettlementDetailViewVO> settlementDetailViewVOS =
                        LakalaSettlementDetailViewVO.renderSettlementDetailForView(
                                settlementDetailVOList, false);
                Map<String, LakalaSettlementDetailViewVO> tailTidToLakalaSettlementDetailViewVOMap =
                        settlementDetailViewVOS.stream().filter(g -> g.getTradeCode().startsWith("OT"))
                                .collect(Collectors.toMap(LakalaSettlementDetailViewVO::getTradeCode, Function.identity()));
                FindByTailOrderNoInResponse findByTailOrderNoInResponse =
                        tradeQueryProvider.findByTailOrderNoIn(FindByTailOrderNoInRequest.builder()
                                .tailOrderIds(new ArrayList<>(tailTidToLakalaSettlementDetailViewVOMap.keySet())).build()).getContext();
                findByTailOrderNoInResponse.getTradeVOList().forEach(v -> {
                    tailTidToLakalaSettlementDetailViewVOMap.get(v.getTailOrderNo()).setBookingTid(v.getId());
                });

                Map<String, LakalaSettlementDetailViewVO> lakalaSettlementDetailViewVOMap =
                        settlementDetailViewVOS.stream().collect(Collectors.toMap(LakalaSettlementDetailViewVO::getTradeCode, Function.identity()));
                findByTailOrderNoInResponse.getTradeVOList().forEach(v -> {
                    lakalaSettlementDetailViewVOMap.get(v.getId()).setBookingTid(v.getTailOrderNo());
                });

                return BaseResponse.success(
                        new MicroServicePage(
                                settlementDetailViewVOS,
                                settlementDetailPageRequest.getPageable(),
                                page.getTotal()));
            }
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        } else {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        }
    }

    /**
     * 查询结算明细
     *
     * @param encrypted
     */
    @Operation(summary = "查询结算明细")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/detail/export/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportSettlementDetailList(@PathVariable("encrypted") String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONObject.parseObject(decrypted);
        if (jsonObject.get("settleId") == null) {
            throw new SbcRuntimeException();
        }
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setBuyAnyThirdChannelOrNot(commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_VOP));
        exportDataRequest.setTypeCd(ReportType.BUSINESS_SETTLEMENT_DETAIL);
        exportDataRequest.setPlatform(Platform.SUPPLIER);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "拉卡拉结算单明细导出")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/lakala/detail/export/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportLakalaSettlementDetailList(@PathVariable("encrypted") String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONObject.parseObject(decrypted);
        if (jsonObject.get("settleId") == null) {
            throw new SbcRuntimeException();
        }
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.LAKALA_BUSINESS_SETTLEMENT_DETAIL);
        exportDataRequest.setPlatform(Platform.SUPPLIER);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }




    /**
     * 查询结算单信息
     *
     * @param settleId
     * @return
     */
    @Operation(summary = "查询结算单信息")
    @Parameter(name = "settleId", description = "结算ID", required = true)
    @RequestMapping(value = "/{settleId}", method = RequestMethod.GET)
    public BaseResponse<SettlementViewVO> getSettlementById(@PathVariable("settleId") Long settleId) {
        SettlementVO settlement = settlementQueryProvider.getById(
                SettlementGetByIdRequest.builder().settleId(settleId).build()
        ).getContext();
        if (settlement != null) {
            commonUtil.checkStoreId(settlement.getStoreId());
            SettlementGetViewRequest request = KsBeanUtil.convert(settlement, SettlementGetViewRequest.class);
            BaseResponse<SettlementGetViewResponse> view = settlementQueryProvider.getView(request);
            return BaseResponse.success(view.getContext());
        } else {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        }
    }

    @Operation(summary = "查询拉卡拉结算单信息")
    @Parameter(
            name = "settleId",
            description = "结算ID",
            required = true)
    @RequestMapping(value = "/lakala/{settleId}", method = RequestMethod.GET)
    public BaseResponse<LakalaSettlementViewVO> getLakalaSettlementById(
            @PathVariable("settleId") Long settleId) {
        LakalaSettlementVO settlement =
                settlementQueryProvider
                        .getLakalaById(
                                SettlementGetByIdRequest.builder().settleId(settleId).build())
                        .getContext();
        if (settlement != null) {
            commonUtil.checkStoreId(settlement.getStoreId());
            LakalaLedgerStatusResponse lakalaLedgerStatusResponse = settlementDetailQueryProvider.listByLakalaSettleUuid(
                    SettlementDetailListBySettleUuidsRequest.builder().settleUuids(Collections.singletonList(settlement.getSettleUuid())).build()).getContext();
            Map<String, LakalaLedgerStatus> map = lakalaLedgerStatusResponse.getOperateMap();
            Map<String, LakalaLedgerStatus> statusMap = lakalaLedgerStatusResponse.getStatusMap();
            LakalaSettlementGetViewRequest request =
                    KsBeanUtil.convert(settlement, LakalaSettlementGetViewRequest.class);
            BaseResponse<LakalaSettlementGetViewResponse> view =
                    settlementQueryProvider.getLakalaView(request);
            LakalaLedgerStatus operateStatus = map.get(settlement.getSettleUuid());
            LakalaLedgerStatus status = statusMap.get(settlement.getSettleUuid());
            if (status != null){
                view.getContext().setLakalaLedgerStatus(status);
            }
            if (operateStatus != null){
                view.getContext().setOperateStatus(operateStatus);
            } else {
                view.getContext().setOperateStatus(view.getContext().getLakalaLedgerStatus());
            }
            return BaseResponse.success(view.getContext());
        } else {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        }
    }

    /**
     * 获得店铺总的结算资金、待结算资金
     *
     * @return
     */
    @Operation(summary = "获得店铺总的结算资金、待结算资金")
    @RequestMapping(value = "/queryToTalSettlement", method = RequestMethod.GET)
    public BaseResponse<List<SettlementTotalVO>> queryToTalSettlement() {
        return BaseResponse.success(settlementQueryProvider.countByStoreId(
                SettlementTotalByStoreIdRequest.builder().storeId(commonUtil.getStoreId()).build()
        ).getContext().getSettlementTotalVOList());
    }


    /**
     * 财务结算导出
     *
     * @param encrypted
     * @throws Exception
     */
    @Operation(summary = "财务结算导出")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @GetMapping(value = "/export/{encrypted}")
    public BaseResponse exportIncome(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        SettlementToExcelRequest request = JSON.parseObject(decrypted, SettlementToExcelRequest.class);
        JSONObject jsonObject = JSONObject.parseObject(decrypted);
        String settleStatus = jsonObject.get("settleStatus").toString();
        request.setSettleStatus(SettleStatus.fromValue(Integer.parseInt(settleStatus)));
        Long storeId = commonUtil.getStoreId();
        if (!ObjectUtils.isEmpty(storeId)) {
            request.setStoreId(storeId);
        }
        request.setStoreType(StoreType.SUPPLIER);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_SETTLEMENT);
        exportDataRequest.setParam(JSONObject.toJSONString(request));
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description 拉卡拉结算
     * @author edz
     * @date: 2022/7/20 22:23
     * @param settlementRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "拉卡拉分账")
    @PostMapping(value = "/lakala/seporcancel")
    public BaseResponse seporcancel(@RequestBody SettlementRequest settlementRequest) {
        if (NumberUtils.INTEGER_ZERO.equals(settlementRequest.getLakalaLedgerType())) {
            settlementRequest.setType(0);
            lakalaShareProfitProvider.seporcancel(settlementRequest);
        } else if (NumberUtils.INTEGER_ONE.equals(settlementRequest.getLakalaLedgerType())) {
            if (CollectionUtils.isNotEmpty(settlementRequest.getIds())){
                settlementDetailProvider.batchUpdateLakalaSettlementDetailStatus(
                        BatchUpdateSettlementDetailStatus.builder()
                                .Offline(settlementRequest.getIds())
                                .build());
            } else if(CollectionUtils.isNotEmpty(settlementRequest.getUuids())){
                settlementDetailProvider.offlineByUuid(OfflineByUuidRequest.builder()
                        .uuid(settlementRequest.getUuids().get(0))
                        .build());
            }
        }
        return BaseResponse.SUCCESSFUL();
    }
}
