package com.wanmi.sbc.goods.mqconsumer;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.bean.RedisHIncrBean;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoAdjustPriceRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsModifyStoreNameByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.adjustprice.AdjustPriceExecuteRequest;
import com.wanmi.sbc.goods.api.request.common.GoodsCommonBatchAddRequest;
import com.wanmi.sbc.goods.api.request.goods.BatchGoodsStockRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsInfoVendibilityRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyStoreNameByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoModifyAlreadyGrouponNumRequest;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoModifyStatisticsNumRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoMinusStockByIdRequest;
import com.wanmi.sbc.goods.api.response.price.adjustment.AdjustPriceExecuteResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentResult;
import com.wanmi.sbc.goods.common.GoodsCommonService;
import com.wanmi.sbc.goods.common.GoodsCommonServiceInterface;
import com.wanmi.sbc.goods.flashsaleRecord.model.root.FlashSaleRecord;
import com.wanmi.sbc.goods.flashsaleRecord.service.FlashSaleRecordService;
import com.wanmi.sbc.goods.flashsalegoods.repository.FlashSaleGoodsRepository;
import com.wanmi.sbc.goods.groupongoodsinfo.service.GrouponGoodsInfoService;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.priceadjustmentrecorddetail.service.PriceAdjustmentRecordDetailServiceInterface;
import com.wanmi.sbc.setting.api.provider.TaskLogProvider;
import com.wanmi.sbc.setting.api.request.TaskLogAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lvzhenwei
 * @className GoodsMqConsumerService
 * @description mq消费者方法处理
 * @date 2021/8/11 3:52 下午
 **/
@Slf4j
@Service
public class GoodsMqConsumerService {

    @Autowired
    private GoodsCommonService goodsCommonService;

    @Autowired
    private FlashSaleGoodsRepository flashSaleGoodsRepository;

    @Autowired
    private FlashSaleRecordService flashSaleRecordService;

    @Autowired
    private GrouponGoodsInfoService grouponGoodsInfoService;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;
    @Autowired
    private GoodsCommonServiceInterface goodsCommonServiceInterface;

    @Autowired
    private PriceAdjustmentRecordDetailServiceInterface priceAdjustmentRecordDetailServiceInterface;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private TaskLogProvider taskLogProvider;
    @Resource
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoProvider;

    /**
     * @description 批量导入-商品-初始化
     * @author  lvzhenwei
     * @date 2021/8/13 1:45 下午
     * @param json
     * @return void
     **/
    @Async
    public void batchAddGoods(String json) {
        try {
            log.info("批量商品入库");
            GoodsCommonBatchAddRequest request = JSONObject.parseObject(json, GoodsCommonBatchAddRequest.class);
            // 判断类型
            if(StoreType.O2O.equals(request.getType())){
                request.setPluginType(PluginType.O2O);
            } else {
                request.setPluginType(PluginType.NORMAL);
            }
            goodsCommonServiceInterface.batchAdd(request);
            log.info("批量商品入库成功");
        } catch (Exception e) {
            log.error("批量商品入库失败, param={},e={}", json, e);
        }
    }


    /**
     * @description 批量导入-商品-修改库存
     * @author  zgl
     * @date 2023/3/15 1:45 下午
     * @param json
     * @return void
     **/
    @Async
    public void batchGoodsStockUpdate(String json) {
        try {
            log.info("批量商品库存修改");
            BatchGoodsStockRequest request = JSONObject.parseObject(json, BatchGoodsStockRequest.class);

            goodsCommonService.batchGoodsStockUpdate(request);
            log.info("批量商品库存修改");
        } catch (Exception e) {
            log.error("批量商品库存修改, param={},e={}", json, e);
        }
    }

    /**
     * @description 秒杀订单--异步处理销量和个人购买记录
     * @author  lvzhenwei
     * @date 2021/8/13 1:44 下午
     * @param json
     * @return void
     **/
    @Transactional
    public void dealFlashSaleRecord(String json) {
        try {
            log.info("秒杀订单--异步开始");
            FlashSaleRecord flashSaleRecord = JSONObject.parseObject(json, FlashSaleRecord.class);
            flashSaleGoodsRepository.plusSalesVolumeById(flashSaleRecord.getPurchaseNum(), flashSaleRecord.getFlashGoodsId());
            FlashSaleRecord record = flashSaleRecordService.getByFlashGoodsId(flashSaleRecord.getFlashGoodsId());
            // 有了该秒杀记录就累积购买数量
            if (Objects.nonNull(record)) {
                flashSaleRecordService.plusPurchaseNumByFlashGoodsId(flashSaleRecord);
            } else {
                flashSaleRecord.setCreateTime(LocalDateTime.now());
                flashSaleRecordService.add(flashSaleRecord);
            }
            log.info("秒杀订单--异步成功");
        } catch (Exception e) {
            log.error("秒杀订单--异步失败consumer, param={},e={}", json, e);
        }
    }

    /**
     * @description 更新已成团人数
     * @author  lvzhenwei
     * @date 2021/8/17 2:02 下午
     * @param json
     * @return void
     **/
    public void updateAlreadyGrouponNum(String json) {
        try {
            GrouponGoodsInfoModifyAlreadyGrouponNumRequest request = JSONObject.parseObject(json, GrouponGoodsInfoModifyAlreadyGrouponNumRequest.class);
            int result = grouponGoodsInfoService.updateAlreadyGrouponNumByGrouponActivityIdAndGoodsInfoId(request.getGrouponActivityId(),request.getGoodsInfoIds(),request.getAlreadyGrouponNum());
            log.info("更新已成团人数，参数详细信息：{}，是否成功 ? {}",json,result == 0 ? "失败" : "成功");
        } catch (Exception e) {
            log.error("更新已成团人数，发生异常! param={}", json, e);
        }
    }

    /**
     * @description 更新商品销售量、订单量、交易额
     * @author  lvzhenwei
     * @date 2021/8/17 2:11 下午
     * @param json
     * @return void
     **/
    public void updateGrouponOrderPayStatistics(String json) {
        try {
            GrouponGoodsInfoModifyStatisticsNumRequest request = JSONObject.parseObject(json, GrouponGoodsInfoModifyStatisticsNumRequest.class);
            int result = grouponGoodsInfoService.updateOrderPayStatisticNumByGrouponActivityIdAndGoodsInfoId(request.getGrouponActivityId(),
                    request.getGoodsInfoId(),request.getGoodsSalesNum(),request.getOrderSalesNum(),request.getTradeAmount());
            log.info("更新已成团人数，参数详细信息：{},是否成功 ? {}",json,result == 0 ? "失败" : "成功");
        } catch (Exception e) {
            log.error("更新已成团人数，发生异常! param={}", json, e);
        }
    }

    /**
     * @description 增加商品库存
     * @author  lvzhenwei
     * @date 2021/8/18 10:44 上午
     * @param json
     * @return void
     **/
    @Transactional
    public void addGoodsInfoStock(String json) {
        try {
            GoodsInfoMinusStockByIdRequest request = JSONObject.parseObject(json, GoodsInfoMinusStockByIdRequest.class);
            //更新redis的标识用于更新es
            List<RedisHIncrBean> beans = new ArrayList<RedisHIncrBean>();
            //sku
            beans.add(new RedisHIncrBean(request.getGoodsInfoId(),-request.getStock()));
            redisService.hincrPipeline(CacheKeyConstant.GOODS_STOCK_SUB_CACHE_SKU, beans);

            int updateCount = goodsInfoRepository.addStockById(request.getStock(), request.getGoodsInfoId());

            if (updateCount <= 0) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030061);
            }
            log.info("增加库存，是否成功 ? {},param={}", "成功", json);
        } catch (Exception e) {
            log.error("增加库存，发生异常! param={}", json, e);
        }
    }

    /**
     * @description 增加商品库存
     * @author  lvzhenwei
     * @date 2021/8/18 10:58 上午
     * @param json
     * @return void
     **/
    @Transactional
    public void subGoodsInfoStock(String json) {
        try {
            GoodsInfoMinusStockByIdRequest request = JSONObject.parseObject(json, GoodsInfoMinusStockByIdRequest.class);
            //更新redis的标识用于更新es
            List<RedisHIncrBean> beans = new ArrayList<RedisHIncrBean>();
            //sku
            beans.add(new RedisHIncrBean(request.getGoodsInfoId(),request.getStock()));
            redisService.hincrPipeline(CacheKeyConstant.GOODS_STOCK_SUB_CACHE_SKU, beans);
            //缓存是扣库存性缓存，加库存则扣除
            int updateCount = goodsInfoRepository.subStockById(request.getStock(), request.getGoodsInfoId());

            if (updateCount <= 0) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030061);
            }
            log.info("扣减数据库库存，是否成功 ? {},param={}", "成功", json);
        } catch (Exception e) {
            log.error("扣减库存，发生异常! param={}", json, e);
        }
    }

    /**
     * @description 增加商品库存
     * @author  lvzhenwei
     * @date 2021/8/18 10:58 上午
     * @param json
     * @return void
     **/
    @Transactional
    public void replaceGoodsInfoStock(String json) {
        try {
            GoodsInfoMinusStockByIdRequest request = JSONObject.parseObject(json, GoodsInfoMinusStockByIdRequest.class);
            //更新redis的标识用于更新es
            List<RedisHIncrBean> beans = new ArrayList<RedisHIncrBean>();
            //sku
            beans.add(new RedisHIncrBean(request.getGoodsInfoId(),request.getStock()));
            redisService.hincrPipeline(CacheKeyConstant.GOODS_STOCK_SUB_CACHE_SKU, beans);
            //缓存是扣库存性缓存，加库存则扣除
            int updateCount = goodsInfoRepository.updateStockById(request.getStock(), request.getGoodsInfoId());

            if (updateCount <= 0) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030061);
            }
            log.info("替换数据库库存，是否成功 ? {},param={}", "成功", json);
        } catch (Exception e) {
            log.error("替换库存，发生异常! param={}", json, e);
        }
    }

    /**
     * @description 商品批量改价
     * @author malianfeng
     * @date 2021/9/9 13:43
     * @param json 消息内容
     * @return void
     */
    public void goodsPriceAdjust(String json) {
        log.info("商品改价消息--开始消费");
        AdjustPriceExecuteRequest request = JSONObject.parseObject(json, AdjustPriceExecuteRequest.class);
        // 调价单ID
        String adjustNo = request.getAdjustNo();
        // 店铺ID
        Long storeId = request.getStoreId();
        // 根据店铺类型确认请求路由
        PluginType pluginType = PluginType.NORMAL;
        if(Objects.nonNull(storeId)){
            StoreVO storeVo = BaseResUtils.getResultFromRes(storeQueryProvider.getById(new StoreByIdRequest(storeId)),
                    StoreByIdResponse::getStoreVO);
            if(Objects.nonNull(storeVo) && StoreType.O2O == storeVo.getStoreType()){
                pluginType = PluginType.O2O;
            }
        }

        try {
            AdjustPriceExecuteResponse response = priceAdjustmentRecordDetailServiceInterface.adjustPriceTaskExecute(adjustNo, storeId, pluginType);
            if(CollectionUtils.isNotEmpty(response.getSkuIds())) {
                EsGoodsInfoAdjustPriceRequest adjustPriceRequest = EsGoodsInfoAdjustPriceRequest.builder()
                        .goodsInfoIds(response.getSkuIds())
                        .type(response.getType()).build();
                // 同步es
                BaseResponse esResponse = esGoodsInfoElasticProvider.adjustPrice(adjustPriceRequest);
                if (CommonErrorCodeEnum.K000000.getCode().equals(esResponse.getCode())) {
                    // 调价成功调用
                    log.info("商品改价，是否成功 ? {},param={}", "成功", json);
                }
            }
        } catch (Exception e) {
            log.error("商品改价，发生异常! param={}", json, e);
            // 更新调价单状态
            priceAdjustmentRecordDetailServiceInterface.executeFail(adjustNo, PriceAdjustmentResult.FAIL, "系统异常");
            // 新增定时任务日志
            taskLogProvider.add(TaskLogAddRequest.builder()
                    .storeId(storeId)
                    .remarks("商品改价，发生异常! " + json)
                    .taskResult(TaskResult.EXECUTE_FAIL)
                    .taskBizType(TaskBizType.PRICE_ADJUST)
                    .stackMessage(ExceptionUtils.getStackTrace(e))
                    .createTime(LocalDateTime.now())
                    .build());
        }
    }

    /**
     * 商品-更新店铺信息时，更新商品goods中对应店铺名称，同步es
     * @param json
     */
    @Transactional
    public void goodsStoreNameModify(String json){
        try {
            GoodsModifyStoreNameByStoreIdRequest request = JSONObject.parseObject(json, GoodsModifyStoreNameByStoreIdRequest.class);
            String supplierName = request.getStoreName();
            Long storeId = request.getStoreId();
            goodsRepository.updateSupplierName(supplierName, storeId);
            goodsRepository.updateProviderName(request.getSupplierName(), storeId);
            // 同步修改esGoods
            EsGoodsModifyStoreNameByStoreIdRequest esGoodsModifyStoreNameRequest = new EsGoodsModifyStoreNameByStoreIdRequest();
            KsBeanUtil.copyProperties(request, esGoodsModifyStoreNameRequest);
            //todo
            //esGoodsInfoElasticProvider.modifyStoreNameByStoreId(esGoodsModifyStoreNameRequest);
            log.info("商品-更新店铺信息时，更新商品goods中对应店铺名称，同步es，是否成功 ? {},param={}", "成功", json);
        } catch (Exception e) {
            log.error("商品-更新店铺信息时，更新商品goods中对应店铺名称，同步es param={}", json, e);
        }
    }

    /**
     * @description   根据供应商商品Id批量处理 代销商品可售状态
     * @author  wur
     * @date: 2023/8/11 9:36
     * @param json
     * @return
     **/
    @Transactional
    public void buildGoodsInfoVendibility(String json) {
        GoodsInfoVendibilityRequest request = JSONObject.parseObject(json, GoodsInfoVendibilityRequest.class);
        List<String> providerGoodsInfoId = request.getGoodsInfoIdList();

        //------------ 处理SPU -------------
        if (CollectionUtils.isNotEmpty(request.getGoodsIdList())) {
            Map<Integer, List<String>> vendibilityGoodsMap = new HashMap<>();
            List<Goods> goods = goodsRepository.findAllByGoodsIdIn(request.getGoodsIdList());
            log.info("------buildGoodsInfoVendibility-------goods={}", JSONObject.toJSONString(goods));
            if (CollectionUtils.isNotEmpty(goods)) {
                goods.forEach(g -> {
                    Integer goodsVendibility = Constants.no;
                    // 未下架、未删除、已审核视为商品可售（商品维度）
                    if ((AddedFlag.NO.toValue() != g.getAddedFlag())
                            && (DeleteFlag.NO == g.getDelFlag())
                            && (CheckStatus.CHECKED == g.getAuditStatus())) {
                        goodsVendibility = Constants.yes;
                    }
                    if (vendibilityGoodsMap.containsKey(goodsVendibility)) {
                        List<String> goodsIdList = vendibilityGoodsMap.get(goodsVendibility);
                        goodsIdList.add(g.getGoodsId());
                        vendibilityGoodsMap.put(goodsVendibility, goodsIdList);
                    } else {
                        List<String> goodsIdList = new ArrayList<>();
                        goodsIdList.add(g.getGoodsId());
                        vendibilityGoodsMap.put(goodsVendibility, goodsIdList);
                    }
                });

            }
            if(MapUtils.isNotEmpty(vendibilityGoodsMap)) {
                vendibilityGoodsMap.forEach((key, value)->{
                    goodsRepository.updateGoodsVendibility(key, value);
                });
            }
        }

        // ----------- 处理SKU-----------------
        // 批量查询供应商商品信
        List<GoodsInfo> providerGoodsInfoList = goodsInfoProvider.findByGoodsInfoIds(providerGoodsInfoId);
        if (CollectionUtils.isEmpty(providerGoodsInfoList)) {
            return;
        }
        List<Long> storeIDList =  providerGoodsInfoList.stream().map(GoodsInfo::getStoreId).collect(Collectors.toList());
        // 批量查询供应商信息
        List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIDList).build()).getContext().getStoreVOList();
        if (CollectionUtils.isEmpty(storeVOList)) {
            return;
        }
        Map<Long, StoreVO> storeMap =  storeVOList.stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
        // 根据供应商商品循环处理
        Integer vendibility = Constants.yes;
        LocalDateTime now = LocalDateTime.now();
        Map<Integer, List<String>> vendibilityMap = new HashMap<>();
        for(GoodsInfo providerGoodsInfo : providerGoodsInfoList) {
            //验证商品状态
            if (!(Objects.equals(DeleteFlag.NO, providerGoodsInfo.getDelFlag())
                    && Objects.equals(CheckStatus.CHECKED, providerGoodsInfo.getAuditStatus())
                    && Objects.equals(AddedFlag.YES.toValue(), providerGoodsInfo.getAddedFlag()))) {
                vendibility = Constants.no;
            }
            //验证店铺
            StoreVO store = storeMap.containsKey(providerGoodsInfo.getStoreId()) ? storeMap.get(providerGoodsInfo.getStoreId()) : null;
            if (Objects.isNull(store)) {
                vendibility = Constants.no;
            } else {
                // 当前签约是否正常，(start <= now <= end)
                boolean isContractNormal = (now.isBefore(store.getContractEndDate()) || now.isEqual(store.getContractEndDate()))
                        && (now.isAfter(store.getContractStartDate()) || now.isEqual(store.getContractStartDate()));
                if (!(
                        Objects.equals(DeleteFlag.NO, store.getDelFlag())
                                && Objects.equals(StoreState.OPENING, store.getStoreState())
                                && isContractNormal
                )) {
                    vendibility = Constants.no;
                }
            }

            if (vendibilityMap.containsKey(vendibility)) {
                List<String> goodsInfoIds = vendibilityMap.get(vendibility);
                goodsInfoIds.add(providerGoodsInfo.getGoodsInfoId());
                vendibilityMap.put(vendibility, goodsInfoIds);
            } else {
                List<String> goodsInfoIds = new ArrayList<>();
                goodsInfoIds.add(providerGoodsInfo.getGoodsInfoId());
                vendibilityMap.put(vendibility, goodsInfoIds);
            }
        }
        if (MapUtils.isEmpty(vendibilityMap)) {
            return;
        }
        vendibilityMap.forEach((key, value)->{
            goodsInfoProvider.updateGoodsInfoVendibility(key, value);
        });
    }
}
