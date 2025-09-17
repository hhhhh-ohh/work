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
import com.wanmi.sbc.account.api.response.finance.record.SettlementToExcelResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.account.bean.vo.*;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.enums.storemessage.ProviderMessageNode;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementProvider;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementQueryProvider;
import com.wanmi.sbc.elastic.api.request.settlement.EsSettlementPageRequest;
import com.wanmi.sbc.elastic.api.request.settlement.SettlementQueryRequest;
import com.wanmi.sbc.elastic.api.response.settlement.EsSettlementResponse;
import com.wanmi.sbc.elastic.bean.vo.settlement.EsSettlementVO;
import com.wanmi.sbc.elastic.bean.vo.settlement.LakalaEsSettlementVO;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.order.api.provider.settlement.SettlementDetailQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.settlement.LakalaSettlementDetailPageRequest;
import com.wanmi.sbc.order.api.request.settlement.SettlementDetailListBySettleUuidRequest;
import com.wanmi.sbc.order.api.request.settlement.SettlementDetailPageRequest;
import com.wanmi.sbc.order.api.request.trade.FindByTailOrderNoInRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Tag(name = "SupplierSettlementController", description = "供应商结算单 Api")
@RestController
@Validated
@RequestMapping("/finance/provider/settlement")
@Slf4j
public class ProviderSettlementController {

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
    private EsSettlementQueryProvider esSettlementQueryProvider;

    @Autowired
    private EsSettlementProvider esSettlementProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired private TradeQueryProvider tradeQueryProvider;

    /**
     * 分页查询结算单(供应商)
     *
     * @param request
     * @return
     */
    @Operation(summary = "分页查询结算单")
    @RequestMapping(value = "/oldPage", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<SettlementViewVO>> pageBasePageRequest(@RequestBody SettlementPageRequest request) {
        Long storeId = commonUtil.getStoreId();
        if (storeId != null) {
            request.setStoreId(storeId);
        }
        request.setStoreType(StoreType.PROVIDER);
        return BaseResponse.success(settlementQueryProvider.page(request).getContext().getSettlementViewVOPage());
    }


    /**
     * 分页查询结算单(供应商) 从es中查找
     *
     * @param request
     * @return
     */
    @Operation(summary = "分页查询结算单")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<EsSettlementVO>> pageBasePageRequestFromEs(@RequestBody EsSettlementPageRequest request) {
        Long storeId = commonUtil.getStoreId();
        if (storeId != null) {
            request.setStoreId(storeId);
        }
        request.setStoreType(StoreType.PROVIDER);
        BaseResponse<EsSettlementResponse> response = esSettlementQueryProvider.esSettlementPage(request);
        if(Objects.nonNull(response.getContext())){
            MicroServicePage<EsSettlementVO> esSettlementVOPage = response.getContext().getEsSettlementVOPage();
            List<Long> storeIds =
                    response.getContext().getEsSettlementVOPage().getContent().stream().map(EsSettlementVO::getStoreId).distinct().collect(Collectors.toList());
            List<StoreVO> storeList = storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest(storeIds)).getContext().getStoreVOList();
            // storeId和storeName映射
            Map<Long, String> goodsMap = storeList.stream().collect(Collectors.toMap(StoreVO::getStoreId,
                    StoreVO::getStoreName, (s, a) -> s));
            esSettlementVOPage.getContent().forEach(esSettlementVO -> {
                if (StringUtils.isNotBlank(goodsMap.get(esSettlementVO.getStoreId()))){
                    esSettlementVO.setStoreName(goodsMap.get(esSettlementVO.getStoreId()));
                }
            });

            return BaseResponse.success(esSettlementVOPage);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "分页查询结算单")
    @RequestMapping(value = "/lakala/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<LakalaEsSettlementVO>> lakalaPageFromEs(@RequestBody EsSettlementPageRequest request) {
        Long storeId = commonUtil.getStoreId();
        if (storeId != null) {
            request.setStoreId(storeId);
        }
        request.setStoreType(StoreType.PROVIDER);
        BaseResponse<EsSettlementResponse> response = esSettlementQueryProvider.lakalaEsSettlementPage(request);
        if(Objects.nonNull(response.getContext())){
            return BaseResponse.success(response.getContext().getLakalaEsSettlementVOPage());
        }
        return BaseResponse.SUCCESSFUL();
    }

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
        esSettlementProvider.updateSettlementStatus(SettlementQueryRequest.builder().settleIdLists(settleIdList).status(status.toValue()).build());

        // ============= 处理供应商的消息发送：待结算单结算提醒 START =============
        storeMessageBizService.handleForSettlementSettled(ProviderMessageNode.SETTLEMENT_SETTLED.getCode(), request);
        // ============= 处理供应商的消息发送：待结算单结算提醒 END =============

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

    @Operation(summary = "查询结算明细分页")
    @RequestMapping(value = "/lakala/detail/page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<LakalaSettlementDetailViewVO>> getLakalaSettlementDetailPage(@RequestBody LakalaSettlementDetailPageRequest settlementDetailPageRequest) {
        LakalaSettlementVO settlement = settlementQueryProvider.getLakalaById(
                SettlementGetByIdRequest.builder().settleId(settlementDetailPageRequest.getSettleId()).build()
        ).getContext();
        if (settlement != null) {
            settlementDetailPageRequest.setSettleUuid(settlement.getSettleUuid());
            BaseResponse<MicroServicePage<LakalaSettlementDetailVO>> response = settlementDetailQueryProvider.findLakalaSettlementDetailPage(settlementDetailPageRequest);
            MicroServicePage<LakalaSettlementDetailVO> page = response.getContext();
            if (Objects.nonNull(page)) {
                List<LakalaSettlementDetailVO> settlementDetailVOList = page.getContent();
                List<LakalaSettlementDetailViewVO> settlementDetailViewVOS =
                        LakalaSettlementDetailViewVO.renderSettlementDetailForView(settlementDetailVOList, false);
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
                return BaseResponse.success(new MicroServicePage(settlementDetailViewVOS,settlementDetailPageRequest.getPageable(), page.getTotal()));
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

//        String settleId = jsonObject.get("settleId").toString();
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setBuyAnyThirdChannelOrNot(commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_VOP));
        exportDataRequest.setTypeCd(ReportType.BUSINESS_SETTLEMENT_DETAIL);
        exportDataRequest.setPlatform(Platform.PROVIDER);
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
        exportDataRequest.setPlatform(Platform.PROVIDER);
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
            //越权校验
            commonUtil.checkStoreId(settlement.getStoreId());
            SettlementGetViewRequest request = KsBeanUtil.convert(settlement, SettlementGetViewRequest.class);
            BaseResponse<SettlementGetViewResponse> view = settlementQueryProvider.getView(request);
            return BaseResponse.success(view.getContext());
        } else {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020010);
        }
    }

    @Operation(summary = "查询结算单信息")
    @Parameter(name = "settleId", description = "结算ID", required = true)
    @RequestMapping(value = "/lakala/{settleId}", method = RequestMethod.GET)
    public BaseResponse<LakalaSettlementViewVO> getLakalaSettlementById(@PathVariable("settleId") Long settleId) {
        LakalaSettlementVO settlement = settlementQueryProvider.getLakalaById(
                SettlementGetByIdRequest.builder().settleId(settleId).build()
        ).getContext();
        if (settlement != null) {
            //越权校验
            commonUtil.checkStoreId(settlement.getStoreId());
            LakalaSettlementGetViewRequest request = KsBeanUtil.convert(settlement, LakalaSettlementGetViewRequest.class);
            BaseResponse<LakalaSettlementGetViewResponse> view = settlementQueryProvider.getLakalaView(request);
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
        JSONObject jsonObject = JSONObject.parseObject(decrypted);
        log.info("------------------ exportIncome {}", JSONObject.toJSONString(jsonObject));
        SettlementToExcelRequest request = JSON.parseObject(decrypted, SettlementToExcelRequest.class);
        if (jsonObject.containsKey("settleStatus") && Objects.nonNull(jsonObject.get("settleStatus"))) {
            String settleStatus = jsonObject.get("settleStatus").toString();
            request.setSettleStatus(SettleStatus.fromValue(Integer.parseInt(settleStatus)));
        }
        Long storeId = commonUtil.getStoreId();
        if (!ObjectUtils.isEmpty(storeId)) {
            request.setStoreId(storeId);
        }
        request.setStoreType(StoreType.PROVIDER);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_SETTLEMENT);
        exportDataRequest.setParam(JSONObject.toJSONString(request));
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "财务结算导出")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @GetMapping(value = "/exportTest")
    public BaseResponse getSettlementExportData(@RequestBody SettlementToExcelRequest request) {
        // 财务结算报表导出数据
        SettlementToExcelResponse excelResponse = settlementQueryProvider.getSettlementExportData(request).getContext();
        return BaseResponse.success(excelResponse);
    }
}
