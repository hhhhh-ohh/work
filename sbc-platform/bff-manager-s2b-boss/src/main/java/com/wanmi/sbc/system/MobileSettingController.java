package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.MobileSettingProvider;
import com.wanmi.sbc.setting.api.request.AboutUsModifyRequest;
import com.wanmi.sbc.setting.api.request.AppShareSettingModifyRequest;
import com.wanmi.sbc.setting.api.request.AppUpgradeModifyRequest;
import com.wanmi.sbc.setting.api.response.AppShareSettingGetResponse;
import com.wanmi.sbc.setting.api.response.AppUpgradeGetResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午11:36 2018/11/19
 * @Description: 移动端设置
 */
@Tag(name = "MobileSettingController", description = "移动端设置")
@RestController
@Validated
@RequestMapping("/mobile-setting")
public class MobileSettingController {

    @Autowired
    private MobileSettingProvider mobileSettingProvider;

    /**
     * 修改app分享配置
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改app分享配置")
    @RequestMapping(value = "/modify-app-share-setting", method = RequestMethod.PUT)
    public BaseResponse modifyAppShareSetting(@RequestBody @Valid AppShareSettingModifyRequest request) {
        return mobileSettingProvider.modifyAppShareSetting(request);
    }

    /**
     * 查询app分享配置
     *
     * @return
     */
    @Operation(summary = "查询app分享配置")
    @RequestMapping(value = "/get-app-share-setting", method = RequestMethod.GET)
    public BaseResponse<AppShareSettingGetResponse> getAppShareSetting() {
        return mobileSettingProvider.getAppShareSetting();
    }

    /**
     * 修改APP检测升级配置
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改APP检测升级配置")
    @RequestMapping(value = "/modify-app-upgrade-setting", method = RequestMethod.PUT)
    public BaseResponse modifyAppUpgrade(@RequestBody AppUpgradeModifyRequest request) {
        return mobileSettingProvider.modifyAppUpgrade(request);
    }


    /**
     * 查询APP升级版本配置信息
     *
     * @return
     */
    @Operation(summary = "查询APP升级版本配置信息")
    @RequestMapping(value = "/get-app-upgrade-setting", method = RequestMethod.GET)
    public BaseResponse<AppUpgradeGetResponse> getAppUpgrade() {
        return mobileSettingProvider.getAppUpgrade();
    }

    /**
     * 修改关于我们
     *
     * @param aboutUsModifyRequest
     * @return
     */
    @Operation(summary = "修改关于我们")
    @RequestMapping(value = "/modify-about-us", method = RequestMethod.POST)
    public BaseResponse modifyAboutUs(@RequestBody AboutUsModifyRequest aboutUsModifyRequest) {
        return mobileSettingProvider.modifyAboutUs(aboutUsModifyRequest);
    }

    /**
     * 查询关于我们
     *
     * @return
     */
    @Operation(summary = "查询关于我们")
    @RequestMapping(value = "/get-about-us", method = RequestMethod.GET)
    public BaseResponse<String> getAboutUs() {
        return mobileSettingProvider.getAboutUs();
    }

}
