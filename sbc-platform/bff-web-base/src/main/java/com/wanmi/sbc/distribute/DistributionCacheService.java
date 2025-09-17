package com.wanmi.sbc.distribute;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DeflaterUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.distribute.dto.InviteRegisterDTO;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsListByIdsResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.request.distribution.DistributionStoreSettingGetByStoreIdRequest;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSetting4StoreBagsResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.marketing.bean.enums.DistributionLimitType;
import com.wanmi.sbc.marketing.bean.enums.RecruitApplyType;
import com.wanmi.sbc.marketing.bean.enums.RegisterLimitType;
import com.wanmi.sbc.marketing.bean.enums.RewardCashType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.DistributionRewardCouponVO;
import com.wanmi.sbc.marketing.bean.vo.DistributionSettingSimVO;
import com.wanmi.sbc.marketing.bean.vo.DistributionSettingVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;

import jakarta.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午11:32 2019/3/5
 * @Description: 分销设置缓存服务
 */
@Slf4j
@Service
public class DistributionCacheService {

    private static final String STORE_SETTING_KEY = "DIS_STORE_SETTING:";

    @Autowired
    private DistributionSettingQueryProvider distributionSettingQueryProvider;
    @Resource
    private AuditQueryProvider auditQueryProvider;
    @Resource
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private RedisUtil redisService;

    /**
     * 查询设置缓存
     */
    private DistributionSettingGetResponse querySettingCache() {
        String disSetting = redisService.getString(RedisKeyConstant.DIS_SETTING);
        if (StringUtils.isNotBlank(disSetting)) {
            return JSONObject.parseObject(DeflaterUtil.unzipString(disSetting),
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
     * 查询是否开启社交分销
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'DistributionSettingOpenFlag'")
    public DefaultFlag queryOpenFlag() {
        return this.querySettingCache().getDistributionSetting().getOpenFlag();
    }

    /**
     * 查询是否开启邀新
     *
     * @return
     */
    public DefaultFlag queryInviteOpenFlag() {
        return this.querySettingCache().getDistributionSetting().getInviteOpenFlag();
    }

    /**
     * 查询是否开启邀新奖励
     *
     * @return
     */
    public DefaultFlag queryInviteFlag() {
        return this.querySettingCache().getDistributionSetting().getInviteFlag();
    }

    /**
     * 查询是否开启邀新奖励限制
     *
     * @return
     */
    public DistributionLimitType queryRewardLimitType() {
        return this.querySettingCache().getDistributionSetting().getRewardLimitType();
    }

    /**
     * 查询邀新奖励金额
     *
     * @return
     */
    public BigDecimal queryRewardCash() {
        return this.querySettingCache().getDistributionSetting().getRewardCash();
    }

    public RewardCashType queryRewardCashType() {
        return this.querySettingCache().getDistributionSetting().getRewardCashType();
    }

    /**
     * 查询邀新奖励人数上限
     *
     * @return
     */
    public Integer queryRewardCashCount() {
        return this.querySettingCache().getDistributionSetting().getRewardCashCount();
    }

    /**
     * 查询分销配置
     *
     * @return
     */
    public DistributionSettingVO queryDistributionSetting() {
        return this.querySettingCache().getDistributionSetting();
    }

    /**
     * 查询店铺是否开启社交分销
     */
    public DefaultFlag queryStoreOpenFlag(String storeId) {
        DistributionStoreSettingGetByStoreIdResponse storeSettingGetByStoreIdResponse = this.queryStoreSettingCache(storeId);
        return null == storeSettingGetByStoreIdResponse ? DefaultFlag.NO : storeSettingGetByStoreIdResponse.getOpenFlag();
    }

    /**
     * 查询平台端-基础分销设置- 小店名称
     *
     * @return
     */
    public String getShopName() {
        return this.querySettingCache().getDistributionSetting().getShopName();
    }

    /**
     * 查询开店礼包
     *
     * @return
     */
    public DistributionSetting4StoreBagsResponse storeBags() {
        // 从缓存中获取分销设置
        DistributionSettingGetResponse distributionSettingGetResponse = this.querySettingCache();
        // Modify by zhengyang for task 【ID1036270】
        removeOutOfStockGoods(distributionSettingGetResponse);


        DistributionSetting4StoreBagsResponse storeBagsResponse = new DistributionSetting4StoreBagsResponse();
        KsBeanUtil.copyPropertiesThird(distributionSettingGetResponse.getDistributionSetting(), storeBagsResponse);
        if (DefaultFlag.YES == distributionSettingGetResponse.getDistributionSetting().getOpenFlag()
                && RecruitApplyType.BUY == distributionSettingGetResponse.getDistributionSetting().getApplyType()) {
            KsBeanUtil.copyPropertiesThird(distributionSettingGetResponse, storeBagsResponse);

        }
        return storeBagsResponse;
    }

    /**
     * 查询邀请注册信息
     */
    public InviteRegisterDTO getInviteRegisterDTO() {
        DistributionSettingVO setting = this.querySettingCache().getDistributionSetting();
        InviteRegisterDTO inviteRegister = new InviteRegisterDTO();
        if (DefaultFlag.YES.equals(setting.getOpenFlag())
                && DefaultFlag.YES.equals(setting.getApplyFlag())
                && RecruitApplyType.REGISTER.equals(setting.getApplyType())) {
            // 开启了邀请注册
            inviteRegister.setEnableFlag(DefaultFlag.YES);
            inviteRegister.setLimitType(setting.getLimitType());
            inviteRegister.setInviteCount(setting.getInviteCount());
        } else {
            // 没有开启邀请注册
            inviteRegister.setEnableFlag(DefaultFlag.NO);
        }
        return inviteRegister;
    }

    /**
     * 获取简单的分销设置信息
     *
     * @return
     */
    public DistributionSettingSimVO getSimDistributionSetting() {
        return KsBeanUtil.convert(queryDistributionSetting(), DistributionSettingSimVO.class);
    }

    /**
     * 查询开店礼包商品
     *
     * @return
     */
    public List<GoodsInfoVO> queryStoreBags() {
        return this.querySettingCache().getGoodsInfos();
    }

    /**
     * 查询是否开启奖励现金开关
     *
     * @return
     */
    public DefaultFlag getRewardCashFlag() {
        return this.querySettingCache().getDistributionSetting().getRewardCashFlag();
    }

    /**
     * 查询是否开启奖励优惠券
     *
     * @return
     */
    public DefaultFlag getRewardCouponFlag() {
        return this.querySettingCache().getDistributionSetting().getRewardCouponFlag();
    }

    /**
     * 查询奖励优惠券上限(组数)
     *
     * @return
     */
    public Integer getRewardCouponCount() {
        return this.querySettingCache().getDistributionSetting().getRewardCouponCount();
    }

    /**
     * 查询优惠券信息
     *
     * @return
     */
    public List<CouponInfoVO> getCouponInfos() {
        return this.querySettingCache().getCouponInfos();
    }


    /**
     * 查询优惠券组数信息
     *
     * @return
     */
    public List<DistributionRewardCouponVO> getCouponInfoCounts() {
        return this.querySettingCache().getCouponInfoCounts();
    }

    /**
     * 获取注册限制
     *
     * @return
     */
    public RegisterLimitType getRegisterLimitType() {
        return this.querySettingCache().getDistributionSetting().getRegisterLimitType();
    }

    /**
     * 获取分销员等级设置信息
     *
     * @return
     */
    public List<DistributorLevelVO> getDistributorLevels() {
        return this.querySettingCache().getDistributorLevels();
    }

    /**
     * 基础邀新奖励限制
     *
     * @return
     */
    public DistributionLimitType getBaseLimitType() {
        return this.querySettingCache().getDistributionSetting().getBaseLimitType();
    }


    /****
     * 从商品列表中移除所有无货商品
     * @param distributionSettingGetResponse
     */
    private void removeOutOfStockGoods(DistributionSettingGetResponse distributionSettingGetResponse) {
        // 判断BOSS是否配置了无货商品不展示
        if (auditQueryProvider.isGoodsOutOfStockShow().getContext().isOutOfStockShow()) {
            // 开店礼包随分销设置整体放在Redis中，所以无法取得实时库存，只能根据数据库中库存过滤返回值
            if (Objects.nonNull(distributionSettingGetResponse)
                    && !CollectionUtils.isEmpty(distributionSettingGetResponse.getGoodsInfos())) {
                // 迭代GoodsInfo中的Id查询商品列表，GoodsInfoFlag设为No，减少查询成本
                BaseResponse<GoodsListByIdsResponse> goodsListRes = goodsQueryProvider.listByIds(GoodsListByIdsRequest.builder()
                        .goodsIds(distributionSettingGetResponse.getGoodsInfos().stream()
                                .map(GoodsInfoVO::getGoodsId).collect(Collectors.toList()))
                        .getGoodsInfoFlag(BoolFlag.NO).build());
                // 判断返回值是否正确
                if (Objects.nonNull(goodsListRes)
                        && Objects.nonNull(goodsListRes.getContext())
                        && !CollectionUtils.isEmpty(goodsListRes.getContext().getGoodsVOList())) {
                    // 将返回值转为GOODID-库存Map
                    Map<String, Long> goodStockMap = goodsListRes.getContext()
                            .getGoodsVOList().stream().collect(Collectors.toMap(GoodsVO::getGoodsId, GoodsVO::getStock));
                    // 循环列表剔除没有库存的商品
                    distributionSettingGetResponse.setGoodsInfos(distributionSettingGetResponse
                            .getGoodsInfos().stream().filter(info -> goodStockMap.containsKey(info.getGoodsId()) && goodStockMap.get(info.getGoodsId()) > 0L)
                            .collect(Collectors.toList()));

                } else {
                    if (log.isWarnEnabled()) {
                        log.warn("storeBags called goodsQueryProvider.listByIds result is empty! params is {} ",
                                JSON.toJSONString(distributionSettingGetResponse.getGoodsInfos()));
                    }
                }

            }
        } }
}
