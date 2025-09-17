package com.wanmi.sbc.job;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsStockProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsListSpuSyncRequest;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsSkuSyncDTO;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsSpuSyncDTO;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.wechatvideo.wechatsku.WechatSkuSaveProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifySalesNumRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoSyncSpuRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoSyncSpuResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务Handler
 * 商品GOODS表将redis库存扣除stock
 *
 * @author dyt
 */
@Component
@Slf4j
public class GoodsStockSyncJobHandler {

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private EsGoodsStockProvider esGoodsStockProvider;

    @Autowired
    private WechatSkuSaveProvider wechatSkuSaveProvider;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired private CommonUtil commonUtil;

    @XxlJob(value = "goodsStockSyncJobHandler")
    public void execute() throws Exception {
        // 每10分钟执行 0 0/10 * * * ?
        RLock rLock = redissonClient.getFairLock("goodsStockSyncJobHandler");
        rLock.lock();
        try{
            //更新ES库存
            // Map<String, String> skuRes = redisService.hgetAllStr(CacheKeyConstant.GOODS_STOCK_SUB_CACHE_SKU);
            Map<String, String> skuRes = redisService.hScanStr(CacheKeyConstant.GOODS_STOCK_SUB_CACHE_SKU,
                    Constants.NUM_100L, "*");
            log.info("商品同步更新库存、销量");
            if (MapUtils.isNotEmpty(skuRes)) {
                List<String> skuIdList = new ArrayList<>(skuRes.keySet());
                // 分批处理
                List<List<String>> partition = Lists.partition(skuIdList, Constants.NUM_20);
                for (List<String> skuIds:partition){
                    GoodsInfoSyncSpuRequest syncSpuRequest = new GoodsInfoSyncSpuRequest();
                    syncSpuRequest.setGoodsInfoIds(skuIds);
                    GoodsInfoSyncSpuResponse response = goodsInfoProvider.syncSpu(syncSpuRequest).getContext();
                    if(CollectionUtils.isNotEmpty(response.getSkuList())){
                        List<EsGoodsSpuSyncDTO> spuList = response.getSpuList().stream()
                                .map(s -> {
                                    EsGoodsSpuSyncDTO spu = new EsGoodsSpuSyncDTO();
                                    spu.setSpuId(s.getGoodsId());
                                    spu.setStock(s.getStock());
                                    spu.setGoodsSource(s.getGoodsSource());
                                    return spu;
                                }).collect(Collectors.toList());

                        List<EsGoodsSkuSyncDTO> skuList = response.getSkuList().stream().map(i -> {
                            EsGoodsSkuSyncDTO sku = new EsGoodsSkuSyncDTO();
                            sku.setSpuId(i.getGoodsId());
                            sku.setSkuId(i.getGoodsInfoId());
                            sku.setGoodsSource(i.getGoodsSource());
                            sku.setStock(i.getStock());
                            return sku;
                        }).collect(Collectors.toList());
                        EsGoodsListSpuSyncRequest syncRequest = new EsGoodsListSpuSyncRequest();
                        syncRequest.setSyncSkuList(skuList);
                        syncRequest.setSyncSpuList(spuList);
                        esGoodsStockProvider.sync(syncRequest);
                    }
                }
            }
            redisService.delete(CacheKeyConstant.GOODS_STOCK_SUB_CACHE_SKU);
            if (commonUtil.findVASBuyOrNot(VASConstants.VAS_WECHAT_CHANNELS)) {
                //同步微信库存
                wechatSkuSaveProvider.syncStock();
            }
            //同步销量
            Map<String, String> saleRes = redisService.hgetAllStr(CacheKeyConstant.GOODS_SALES_ADD);
            if (MapUtils.isNotEmpty(saleRes)) {
                saleRes.forEach((k,v) -> {
                    GoodsModifySalesNumRequest request = new GoodsModifySalesNumRequest();
                    request.setGoodsId(k);
                    request.setGoodsSalesNum(NumberUtils.toLong(v));
                    goodsProvider.updateGoodsSalesNum(request);
                });
                List<String> spuIds = new ArrayList<>(saleRes.keySet());
                esGoodsInfoElasticProvider.initEsGoodsInfo(
                        EsGoodsInfoRequest.builder().goodsIds(spuIds).build());
            }
            redisService.delete(CacheKeyConstant.GOODS_SALES_ADD);
        }finally {
            rLock.unlock();
        }
    }
}
