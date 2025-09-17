package com.wanmi.sbc.umengpushconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.apppush.AppPushSettingProvider;
import com.wanmi.sbc.empower.api.provider.apppush.AppPushSettingQueryProvider;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingAddRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingByIdRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingAddResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingByIdResponse;
import com.wanmi.sbc.empower.bean.enums.AppPushPlatformType;
import com.wanmi.sbc.setting.api.response.umengpushconfig.UmengPushConfigAddResponse;
import com.wanmi.sbc.setting.api.response.umengpushconfig.UmengPushConfigByIdResponse;
import com.wanmi.sbc.setting.bean.vo.UmengPushConfigVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Tag(name =  "友盟push接口配置管理API", description =  "UmengPushConfigController")
@RestController
@Validated
@RequestMapping(value = "/umengpushconfig")
public class UmengPushConfigController {

    @Autowired
    private AppPushSettingQueryProvider appPushSettingQueryProvider;

    @Autowired
    private AppPushSettingProvider appPushSettingProvider;

    @Operation(summary = "查询友盟push接口配置")
    @GetMapping("/getConfig")
    public BaseResponse<UmengPushConfigByIdResponse> getConfig() {
        AppPushSettingByIdRequest idReq = new AppPushSettingByIdRequest();
        idReq.setId(-1);
        BaseResponse<AppPushSettingByIdResponse> response = appPushSettingQueryProvider.getById(idReq);
        UmengPushConfigVO umengPushConfigVO =
                KsBeanUtil.copyPropertiesThird(response.getContext().getAppPushSettingVO(), UmengPushConfigVO.class);
        return BaseResponse.success(UmengPushConfigByIdResponse.builder().umengPushConfigVO(umengPushConfigVO).build());
    }

    @Operation(summary = "新增、修改友盟push接口配置")
    @PostMapping("/add")
    public BaseResponse<UmengPushConfigAddResponse> add(@RequestBody @Valid AppPushSettingAddRequest addReq) {
        if(addReq.getPlatformType() == null) {
            addReq.setPlatformType(AppPushPlatformType.UMENG);
        }
        BaseResponse<AppPushSettingAddResponse> response = appPushSettingProvider.add(addReq);
        UmengPushConfigVO umengPushConfigVO =
                KsBeanUtil.copyPropertiesThird(response.getContext().getAppPushSettingVO(), UmengPushConfigVO.class);
        return BaseResponse.success(UmengPushConfigAddResponse.builder().umengPushConfigVO(umengPushConfigVO).build());
    }
}
