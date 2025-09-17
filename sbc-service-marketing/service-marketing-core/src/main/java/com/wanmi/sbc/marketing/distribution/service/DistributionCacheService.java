package com.wanmi.sbc.marketing.distribution.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DeflaterUtil;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.marketing.api.response.distribution.MultistageSettingGetResponse;
import com.wanmi.sbc.marketing.bean.vo.DistributionSettingCacheVO;
import com.wanmi.sbc.marketing.cache.MarketingCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午4:35 2019/3/25
 * @Description: 分销缓存服务
 */
@Service
public class DistributionCacheService {

    private static final String STORE_SETTING_KEY = "DIS_STORE_SETTING:";

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private MarketingCacheService marketingCacheService;

    /**
     * 保存分销设置
     */
    public void saveSetting(DistributionSettingGetResponse setting) {
        // 压缩存入redis
        String value = DeflaterUtil.zipString(JSONObject.toJSONString(setting));
        if (!Constants.FAIL.equals(value)){
            redisService.setString(RedisKeyConstant.DIS_SETTING, value);
        }
    }

    /**
     * 保存店铺分销设置
     */
    public void saveStoreSetting(DistributionStoreSettingGetByStoreIdResponse storeSetting) {
        redisService.setString(STORE_SETTING_KEY + storeSetting.getStoreId(),
                JSONObject.toJSONString(storeSetting));
    }

    /**
     * 查询多级分销设置信息
     */
    public MultistageSettingGetResponse getMultistageSetting() {
        DistributionSettingCacheVO setting = marketingCacheService.queryDistributionSetting();
        MultistageSettingGetResponse multistageSetting = new MultistageSettingGetResponse();
        multistageSetting.setCommissionUnhookType(setting.getDistributionSetting().getCommissionUnhookType());
        multistageSetting.setCommissionPriorityType(setting.getDistributionSetting().getCommissionPriorityType());
        multistageSetting.setDistributorLevels(setting.getDistributorLevels());
        return multistageSetting;
    }

}
