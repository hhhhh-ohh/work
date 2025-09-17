package com.wanmi.sbc.goods.info.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoMinusStockByIdRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMinusStockDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoRedisStockVO;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * sku库存服务
 *
 * @author: hehu
 * @createDate: 2020-03-16 14:35:19
 * @version: 1.0
 */
@Service
@Slf4j
public class GoodsInfoStockService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 初始化redis缓存
     *
     * @param stock
     * @param goodsInfoId
     */
    public void initCacheStock(Long stock, String goodsInfoId) {
        log.info("初始化/覆盖redis库存开始：skuId={},stock={}", goodsInfoId, stock);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId, stock.toString());
        log.info("初始化/覆盖redis库存结束：skuId={},stock={}", goodsInfoId, stock);

    }

    /**
     * 根据SKU编号加库存
     *
     * @param stock       库存数
     * @param goodsInfoId SKU编号
     */
    public void addStockById(Long stock, String goodsInfoId,String goodsId) {

        // 检查数据准确
        checkStockCache(goodsInfoId);

        Long count = redisTemplate.opsForValue().increment(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId, stock);
        log.info("增量增加库存结束：skuId={},增加的库存增量:{},增加后的库存:{}", goodsInfoId, stock, count);

        //发送mq，增加数据库库存
        log.info("扣减redis库存后，发送mq同步至数据库开始skuId={},stock={}...", goodsInfoId, stock);
        GoodsInfoMinusStockByIdRequest request = GoodsInfoMinusStockByIdRequest.builder().goodsInfoId(goodsInfoId).stock(stock).build();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_INFO_STOCK_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
        log.info("增加redis库存后，发送mq同步至数据库结束skuId={},stock={}...", goodsInfoId, stock);
    }


    /**
     * 根据SKU编号减库存
     *
     * @param stock       库存数
     * @param goodsInfoId SKU编号
     */
    public void subStockById(Long stock, String goodsInfoId,String goodsId) {
        // 检查数据准确
        checkStockCache(goodsInfoId);

        //扣除库存
        Long result = redisService.deductStock(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId, stock);
        log.info("增量扣减库存结束：skuId={},扣减的库存增量:{},扣减结果:{}", goodsInfoId, stock, result);
        if (result == 0) {
            log.info("redis中库存不足，skuId={},stock={}...", goodsInfoId, stock);
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030061);
        } else {
            log.info("扣减redis库存后，发送mq同步至数据库开始skuId={},stock={}...", goodsInfoId, stock);
            //发送mq，扣减数据库库存
            GoodsInfoMinusStockByIdRequest request = GoodsInfoMinusStockByIdRequest.builder().goodsInfoId(goodsInfoId).stock(stock).build();
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.GOODS_INFO_STOCK_SUB);
            mqSendDTO.setData(JSONObject.toJSONString(request));
            mqSendProvider.send(mqSendDTO);
            log.info("扣减redis库存后，发送mq同步至数据库结束skuId={},stock={}...", goodsInfoId, stock);
        }
    }

    /**
     * 检查缓存中数据准确性并更新
     *
     * @param goodsInfoId skuId
     */
    public Long checkStockCache(String goodsInfoId) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        // 检查缓存中数据是否准确。
        boolean updateFlag = false;
        Object stockInRedis = redisTemplate.opsForValue().get(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId);
        Long stockNum = 0L;
        if (Objects.isNull(stockInRedis)) {
            log.info("redis中无{}的库存，准备重载redis中库存...", goodsInfoId);
            // 当缓存中无数据时 更新
            updateFlag = true;
        } else {
            stockNum = Long.valueOf((String) stockInRedis);
        }

        if (updateFlag) {
            List<GoodsInfo> byGoodsInfoIds = goodsInfoRepository.findByGoodsInfoIds(Collections.singletonList(goodsInfoId));
            if (CollectionUtils.isNotEmpty(byGoodsInfoIds)) {
                stockNum = byGoodsInfoIds.get(0).getStock();
                stockNum = stockNum == null ? Long.valueOf(0) : stockNum;
                redisTemplate.opsForValue().set(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId, stockNum.toString());
                log.info("redis中{}的库存重载完毕，重载后的库存为{}...", goodsInfoId, stockNum);
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"商品不存在");
            }
        }
        return stockNum;
    }


//    /**
//     * 批量加库存
//     *
//     * @param dtoList 增量库存参数
//     */
//    public void batchAddStock(List<GoodsInfoPlusStockDTO> dtoList) {
//        if (CollectionUtils.isEmpty(dtoList)) {
//            return;
//        }
//        //缓存是扣库存性缓存，加库存则扣除
//        List<RedisHIncrBean> beans = dtoList.stream().map(g -> new RedisHIncrBean(g.getGoodsInfoId(), -g.getStock()))
//                .collect(Collectors.toList());
//        redisService.hincrPipeline(CacheKeyConstant.GOODS_STOCK_SUB_CACHE_SKU, beans);
//    }
//
//    /**
//     * 批量减库存
//     *
//     * @param dtoList 减量库存参数
//     */
//    public void batchSubStock(List<GoodsInfoMinusStockDTO> dtoList) {
//        if (CollectionUtils.isEmpty(dtoList)) {
//            return;
//        }
//        List<RedisHIncrBean> beans = dtoList.stream().map(g -> new RedisHIncrBean(g.getGoodsInfoId(), g.getStock()))
//                .collect(Collectors.toList());
//        redisService.hincrPipeline(CacheKeyConstant.GOODS_STOCK_SUB_CACHE_SKU, beans);
//    }

    /**
     * @description 商品库存校验
     * @author  edz
     * @date 2021/5/10 10:56 上午
     * @param dtoList
     * @return
     */
    public void checkStock(List<GoodsInfoMinusStockDTO> dtoList) {
        dtoList.forEach(dto -> {
            //更新商品库存时，判断其中商品是否来自供应商，来自供应商的商品，要改为更新供应商商品库存
            GoodsInfo goodsInfo = goodsInfoRepository.getOne(dto.getGoodsInfoId());
            String goodsInfoId = goodsInfo.getGoodsInfoId();
            if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
                goodsInfoId = goodsInfo.getProviderGoodsInfoId();
            }
            // 检查数据准确
            checkStockCache(goodsInfoId);

            //获取最新库存
            Object stockInRedis = redisTemplate.opsForValue().get(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId);
            Long stockNum = NumberUtils.LONG_ZERO;
            if(stockInRedis != null) {
                stockNum = Long.valueOf((String) stockInRedis);
            }
            if(dto.getStock().longValue() > stockNum.longValue()) {
                log.info("redis中库存不足，，skuId={},stock={}...", goodsInfoId, dto.getStock());
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030061);
            }
        });

    }

    public Map<String, Long> getStockByGoodsInfoIds(List<String> ids) {
        Map<String, Long> retMap = getRedisStockByGoodsInfoIds(ids);

        List<String> noCacheKey = null;
        if (MapUtils.isNotEmpty(retMap)){
            noCacheKey =
            retMap.entrySet().stream()
                    .filter(e -> e.getValue() == null)
                    .map(t -> t.getKey())
                    .collect(Collectors.toList());
        }else{
            noCacheKey = ids;
        }
        if (CollectionUtils.isNotEmpty(noCacheKey)) {
            List<GoodsInfo> goodsInfos = goodsInfoRepository.findByGoodsInfoIds(noCacheKey);
            if (CollectionUtils.isNotEmpty(goodsInfos)) {
                for (GoodsInfo goodsInfo : goodsInfos) {

                    Long stockNum = goodsInfo.getStock();
                    stockNum = stockNum == null ? Long.valueOf(0) : stockNum;
                    retMap.put(goodsInfo.getGoodsInfoId(), goodsInfo.getStock());

                    // 慎用，有并发问题，并发大了会产生超卖，如果要放入缓存，则需要对所有操作加锁
                    // redisTemplate.opsForValue().set(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX +
                    // goodsInfo.getGoodsId(), stockNum.toString());
                }
            }
        }
        return retMap;
    }

    public Map<String,Long> getRedisStockByGoodsInfoIds(List<String> ids){
        Map<String, Long> retMap = new HashMap<>();
        if(CollectionUtils.isEmpty(ids)){
            return retMap;
        }
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        List<Object> values =
                redisTemplate.executePipelined(
                        new RedisCallback<Long>() {

                            @Override
                            public Long doInRedis(@NotNull RedisConnection redisConnection)
                                    throws DataAccessException {
                                for (String id : ids) {
                                    String key = RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + id;
                                    redisConnection.stringCommands().get(redisTemplate.getStringSerializer().serialize(key));
                                }
                                return null;
                            }
                        });
        if (CollectionUtils.isNotEmpty(values)) {
            for (int i = 0; i < ids.size(); i++) {
                retMap.put(ids.get(i), values.get(i)==null?null:Long.parseLong((String)values.get(i)));
            }
        }
        return retMap;
    }

    /**
     * 根据商品Id集合批量查询缓存中的库存
     * @author  wur
     * @date: 2021/11/5 15:35
     * @param goodsInfoIds  商品Id
     * @return
     **/
    public List<GoodsInfoRedisStockVO> getRedisStock(List<String> goodsInfoIds) {
        List<GoodsInfoRedisStockVO> redisStockDTOList = new ArrayList<>();
        goodsInfoIds.forEach(goodsInfoId -> {
            //获取最新库存
            Object stockInRedis = redisTemplate.opsForValue().get(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId);
            if(stockInRedis != null) {
                GoodsInfoRedisStockVO redisStockDTO = new GoodsInfoRedisStockVO();
                redisStockDTO.setStock(Long.valueOf((String) stockInRedis));
                redisStockDTO.setGoodsInfoId(goodsInfoId);
                redisStockDTOList.add(redisStockDTO);
            }
        });
        return redisStockDTOList;
    }

    /**
     * 根据SKU编号加库存，仅仅用于商品库存的批量导入
     *
     * @param stock       库存数
     * @param goodsInfoId SKU编号
     */
    public void addStockByIdNoErr(Long stock, String goodsInfoId) {

        // 检查数据准确
        checkStockCache(goodsInfoId);

        Long count = redisTemplate.opsForValue().increment(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId, stock);
        log.info("增量增加库存结束：skuId={},增加的库存增量:{},增加后的库存:{}", goodsInfoId, stock, count);

        //发送mq，增加数据库库存
        log.info("扣减redis库存后，发送mq同步至数据库开始skuId={},stock={}...", goodsInfoId, stock);
        GoodsInfoMinusStockByIdRequest request = GoodsInfoMinusStockByIdRequest.builder().goodsInfoId(goodsInfoId).stock(stock).build();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_INFO_STOCK_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
        log.info("增加redis库存后，发送mq同步至数据库结束skuId={},stock={}...", goodsInfoId, stock);
    }


    /**
     * 根据SKU编号减库存，仅仅用于商品库存的批量导入
     *
     * @param stock       库存数
     * @param goodsInfoId SKU编号
     */
    public void subStockByIdNoErr(Long stock, String goodsInfoId) {
        // 检查数据准确
        checkStockCache(goodsInfoId);

        //扣除库存
        Long result = redisService.decrByKey(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId, stock);
        log.info("增量扣减库存结束：skuId={},扣减的库存增量:{},扣减结果:{}", goodsInfoId, stock, result);
        if (result < 0) {
            log.info("redis中库存不足，skuId={},stock={}...", goodsInfoId, stock);
            redisTemplate.opsForValue().set(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId,"0");
            stock+=result;
        }
        if(stock<=0){
            return;
        }
        log.info("扣减redis库存后，发送mq同步至数据库开始skuId={},stock={}...", goodsInfoId, stock);
        //发送mq，扣减数据库库存
        GoodsInfoMinusStockByIdRequest request = GoodsInfoMinusStockByIdRequest.builder().goodsInfoId(goodsInfoId).stock(stock).build();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_INFO_STOCK_SUB);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
        log.info("扣减redis库存后，发送mq同步至数据库结束skuId={},stock={}...", goodsInfoId, stock);

    }

    /**
     * 根据SKU编号覆盖，仅仅用于商品库存的批量导入
     *
     * @param stock       库存数
     * @param goodsInfoId SKU编号
     */
    public void replaceStockByIdNoErr(Long stock, String goodsInfoId) {
        // 检查数据准确
        checkStockCache(goodsInfoId);

        //扣除库存
        redisTemplate.opsForValue().set(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId,stock.toString());

        log.info("替换redis库存后，发送mq同步至数据库开始skuId={},stock={}...", goodsInfoId, stock);
        //发送mq，扣减数据库库存
        GoodsInfoMinusStockByIdRequest request = GoodsInfoMinusStockByIdRequest.builder().goodsInfoId(goodsInfoId).stock(stock).build();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_INFO_STOCK_REPLACE);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
        log.info("扣减redis库存后，发送mq同步至数据库结束skuId={},stock={}...", goodsInfoId, stock);

    }

}
