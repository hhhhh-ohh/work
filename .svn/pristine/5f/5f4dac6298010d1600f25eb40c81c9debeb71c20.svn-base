package com.wanmi.sbc.system.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.setting.bean.enums.SettingRedisKey;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分设置Service
 * Created by dyt on 2017/8/17.
 */
@Slf4j
@Service
public class SystemPointsConfigService {


    @Autowired
    private SystemPointsConfigQueryProvider systemPointsConfigQueryProvider;

    @Autowired
    private RedisUtil redisService;

    /***
     * 从Redis中查询积分设置缓存
     * 因Remark字段过大不适合放入Redis
     * 该接口不再返回Remark字段
     * @return
     */
    public SystemPointsConfigQueryResponse querySettingCache() {
        String key = SettingRedisKey.SYSTEM_POINTS_CONFIG.toValue();
        String val = redisService.getString(key);
        if (StringUtils.isNotBlank(val)) {
            return JSONObject.parseObject(val, SystemPointsConfigQueryResponse.class);
        }
        SystemPointsConfigQueryResponse response = systemPointsConfigQueryProvider.querySystemPointsConfig().getContext();
        response.setRemark(null);
        redisService.setString(key, JSONObject.toJSONString(response));
        return response;
    }

    /***
     * 从Mysql中查询积分设置对象
     * @return
     */
    public BaseResponse<SystemPointsConfigQueryResponse> querySetting() {
        return systemPointsConfigQueryProvider.querySystemPointsConfig();
    }

    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'GoodsPointSettingIsGoodsPoint'")
    public boolean isGoodsPoint(){
        SystemPointsConfigQueryResponse response = querySettingCache();
        return EnableStatus.ENABLE.equals(response.getStatus()) && PointsUsageFlag.GOODS.equals(response.getPointsUsageFlag());
    }

    /**
     * 未开启商品抵扣时，清零buyPoint
     * @param skus
     */
    public void clearBuyPoinsForEsSku(List<EsGoodsInfoVO> skus){
        Long sum = skus.stream().filter(g -> g.getGoodsInfo()!= null && g.getGoodsInfo().getBuyPoint() != null
                && g.getGoodsInfo().getBuyPoint() > 0).map(EsGoodsInfoVO::getGoodsInfo)
                .mapToLong(GoodsInfoNestVO::getBuyPoint).sum();
        if(sum>0 && (!isGoodsPoint())){
            skus.forEach(g -> g.getGoodsInfo().setBuyPoint(0L));
        }
    }
}