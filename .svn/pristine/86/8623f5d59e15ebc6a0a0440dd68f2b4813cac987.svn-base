package com.wanmi.sbc.mq.report.service.base;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationQueryProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyPageRequest;
import com.wanmi.sbc.elastic.bean.vo.storeInformation.EsCompanyInfoVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeProvider;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrdersRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeNoByBusinessIdRequest;
import com.wanmi.sbc.order.api.request.trade.FindProviderTradeRequest;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeListByTradeIdsRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListExportRequest;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrdersResponse;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeNoMapResponse;
import com.wanmi.sbc.order.bean.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className TradeExportService
 * @description 订单导出
 * @date 2021/6/1 10:43 上午
 **/
@Service
@Slf4j
public class TradeBaseService {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private ProviderTradeProvider providerTradeProvider;

    @Autowired
    private ThirdPlatformTradeQueryProvider thirdPlatformTradeQueryProvider;

    @Autowired
    private ProviderTradeQueryProvider providerTradeQueryProvider;
   @Resource
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired
    private PayOrderQueryProvider payOrderQueryProvider;

    @Autowired
    private EsStoreInformationQueryProvider esStoreInformationQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    /**
     * @description 供应商订单
     * @author  xuyunpneng
     * @date 2021/6/1 7:05 下午
     * @return
     */
    @ReturnSensitiveWords(functionName = "f_export_provider_trade_sign_word")
    public List<ProviderTradeExportVO> getTradeForProvider(Operator operator, TradeListExportRequest exportRequest) {
        List<TradeVO> trades = providerTradeQueryProvider.providerListTradeExport(exportRequest).getContext().getTradeVOList();

        //按下单时间降序排列
        Comparator<TradeVO> c = Comparator.comparing(a -> a.getTradeState().getCreateTime());
        trades = trades.stream().sorted(
                c.reversed()
        ).collect(Collectors.toList());

        // 获取供应商上商品ID
        List<String> providerGoodsInfoIdList = new ArrayList<>();
        trades.forEach(tradeVO -> {
            if (CollectionUtils.isNotEmpty(tradeVO.getTradeItems())) {
                providerGoodsInfoIdList.addAll(tradeVO.getTradeItems().stream().map(TradeItemVO::getProviderSkuId).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(tradeVO.getGifts())) {
                providerGoodsInfoIdList.addAll(tradeVO.getGifts().stream().map(TradeItemVO::getProviderSkuId).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(tradeVO.getPreferential())) {
                providerGoodsInfoIdList.addAll(tradeVO.getPreferential().stream().map(TradeItemVO::getProviderSkuId).collect(Collectors.toList()));
            }
        });
        Map<String, GoodsInfoVO> providerGoodsInfoMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(providerGoodsInfoIdList)) {
            List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.getGoodsInfoByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(providerGoodsInfoIdList).build()).getContext().getGoodsInfos();
            if (CollectionUtils.isNotEmpty(goodsInfos)) {
                providerGoodsInfoMap  = goodsInfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
            }
        }


        // 遍历封装导出信息
        List<ProviderTradeExportVO> tradeExportVOs = new ArrayList<>();
        Map<String, GoodsInfoVO> finalProviderGoodsInfoMap = providerGoodsInfoMap;
        trades.forEach(tradeVO -> {
            ProviderTradeExportVO exportVO;
            // 商家信息
            String supplierName = StringUtils.isNotEmpty(tradeVO.getSupplierName()) ? tradeVO.getSupplierName() : "";
            String supplierCode = StringUtils.isNotEmpty(tradeVO.getSupplierCode()) ? tradeVO.getSupplierCode() : "";
            String supplierInfo = supplierName + "  " + supplierCode;
            if (CollectionUtils.isNotEmpty(tradeVO.getTradeItems())) {
                for (int i = 0; i < tradeVO.getTradeItems().size(); i++) {
                    TradeItemVO tradeItemVO = tradeVO.getTradeItems().get(i);
                    exportVO = getProviderTradeExportVO(tradeVO, supplierInfo, tradeItemVO,tradeVO.getTradeItems(), finalProviderGoodsInfoMap);
                    tradeExportVOs.add(exportVO);
                }
            }

            if(CollectionUtils.isNotEmpty(tradeVO.getGifts())){
                for (int i = 0; i < tradeVO.getGifts().size(); i++) {
                    TradeItemVO tradeItemVO = tradeVO.getGifts().get(i);
                    exportVO = getProviderTradeExportVO(tradeVO, supplierInfo, tradeItemVO,tradeVO.getGifts(), finalProviderGoodsInfoMap);
                    tradeExportVOs.add(exportVO);
                }
            }

            if(CollectionUtils.isNotEmpty(tradeVO.getPreferential())){
                for (int i = 0; i < tradeVO.getPreferential().size(); i++) {
                    TradeItemVO tradeItemVO = tradeVO.getPreferential().get(i);
                    exportVO = getProviderTradeExportVO(tradeVO, supplierInfo, tradeItemVO,tradeVO.getPreferential(), finalProviderGoodsInfoMap);
                    tradeExportVOs.add(exportVO);
                }
            }
        });
        return tradeExportVOs;
    }

    private ProviderTradeExportVO getProviderTradeExportVO(TradeVO tradeVO, String supplierInfo, TradeItemVO tradeItemVO,List<TradeItemVO> tradeItemVOs, Map<String, GoodsInfoVO> providerGoodsInfoMap) {
        ProviderTradeExportVO exportVO;
        exportVO = new ProviderTradeExportVO();

        KsBeanUtil.copyProperties(tradeVO, exportVO);
        // 下单时间
        exportVO.setCreateTime(tradeVO.getTradeState().getCreateTime());
        //完成时间
        exportVO.setEndTime(tradeVO.getTradeState().getEndTime());
        // 商家信息
        exportVO.setSupplierInfo(supplierInfo);
        // 订单商品金额
        exportVO.setOrderGoodsPrice(tradeItemVO.getTotalSupplyPrice()==null?BigDecimal.ZERO:tradeItemVO.getTotalSupplyPrice());
        // 订单状态
        exportVO.setFlowState(tradeVO.getTradeState().getFlowState());
        // 发货状态
        exportVO.setDeliverStatus(tradeVO.getTradeState().getDeliverStatus());
        exportVO.setConsigneeName(tradeVO.getConsignee().getName());
        exportVO.setDetailAddress(tradeVO.getConsignee().getDetailAddress());
        exportVO.setConsigneePhone(tradeVO.getConsignee().getPhone());
        exportVO.setSkuName(StringUtils.isBlank(tradeItemVO.getSkuName())?null:tradeItemVO.getSkuName());
        exportVO.setSpecDetails(StringUtils.isBlank(tradeItemVO.getSpecDetails())?null:tradeItemVO.getSpecDetails());
        String skuNo = "";
        if (providerGoodsInfoMap.containsKey(tradeItemVO.getProviderSkuId())) {
            skuNo = providerGoodsInfoMap.get(tradeItemVO.getProviderSkuId()).getGoodsInfoNo();
        }
        exportVO.setSkuNo(skuNo);
        exportVO.setNum(tradeItemVO.getNum());
        exportVO.setSupplyPrice(tradeItemVO.getSupplyPrice());
        exportVO.setProviderName(tradeItemVO.getProviderName());
        exportVO.setPoints(tradeItemVO.getPoints());
        exportVO.setPointsPrice(tradeItemVO.getPointsPrice());
        exportVO.setTotalNum(tradeItemVOs.stream().collect(Collectors.summingLong(TradeItemVO::getNum)));
        exportVO.setGoodsSpecies(Long.valueOf(tradeItemVOs.stream().map(TradeItemVO::getCateId).collect(Collectors.toSet()).size()));

        if (CollectionUtils.isNotEmpty(tradeItemVO.getGiftCardItemList())) {
            BigDecimal giftCardPrice = tradeItemVO.getGiftCardItemList().stream().map(vo->Objects.isNull(vo.getPrice())?BigDecimal.ZERO:vo.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
            exportVO.setGiftCardPrice(giftCardPrice);
        }

        if (tradeVO.getOrderTag() != null && tradeVO.getOrderTag().getPickupCardFlag() != null &&  tradeVO.getOrderTag().getPickupCardFlag()){
            exportVO.setGiftCardPrice(tradeVO.getTradePrice().getGiftCardPrice());
        }
        return exportVO;
    }

    /**
     * @description 子订单数据
     * @author  xuyunpeng
     * @date 2021/6/1 2:36 下午
     * @param data
     * @param exportRequest
     * @return
     */
    @ReturnSensitiveWords(functionName = "f_get_provide_trade_sign_word")
    public List<ProviderTradeExportVO> getProviderTrade(Operator operator, ExportData data, TradeListExportRequest exportRequest){
        List<TradeVO> trades = tradeQueryProvider.listTradeExport(exportRequest).getContext().getTradeVOList();

        //按下单时间降序排列
        Comparator<TradeVO> c = Comparator.comparing(a -> a.getTradeState().getCreateTime());
        trades = trades.stream().sorted(
                c.reversed()
        ).collect(Collectors.toList());
        //只导出子订单
        List<String> parentIdList=new ArrayList<>();
        trades.forEach(vo->{
            parentIdList.add(vo.getId());
        });
        List<TradeVO> tradeVOList=providerTradeProvider.findByParentIdList(FindProviderTradeRequest.builder().parentId(parentIdList).build()).getContext().getTradeVOList();

        Map<String, String> thirdPlatformOrderIdMap = new HashMap<>();
        Map<String, String> outOrderIdMap = new HashMap<>();
        if (data.getBuyAnyThirdChannelOrNot()) {
            // 对接了第三方渠道，获取第三方渠道订单信息
            List<ThirdPlatformTradeVO> tradeList =
                    thirdPlatformTradeQueryProvider.listByTradeIds(ThirdPlatformTradeListByTradeIdsRequest.builder().tradeIds(parentIdList).build()).getContext().getTradeList();

            // 获取商品对应的第三方渠道订单编号和外部订单号
            tradeList.forEach(thirdPlatformTradeVO -> {
                thirdPlatformTradeVO.getTradeItems().forEach(tradeItemVO -> {
                    thirdPlatformOrderIdMap.put(tradeItemVO.getOid(),
                            CollectionUtils.isNotEmpty(thirdPlatformTradeVO.getThirdPlatformOrderIds()) ?
                                    thirdPlatformTradeVO.getThirdPlatformOrderIds().get(0) : null);
                    outOrderIdMap.put(tradeItemVO.getOid(),
                            CollectionUtils.isNotEmpty(thirdPlatformTradeVO.getOutOrderIds()) ?
                                    thirdPlatformTradeVO.getOutOrderIds().get(0) : null);
                    //第三方订单已拆单时，取拆单详情中的子单
                    if(CollectionUtils.isNotEmpty(thirdPlatformTradeVO.getSuborderList())){
                        Optional<TradePlatformSuborderVO> suborderVOOptional = thirdPlatformTradeVO.getSuborderList().stream()
                                .filter(tradePlatformSuborderVO -> tradePlatformSuborderVO.getItemList().stream()
                                        .anyMatch(tradePlatformSuborderItemVO -> Objects.equals(tradePlatformSuborderItemVO.getSkuId()
                                                , tradeItemVO.getThirdPlatformSkuId()))
                                ).findFirst();
                        suborderVOOptional.ifPresent(tradePlatformSuborderVO -> outOrderIdMap.put(
                                tradeItemVO.getOid(), tradePlatformSuborderVO.getSuborderId())
                        );
                    }
                });

                //加价购商品处理
                if(CollectionUtils.isNotEmpty(thirdPlatformTradeVO.getPreferential())){
                    thirdPlatformTradeVO.getPreferential().forEach(tradeItemVO -> {
                        thirdPlatformOrderIdMap.put(tradeItemVO.getOid(),
                                CollectionUtils.isNotEmpty(thirdPlatformTradeVO.getThirdPlatformOrderIds()) ?
                                        thirdPlatformTradeVO.getThirdPlatformOrderIds().get(0) : null);
                        outOrderIdMap.put(tradeItemVO.getOid(),
                                CollectionUtils.isNotEmpty(thirdPlatformTradeVO.getOutOrderIds()) ?
                                        thirdPlatformTradeVO.getOutOrderIds().get(0) : null);
                        //第三方订单已拆单时，取拆单详情中的子单
                        if(CollectionUtils.isNotEmpty(thirdPlatformTradeVO.getSuborderList())){
                            Optional<TradePlatformSuborderVO> suborderVOOptional = thirdPlatformTradeVO.getSuborderList().stream()
                                    .filter(tradePlatformSuborderVO -> tradePlatformSuborderVO.getItemList().stream()
                                            .anyMatch(tradePlatformSuborderItemVO -> Objects.equals(tradePlatformSuborderItemVO.getSkuId()
                                                    , tradeItemVO.getThirdPlatformSkuId()))
                                    ).findFirst();
                            suborderVOOptional.ifPresent(tradePlatformSuborderVO -> outOrderIdMap.put(
                                    tradeItemVO.getOid(), tradePlatformSuborderVO.getSuborderId())
                            );
                        }
                    });
                }

            });
        }

        // 遍历封装导出信息
        List<ProviderTradeExportVO> tradeExportVOs = new ArrayList<>();
        log.error("********************tradeVOList", JSONObject.toJSONString(tradeVOList));
        tradeVOList.forEach(tradeVO -> {
            ProviderTradeExportVO exportVO;
            // 商家信息
            String supplierInfo = "";
            String supplierName = "";
            String supplierCode = "";
            if(Objects.nonNull(tradeVO.getSupplier())){
                supplierName = StringUtils.isNotEmpty(tradeVO.getSupplier().getSupplierName()) ? tradeVO.getSupplier().getSupplierName() : "";
                supplierCode = StringUtils.isNotEmpty(tradeVO.getSupplier().getSupplierCode()) ? tradeVO.getSupplier().getSupplierCode() : "";
                supplierInfo = supplierName.concat(supplierCode);
            }
            for (int i = 0; i < tradeVO.getTradeItems().size(); i++) {
                TradeItemVO tradeItemVO = tradeVO.getTradeItems().get(i);
                exportVO = new ProviderTradeExportVO();
                KsBeanUtil.copyProperties(tradeVO, exportVO);
                // 下单时间
                exportVO.setCreateTime(tradeVO.getTradeState().getCreateTime());
                //完成时间
                exportVO.setEndTime(tradeVO.getTradeState().getEndTime());
                // 商家信息
                exportVO.setSupplierInfo(supplierInfo);
                // 供应商名称
                exportVO.setSupplierName(supplierName);
                // 订单商品金额
                exportVO.setOrderGoodsPrice(tradeVO.getTradePrice().getTotalPrice());
                // 订单状态
                exportVO.setFlowState(tradeVO.getTradeState().getFlowState());
                // 发货状态
                exportVO.setDeliverStatus(tradeVO.getTradeState().getDeliverStatus());
                exportVO.setConsigneeName(tradeVO.getConsignee().getName());
                exportVO.setDetailAddress(tradeVO.getConsignee().getDetailAddress());
                exportVO.setConsigneePhone(tradeVO.getConsignee().getPhone());
                exportVO.setTotalNum(tradeVO.getTradeItems().stream().collect(Collectors.summingLong(TradeItemVO::getNum)));
                exportVO.setGoodsSpecies(Long.valueOf(tradeVO.getTradeItems().stream().map(TradeItemVO::getCateId).collect(Collectors.toSet()).size()));
                exportVO.setSkuName(tradeItemVO.getSkuName());
                exportVO.setPayState(tradeVO.getTradeState().getPayState());
                exportVO.setSpecDetails(tradeItemVO.getSpecDetails());
                exportVO.setSkuNo(tradeItemVO.getSkuNo());
                exportVO.setNum(tradeItemVO.getNum());
                exportVO.setSupplyPrice(tradeItemVO.getSupplyPrice());
                exportVO.setProviderName(tradeItemVO.getProviderName());
                exportVO.setPoints(tradeItemVO.getPoints());
                exportVO.setPointsPrice(tradeItemVO.getPointsPrice());
                if (data.getBuyAnyThirdChannelOrNot()) {
                    exportVO.setThirdPlatformOrderId(thirdPlatformOrderIdMap.get(tradeItemVO.getOid()));
                    exportVO.setOutOrderId(outOrderIdMap.get(tradeItemVO.getOid()));
                }
                if (CollectionUtils.isNotEmpty(tradeItemVO.getGiftCardItemList())) {
                    BigDecimal giftCardPrice = tradeItemVO.getGiftCardItemList().stream().map(item->Objects.isNull(item.getPrice())? BigDecimal.ZERO:item.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    exportVO.setGiftCardPrice(giftCardPrice);
                }

                tradeExportVOs.add(exportVO);
            }
            //添加导出赠品逻辑
            List<TradeItemVO> gifts = tradeVO.getGifts();
            if(CollectionUtils.isNotEmpty(gifts)){
                for (int i = 0; i < gifts.size(); i++) {
                    TradeItemVO tradeItemVO = gifts.get(i);
                    ProviderTradeExportVO giftVO = new ProviderTradeExportVO();
                    KsBeanUtil.copyProperties(tradeVO, giftVO);
                    // 下单时间
                    giftVO.setCreateTime(tradeVO.getTradeState().getCreateTime());
                    //完成时间
                    giftVO.setEndTime(tradeVO.getTradeState().getEndTime());
                    // 商家信息
                    giftVO.setSupplierInfo(supplierInfo);
                    // 供应商名称
                    giftVO.setSupplierName(supplierName);
                    // 订单商品金额
                    giftVO.setOrderGoodsPrice(tradeVO.getTradePrice().getTotalPrice());
                    // 订单状态
                    giftVO.setFlowState(tradeVO.getTradeState().getFlowState());
                    // 发货状态
                    giftVO.setDeliverStatus(tradeVO.getTradeState().getDeliverStatus());
                    giftVO.setConsigneeName(tradeVO.getConsignee().getName());
                    giftVO.setDetailAddress(tradeVO.getConsignee().getDetailAddress());
                    giftVO.setConsigneePhone(tradeVO.getConsignee().getPhone());
                    giftVO.setTotalNum(gifts.stream().mapToLong(TradeItemVO::getNum).sum());
                    giftVO.setGoodsSpecies((long) gifts.stream().map(TradeItemVO::getCateId).collect(Collectors.toSet()).size());
                    giftVO.setSkuName(tradeItemVO.getSkuName());
                    giftVO.setPayState(tradeVO.getTradeState().getPayState());
                    giftVO.setSpecDetails(tradeItemVO.getSpecDetails());
                    giftVO.setSkuNo(tradeItemVO.getSkuNo());
                    giftVO.setNum(tradeItemVO.getNum());

                    if (data.getBuyAnyThirdChannelOrNot()) {
                        giftVO.setThirdPlatformOrderId(thirdPlatformOrderIdMap.get(tradeItemVO.getOid()));
                        giftVO.setOutOrderId(outOrderIdMap.get(tradeItemVO.getOid()));
//                        if(StringUtils.isBlank(giftVO.getOutOrderId()) && StringUtils.isNotBlank(giftVO.getThirdPlatformOrderId())){
//                            giftVO.setOutOrderId(giftVO.getThirdPlatformOrderId());
//                        }
                    }

                    tradeExportVOs.add(giftVO);
                }
            }

            //添加导出加价购逻辑
            List<TradeItemVO> preferentials = tradeVO.getPreferential();
            if(CollectionUtils.isNotEmpty(preferentials)){
                for (TradeItemVO tradeItemVO : preferentials) {
                    ProviderTradeExportVO export = new ProviderTradeExportVO();
                    KsBeanUtil.copyProperties(tradeVO, export);
                    // 下单时间
                    export.setCreateTime(tradeVO.getTradeState().getCreateTime());
                    //完成时间
                    export.setEndTime(tradeVO.getTradeState().getEndTime());
                    // 商家信息
                    export.setSupplierInfo(supplierInfo);
                    // 供应商名称
                    export.setSupplierName(supplierName);
                    // 订单商品金额
                    export.setOrderGoodsPrice(tradeVO.getTradePrice().getTotalPrice());
                    // 订单状态
                    export.setFlowState(tradeVO.getTradeState().getFlowState());
                    // 发货状态
                    export.setDeliverStatus(tradeVO.getTradeState().getDeliverStatus());
                    export.setConsigneeName(tradeVO.getConsignee().getName());
                    export.setDetailAddress(tradeVO.getConsignee().getDetailAddress());
                    export.setConsigneePhone(tradeVO.getConsignee().getPhone());
                    export.setTotalNum(tradeVO.getPreferential().stream().collect(Collectors.summingLong(TradeItemVO::getNum)));
                    export.setGoodsSpecies(Long.valueOf(tradeVO.getPreferential().stream().map(TradeItemVO::getCateId).collect(Collectors.toSet()).size()));
                    export.setSkuName(tradeItemVO.getSkuName());
                    export.setPayState(tradeVO.getTradeState().getPayState());
                    export.setSpecDetails(tradeItemVO.getSpecDetails());
                    export.setSkuNo(tradeItemVO.getSkuNo());
                    export.setNum(tradeItemVO.getNum());
                    export.setSupplyPrice(tradeItemVO.getSupplyPrice());
                    export.setProviderName(tradeItemVO.getProviderName());
                    export.setPoints(tradeItemVO.getPoints());
                    export.setPointsPrice(tradeItemVO.getPointsPrice());
                    if (data.getBuyAnyThirdChannelOrNot()) {
                        export.setThirdPlatformOrderId(thirdPlatformOrderIdMap.get(tradeItemVO.getOid()));
                        export.setOutOrderId(outOrderIdMap.get(tradeItemVO.getOid()));
                    }
                    if (CollectionUtils.isNotEmpty(tradeItemVO.getGiftCardItemList())) {
                        BigDecimal giftCardPrice = tradeItemVO.getGiftCardItemList().stream().map(item->Objects.isNull(item.getPrice())?BigDecimal.ZERO:item.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                        export.setGiftCardPrice(giftCardPrice);
                    }

                    tradeExportVOs.add(export);
                }
            }
        });
        return tradeExportVOs;
    }


    /**
     * @description 主订单组装数据
     * @author  xuyunpeng
     * @date 2021/6/1 2:37 下午
     * @param exportRequest
     * @return
     */
    @ReturnSensitiveWords(functionName = "f_get_trade_sign_word")
    public List<TradeVO> getTrade(Operator operator, TradeListExportRequest exportRequest){
        List<TradeVO> tradesnNew = new ArrayList<>();
        List<TradeVO> trades = tradeQueryProvider.listTradeExport(exportRequest).getContext().getTradeVOList();

        if (CollectionUtils.isEmpty(trades)) {
            return tradesnNew;
        }

        //根据provideId查询provideName
        List<Long> providerIdList = new ArrayList<>();

        trades.forEach(tradeVO -> {
            if (CollectionUtils.isNotEmpty(tradeVO.getTradeItems())) {
                providerIdList.addAll(tradeVO.getTradeItems().stream()
                        .filter(tradeItemVO -> Objects.nonNull(tradeItemVO.getProviderId()))
                        .map(TradeItemVO::getProviderId)
                        .collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(tradeVO.getGifts())) {
                providerIdList.addAll(tradeVO.getGifts().stream()
                        .filter(tradeItemVO -> Objects.nonNull(tradeItemVO.getProviderId()))
                        .map(TradeItemVO::getProviderId)
                        .collect(Collectors.toList()));
            }

            if (CollectionUtils.isNotEmpty(tradeVO.getPreferential())) {
                providerIdList.addAll(tradeVO.getPreferential().stream()
                        .filter(tradeItemVO -> Objects.nonNull(tradeItemVO.getProviderId()))
                        .map(TradeItemVO::getProviderId)
                        .collect(Collectors.toList()));
            }
        });

        Map<Long, String> providerMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(providerIdList)) {
            EsCompanyPageRequest esCompanyPageRequest = new EsCompanyPageRequest();
            esCompanyPageRequest.setStoreIds(providerIdList);
            esCompanyPageRequest.setStoreType(StoreType.PROVIDER);
            esCompanyPageRequest.setPageSize(providerIdList.size());
            List<EsCompanyInfoVO> providerList = esStoreInformationQueryProvider.companyInfoPage(esCompanyPageRequest).getContext().getEsCompanyAccountPage().getContent();
            if (CollectionUtils.isNotEmpty(providerList)) {
                providerList.forEach(t -> providerMap.put(t.getStoreId(),t.getSupplierName()));

            }
        }

        // 按下单时间降序排列
        Comparator<TradeVO> c = (o1, o2) -> {
            TradeStateVO tradeState1 = o1.getTradeState();
            TradeStateVO tradeState2 = o2.getTradeState();

            if(tradeState1 != null && tradeState2 != null) {
                if(tradeState1.getCreateTime() != null && tradeState2.getCreateTime() != null) {
                    return tradeState1.getCreateTime().compareTo(tradeState2.getCreateTime());
                } else {
                    return tradeState1.getCreateTime() != null ? 1 : -1;
                }
            } else {
                return tradeState1 != null ? 1 : -1;
            }
        };
        trades = trades.stream().sorted(
                c.reversed()
        ).collect(Collectors.toList());

        trades.forEach(tradeVO -> {

            BookingType bookingType = tradeVO.getBookingType();
            if (bookingType == BookingType.EARNEST_MONEY) {
                String tId = tradeVO.getId();
                FindPayOrdersRequest build = FindPayOrdersRequest.builder().orderNo(tId).build();
                FindPayOrdersResponse response = payOrderQueryProvider.findPayOrders(build).getContext();
                List<PayOrderResponseVO> payOrderResponses = response.getPayOrderResponses();
                if (CollectionUtils.isNotEmpty(payOrderResponses)){
                    List<TradeItemVO> tradeItems = tradeVO.getTradeItems();
                    AtomicInteger m = new AtomicInteger();
                    payOrderResponses.forEach(payOrderResponseVO -> {
                        TradeVO tradeVONew = KsBeanUtil.convert(tradeVO, TradeVO.class);
                        tradeVONew.setTradeItems(KsBeanUtil.convertList(tradeItems,TradeItemVO.class));

                        //设置交易流水号
                        tradeVONew.setTradeNo(payOrderResponseVO.getTradeNo());
                        //设置支付方式
                        String payChannelValue = payOrderResponseVO.getPayChannelValue();
                        tradeVONew.setPayWayValue(payChannelValue);

                        //第一条数据记录子单个数，合并单元格使用
                        if (m.getAndIncrement() == 0){
                            tradeVONew.setRowNum(payOrderResponses.size());
                        }

                        tradeVONew.getTradeItems().forEach(item->{
                            // 验证财务单是否有积分
                            if  (Objects.isNull(payOrderResponseVO.getPayOrderPoints())
                                    || payOrderResponseVO.getPayOrderPoints().compareTo(Long.valueOf(0L))<=0 ) {
                                item.setPointsPrice(BigDecimal.ZERO);
                                item.setPoints(Long.valueOf(0L));
                            }
                            //验证财务单是否有礼品卡
                            if (Objects.isNull(payOrderResponseVO.getGiftCardPrice())
                                    || BigDecimal.ZERO.compareTo(payOrderResponseVO.getGiftCardPrice()) >= 0) {
                                item.setGiftCardItemList(new ArrayList<>());
                            }
                        });

                        tradesnNew.add(tradeVONew);
                    });
                }
            }else {

                // 订单是否是虚拟订单
                Boolean isVirtualOrder = null != tradeVO.getOrderTag() && (tradeVO.getOrderTag().getVirtualFlag() || tradeVO.getOrderTag().getElectronicCouponFlag());
                // 针对虚拟订单、卡券订单的导出，导出字段新增商品类型字段，配送方式、配送费用、收货人、收货人手机、收货地址为空
                if (isVirtualOrder){
                    tradeVO.setDeliverWay(null);
                //    ConsigneeVO consigneeVO = tradeVO.getConsignee();
                //    consigneeVO.setName("");
                //    consigneeVO.setPhone("");
                //    consigneeVO.setDetailAddress("");
                //    tradeVO.setConsignee(consigneeVO);
                }
                List<TradeItemVO> tradeItems = tradeVO.getTradeItems();
                List<TradeItemVO> giftList = tradeVO.getGifts();
                if(CollectionUtils.isNotEmpty(giftList)){
                    // 这里赋值给 levelPrice 0.00（使"订单支付金额"能够展示 0.00）
                    giftList.forEach(item -> item.setLevelPrice(BigDecimal.ZERO));
                    tradeItems.addAll(giftList);
                }
                List<TradeItemVO> preferentialList = tradeVO.getPreferential();
                if (CollectionUtils.isNotEmpty(preferentialList)){
                    // 加价购使用 price 作为商品单价，这里赋值给 levelPrice 统一处理
                    preferentialList.forEach(item -> item.setLevelPrice(item.getPrice()));
                    tradeItems.addAll(preferentialList);
                }
                AtomicInteger m = new AtomicInteger();

                Boolean leaderCommission = exportRequest.getTradeQueryDTO().getLeaderCommissionDetailExport();

                //团长佣金明细导出不需要商品纬度
                if(Objects.nonNull(leaderCommission) && leaderCommission){
                    TradeVO tradeVONew = KsBeanUtil.convert(tradeVO,TradeVO.class);
                    tradesnNew.add(tradeVONew);
                }else{
                    tradeItems.forEach(tradeItemVO -> {
                        TradeVO tradeVONew =KsBeanUtil.convert(tradeVO,TradeVO.class);
                        List<TradeItemVO> tradeItemsNew = new ArrayList<>();
                        tradeItemsNew.add(tradeItemVO);
                        tradeVONew.setTradeItems(tradeItemsNew);
                        //第一条数据记录子单个数，合并单元格使用
                        if (m.getAndIncrement() == 0){
                            tradeVONew.setRowNum(tradeItems.size());
                        }
                        tradesnNew.add(tradeVONew);
                    });
                }


            }

            //处理供应名
            if(CollectionUtils.isNotEmpty(tradeVO.getTradeItems())) {
                tradeVO.getTradeItems().forEach(tradeItemVO -> {
                    if (Objects.nonNull(tradeItemVO.getProviderId()) && providerMap.containsKey(tradeItemVO.getProviderId())) {
                        tradeItemVO.setProviderName(providerMap.get(tradeItemVO.getProviderId()));
                    }
                });
            }
            if (CollectionUtils.isNotEmpty(tradeVO.getGifts())) {
                tradeVO.getGifts().forEach(tradeItemVO -> {
                    if (Objects.nonNull(tradeItemVO.getProviderId()) && providerMap.containsKey(tradeItemVO.getProviderId())) {
                        tradeItemVO.setProviderName(providerMap.get(tradeItemVO.getProviderId()));
                    }
                });
            }
            if (CollectionUtils.isNotEmpty(tradeVO.getPreferential())) {
                tradeVO.getPreferential().forEach(tradeItemVO -> {
                    if (Objects.nonNull(tradeItemVO.getProviderId()) && providerMap.containsKey(tradeItemVO.getProviderId())) {
                        tradeItemVO.setProviderName(providerMap.get(tradeItemVO.getProviderId()));
                    }
                });
            }
        });

        // 导出列表中，添加交易流水号字段
        List<String> tradeIds = tradesnNew.stream().filter(tradeVO -> BookingType.EARNEST_MONEY != tradeVO.getBookingType())
                .map(vo -> {
                    if (StringUtils.isNotEmpty(vo.getTailOrderNo()) && StringUtils.isNotEmpty(vo.getTailPayOrderId())) {
                        return vo.getTailOrderNo();
                    } else {
                        return vo.getPayInfo().isMergePay() ? vo.getParentId() : vo.getId();
                    }
                }).collect(Collectors.toList());
        if (WmCollectionUtils.isNotEmpty(tradeIds)) {
            Map<String, String> tradeNoMap = BaseResUtils.getResultFromRes(payTradeRecordQueryProvider.queryTradeNoMapByBusinessIds(TradeNoByBusinessIdRequest
                    .builder().businessIdList(tradeIds).build()), PayTradeNoMapResponse::getTradeNoMap);
            if (Objects.nonNull(tradeNoMap)) {
                tradesnNew.stream().filter(tradeVO -> BookingType.EARNEST_MONEY != tradeVO.getBookingType()).forEach(trade -> {
                    String id = null;
                    if (StringUtils.isNotEmpty(trade.getTailOrderNo())
                            && StringUtils.isNotEmpty(trade.getTailPayOrderId())) {
                        id = trade.getTailOrderNo();
                    } else {
                        id = trade.getPayInfo().isMergePay() ? trade.getParentId() : trade.getId();
                    }
                    if (tradeNoMap.containsKey(id)) {
                        trade.setTradeNo(tradeNoMap.get(id));
                    } else {
                        trade.setTradeNo("-");
                    }
                });
            }
        }
        tradesnNew.stream().filter(tradeVO -> tradeVO.getTradeNo() == null).forEach(trade->trade.setTradeNo("-"));
        return tradesnNew;
    }
}
