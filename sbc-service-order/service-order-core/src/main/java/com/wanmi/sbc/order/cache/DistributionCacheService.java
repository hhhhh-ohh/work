package com.wanmi.sbc.order.cache;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DeflaterUtil;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.request.distribution.DistributionStoreSettingGetByStoreIdRequest;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.marketing.bean.enums.RecruitApplyType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午11:32 2019/3/5
 * @Description: 分销设置缓存服务
 */
@Service
public class DistributionCacheService {

    private static final String STORE_SETTING_KEY = "DIS_STORE_SETTING:";

    @Autowired
    private DistributionSettingQueryProvider distributionSettingQueryProvider;

    @Autowired
    private RedisUtil redisService;

    /**
     * 查询设置缓存
     */
    private DistributionSettingGetResponse querySettingCache() {
//        boolean hasKey = redisService.hasKey(RedisKeyConstant.DIS_SETTING);
        String val = redisService.getString(RedisKeyConstant.DIS_SETTING);
        if (StringUtils.isNotBlank(val)) {
            return JSONObject.parseObject(DeflaterUtil.unzipString(val),
                    DistributionSettingGetResponse.class);
        } else {
            DistributionSettingGetResponse setting = distributionSettingQueryProvider.getSetting().getContext();
            // 压缩存入redis
            String value = DeflaterUtil.zipString(JSONObject.toJSONString(setting));
            if (!Constants.FAIL.equals(value)){
                redisService.setString(RedisKeyConstant.DIS_SETTING, value);
            }
            return setting;
        }
    }

    /**
     * 查询店铺设置缓存
     */
    private DistributionStoreSettingGetByStoreIdResponse queryStoreSettingCache(String storeId) {
        String key = STORE_SETTING_KEY + storeId;
//        boolean hasKey = redisService.hasKey(key);
        String val = redisService.getString(key);
        if (StringUtils.isNotBlank(val)) {
            return JSONObject.parseObject(val, DistributionStoreSettingGetByStoreIdResponse.class);
        } else {
            DistributionStoreSettingGetByStoreIdResponse setting = distributionSettingQueryProvider.getStoreSettingByStoreId(
                    new DistributionStoreSettingGetByStoreIdRequest(storeId)).getContext();
            redisService.setString(key, JSONObject.toJSONString(setting));
            return setting;
        }
    }

    /**
     * 查询店铺设置缓存
     */
    public Map<Long,DefaultFlag> queryStoreListOpenFlag(List<Long> storeIds) {
        Map<Long,DefaultFlag> retMap = new HashMap<>();
        storeIds.forEach( s -> {
            String storeId = s.toString();
            retMap.put(s,queryStoreOpenFlag(storeId));
        });
        return retMap;
    }

    /**
     * 查询是否开启社交分销
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'DistributionSettingOpenFlag'")
    public DefaultFlag queryOpenFlag() {
        return this.querySettingCache().getDistributionSetting().getOpenFlag();
    }

    /**
     * 查询店铺是否开启社交分销
     */
    public DefaultFlag queryStoreOpenFlag(String storeId) {
        DistributionStoreSettingGetByStoreIdResponse storeSettingGetByStoreIdResponse=this.queryStoreSettingCache(storeId);
        return null==storeSettingGetByStoreIdResponse?DefaultFlag.NO:storeSettingGetByStoreIdResponse.getOpenFlag();
    }

    /**
     * 查询平台端-基础分销设置- 升级方式 0：购买礼包  1：邀新注册
     * @return
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'DistributionSettingApplyType'")
    public RecruitApplyType getApplyType() {
        return this.querySettingCache().getDistributionSetting().getApplyType();
    }


    /**
     * 查询开店礼包商品
     * @return
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'DistributionSettingStoreBags'")
    public List<GoodsInfoVO> queryStoreBags() {
        return this.querySettingCache().getGoodsInfos();
    }

    /**
     * 查询平台端-基础分销设置- 小店名称
     *
     * @return
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'DistributionSettingShopName'")
    public String getShopName() {
        return this.querySettingCache().getDistributionSetting().getShopName();
    }

}
