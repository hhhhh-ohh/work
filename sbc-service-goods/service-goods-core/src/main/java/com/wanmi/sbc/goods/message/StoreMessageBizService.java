package com.wanmi.sbc.goods.message;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.storemessage.BossMessageNode;
import com.wanmi.sbc.common.enums.storemessage.SupplierMessageNode;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.MutableMap;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.common.GoodsCommonBatchAddRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsEditType;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.mq.ProducerService;
import com.wanmi.sbc.setting.api.provider.stockwarning.StockWarningProvider;
import com.wanmi.sbc.setting.api.provider.stockwarning.StockWarningQueryProvider;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningAddRequest;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningByIdRequest;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningDeleteBySkuIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingByStoreIdRequest;
import com.wanmi.sbc.setting.api.response.stockwarning.StockWarningByIdResponse;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSettingVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description goods，商家消息具体发送业务服务，统一管理
 * @author malianfeng
 * @date 2022/7/12 14:59
 */
@Slf4j
@Service
    public class StoreMessageBizService {

        @Autowired private ProducerService producerService;

        @Autowired private StockWarningQueryProvider stockWarningQueryProvider;

        @Autowired private StoreMessageNodeSettingQueryProvider storeMessageNodeSettingQueryProvider;

        @Autowired private StockWarningProvider stockWarningProvider;

        @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    /**
     * 商家/供应商导入商品待审核
     */
    public void handleForBatchImportGoodsAudit(GoodsSaveDTO tempGoods, GoodsCommonBatchAddRequest request) {
        try {
            // 区分商家/供应商
            BossMessageNode node;
            switch (request.getType()) {
                case SUPPLIER: node = BossMessageNode.SUPPLIER_GOODS_WAIT_AUDIT; break;
                case PROVIDER: node = BossMessageNode.PROVIDER_GOODS_WAIT_AUDIT; break;
                default: return;
            }
            // 封装发送请求
            StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
            mqRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
            mqRequest.setNodeCode(node.getCode());
            mqRequest.setProduceTime(LocalDateTime.now());
            mqRequest.setContentParams(Lists.newArrayList(tempGoods.getGoodsNo()));
            mqRequest.setRouteParams(MutableMap.of("oldGoodsId", tempGoods.getGoodsId()));
            producerService.sendStoreMessage(mqRequest);
        } catch (Exception e) {
            log.error("商家/供应商导入商品待审核，消息处理失败，{} {}", tempGoods, request, e);
        }
    }

    /**
     * 代销商品变更提醒
     */
    public void handleForCommissionGoodsChange(GoodsSaveDTO goods, Map<GoodsEditType, ArrayList<String>> editMap) {
        try {
            if (Objects.nonNull(goods) && DeleteFlag.NO == goods.getDelFlag()) {
                String providerName = goods.getProviderName();
                String changeContent = editMap.values().stream().map(item -> String.join("，", item)).collect(Collectors.joining("，"));
                // 封装发送请求
                StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
                mqRequest.setStoreId(goods.getStoreId());
                mqRequest.setNodeCode(SupplierMessageNode.COMMISSION_GOODS_CHANGE.getCode());
                mqRequest.setProduceTime(LocalDateTime.now());
                mqRequest.setContentParams(Lists.newArrayList(providerName, goods.getGoodsNo(), goods.getGoodsName(), changeContent));
                mqRequest.setRouteParams(MutableMap.of(
                        "oldGoodsId", goods.getGoodsId(),
                        "providerGoodsId", goods.getProviderGoodsId()));
                producerService.sendStoreMessage(mqRequest);
            }
        } catch (Exception e) {
            log.error("代销商品变更提醒，消息处理失败，{} {}", goods, editMap, e);
        }
    }

    /**
     * 商家批量修改库存，库存预警预警提醒
     * supplier -> 预警 和 取消预警  自建
     * provider
     */
    public void handleWarningStockByGoodsInfos(List<GoodsInfo> goodsInfoList) {
        try {
            goodsInfoList.forEach(item -> {
                String skuNo = item.getGoodsInfoNo();
                String skuName = item.getGoodsInfoName();
                String skuId;
                if (Objects.nonNull(item.getOldGoodsInfoId())) {
                    skuId = item.getOldGoodsInfoId();
                } else {
                    skuId = item.getGoodsInfoId();
                }

                Long storeId = item.getStoreId();
                String nodeCode = SupplierMessageNode.GOODS_SKU_WARN_STOCK.getCode();

                // 处理商家的
                if (GoodsSource.SELLER.toValue() == item.getGoodsSource()) {
                    this.commonSendWaringStock(skuId, storeId, skuNo, skuName, nodeCode);
                }

                if (Objects.nonNull(item.getProviderGoodsInfoId())) {
                    // 处理供应商的
                    skuId = item.getProviderGoodsInfoId();
                    storeId = item.getProviderId();
                    this.commonSendWaringStock(skuId, storeId, skuNo, skuName, nodeCode);
                } else if (GoodsSource.PROVIDER.toValue() == item.getGoodsSource()) {
//                    skuId = item.getProviderGoodsInfoId();
//                    storeId = item.getProviderId();
                    this.commonSendWaringStock(skuId, storeId, skuNo, skuName, nodeCode);
                    //根据skuId查询有该skuId商家的storeId

                    List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder()
                            .delFlag(0)
                            .providerGoodsInfoIds(Collections.singletonList(skuId))
                            .build()).getContext().getGoodsInfos();

                    if (Objects.nonNull(goodsInfos)) {
                        String finalSkuId = skuId;
                        goodsInfos.forEach(goodsInfoVO -> {
                            String providerSkuId = goodsInfoVO.getGoodsInfoId();
                            Long providerStoreId = goodsInfoVO.getStoreId();
                            this.commonSendWaringStock(providerSkuId, finalSkuId, providerStoreId, goodsInfoVO.getGoodsInfoNo(), goodsInfoVO.getGoodsInfoName(), nodeCode);
                        });
                    }
                }
            });
        } catch (Exception e) {
            log.error("商家库存预警消息提醒,消息处理失败,{}",goodsInfoList,e);
        }
    }

    public void commonSendWaringStock(String skuId, String providerSkuId, Long storeId, String skuNo, String skuName, String nodeCode) {
        String stockSkuId = Objects.nonNull(providerSkuId) ? providerSkuId : skuId;
        Map<String, Long> stockRedisMap = goodsInfoQueryProvider
                .getStockByGoodsInfoIds(
                        GoodsInfoListByIdsRequest.builder()
                                .goodsInfoIds(Collections.singletonList(Objects.nonNull(providerSkuId) ? providerSkuId : skuId))
                                .build())
                .getContext();
        Long currentStock;
        if (MapUtils.isEmpty(stockRedisMap) || Objects.isNull(currentStock = stockRedisMap.get(stockSkuId))) {
            return;
        }
        this.commonSendWaringStock(skuId, currentStock, storeId, skuNo, skuName, nodeCode);
    }

    public void commonSendWaringStock(String skuId, Long currentStock, Long storeId, String skuNo, String skuName, String nodeCode) {
        // 1. 查预警记录表，判断此商品是否已预警过
        StockWarningByIdResponse response = stockWarningQueryProvider
                .findIsWarning(StockWarningByIdRequest.builder().storeId(storeId).skuId(skuId).build()).getContext();
        boolean hasWaring = Objects.equals(BoolFlag.YES, response.getIsWarning());
        // 2 待预警，需要判断需要达到预警阈值
        // 2.1 获取当前商家配置的预警值
        Long warningStock = null;
        StoreMessageNodeSettingVO storeMessageNodeSettingVO = storeMessageNodeSettingQueryProvider
                .getWarningStock(StoreMessageNodeSettingByStoreIdRequest.builder().storeId(storeId).build()).getContext().getStoreMessageNodeSettingVO();
        if (Objects.nonNull(storeMessageNodeSettingVO)) {
            warningStock = storeMessageNodeSettingVO.getWarningStock();
        }
        // 若未配置预警值，直接返回
        if (Objects.isNull(warningStock)) {
            return;
        }
        // 2.2 获取redis中的商品库存
        if (Objects.isNull(currentStock)) {
            Map<String, Long> stockRedisMap = goodsInfoQueryProvider
                    .getStockByGoodsInfoIds(
                            GoodsInfoListByIdsRequest.builder()
                                    .goodsInfoIds(Collections.singletonList(skuId))
                                    .build())
                    .getContext();
            if (MapUtils.isEmpty(stockRedisMap) || Objects.isNull(currentStock = stockRedisMap.get(skuId))) {
                return;
            }
        }
        // 3. 已预警则不处理
        if (hasWaring) {
            if (currentStock >= warningStock) {
                stockWarningProvider.delete(StockWarningDeleteBySkuIdRequest.builder().skuId(skuId).build());
            }
            return;
        }

        if (currentStock < warningStock) {
            // 4.3 否达到阈值，发送消息
            StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
            mqRequest.setStoreId(storeId);
            mqRequest.setNodeCode(nodeCode);
            mqRequest.setProduceTime(LocalDateTime.now());
            mqRequest.setContentParams(Lists.newArrayList(skuName, skuNo, warningStock));
            mqRequest.setRouteParams(MutableMap.of("skuId", skuId));
            producerService.sendStoreMessage(mqRequest);
            // 3.4 新增商家预警记录
            if (Objects.equals(BoolFlag.YES, storeMessageNodeSettingVO.getStatus())) {
                stockWarningProvider.add(StockWarningAddRequest.builder()
                        .skuId(skuId)
                        .isWarning(Constants.ONE)
                        .storeId(storeId)
                        .delFlag(DeleteFlag.NO)
                        .createTime(LocalDateTime.now())
                        .build());
            }
        }
    }

    public void commonSendWaringStock(String skuId, Long storeId, String skuNo, String skuName, String nodeCode) {
        // 1. 查预警记录表，判断此商品是否已预警过
        StockWarningByIdResponse response = stockWarningQueryProvider
                .findIsWarning(StockWarningByIdRequest.builder().storeId(storeId).skuId(skuId).build()).getContext();
        boolean hasWaring = Objects.equals(BoolFlag.YES, response.getIsWarning());
        // 2 待预警，需要判断需要达到预警阈值
        // 2.1 获取当前商家配置的预警值
        Long warningStock = null;
        StoreMessageNodeSettingVO storeMessageNodeSettingVO = storeMessageNodeSettingQueryProvider
                .getWarningStock(StoreMessageNodeSettingByStoreIdRequest.builder().storeId(storeId).build()).getContext().getStoreMessageNodeSettingVO();
        if (Objects.nonNull(storeMessageNodeSettingVO)) {
            warningStock = storeMessageNodeSettingVO.getWarningStock();
        }
        // 若未配置预警值，直接返回
        if (Objects.isNull(warningStock)) {
            return;
        }
        // 2.2 获取redis中的商品库存
        Map<String, Long> stockRedisMap = goodsInfoQueryProvider
                .getStockByGoodsInfoIds(
                        GoodsInfoListByIdsRequest.builder()
                                .goodsInfoIds(Collections.singletonList(skuId))
                                .build())
                .getContext();
        Long currentStock;
        if (MapUtils.isEmpty(stockRedisMap) || Objects.isNull(currentStock = stockRedisMap.get(skuId))) {
            return;
        }
        // 3. 已预警则不处理
        if (hasWaring) {
            if (currentStock >= warningStock) {
                stockWarningProvider.delete(StockWarningDeleteBySkuIdRequest.builder().skuId(skuId).build());
            }
            return;
        }

        if (currentStock < warningStock) {
            // 4.3 否达到阈值，发送消息
            StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
            mqRequest.setStoreId(storeId);
            mqRequest.setNodeCode(nodeCode);
            mqRequest.setProduceTime(LocalDateTime.now());
            mqRequest.setContentParams(Lists.newArrayList(skuName, skuNo, warningStock));
            mqRequest.setRouteParams(MutableMap.of("skuId", skuId));
            producerService.sendStoreMessage(mqRequest);
            // 3.4 新增商家预警记录
            if (Objects.equals(BoolFlag.YES, storeMessageNodeSettingVO.getStatus())) {
                stockWarningProvider.add(StockWarningAddRequest.builder()
                        .skuId(skuId)
                        .isWarning(Constants.ONE)
                        .storeId(storeId)
                        .delFlag(DeleteFlag.NO)
                        .createTime(LocalDateTime.now())
                        .build());
            }
        }
    }
}

