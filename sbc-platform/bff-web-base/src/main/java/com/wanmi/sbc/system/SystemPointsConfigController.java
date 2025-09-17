package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.system.response.SystemPointsConfigSimplifyQueryResponse;
import com.wanmi.sbc.system.service.SystemPointsConfigService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;


/**
 * WEB端-积分设置
 */
@Tag(name = "SystemPointsConfigController", description = "WEB端-积分设置API")
@RestController
@Validated
@RequestMapping("/pointsConfig")
public class SystemPointsConfigController {

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    /**
     * 查询积分设置
     * @return  Redis中缓存的积分规则
     */
    @Operation(summary = "查询积分设置，不返回积分规则(缓存)")
    @GetMapping
    public BaseResponse<SystemPointsConfigQueryResponse> queryWithCache() {
        return BaseResponse.success(this.querySettingCache());
    }

    /***
     * 查询积分设置
     * @return  MySQL中的积分设置
     */
    @Operation(summary = "查询数据库中的积分设置")
    @GetMapping("/queryWithDb")
    public BaseResponse<SystemPointsConfigQueryResponse> query() {
        return systemPointsConfigService.querySetting();
    }

    /**
     * 查询积分设置-精简版
     *
     * @return
     */
    @Operation(summary = "查询积分设置-精简版")
    @RequestMapping(value = "/simplify",method = RequestMethod.GET)
    public BaseResponse<SystemPointsConfigSimplifyQueryResponse> simplify() {
        SystemPointsConfigQueryResponse response = this.querySettingCache();
        SystemPointsConfigSimplifyQueryResponse simplifyQueryResponse = SystemPointsConfigSimplifyQueryResponse.builder()
                .maxDeductionRate(response.getMaxDeductionRate()).overPointsAvailable(response.getOverPointsAvailable())
                .pointsUsageFlag(response.getPointsUsageFlag()).pointsWorth(response.getPointsWorth())
                .status(response.getStatus())
                .build();
        return BaseResponse.success(simplifyQueryResponse);
    }

    /**
     * 查询积分设置
     * @return
     */
    private SystemPointsConfigQueryResponse querySettingCache(){
        return systemPointsConfigService.querySettingCache();
    }

}
