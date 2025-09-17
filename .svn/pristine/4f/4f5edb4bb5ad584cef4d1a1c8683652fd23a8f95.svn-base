package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.systemconfig.OfflinePaySettingResponse;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;


@Tag(name =  "支付设置管理API", description =  "PaySettingConfigController")
@RestController
@Validated
@RequestMapping(value = "/sysconfig/paysetting")
public class PaySettingConfigController {

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private RedisUtil redisService;

    /**
     * 查询线下支付是否开启（先查REDIS缓存，没有再查数据库）
     *
     * @return
     */
    @Operation(summary = "查询线下支付是否开启")
    @GetMapping(value = "/get-offline-pay-setting")
    public BaseResponse<OfflinePaySettingResponse> getOfflinePaySetting() {
        String cacheStatus = redisService.getString(CacheKeyConstant.OFFLINE_PAY_SETTING);

        Integer status;

        if (StringUtils.isBlank(cacheStatus)) {
            OfflinePaySettingResponse response = systemConfigQueryProvider.getOfflinePaySetting().getContext();

            if (response.getStatus() != null) {
                status = response.getStatus();
            } else {
                status = EnableStatus.DISABLE.toValue();
            }
        } else {
            status = Integer.valueOf(cacheStatus);
        }

        return BaseResponse.success(OfflinePaySettingResponse.builder().status(status).build());
    }

}
