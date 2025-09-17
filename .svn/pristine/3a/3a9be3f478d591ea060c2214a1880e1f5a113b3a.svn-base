package com.wanmi.sbc.setting.provider.impl.thirdplatformconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.setting.api.provider.thirdplatformconfig.ThirdPlatformConfigProvider;
import com.wanmi.sbc.setting.api.request.thirdplatformconfig.ThirdPlatformConfigModifyRequest;
import com.wanmi.sbc.setting.thirdplatformconfig.service.ThirdPlatformConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 第三方平台配置查询服务接口实现
 *
 * @author bob
 * @date 2020-01-07 10:34:04
 */
@RestController
@Validated
public class ThirdPlatformConfigController implements ThirdPlatformConfigProvider {
    @Autowired private ThirdPlatformConfigService thirdPlatformConfigService;

    @Override
    public BaseResponse modify(@RequestBody @Valid ThirdPlatformConfigModifyRequest request) {
        request.checkParam();
        // linked-Mall配置
        if (ThirdPlatformType.LINKED_MALL.toValue() == request.getThirdPlatformType()) {
            thirdPlatformConfigService.modifyByLinkedMall(request);
        }
        return BaseResponse.SUCCESSFUL();
    }
}
