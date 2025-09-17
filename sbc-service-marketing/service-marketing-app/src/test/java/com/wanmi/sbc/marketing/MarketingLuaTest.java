package com.wanmi.sbc.marketing;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @author zhanggaolei
 * @className MarketingLuaTest
 * @description
 * @date 2021/6/23 15:09
 **/
@SpringBootTest
@Slf4j
public class MarketingLuaTest {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Resource(name="marketingRedisScript")
    private DefaultRedisScript<String> redisScript;

    @Test
    public void goodsDetail() {
        for (int i = 0; i <50; i++) {
            long start = System.nanoTime();
            String result = redisTemplate.execute(redisScript, Collections.singletonList("goods_info_marketing:2c9384ab79a1b2400179a1cce9380005"),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            log.warn("第{}次redis lua执行时间:{}",i,System.nanoTime()-start);
            log.info(""+result);
            Map<MarketingPluginType , List<GoodsInfoMarketingCacheDTO>> map = new HashMap<>();
            Map<String,String> jsonMap = JSONObject.parseObject(result,Map.class);
            for(Entry<String,String> entry : jsonMap.entrySet()){
                GoodsInfoMarketingCacheDTO cache = new GoodsInfoMarketingCacheDTO();
                cache = JSONObject.parseObject(entry.getValue(), GoodsInfoMarketingCacheDTO.class);
                cache.setMarketingPluginType(MarketingPluginType.fromValue(entry.getKey().split(":")[0]));
                List<GoodsInfoMarketingCacheDTO> temList = map.get(cache.getMarketingPluginType());
                if(temList==null){
                    temList = new ArrayList<>();
                }
                temList.add(cache);
                map.put(cache.getMarketingPluginType(),temList);
            }

            log.info(map.get(MarketingPluginType.DISCOUNT).toString());
        }

    }
}
