package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigListResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.system.response.SystemOrderConfigSimplifyQueryResponse;


import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Objects;


/**
 * WEB端-订单设置
 */
@Tag(name = "SystemOrderConfigController", description = "WEB端-订单设置API")
@RestController
@Validated
@RequestMapping("/orderConfig")
public class SystemOrderConfigController {

    @Autowired private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired private AuditQueryProvider auditQueryProvider;

    /**
     * 查询订单设置-精简版
     *
     * @return
     */
    @Operation(summary = "查询订单设置-精简版")
    @RequestMapping(value = "/simplify", method = RequestMethod.GET)
    public BaseResponse<SystemOrderConfigSimplifyQueryResponse> simplify() {
        SystemOrderConfigSimplifyQueryResponse response = new SystemOrderConfigSimplifyQueryResponse();
        TradeConfigListResponse configResponse = auditQueryProvider.cachedListTradeConfig().getContext();
        if (Objects.nonNull(configResponse) && CollectionUtils.isNotEmpty(configResponse.getConfigVOList())) {
            for (ConfigVO configVO : configResponse.getConfigVOList()) {
                String configType = configVO.getConfigType();
                // 目前仅处理 "买家自助修改收货地址开关"，后续需要其他字段，可以继续追加扩展
                if (Objects.equals(configType, ConfigType.ORDER_SETTING_BUYER_MODIFY_CONSIGNEE.toValue())) {
                    // 买家自助修改收货地址开关
                    response.setBuyerModifyConsigneeFlag(configVO.getStatus());
                }
            }
        }
        return BaseResponse.success(response);
    }

    @Operation(summary = "获取快速下单开关")
    @GetMapping("/quickOrder")
    public BaseResponse<SystemConfigTypeResponse> get(){
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_QUICK_ORDER.toValue());
        return systemConfigQueryProvider.findByConfigTypeAndDelFlag(request);
    }

}
