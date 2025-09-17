package com.wanmi.sbc.distribution;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DeflaterUtil;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.request.distribution.DistributionStoreSettingGetByStoreIdRequest;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.marketing.bean.enums.DistributionLimitType;
import com.wanmi.sbc.marketing.bean.enums.RewardCashType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;

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
    public DistributionSettingGetResponse querySettingCache() {
        String val = redisService.getString(RedisKeyConstant.DIS_SETTING);
        if ( StringUtils.isNotBlank(val)) {
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
     * 查询店铺是否开启社交分销
     */
    public DefaultFlag queryStoreOpenFlag(String storeId) {
        DistributionStoreSettingGetByStoreIdResponse storeSettingGetByStoreIdResponse = this.queryStoreSettingCache(storeId);
        return null == storeSettingGetByStoreIdResponse ? DefaultFlag.NO : storeSettingGetByStoreIdResponse.getOpenFlag();
    }

    /**
     * 查询是否开启奖励现金开关
     * @return
     */
    public DefaultFlag getRewardCashFlag() {
        return this.querySettingCache().getDistributionSetting().getRewardCashFlag();
    }

    /**
     * 查询是否开启奖励优惠券
     * @return
     */
    public DefaultFlag getRewardCouponFlag() {
        return this.querySettingCache().getDistributionSetting().getRewardCouponFlag();
    }

    /**
     * 奖励上限类型设置
     * @return
     */
    public RewardCashType queryRewardCashType() {
        return this.querySettingCache().getDistributionSetting().getRewardCashType();
    }

    /**
     * 查询奖励优惠券上限(组数)
     * @return
     */
    public Integer getRewardCouponCount() {
        return this.querySettingCache().getDistributionSetting().getRewardCouponCount();
    }

    /**
     * 查询邀新奖励人数上限
     * @return
     */
    public Integer queryRewardCashCount() {
        return this.querySettingCache().getDistributionSetting().getRewardCashCount();
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

    /**
     * 查询优惠券信息
     * @return
     */
    public List<CouponInfoVO> getCouponInfos() {
        return this.querySettingCache().getCouponInfos();
    }

}
