package com.wanmi.ares.report.base.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Objects;

/***
 * Ares中系统配置查询Service
 * 封装从Settting查询系统配置的操作
 * @className AresSystemConfigQueryService
 * @author zhengyang
 * @date 2021/10/18 15:11
 **/
@Service
public class AresSystemConfigQueryService {

    @Resource
    private SystemConfigQueryProvider systemConfigQueryProvider;

    /***
     * 查询O2O是否购买
     * @return
     */
    public Boolean queryO2oOpening(){
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.VAS_O2O_SETTING.toValue());
        ConfigVO config = BaseResUtils.getResultFromRes(systemConfigQueryProvider.findByConfigTypeAndDelFlag(request),
                SystemConfigTypeResponse::getConfig);
        return (Objects.nonNull(config) && Objects.nonNull(config.getStatus())
                && BoolFlag.YES.toValue() == config.getStatus());
    }
}
