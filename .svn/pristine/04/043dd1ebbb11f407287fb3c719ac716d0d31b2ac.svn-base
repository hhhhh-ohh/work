package com.wanmi.sbc.providertrade;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdsResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeProvider;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.trade.TradeCountByFlowStateResponse;
import com.wanmi.sbc.order.api.response.trade.TradeGetByIdResponse;
import com.wanmi.sbc.order.bean.dto.ProviderTradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeDeliverDTO;
import com.wanmi.sbc.order.bean.dto.TradeDeliverRequestDTO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.request.TradeExportRequest;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.setting.api.provider.expresscompany.ExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyByIdRequest;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 供应商订单控制层
 * @Autho qiaokang
 * @Date：2020-03-27 14:34
 */
@Tag(name = "ProviderTradeController", description = "订单服务 Api")
@RestController
@Validated
@RequestMapping("/providerTrade")
@Slf4j
public class ProviderTradeController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ProviderTradeQueryProvider providerTradeQueryProvider;

    @Autowired
    private ProviderTradeProvider providerTradeProvider;

    @Autowired
    private ExpressCompanyQueryProvider expressCompanyQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    /**
     * 分页查询
     *
     * @param tradeQueryRequest
     * @return
     */
    @Operation(summary = "分页查询")
    @EmployeeCheck
    @RequestMapping(method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_provider_trade_page_sign_word")
    public BaseResponse<MicroServicePage<TradeVO>> page(@RequestBody ProviderTradeQueryDTO tradeQueryRequest) {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        if (companyInfoId != null) {
            tradeQueryRequest.setSupplierId(companyInfoId);
        }

        // 供应商订单列表要过滤待审核和待成团的订单
        tradeQueryRequest.setNotFlowStates(Arrays.asList(FlowState.INIT, FlowState.GROUPON));
        //设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        tradeQueryRequest.makeAllAuditFlow();
        tradeQueryRequest.setOrderType(OrderType.ALL_ORDER);

        return BaseResponse.success(providerTradeQueryProvider.providerPageCriteria(
                ProviderTradePageCriteriaRequest.builder().tradePageDTO(tradeQueryRequest).build()).getContext().getTradePage());
    }

    /**
     * 订单统计
     * @return
     */
    @Operation(summary = "订单todo")
    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public BaseResponse<TradeCountByFlowStateResponse> TardeTodo() {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        TradeCountByFlowStateResponse tradeTodoReponse = new TradeCountByFlowStateResponse();
        ProviderTradeQueryDTO providerTradeQueryDTO  = new ProviderTradeQueryDTO();
        if (companyInfoId != null) {
            providerTradeQueryDTO.setSupplierId(companyInfoId);
        }
        // 供应商订单列表要过滤待审核和待成团的订单
        providerTradeQueryDTO.setNotFlowStates(Arrays.asList(FlowState.INIT, FlowState.GROUPON));
        providerTradeQueryDTO.setOrderType(OrderType.ALL_ORDER);

        TradeStateDTO tradeState = new TradeStateDTO();

        //待审核
        //设置待审核状态
        tradeState.setFlowState(FlowState.INIT);
        providerTradeQueryDTO.setTradeState(tradeState);
        tradeTodoReponse.setWaitAudit(providerTradeQueryProvider.countCriteria(ProviderTradeCountCriteriaRequest.builder()
                .tradePageDTO(providerTradeQueryDTO).build()).getContext().getCount());


        //设置待付款订单
        tradeState.setFlowState(null);
        tradeState.setPayState(PayState.NOT_PAID);
        providerTradeQueryDTO.setTradeState(tradeState);
        tradeTodoReponse.setWaitPay(providerTradeQueryProvider.countCriteria(ProviderTradeCountCriteriaRequest.builder()
                .tradePageDTO(providerTradeQueryDTO).build()).getContext().getCount());
        tradeState.setPayState(null);

        //设置待收货订单
        tradeState.setFlowState(FlowState.DELIVERED);
        providerTradeQueryDTO.setTradeState(tradeState);
        tradeTodoReponse.setWaitReceiving(providerTradeQueryProvider.countCriteria(ProviderTradeCountCriteriaRequest.builder()
                .tradePageDTO(providerTradeQueryDTO).build()).getContext().getCount());

        //设置待发货状态 部分发货和已审核
        tradeState.setFlowState(FlowState.AUDIT);
        providerTradeQueryDTO.setTradeState(tradeState);
        providerTradeQueryDTO.makeAllAuditFlow();
        Long noDeliveredCount = providerTradeQueryProvider.countCriteria(ProviderTradeCountCriteriaRequest.builder()
                .tradePageDTO(providerTradeQueryDTO).build()).getContext().getCount();
        tradeTodoReponse.setWaitDeliver(noDeliveredCount);
        return BaseResponse.success(tradeTodoReponse);
    }

    /**
     * 查看订单详情
     *
     * @param tid
     * @return
     */
    @Operation(summary = "查看订单详情")
    @Parameter(name = "tid", description = "订单id", required = true)
    @RequestMapping(value = "/{tid}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_provider_trade_detail_sign_word")
    public BaseResponse<TradeVO> detail(@PathVariable String tid) {
        TradeVO trade = providerTradeQueryProvider.providerGetById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
        if (trade != null){
            //越权校验
            commonUtil.checkStoreId(trade.getSupplier().getStoreId());
            List<String> providerSkuId =
                    trade.getTradeItems().stream().map(TradeItemVO::getProviderSkuId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(trade.getGifts())) {
                providerSkuId.addAll(trade.getGifts().stream().map(TradeItemVO::getProviderSkuId).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
                providerSkuId.addAll(trade.getPreferential().stream().map(TradeItemVO::getProviderSkuId).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(providerSkuId)){
                GoodsInfoListByIdsResponse goodsInfoListByIdsResponse =
                        goodsInfoQueryProvider.getGoodsInfoByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(providerSkuId).build()).getContext();
                if (goodsInfoListByIdsResponse != null){
                    Map<String, String> skuIdSkuNoMap =
                            goodsInfoListByIdsResponse.getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, GoodsInfoVO::getGoodsInfoNo));
                    trade.getTradeItems().forEach(tradeItemVO -> {
                        String providerSkuNo = skuIdSkuNoMap.get(tradeItemVO.getProviderSkuId());
                        tradeItemVO.setProviderSkuNo(providerSkuNo);
                    });
                    if (CollectionUtils.isNotEmpty(trade.getGifts())) {
                        trade.getGifts().forEach(tradeItemVO -> {
                            String providerSkuNo = skuIdSkuNoMap.get(tradeItemVO.getProviderSkuId());
                            tradeItemVO.setProviderSkuNo(providerSkuNo);
                        });
                    }
                    if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
                        trade.getPreferential().forEach(tradeItemVO -> {
                            String providerSkuNo = skuIdSkuNoMap.get(tradeItemVO.getProviderSkuId());
                            tradeItemVO.setProviderSkuNo(providerSkuNo);
                        });
                    }
                }
            }
            //查询订单数据
            TradeGetByIdResponse tradeGetByIdResponse = tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(trade.getParentId()).build()).getContext();
            if ( Objects.nonNull(tradeGetByIdResponse) && Objects.nonNull(tradeGetByIdResponse.getTradeVO().getSellPlatformType())) {
                trade.setSellPlatformType(tradeGetByIdResponse.getTradeVO().getSellPlatformType());
                trade.setSellPlatformOrderId(tradeGetByIdResponse.getTradeVO().getSellPlatformOrderId());
            }
        }
        return BaseResponse.success(trade);
    }

    /**
     * 发货
     *
     * @param tid
     * @return
     */
    @Operation(summary = "发货")
    @Parameter(name = "tid", description = "订单id", required = true)
    @RequestMapping(value = "/deliver/{tid}", method = RequestMethod.PUT)
    @MultiSubmit
    @GlobalTransactional
    public ResponseEntity<BaseResponse> deliver(@PathVariable String tid, @Valid @RequestBody TradeDeliverRequestDTO
            tradeDeliverRequest) {
        TradeVO trade = providerTradeQueryProvider.providerGetById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
        if (tradeDeliverRequest.getShippingItemList().isEmpty() &&
                        tradeDeliverRequest.getGiftItemList().isEmpty() &&
                        tradeDeliverRequest.getPreferentialItemList().isEmpty()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050086);
        }
        // 物流Id 物流单号校验
        if (StringUtils.isBlank(tradeDeliverRequest.getDeliverNo()) || StringUtils.isBlank(tradeDeliverRequest.getDeliverId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        Operator operator = commonUtil.getOperator();

        TradeDeliveryCheckRequest tradeDeliveryCheckRequest = TradeDeliveryCheckRequest.builder()
                .tid(tid)
                .tradeDeliver(tradeDeliverRequest)
                .operator(operator)
                .build();

        providerTradeProvider.providerDeliveryCheck(tradeDeliveryCheckRequest);

        // 发货校验
        ExpressCompanyByIdRequest request = new ExpressCompanyByIdRequest();
        request.setExpressCompanyId(Long.valueOf(tradeDeliverRequest.getDeliverId()));
        ExpressCompanyVO expressCompanyVO = expressCompanyQueryProvider.getById(request).getContext().getExpressCompanyVO();
        TradeDeliverVO tradeDeliver = tradeDeliverRequest.toTradeDevlier(expressCompanyVO);
        tradeDeliver.setShipperType(ShipperType.PROVIDER);

        TradeDeliverRequest deliverRequest = TradeDeliverRequest.builder()
                .tradeDeliver(KsBeanUtil.convert(tradeDeliver, TradeDeliverDTO.class))
                .tid(tid)
                .operator(operator)
                .build();
        //周期购
        OrderTagVO orderTag = trade.getOrderTag();
        if(Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag()) {
            TradeBuyCycleVO tradeBuyCycle = trade.getTradeBuyCycle();
            List<CycleDeliveryPlanVO> deliveryPlanS = tradeBuyCycle.getDeliveryPlanS();
            long count = deliveryPlanS.parallelStream().filter(cycleDeliveryPlanVO ->
                    Objects.equals(CycleDeliveryState.NOT_SHIP, cycleDeliveryPlanVO.getCycleDeliveryState())).count();
            //如果不是最后一期，必须要传周期购提前几天提醒发货
            if (count > Constants.ONE && Objects.isNull(tradeDeliverRequest.getRemindShipping())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            deliverRequest.setRemindShipping(tradeDeliverRequest.getRemindShipping());
        }
        // 供应商发货处理
        String deliverId = providerTradeProvider.providerDeliver(deliverRequest).getContext().getDeliverId();

        return ResponseEntity.ok(BaseResponse.success(deliverId));
    }

    /**
     * 导出订单
     *
     * @return
     */
    @Operation(summary = "导出订单")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);

        TradeExportRequest tradeExportRequest = JSON.parseObject(decrypted, TradeExportRequest.class);
        tradeExportRequest.setNotFlowStates(Arrays.asList(FlowState.INIT, FlowState.GROUPON));
        //设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        tradeExportRequest.makeAllAuditFlow();
        tradeExportRequest.setOrderType(OrderType.ALL_ORDER);

        Operator operator = commonUtil.getOperator();
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setParam(JSONObject.toJSONString(tradeExportRequest));
        exportDataRequest.setTypeCd(ReportType.BUSINESS_TRADE);
        exportDataRequest.setBuyAnyThirdChannelOrNot(commonUtil.buyAnyThirdChannelOrNot());
        exportDataRequest.setOperator(operator);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 发货作废
     *
     * @param tid
     * @param tdId
     * @return
     */
    @Operation(summary = "发货作废")
    @Parameters({
            @Parameter(name = "tid", description = "订单Id", required = true),
            @Parameter(name = "tdId", description = "发货单Id", required = true)
    })
    @RequestMapping(value = "/deliver/{tid}/void/{tdId}", method = RequestMethod.GET)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> deliverVoid(@PathVariable String tid, @PathVariable String tdId,
                                                    HttpServletRequest req) {

        providerTradeProvider.deliverRecordObsolete(TradeDeliverRecordObsoleteRequest.builder()
                .deliverId(tdId).tid(tid).operator(commonUtil.getOperator()).build());
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }
}
