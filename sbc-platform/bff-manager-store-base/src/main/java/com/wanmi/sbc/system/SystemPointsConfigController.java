package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

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
    private CommonUtil commonUtil;

    /**
     * 查询积分设置
     *
     * @return
     */
    @Operation(summary = "查询积分设置")
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<SystemPointsConfigQueryResponse> query() {
        BaseResponse<SystemPointsConfigQueryResponse> response = systemPointsConfigQueryProvider.querySystemPointsConfig();
        if(BoolFlag.YES.equals(commonUtil.getCompanyType())){
            response.getContext().setPointsUsageFlag(PointsUsageFlag.ORDER);
        }
        return response;
    }
}
