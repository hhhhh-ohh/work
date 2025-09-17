package com.wanmi.sbc.third;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingProvider;
import com.wanmi.sbc.empower.api.provider.wechatloginset.WechatLoginSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.wechatloginset.WechatLoginSetSaveProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetModifyRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.PayGatewaySaveByTerminalTypeRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.WechatConfigSaveRequest;
import com.wanmi.sbc.empower.api.request.wechatloginset.WechatLoginSetAddRequest;
import com.wanmi.sbc.empower.api.response.wechatloginset.WechatLoginSetResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.setting.api.request.wechat.WechatSetRequest;
import com.wanmi.sbc.setting.api.response.wechat.WechatSetResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Arrays;

@Tag(name = "WechatSetController", description = "微信设置服务")
@RestController
@Validated
@RequestMapping("/third/wechatSet")
public class WechatSetController {

    @Autowired
    private WechatLoginSetSaveProvider wechatLoginSetSaveProvider;

    @Autowired
    private WechatLoginSetQueryProvider wechatLoginSetQueryProvider;

    @Autowired
    private PaySettingProvider paySettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private WechatSetService wechatSetService;

    @Autowired
    private MiniProgramSetProvider miniProgramSetProvider;

    /**
     * 获取微信授信配置
     *
     * @return
     */
    @Operation(summary = "获取微信设置")
    @GetMapping
    public BaseResponse<WechatSetResponse> get() {
        return BaseResponse.success(wechatSetService.get(Arrays.asList(TerminalType.PC, TerminalType.H5, TerminalType.MINI, TerminalType.APP)));
    }

    /**
     * 保存微信授信配置
     *
     * @return
     */
    @Operation(summary = "保存微信设置")
    @GlobalTransactional
    @MultiSubmit
    @PutMapping
    public BaseResponse set(@RequestBody @Valid WechatSetRequest request) {
        Long storeId = Constants.BOSS_DEFAULT_STORE_ID;
        if(TerminalType.PC.equals(request.getTerminalType())){
            WechatLoginSetAddRequest addRequest = new WechatLoginSetAddRequest();
            WechatLoginSetResponse setResponse = wechatLoginSetQueryProvider.getInfo().getContext();
            KsBeanUtil.copyPropertiesThird(setResponse, addRequest);
            addRequest.setPcAppId(request.getAppId());
            addRequest.setPcAppSecret(request.getSecret());
            addRequest.setPcServerStatus(request.getStatus());
            addRequest.setOperatePerson(commonUtil.getOperatorId());
            addRequest.setStoreId(storeId);
            wechatLoginSetSaveProvider.add(addRequest);
            operateLogMQUtil.convertAndSend("设置", "微信设置", "编辑微信PC接口");
        }else if(TerminalType.H5.equals(request.getTerminalType())){
            // 暂时保存 以防部分没有排查处的业务使用
            PayGatewaySaveByTerminalTypeRequest saveRequest = new PayGatewaySaveByTerminalTypeRequest();
            saveRequest.setStoreId(storeId);
            saveRequest.setAppId(request.getAppId());
            saveRequest.setSecret(request.getSecret());
            saveRequest.setTerminalType(com.wanmi.sbc.empower.bean.enums.TerminalType.H5);
            saveRequest.setPayGatewayEnum(PayGatewayEnum.WECHAT);
            paySettingProvider.savePayGatewayByTerminalType(saveRequest);

            //更新H5微信设置（0：扫码 ，H5，微信网页-JSAPI）
            WechatConfigSaveRequest saveWechatRequest = new WechatConfigSaveRequest();
            saveWechatRequest.setStoreId(storeId);
            saveWechatRequest.setAppId(request.getAppId());
            saveWechatRequest.setSecret(request.getSecret());
            saveWechatRequest.setSceneType(NumberUtils.INTEGER_ZERO);
            paySettingProvider.saveWechatConfig(saveWechatRequest);
            //同步LoginSet
            WechatLoginSetAddRequest addRequest = new WechatLoginSetAddRequest();
            WechatLoginSetResponse setResponse = wechatLoginSetQueryProvider.getInfo().getContext();
            KsBeanUtil.copyPropertiesThird(setResponse, addRequest);
            addRequest.setMobileAppId(request.getAppId());
            addRequest.setMobileAppSecret(request.getSecret());
            addRequest.setMobileServerStatus(request.getStatus());
            addRequest.setOperatePerson(commonUtil.getOperatorId());
            addRequest.setStoreId(storeId);
            wechatLoginSetSaveProvider.add(addRequest);
            operateLogMQUtil.convertAndSend("设置", "微信设置", "编辑微信H5接口");
        }else if(TerminalType.MINI.equals(request.getTerminalType())){
            //更新小程序（miniJSAPI）
            WechatConfigSaveRequest saveRequest = new WechatConfigSaveRequest();
            saveRequest.setStoreId(storeId);
            saveRequest.setAppId(request.getAppId());
            saveRequest.setSecret(request.getSecret());
            saveRequest.setSceneType(NumberUtils.INTEGER_ONE);
            paySettingProvider.saveWechatConfig(saveRequest);

            miniProgramSetProvider.modify(MiniProgramSetModifyRequest.builder()
                    .appId(request.getAppId())
                    .appSecret(request.getSecret())
                    .status(request.getStatus().toValue())
                    .type(0)//小程序类型 0 微信小程序
                    .updatePerson(commonUtil.getOperatorId())
                    .build());
            operateLogMQUtil.convertAndSend("设置", "微信设置", "编辑微信小程序接口");
        }else if(TerminalType.APP.equals(request.getTerminalType())){
            // 暂时保存 以防部分没有排查处的业务使用
            PayGatewaySaveByTerminalTypeRequest saveRequest = new PayGatewaySaveByTerminalTypeRequest();
            saveRequest.setStoreId(storeId);
            saveRequest.setAppId(request.getAppId());
            saveRequest.setSecret(request.getSecret());
            saveRequest.setTerminalType(com.wanmi.sbc.empower.bean.enums.TerminalType.APP);
            saveRequest.setPayGatewayEnum(PayGatewayEnum.WECHAT);
            paySettingProvider.savePayGatewayByTerminalType(saveRequest);

            //更新 APP
            WechatConfigSaveRequest saveWechatRequest = new WechatConfigSaveRequest();
            saveWechatRequest.setStoreId(storeId);
            saveWechatRequest.setAppId(request.getAppId());
            saveWechatRequest.setSecret(request.getSecret());
            saveWechatRequest.setSceneType(2);
            paySettingProvider.saveWechatConfig(saveWechatRequest);

            //同步LoginSet
            WechatLoginSetAddRequest addRequest = new WechatLoginSetAddRequest();
            WechatLoginSetResponse setResponse = wechatLoginSetQueryProvider.getInfo().getContext();
            KsBeanUtil.copyPropertiesThird(setResponse, addRequest);
            addRequest.setOperatePerson(commonUtil.getOperatorId());
            addRequest.setStoreId(storeId);
            addRequest.setAppServerStatus(request.getStatus());
            wechatLoginSetSaveProvider.add(addRequest);
            operateLogMQUtil.convertAndSend("设置", "微信设置", "编辑微信APP接口");
        }
        return BaseResponse.SUCCESSFUL();
    }

}
