package com.wanmi.sbc.marketing.newplugin.impl.coupon;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wur
 * @className CouponPluginContext
 * @description
 * @date 2022/9/28 16:13
 **/
@Component
@Slf4j
public class CouponPluginContext implements InitializingBean {

    @Autowired(required = false)
    private List<MarketingCouponPluginInterface> marketingCouponPluginList;

    private Map<CouponMarketingType, MarketingCouponPluginInterface> marketingCouponPluginMap;

    /**
     * @description   初始化处理类
     * @author  wur
     * @date: 2022/9/28 16:20
     * @return
     **/
    @Override
    public void afterPropertiesSet() {
        if (CollectionUtils.isEmpty(marketingCouponPluginList)) {
            return;
        }
        marketingCouponPluginMap = new HashMap<>();
        for(MarketingCouponPluginInterface pluginService : marketingCouponPluginList) {
            if (!marketingCouponPluginMap.containsKey(pluginService.getCouponMarketingType())) {
                marketingCouponPluginMap.put(pluginService.getCouponMarketingType(), pluginService);
            }
        }
    }

    /**
     * @description   根据券的类型获取具体的处理类
     * @author  wur
     * @date: 2022/9/28 16:21
     * @param couponMarketingType
     * @return
     **/
    public MarketingCouponPluginInterface getCouponService(CouponMarketingType couponMarketingType) {
        if (Objects.isNull(couponMarketingType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (!marketingCouponPluginMap.containsKey(couponMarketingType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return marketingCouponPluginMap.get(couponMarketingType);
    }

}