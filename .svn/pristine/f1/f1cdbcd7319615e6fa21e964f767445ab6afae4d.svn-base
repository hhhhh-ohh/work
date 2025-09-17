package com.wanmi.sbc.vas.iep;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.iep.iepsetting.IepSettingQueryProvider;
import com.wanmi.sbc.vas.api.response.iep.iepsetting.IepSettingTopOneResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * @Description: 企业购设置
 */
@Tag(name = "IepSettingQueryController", description = "增值服务-企业购设置查询 API")
@RestController
@Validated
@RequestMapping("/vas/iep/setting")
public class IepSettingQueryController {


    @Autowired
    private IepSettingQueryProvider iepSettingQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "查询企业购设置信息")
    @GetMapping("/detail")
    public BaseResponse<IepSettingTopOneResponse> findTopOne() {
        return iepSettingQueryProvider.findTopOne();
    }

    @Operation(summary = "查询缓存中的企业购设置信息提供给用户")
    @GetMapping("/cache")
    public BaseResponse<IepSettingTopOneResponse> findCacheForCustomer() {
        // 首先判断是否购买企业购增值服务,未购买或未设置自动抛出异常
        return BaseResponse.success(new IepSettingTopOneResponse(commonUtil.getIepSettingInfo()));

    }
}
