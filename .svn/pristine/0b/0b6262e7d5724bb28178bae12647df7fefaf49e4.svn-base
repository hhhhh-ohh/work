package com.wanmi.sbc.goods.standard.service;


import com.wanmi.sbc.common.constant.RedisKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StandardSkuStockService {

    @Autowired
    private RedisTemplate redisTemplate;

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
                            public Long doInRedis(RedisConnection redisConnection)
                                    throws DataAccessException {

                                for (String id : ids) {
                                    String key = RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + id;
                                    redisConnection.get(
                                            redisTemplate.getStringSerializer().serialize(key));
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
}
