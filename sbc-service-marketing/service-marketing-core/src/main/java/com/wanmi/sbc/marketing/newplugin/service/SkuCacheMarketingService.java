package com.wanmi.sbc.marketing.newplugin.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.marketing.api.request.newplugin.MarketingPluginPreRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author zhanggaolei
 * @className SkuCacheMarketingService
 * @description
 * @date 2021/7/21 4:00 下午
 */
@Service
public class SkuCacheMarketingService {

    @Autowired StringRedisTemplate redisTemplate;

    @Resource(name = "marketingRedisScript")
    private DefaultRedisScript<String> redisScript;

    /**
     * @description 根据goodsInfoId获取相应的营销活动
     * @author zhanggaolei
     * @date 2021/6/25 11:00
     * @param goodsInfoIds
     * @return
     *     java.util.Map<com.wanmi.sbc.marketing.bean.enums.MarketingPluginType,com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO>
     */
    public Map<String, Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>>> getSkuCacheMarketing(
            List<String> goodsInfoIds, boolean isStart) {
        Map<String, Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>>> retMap = new HashMap<>();
        String result =
                redisTemplate.execute(
                        redisScript,
                        goodsInfoIds,
                        JSONObject.toJSONString(LocalDateTime.now()));
        if (StringUtils.isNotEmpty(result)) {
            Map<String, String> jsonMap = JSONObject.parseObject(result, Map.class);
            for (Map.Entry<String, String> e : jsonMap.entrySet()) {
                //  Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>> map = new
                // HashMap<>();
                String goodsInfoId = e.getKey();
                Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>> listMap =
                        retMap.getOrDefault(goodsInfoId, new HashMap<>());
                Map<String, String> valMap = JSONObject.parseObject(e.getValue(), Map.class);
                for (Map.Entry<String, String> entry : valMap.entrySet()) {
                    MarketingPluginType pluginType =
                            MarketingPluginType.fromValue(entry.getKey().split(":")[0]);
                    if (pluginType != null && !pluginType.equals(MarketingPluginType.OTHER)) {

                        GoodsInfoMarketingCacheDTO cache =
                                JSONObject.parseObject(
                                        entry.getValue(), GoodsInfoMarketingCacheDTO.class);
                        cache.setMarketingPluginType(pluginType);

                        // 是不是需要校验活动是否开始或者结束
                        if (isStart) {
                            if (LocalDateTime.now().isBefore(cache.getBeginTime())
                                    || LocalDateTime.now().isAfter(cache.getEndTime())) {
                                continue;
                            }
                        }
                        // Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>> listMap =
                        //        retMap.getOrDefault(goodsInfoId, new HashMap<>());
                        List<GoodsInfoMarketingCacheDTO> temList = listMap.get(pluginType);
                        if (temList == null) {
                            temList = new ArrayList<>();
                        }
                        temList.add(cache);
                        listMap.put(pluginType, temList);
                    }
                }
                retMap.put(goodsInfoId, listMap);
            }
            return retMap;
        }
        return Maps.newHashMap();
    }

    /**
     * @description 根据goodsInfoId获取相应的预热营销活动（拼团、秒杀、限时抢购）
     * @author 王超
     * @date 2022/2/17 11:00
     * @param request
     * @return
     *     java.util.Map<com.wanmi.sbc.marketing.bean.enums.MarketingPluginType,com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO>
     */
    public GoodsInfoDetailPluginResponse getSkuCachePreMarketing(
            MarketingPluginPreRequest request) {

        GoodsInfoDetailPluginResponse response = new GoodsInfoDetailPluginResponse();
        List<GoodsInfoMarketingCacheDTO> goodsInfoMarketingCacheDTOS = new ArrayList<>();
        String result =
                redisTemplate.execute(
                        redisScript,
                        Collections.singletonList(
                                RedisKeyConstant.GOODS_INFO_MARKETING_KEY + request.getGoodsInfoId()),
                        LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (StringUtils.isNotEmpty(result)) {
            Map<String, String> jsonMap = JSONObject.parseObject(result, Map.class);
            for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                MarketingPluginType pluginType =
                        MarketingPluginType.fromValue(entry.getKey().split(":")[0]);

                if (pluginType != null) {

                    GoodsInfoMarketingCacheDTO cache =
                            JSONObject.parseObject(
                                    entry.getValue(), GoodsInfoMarketingCacheDTO.class);
                    cache.setMarketingPluginType(pluginType);

                    if((pluginType.equals(MarketingPluginType.GROUPON) ||
                            pluginType.equals(MarketingPluginType.FLASH_PROMOTION))){
                        //拼团、限时抢购 使用缓存的preStartTime
                        if (Objects.isNull(cache.getPreStartTime())
                                || LocalDateTime.now().isBefore(cache.getPreStartTime())
                                || LocalDateTime.now().isAfter(cache.getBeginTime())) {
                            continue;
                        }
                    }else if(pluginType.equals(MarketingPluginType.FLASH_SALE)
                            && Objects.nonNull(request.getPreTime())
                            && request.getPreTime() > 0
                    ){
                        //秒杀 使用开始时间和预热时间字段
                        if (LocalDateTime.now().isBefore(cache.getBeginTime().minusHours(request.getPreTime()))
                                || LocalDateTime.now().isAfter(cache.getBeginTime())) {
                            continue;
                        }
                    }else{
                        continue;
                    }

                    goodsInfoMarketingCacheDTOS.add(cache);
                }
            }
        }
        response.setPreMarketings(goodsInfoMarketingCacheDTOS);
        return response;
    }

//    /**
//     * @description 根据goodsInfoIds获取相应的营销活动
//     * @author zhanggaolei
//     * @date 2021/6/25 11:00
//     * @param goodsInfoIds
//     * @return
//     *     java.util.Map<java.lang.String,java.util.Map<com.wanmi.sbc.marketing.bean.enums.MarketingPluginType,com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO>>
//     */
//    public Map<String, Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>>>
//            getSkuCacheMarketing(List<String> goodsInfoIds, boolean isStart) {
//        Map<String, Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>>> retMap =
//                new HashMap<>();
//        for (String goodsInfoId : goodsInfoIds) {
//            Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>> map =
//                    getSkuCacheMarketing(goodsInfoId, isStart);
//            if (MapUtils.isNotEmpty(map)) {
//                retMap.put(goodsInfoId, map);
//            }
//        }
//        return retMap;
//    }

    public boolean setSkuCacheMarketing(List<GoodsInfoMarketingCacheDTO> list) {
        return redisTemplate.execute(
                (RedisCallback<Boolean>)
                        redisConnection -> {
                            list.forEach(
                                    g ->
                                            redisConnection.hSet(
                                                    redisTemplate
                                                            .getStringSerializer()
                                                            .serialize(
                                                                    RedisKeyConstant
                                                                                    .GOODS_INFO_MARKETING_KEY
                                                                            + g.getSkuId()),
                                                    redisTemplate
                                                            .getStringSerializer()
                                                            .serialize(
                                                                    g.getMarketingPluginType()
                                                                                    .name()
                                                                            + ":"
                                                                            + g.getId()),
                                                    redisTemplate
                                                            .getStringSerializer()
                                                            .serialize(
                                                                    JSONObject.toJSONString(g))));
                            return Boolean.TRUE;
                        });
    }

    public boolean delSkuCacheMarketing(List<GoodsInfoMarketingCacheDTO> list) {
        return redisTemplate.execute(
                (RedisCallback<Boolean>)
                        redisConnection -> {
                            list.forEach(
                                    g ->
                                            redisConnection.hDel(
                                                    redisTemplate
                                                            .getStringSerializer()
                                                            .serialize(
                                                                    RedisKeyConstant
                                                                                    .GOODS_INFO_MARKETING_KEY
                                                                            + g.getSkuId()),
                                                    redisTemplate
                                                            .getStringSerializer()
                                                            .serialize(
                                                                    g.getMarketingPluginType()
                                                                                    .name()
                                                                            + ":"
                                                                            + g.getId())));
                            return Boolean.TRUE;
                        });
    }
}
