package com.wanmi.sbc.mq.distribution;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DeflaterUtil;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.request.distribution.DistributionStoreSettingGetByStoreIdRequest;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.marketing.bean.enums.DistributionLimitType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 获取分销员等级设置信息
     * @return
     */
    public List<DistributorLevelVO> getDistributorLevels() {
        return this.querySettingCache().getDistributorLevels();
    }

    /**
     * 基础邀新奖励限制
     * @return
     */
    public DistributionLimitType getBaseLimitType(){
        return this.querySettingCache().getDistributionSetting().getBaseLimitType();
    }

}
