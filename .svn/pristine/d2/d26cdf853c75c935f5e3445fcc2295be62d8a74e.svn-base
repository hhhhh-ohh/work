package com.wanmi.sbc.customerserver;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.customerservice.CustomerServiceSettingProvider;
import com.wanmi.sbc.empower.api.provider.customerservice.CustomerServiceSettingQueryProvider;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingByStoreIdRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingModifyRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingByStoreIdResponse;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingModifyResponse;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;
import com.wanmi.sbc.setting.api.request.ConfigContextModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Tag(name =  "客服API", description =  "CustomerServiceController")
@RestController
@Validated
@RequestMapping("/customerService")
public class CustomerServiceController {

    @Autowired private CustomerServiceSettingQueryProvider customerServiceSettingQueryProvider;
    @Autowired private CustomerServiceSettingProvider customerServiceSettingProvider;

    @Autowired private OperateLogMQUtil operateLogMQUtil;

    /**
     * 查询qq客服配置明细
     *
     * @return
     */
    @Operation(summary = "查询qq客服配置明细")
    @Parameter(
            name = "storeId",
            description = "店铺id",
            required = true)
    @RequestMapping(
            value = {"/qq/detail/{storeId}"},
            method = RequestMethod.GET)
    public BaseResponse<CustomerServiceSettingByStoreIdResponse> qqDetail(
            @PathVariable Long storeId) {
        return customerServiceSettingQueryProvider.getByStoreId(
                CustomerServiceSettingByStoreIdRequest.builder()
                        .platformType(CustomerServicePlatformType.QQ)
                        .storeId(storeId)
                        .build());
    }

    /**
     * 查询企微客服配置明细
     *
     * @return
     */
    @Operation(summary = "查询企微客服配置明细")
    @Parameter(name = "storeId",
            description = "店铺id",
            required = true)
    @RequestMapping(
            value = {"/weChat/detail/{storeId}"},
            method = RequestMethod.GET)
    public BaseResponse<CustomerServiceSettingByStoreIdResponse> weChatDetail(
            @PathVariable Long storeId) {
        return customerServiceSettingQueryProvider.getByStoreId(
                CustomerServiceSettingByStoreIdRequest.builder()
                        .platformType(CustomerServicePlatformType.WECHAT)
                        .storeId(storeId)
                        .build());
    }

    /**
     * 查询qq客服开关
     *
     * @return
     */
    @Operation(summary = "查询qq客服开关")
    @Parameter(name = "storeId",
            description = "店铺id",
            required = true)
    @RequestMapping(
            value = {"/qq/switch/{storeId}"},
            method = RequestMethod.GET)
    public BaseResponse<ConfigResponse> qqSwitch(@PathVariable Long storeId) {
        BaseResponse<CustomerServiceSettingByStoreIdResponse> qqResponse =
                customerServiceSettingQueryProvider.getByStoreId(
                        CustomerServiceSettingByStoreIdRequest.builder()
                                .storeId(storeId)
                                .platformType(CustomerServicePlatformType.QQ)
                                .build());

        BaseResponse<CustomerServiceSettingByStoreIdResponse> aliyunResponse =
                customerServiceSettingQueryProvider.getByStoreId(
                        CustomerServiceSettingByStoreIdRequest.builder()
                                .storeId(storeId)
                                .platformType(CustomerServicePlatformType.ALIYUN)
                                .build());

        BaseResponse<CustomerServiceSettingByStoreIdResponse> weChatResponse =
                customerServiceSettingQueryProvider.getByStoreId(
                        CustomerServiceSettingByStoreIdRequest.builder()
                                .storeId(storeId)
                                .platformType(CustomerServicePlatformType.WECHAT)
                                .build());

        BaseResponse<CustomerServiceSettingByStoreIdResponse> qiYuResponse =
                customerServiceSettingQueryProvider.getByStoreId(
                        CustomerServiceSettingByStoreIdRequest.builder()
                                .storeId(storeId)
                                .platformType(CustomerServicePlatformType.QIYU)
                                .build());
        SystemConfigVO systemConfigVO = new SystemConfigVO();
        if (aliyunResponse != null
                && aliyunResponse.getContext() != null
                && aliyunResponse.getContext().getQqOnlineServerRop() != null) {
            CustomerServiceSettingVO customerServiceSettingVO =
                    aliyunResponse.getContext().getQqOnlineServerRop();
            systemConfigVO = toSystemConfigVO(customerServiceSettingVO);
        }

        // 改造 bff
        return BaseResponse.success(
                ConfigResponse.builder()
                        .onlineServiceVO(qqResponse.getContext().getQqOnlineServerRop())
                        .systemConfigVO(systemConfigVO)
                        .weChatServiceVO(weChatResponse.getContext().getWeChatOnlineServerRop())
                        .qiYuServiceVO(qiYuResponse.getContext().getQiYuOnlineServerRop())
                        .build());
    }

    private SystemConfigVO toSystemConfigVO(CustomerServiceSettingVO customerServiceSettingVO) {
        SystemConfigVO systemConfigVO =
                KsBeanUtil.copyPropertiesThird(customerServiceSettingVO, SystemConfigVO.class);
        systemConfigVO.setStatus(customerServiceSettingVO.getStatus().toValue());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", customerServiceSettingVO.getServiceTitle());
        jsonObject.put("key", customerServiceSettingVO.getServiceKey());
        jsonObject.put("aliyunChat", customerServiceSettingVO.getServiceUrl());
        systemConfigVO.setContext(jsonObject.toJSONString());
        systemConfigVO.setConfigKey(ConfigKey.ONLINESERVICE.toValue());
        systemConfigVO.setConfigType(ConfigType.ALIYUN_ONLINE_SERVICE.toValue());
        systemConfigVO.setDelFlag(DeleteFlag.NO);
        return systemConfigVO;
    }

    /**
     * 保存qq客服配置明细
     *
     * @param ropRequest
     * @return
     */
    @Operation(summary = "保存qq客服配置明细")
    @RequestMapping(
            value = {"/qq/saveDetail"},
            method = RequestMethod.POST)
    public BaseResponse qqSaveDetail(@RequestBody CustomerServiceSettingModifyRequest ropRequest) {
        operateLogMQUtil.convertAndSend("设置", "编辑在线客服", "编辑在线客服");
        if (ropRequest.getQqOnlineServerRop() != null) {
            ropRequest.getQqOnlineServerRop().setPlatformType(CustomerServicePlatformType.QQ);
        }
        return customerServiceSettingProvider.modify(ropRequest);
    }

    /**
     * 保存企微客服配置明细
     *
     * @param ropRequest
     * @return
     */
    @Operation(summary = "保存企微客服配置明细")
    @RequestMapping(
            value = {"/weChat/saveDetail"},
            method = RequestMethod.POST)
    public BaseResponse weChatSaveDetail(@RequestBody CustomerServiceSettingModifyRequest ropRequest) {
        ropRequest.setStoreId(0L);
        operateLogMQUtil.convertAndSend("设置", "编辑在线企微客服", "编辑在线企微客服");
        return customerServiceSettingProvider.weChatModify(ropRequest);
    }

    /**
     * 查询阿里云客服配置
     *
     * @return
     */
    @Operation(summary = "查询阿里云客服配置")
    @PostMapping("/aliyun/detail")
    public BaseResponse queryAliyun() {
        BaseResponse<CustomerServiceSettingByStoreIdResponse> aliyunResponse =
                customerServiceSettingQueryProvider.getByStoreId(
                        CustomerServiceSettingByStoreIdRequest.builder()
                                .storeId(0L)
                                .platformType(CustomerServicePlatformType.ALIYUN)
                                .build());
        if (aliyunResponse != null
                && aliyunResponse.getContext() != null
                && aliyunResponse.getContext().getQqOnlineServerRop() != null) {
            CustomerServiceSettingVO customerServiceSettingVO =
                    aliyunResponse.getContext().getQqOnlineServerRop();
            SystemConfigVO systemConfigVO = toSystemConfigVO(customerServiceSettingVO);
            return BaseResponse.success(systemConfigVO);
        }
        return BaseResponse.FAILED();
    }

    @Operation(summary = "修改阿里云客服配置")
    @PostMapping("/aliyun/modify")
    public BaseResponse modifyAliyun(@RequestBody ConfigContextModifyByTypeAndKeyRequest request) {
        // 店铺ID目前都是写死的0，表示BOSS平台
        BaseResponse<CustomerServiceSettingByStoreIdResponse> aliyunResponse =
                customerServiceSettingQueryProvider.getByStoreId(
                        CustomerServiceSettingByStoreIdRequest.builder()
                                .storeId(0L)
                                .platformType(CustomerServicePlatformType.ALIYUN)
                                .build());
        if (aliyunResponse.getContext() == null
                || aliyunResponse.getContext().getQqOnlineServerRop() == null) {
            return BaseResponse.FAILED();
        }
        CustomerServiceSettingVO customerServiceSettingVO =
                aliyunResponse.getContext().getQqOnlineServerRop();
        customerServiceSettingVO.setPlatformType(CustomerServicePlatformType.ALIYUN);
        customerServiceSettingVO.setServerStatus(DefaultFlag.fromValue(request.getStatus()));
        customerServiceSettingVO.setStatus(DefaultFlag.fromValue(request.getStatus()));
        JSONObject jsonObject = JSONObject.parseObject(request.getContext());
        customerServiceSettingVO.setServiceTitle((String) jsonObject.get("title"));
        customerServiceSettingVO.setServiceKey((String) jsonObject.get("key"));
        customerServiceSettingVO.setServiceUrl((String) jsonObject.get("aliyunChat"));
        CustomerServiceSettingModifyRequest ropRequest =
                CustomerServiceSettingModifyRequest.builder()
                        .qqOnlineServerRop(customerServiceSettingVO)
                        .build();
        return customerServiceSettingProvider.modify(ropRequest);
    }

    @Operation(summary = "修改网易七鱼客服配置")
    @PostMapping("/qiyu/modify")
    public BaseResponse<CustomerServiceSettingModifyResponse> modifyQiYu(@RequestBody ConfigContextModifyByTypeAndKeyRequest request) {
        // 店铺ID目前都是写死的0，表示BOSS平台
        BaseResponse<CustomerServiceSettingByStoreIdResponse> qiYuResponse =
                customerServiceSettingQueryProvider.getByStoreId(
                        CustomerServiceSettingByStoreIdRequest.builder()
                                .storeId(0L)
                                .platformType(CustomerServicePlatformType.QIYU)
                                .build());
        if (qiYuResponse.getContext() == null
                || qiYuResponse.getContext().getQiYuOnlineServerRop() == null) {
            return BaseResponse.FAILED();
        }
        CustomerServiceSettingVO customerServiceSettingVO =
                qiYuResponse.getContext().getQiYuOnlineServerRop();
        customerServiceSettingVO.setPlatformType(CustomerServicePlatformType.QIYU);
        customerServiceSettingVO.setServerStatus(DefaultFlag.fromValue(request.getStatus()));
        customerServiceSettingVO.setStatus(DefaultFlag.fromValue(request.getStatus()));
        JSONObject jsonObject = JSONObject.parseObject(request.getContext());
        if(Objects.isNull(jsonObject.get("key")) || (!Objects.isNull(jsonObject.get("key")) && StringUtils.isBlank((String)jsonObject.get("key")))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        customerServiceSettingVO.setServiceKey((String) jsonObject.get("key"));
        CustomerServiceSettingModifyRequest ropRequest =
                CustomerServiceSettingModifyRequest.builder()
                        .qiYuOnlineServerRop(customerServiceSettingVO)
                        .storeId(0L)
                        .build();
        return customerServiceSettingProvider.qiYuModify(ropRequest);
    }
}
