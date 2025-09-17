package com.wanmi.sbc.marketing.cache;

import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.marketing.bean.vo.DistributionSettingCacheVO;
import com.wanmi.sbc.marketing.distribution.service.DistributionSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class MarketingCacheService {


    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired
    private DistributionSettingService distributionSettingService;


    /**
     * 查询分销设置
     */
    @Cacheable(value = WmCacheConfig.MARKETING, key = "'dis_setting'")
    public DistributionSettingCacheVO queryDistributionSetting() {
        DistributionSettingGetResponse response = distributionSettingService.querySetting(Boolean.FALSE);
        return DistributionSettingCacheVO.builder()
                .distributionSetting(response.getDistributionSetting())
                .distributorLevels(response.getDistributorLevels())
                .build();
    }
}
