package com.wanmi.sbc.third.login;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.wechatloginset.WechatLoginSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.wechatloginset.WechatLoginSetSaveProvider;
import com.wanmi.sbc.empower.api.request.wechatloginset.WechatLoginSetAddRequest;
import com.wanmi.sbc.empower.api.response.wechatloginset.WechatLoginSetResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Tag(name = "WechatLoginController", description = "微信授信登录")
@RestController
@Validated
@RequestMapping("/third/login/wechat")
public class WechatLoginController {

    @Autowired
    private WechatLoginSetQueryProvider wechatLoginSetQueryProvider;

    @Autowired
    private WechatLoginSetSaveProvider wechatLoginSetSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    /**
     * 获取微信授信配置
     *
     * @return
     */
    @Operation(summary = "获取微信授信配置")
    @RequestMapping(value = "/set/detail", method = RequestMethod.GET)
    public BaseResponse<WechatLoginSetResponse> getWechatLoginSetDetail() {
        return wechatLoginSetQueryProvider.getInfo();
    }


    /**
     * 保存授信配置
     *
     * @param request
     * @return
     */
    @Operation(summary = "保存授信配置")
    @RequestMapping(value = "/set", method = RequestMethod.PUT)
    public BaseResponse saveWechatLoginSet(@RequestBody WechatLoginSetAddRequest request) {
        if (request.getAppServerStatus() == null || request.getMobileServerStatus() == null || request.getPcServerStatus() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        } else {
            if (Objects.equals(DefaultFlag.YES, request.getMobileServerStatus())) {
                if (request.getMobileAppId() == null || request.getMobileAppSecret() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
            if (Objects.equals(DefaultFlag.YES, request.getPcServerStatus())) {
                if (request.getPcAppId() == null || request.getPcAppSecret() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
            if (request.getMobileAppId() != null && request.getMobileAppId().length() > Constants.NUM_50) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (request.getMobileAppSecret() != null && request.getMobileAppSecret().length() > Constants.NUM_50) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (request.getPcAppId() != null && request.getPcAppId().length() > Constants.NUM_50) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (request.getPcAppSecret() != null && request.getPcAppSecret().length() > Constants.NUM_50) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

        }

        request.setOperatePerson(commonUtil.getOperatorId());
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);

        operateLogMQUtil.convertAndSend("设置", "编辑登录接口", "编辑登录接口");

        return wechatLoginSetSaveProvider.add(request);
    }
}
