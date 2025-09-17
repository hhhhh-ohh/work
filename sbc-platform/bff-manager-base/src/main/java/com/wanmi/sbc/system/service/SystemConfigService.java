package com.wanmi.sbc.system.service;

import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统设置服务
 */
@Slf4j
@Service
public class SystemConfigService {
    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private RedisUtil redisService;
    /**
     * 线下支付校验，如果选择了线下支付但是开关关闭了，则抛出异常
     *
     * @param payType
     */
    public void validOfflinePayType(PayType payType) {
        // 如果选择的是线下支付
        if (payType == PayType.OFFLINE) {
            Integer status;

            // 先从redis缓存获取
            String cacheStatus = redisService.getString(CacheKeyConstant.OFFLINE_PAY_SETTING);

            if (StringUtils.isBlank(cacheStatus)) {
                // redis缓存获取不到则查库获取
                status = systemConfigQueryProvider.getOfflinePaySetting().getContext().getStatus();
            } else {
                status = Integer.valueOf(cacheStatus);
            }

            if (status == null || status == EnableStatus.DISABLE.toValue()) {
                throw new SbcRuntimeException(SettingErrorCodeEnum.K070019);
            }
        }
    }
}
