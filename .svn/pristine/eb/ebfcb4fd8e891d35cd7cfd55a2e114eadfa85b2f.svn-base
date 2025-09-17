package com.wanmi.sbc.order.common;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.setting.bean.enums.SettingRedisKey;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 积分设置缓存处理服务
 * Created by dyt on 2017/8/17.
 */
@Slf4j
@Service
public class SystemPointsConfigService {


    @Autowired
    private SystemPointsConfigQueryProvider systemPointsConfigQueryProvider;

    @Autowired
    private RedisUtil redisService;

    /**
     * 查询设置缓存
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'GoodsPointSetting'")
    public SystemPointsConfigQueryResponse querySettingCache() {
        String key = SettingRedisKey.SYSTEM_POINTS_CONFIG.toValue();
//        boolean hasKey = redisService.hasKey(key);
        String val = redisService.getString(key);
        if ( StringUtils.isNotBlank(val)) {
            return JSONObject.parseObject(val, SystemPointsConfigQueryResponse.class);
        }
        SystemPointsConfigQueryResponse response =systemPointsConfigQueryProvider.querySystemPointsConfig().getContext();
        response.setRemark(null);
        redisService.setString(key, JSONObject.toJSONString(response));
        return response;
    }

    /**
     * 是否商品积分抵扣
     * @return
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'GoodsPointSettingIsGoodsPoint'")
    public boolean isGoodsPoint(){
        SystemPointsConfigQueryResponse response = querySettingCache();
        return EnableStatus.ENABLE.equals(response.getStatus()) && PointsUsageFlag.GOODS.equals(response.getPointsUsageFlag());
    }
}