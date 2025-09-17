package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.SystemGrowthValueConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.SystemGrowthValueOpenResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * 成长值Controller
 * Created by yang on 2019/03/07.
 */
@Tag(name = "SystemGrowthValueConfigController", description = "成长值开关查询 Api")
@RestController
@Validated
@RequestMapping("/growthValue")
public class SystemGrowthValueConfigController {

    @Autowired
    private SystemGrowthValueConfigQueryProvider systemGrowthValueConfigQueryProvider;

    /**
     * 查询成长值是否开启
     *
     * @return
     */
    @Operation(summary = "查询成长值是否开启")
    @RequestMapping(value = "/isOpen", method = RequestMethod.GET)
    public BaseResponse<SystemGrowthValueOpenResponse> isGrowthValueOpen() {
        SystemGrowthValueOpenResponse growthValueOpenResponse = systemGrowthValueConfigQueryProvider.isGrowthValueOpen().getContext();
        return BaseResponse.success(growthValueOpenResponse);
    }
}
