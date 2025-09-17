package com.wanmi.sbc.distribute;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSimSettingResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * 分销设置controller
 *
 * @Author: liutao
 * @Date: Created In 下午1:44 2019/4/22
 * @Description:
 */
@Tag(name =  "分销设置服务" ,description = "DistributionSettingController")
@RestController
@Validated
@RequestMapping("/distribution-setting")
public class DistributionSettingController {

    @Autowired
    private DistributionSettingQueryProvider distributionSettingQueryProvider;

    /**
     * 查询分销设置API
     *
     * @return
     */
    @Operation(summary = "查询分销设置")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'distributionSetting'")
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<DistributionSimSettingResponse> findOne() {
        return distributionSettingQueryProvider.findOneSetting();
    }
}
