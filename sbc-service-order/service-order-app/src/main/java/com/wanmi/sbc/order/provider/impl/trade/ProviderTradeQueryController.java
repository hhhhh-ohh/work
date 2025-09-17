package com.wanmi.sbc.order.provider.impl.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListBySkuNosRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoBySkuNosResponse;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.trade.*;
import com.wanmi.sbc.order.bean.dto.ProviderTradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;
import com.wanmi.sbc.order.bean.vo.ProviderTradeVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.Buyer;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.ProviderTradeQueryRequest;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 供应商订单处理
 * @Autho qiaokang
 * @Date：2020-03-27 09:17
 */
@Validated
@RestController
public class ProviderTradeQueryController implements ProviderTradeQueryProvider {

    @Autowired
    private ProviderTradeService providerTradeService;

    @Autowired
    private TradeCacheService tradeCacheService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    /**
     * 分页查询供应商订单
     *
     * @param tradePageCriteriaRequest 带参分页参数
     * @return
     */
    @Override
    public BaseResponse<TradePageCriteriaResponse> providerPageCriteria(@RequestBody @Valid ProviderTradePageCriteriaRequest tradePageCriteriaRequest) {
        ProviderTradeQueryRequest tradeQueryRequest = KsBeanUtil.convert(
                tradePageCriteriaRequest.getTradePageDTO(), ProviderTradeQueryRequest.class);
        ProviderTradeQueryDTO dto = tradePageCriteriaRequest.getTradePageDTO();
        if (Objects.nonNull(dto.getQueryOrderType())) {
            switch (dto.getQueryOrderType()) {
                case BUY_CYCLE:
                    tradeQueryRequest.setBuyCycleFlag(Boolean.TRUE);
                    break;
                default:
                    break;
            }
        }
        // 查询订单支付顺序设置
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_PAYMENT_ORDER);
        Integer paymentOrder = tradeCacheService.getTradeConfigByType(ConfigType.ORDER_SETTING_PAYMENT_ORDER).getStatus();
        if(PaymentOrder.PAY_FIRST == PaymentOrder.values()[paymentOrder]) {
            tradeQueryRequest.getTradeState().setPayState(PayState.PAID);
            //包含历史订单中 不限的
            tradeQueryRequest.setNeedNoLimit(Boolean.TRUE);
        }

        //查看渠道待处理就显示待发货和部分发货
        if (Boolean.TRUE.equals(tradeQueryRequest.getThirdPlatformToDo())) {
            tradeQueryRequest.setFlowStates(Arrays.asList(FlowState.AUDIT, FlowState.DELIVERED_PART));
            tradeQueryRequest.setReturnHasFlag(Boolean.FALSE);//没有退单
        }
        // 处理SKU编码
        if (StringUtils.isNotEmpty(tradeQueryRequest.getSkuNo())) {
            GoodsInfoBySkuNosResponse skuNoResponse =
                    goodsInfoQueryProvider
                            .listGoodsInfoBySkuNos(
                                    GoodsInfoListBySkuNosRequest.builder()
                                            .goodsInfoNoList(Arrays.asList(tradeQueryRequest.getSkuNo()))
                                            .delFlag(Boolean.FALSE)
                                            .build())
                            .getContext();
            if(Objects.isNull(skuNoResponse)
                    || CollectionUtils.isEmpty(skuNoResponse.getGoodsInfoVoList())
                    || Objects.isNull(skuNoResponse.getGoodsInfoVoList().get(0))){
                return BaseResponse.success(TradePageCriteriaResponse.builder().tradePage(new MicroServicePage<>(Collections.emptyList(), tradeQueryRequest.getPageable(), 0)).build());
            }
            tradeQueryRequest.setProviderSkuId(skuNoResponse.getGoodsInfoVoList().get(0).getGoodsInfoId());
        }
        Criteria criteria;
        if (tradePageCriteriaRequest.isReturn()) {
            criteria = tradeQueryRequest.getCanReturnCriteria();
        } else {
            criteria = tradeQueryRequest.getWhereCriteria();
        }
        Page<ProviderTrade> page = providerTradeService.providerPage(criteria, tradeQueryRequest);
        MicroServicePage<TradeVO> tradeVOS = KsBeanUtil.convertPage(page, TradeVO.class);
        List<TradeVO> tradeVOList = tradeVOS.getContent();
        List<String> pTidList = tradeVOList.stream().map(TradeVO::getId).collect(Collectors.toList());
        Map<String, List<String>> returnOrderIdMap = returnOrderService.getRidMap(pTidList);
        tradeVOS.forEach(tradeVO -> {
            List<String> ridList = returnOrderIdMap.get(tradeVO.getId());
            if(CollectionUtils.isNotEmpty(ridList)){
                tradeVO.setIsHasPostSales(Boolean.TRUE);
                tradeVO.setReturnOrderIdList(ridList);
            }
            this.setCanReturnNum(tradeVO);
        });
        return BaseResponse.success(TradePageCriteriaResponse.builder().tradePage(tradeVOS).build());
    }

    /**
     * 查询供应商订单
     *
     * @param tradeGetByIdRequest 交易单id {@link TradeGetByIdRequest}
     * @return
     */
    @Override
    public BaseResponse<TradeGetByIdResponse> providerGetById(@RequestBody @Valid TradeGetByIdRequest tradeGetByIdRequest) {
        ProviderTrade trade = providerTradeService.providerDetail(tradeGetByIdRequest.getTid());

        TradeVO tradeVO = KsBeanUtil.convert(trade, TradeVO.class);
        //商品是否全部申请售后
        if (tradeVO != null) {
            List<ReturnOrder> returnOrderList = returnOrderService.findByPtid(tradeVO.getId());
            returnOrderService.goodsIsHasPostSales(tradeVO,returnOrderList);
            //设置商品可退数量
            this.setCanReturnNum(tradeVO);
            tradeVO.setHasReturn(tradeService.checkHasReturn(tradeVO.getId()));
        }
        if (tradeVO != null && CollectionUtils.isNotEmpty(tradeVO.getTradeItems())) {
            List<ProviderTrade> providerTradeList = providerTradeService.findListByParentId(trade.getParentId());

            StringBuilder subOrderNo = new StringBuilder();
            if (CollectionUtils.isNotEmpty(providerTradeList)) {
                for (int i = 0; i < providerTradeList.size(); i++) {
                    if (i == providerTradeList.size() - 1) {
                        subOrderNo.append(providerTradeList.get(i).getId());
                    } else {
                        subOrderNo.append(providerTradeList.get(i).getId());
                        subOrderNo.append(',');
                    }
                }
            }
        }

        BaseResponse<TradeGetByIdResponse> baseResponse = BaseResponse.success(TradeGetByIdResponse.builder().
                tradeVO(tradeVO).build());
        return baseResponse;
    }

    @Override
    public BaseResponse<ProviderTradeGetByIdsResponse> providerGetByIds(@Valid ProviderTradeGetByIdListRequest providerTradeGetByIdListRequest) {
        List<ProviderTrade> providerTradeList = providerTradeService.details(providerTradeGetByIdListRequest.getIdList());
        List<ProviderTradeVO> providerTradeVOList = KsBeanUtil.convert(providerTradeList, ProviderTradeVO.class);
        return BaseResponse.success(new ProviderTradeGetByIdsResponse(providerTradeVOList));
    }

    /**
     * 根据父订单号查询供应商订单
     *
     * @param tradeListByParentIdRequest 父交易单id {@link TradeListByParentIdRequest}
     * @return
     */
    @Override
    public BaseResponse<TradeListByParentIdResponse> getProviderListByParentId(@RequestBody @Valid TradeListByParentIdRequest tradeListByParentIdRequest) {
        List<ProviderTrade> tradeList =
                providerTradeService.findListByParentId(tradeListByParentIdRequest.getParentTid());
        if (tradeList.isEmpty()) {
            return BaseResponse.success(TradeListByParentIdResponse.builder().tradeVOList(Collections.emptyList()).build());
        }
        // 父订单号对应的子订单的买家信息应该是相同的
        ProviderTrade trade = tradeList.get(0);

        final Buyer buyer = trade.getBuyer();
        //统一设置账号加密后的买家信息
        List<TradeVO> tradeVOList = tradeList.stream().map(i -> {
            i.setBuyer(buyer);
            return KsBeanUtil.convert(i, TradeVO.class);
        }).collect(Collectors.toList());
        return BaseResponse.success(TradeListByParentIdResponse.builder().tradeVOList(tradeVOList).build());
    }

    /**
     * 查询导出数据
     *
     * @param tradeListExportRequest 查询条件 {@link TradeListExportRequest}
     * @return
     */
    @Override
    public BaseResponse<TradeListExportResponse> providerListTradeExport(@RequestBody @Valid TradeListExportRequest tradeListExportRequest) {
        ProviderTradeQueryRequest tradeQueryRequest = KsBeanUtil.convert(tradeListExportRequest.getTradeQueryDTO(),
                ProviderTradeQueryRequest.class);
        TradeQueryDTO dto = tradeListExportRequest.getTradeQueryDTO();
        if (Objects.nonNull(dto.getQueryOrderType())) {
            switch (dto.getQueryOrderType()) {
                case GROUPON:
                    tradeQueryRequest.setGrouponFlag(Boolean.TRUE);
                    break;
                case FLASH_SALE:
                    tradeQueryRequest.setFlashSaleFlag(Boolean.TRUE);
                    break;
                case BUY_POINTS_ORDER:
                    tradeQueryRequest.setOrderType(OrderType.NORMAL_ORDER);
                    break;
                case BARGAIN:
                    tradeQueryRequest.setBargainFlag(Boolean.TRUE);
                    break;
                case BUY_CYCLE:
                    tradeQueryRequest.setBuyCycleFlag(Boolean.TRUE);
                    break;
                default:
                    break;
            }
        }
        List<ProviderTrade> tradeList = providerTradeService.listProviderTradeExport(tradeQueryRequest);
        return BaseResponse.success(TradeListExportResponse.builder().tradeVOList(KsBeanUtil.convert(tradeList,
                TradeVO.class)).build());
    }

    @Override
    public BaseResponse<Long> countProviderTradeExport(@Valid TradeListExportRequest tradeListExportRequest) {
        ProviderTradeQueryRequest tradeQueryRequest = KsBeanUtil.convert(tradeListExportRequest.getTradeQueryDTO(),
                ProviderTradeQueryRequest.class);
        Integer paymentOrder = tradeCacheService.getTradeConfigByType(ConfigType.ORDER_SETTING_PAYMENT_ORDER).getStatus();
        if(PaymentOrder.PAY_FIRST == PaymentOrder.values()[paymentOrder]) {
            if(Objects.isNull(tradeQueryRequest.getTradeState())) {
                tradeQueryRequest.setTradeState(TradeState.builder().payState(PayState.PAID).build());
            } else {
                tradeQueryRequest.getTradeState().setPayState(PayState.PAID);
            }
            //包含历史订单中 不限的
            tradeQueryRequest.setNeedNoLimit(Boolean.TRUE);
        }
        tradeQueryRequest.setOrderType(OrderType.ALL_ORDER);
        TradeQueryDTO dto = tradeListExportRequest.getTradeQueryDTO();
        if (Objects.nonNull(dto.getQueryOrderType())) {
            switch (dto.getQueryOrderType()) {
                case GROUPON:
                    tradeQueryRequest.setGrouponFlag(Boolean.TRUE);
                    break;
                case FLASH_SALE:
                    tradeQueryRequest.setFlashSaleFlag(Boolean.TRUE);
                    break;
                case BUY_POINTS_ORDER:
                    tradeQueryRequest.setOrderType(OrderType.NORMAL_ORDER);
                    break;
                case BARGAIN:
                    tradeQueryRequest.setBargainFlag(Boolean.TRUE);
                    break;
                case BUY_CYCLE:
                    tradeQueryRequest.setBuyCycleFlag(Boolean.TRUE);
                    break;
                default:
                    break;
            }
        }
        long total = providerTradeService.countNum(tradeQueryRequest.getWhereCriteria(), tradeQueryRequest);
        return BaseResponse.success(total);
    }

    /**
     * 按条件统计数量
     * @param tradeCountCriteriaRequest 带参分页参数 {@link TradeCountCriteriaRequest}
     * @return
     */
    @Override
    public BaseResponse<ProviderTradeCountCriteriaResponse> countCriteria(@RequestBody @Valid ProviderTradeCountCriteriaRequest tradeCountCriteriaRequest) {
        ProviderTradeQueryRequest providerTradeQueryRequest = KsBeanUtil.convert(
                tradeCountCriteriaRequest.getTradePageDTO(), ProviderTradeQueryRequest.class);
        long count = providerTradeService.countNum(providerTradeQueryRequest.getWhereCriteria(), providerTradeQueryRequest);
        return BaseResponse.success(ProviderTradeCountCriteriaResponse.builder().count(count).build());
    }

    /**
     * 设置商品可退数量
     * @param tradeVO
     */
    private void setCanReturnNum(TradeVO tradeVO) {
        Trade trade = KsBeanUtil.convert(tradeVO, Trade.class);
        //商品可退数量
        if (tradeVO.getTradeState().getDeliverStatus() != DeliverStatus.VOID) {
            //计算商品可退数 商品可退数量=购买数量-退货处理中&退货成功的商品数量
            Map<String, Integer> map = returnOrderService.getLeftItems(trade);
            tradeVO.getTradeItems().forEach(
                    item -> item.setCanReturnNum(map.get(item.getSkuId()))
            );
            //计算赠品可退数 赠品可退数量=购买数量-退货处理中&退货成功的赠品数量
            if (CollectionUtils.isNotEmpty(tradeVO.getGifts())) {
                Map<Long, Map<String, Long>> giftMap = returnOrderService.findLeftGiftItems(trade);
                if(giftMap.keySet().size() > 0){
                    tradeVO.getGifts().forEach(
                            item -> item.setCanReturnNum(giftMap.getOrDefault(item.getMarketingIds().get(0),
                                    new HashMap<>()).get(item.getSkuId()).intValue())
                    );
                }
            }
            if (CollectionUtils.isNotEmpty(tradeVO.getPreferential())) {
                // <活动ID ,<skuid, 可退数量>>
                Map<Long, Map<String, Long>> returnMap = returnOrderService.findLeftPreferentialItems(trade);
                tradeVO.getPreferential().forEach(
                        item -> item.setCanReturnNum(Integer.parseInt(returnMap.getOrDefault(item.getMarketingIds().get(0), new HashMap<>())
                                .get(item.getSkuId()).toString()))
                );
            }
        }
    }

}
