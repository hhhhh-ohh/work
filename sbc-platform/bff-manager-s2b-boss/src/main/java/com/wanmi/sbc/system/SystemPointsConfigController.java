package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigProvider;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.SystemPointsConfigModifyRequest;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * 平台端-积分设置
 */
@Tag(name = "SystemPointsConfigController", description = "平台端-积分设置API")
@RestController
@Validated
@RequestMapping("/boss/pointsConfig")
public class SystemPointsConfigController {

    @Autowired
    private SystemPointsConfigQueryProvider systemPointsConfigQueryProvider;

    @Autowired
    private SystemPointsConfigProvider systemPointsConfigProvider;

    /**
     * 查询积分设置
     *
     * @return
     */
    @Operation(summary = "查询积分设置")
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<SystemPointsConfigQueryResponse> query() {
        return systemPointsConfigQueryProvider.querySystemPointsConfig();
    }

    /**
     * 更新积分设置
     *
     * @return BaseResponse
     */
    @Operation(summary = "更新积分设置")
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse modify(@Valid @RequestBody SystemPointsConfigModifyRequest request) {
        return systemPointsConfigProvider.modifySystemPointsConfig(request);
    }

}
