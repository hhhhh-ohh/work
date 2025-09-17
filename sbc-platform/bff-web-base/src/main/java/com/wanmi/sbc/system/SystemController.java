package com.wanmi.sbc.system;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.fc.csplatform.common.crypto.Base64Util;
import com.alipay.fc.csplatform.common.crypto.CustomerInfoCryptoUtil;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailGetCustomerIdResponse;
import com.wanmi.sbc.empower.api.provider.customerservice.CustomerServiceSettingQueryProvider;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingByStoreIdRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingByStoreIdResponse;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;
import com.wanmi.sbc.setting.api.provider.baseconfig.BaseConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.businessconfig.BusinessConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.baseconfig.BaseConfigRopResponse;
import com.wanmi.sbc.setting.api.response.baseconfig.LoadingUrlResponse;
import com.wanmi.sbc.setting.api.response.businessconfig.BusinessConfigRopResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.SeoSettingVO;
import com.wanmi.sbc.system.request.OnlineServiceUrlRequest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Objects;

/**
 * 基本设置服务
 * Created by CHENLI on 2017/5/12.
 */
@Slf4j
@Tag(name = "SystemController", description = "基本设置 API")
@RestController
@Validated
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private BaseConfigQueryProvider baseConfigQueryProvider;

    @Autowired
    private BusinessConfigQueryProvider businessConfigQueryProvider;

    @Autowired
    private CustomerServiceSettingQueryProvider customerServiceSettingQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    /**
     * 查询基本设置
     */
    @Operation(summary = "查询基本设置")
    @Cacheable(key = "'BASE_CONFIG'",value = CacheConstants.GLOBAL_CACHE_NAME)
    @RequestMapping(value = "/baseConfig", method = RequestMethod.GET)
    public BaseResponse<BaseConfigRopResponse> findBaseConfig() {
        return baseConfigQueryProvider.getBaseConfig();
//        CompositeResponse<BaseConfigRopResponse> response =  sdkClient.buildClientRequest()
//                .get(BaseConfigRopResponse.class, "baseConfig.query", "1.0.0");
//        return BaseResponse.success( response.getSuccessResponse());
    }

    /**
     * 查询招商页设置
     * @return
     */
    @Operation(summary = "查询招商页设置")
    @RequestMapping(value = "/businessConfig", method = RequestMethod.GET)
    public BaseResponse<BusinessConfigRopResponse> findConfig() {
        return businessConfigQueryProvider.getInfo();
//        CompositeResponse<BusinessConfigRopResponse> response =  sdkClient.buildClientRequest()
//                .get(BusinessConfigRopResponse.class, "businessConfig.query", "1.0.0");
//        return BaseResponse.success( response.getSuccessResponse() );
    }

    /**
     * 获取服务时间
     * @return
     */
    @Operation(summary = "获取服务时间")
    @RequestMapping(value = "/queryServerTime", method = RequestMethod.GET)
    public BaseResponse<Long> queryServerTime() {
        return BaseResponse.success(System.currentTimeMillis());
    }

    /**
     *  查询阿里云客服配置
     * @return
     */
    @Operation(summary = "查询阿里云客服配置")
    @PostMapping("/aliyun/detail")
    public BaseResponse queryAliyun(@RequestBody OnlineServiceUrlRequest onlineServiceUrlRequest){
        String url = "";
        try {
            BaseResponse<CustomerServiceSettingByStoreIdResponse> aliyunResponse =
                    customerServiceSettingQueryProvider.getByStoreId(
                            CustomerServiceSettingByStoreIdRequest.builder()
                                    .storeId(0L)
                                    .platformType(CustomerServicePlatformType.ALIYUN)
                                    .bffWeb(true)
                                    .build());
            if (aliyunResponse != null
                    && aliyunResponse.getContext() != null
                    && aliyunResponse.getContext().getQqOnlineServerRop() != null) {
                CustomerServiceSettingVO customerServiceSettingVO = aliyunResponse.getContext().getQqOnlineServerRop();

                //根据用户id查询用户的信息
                CustomerDetailGetCustomerIdResponse customer = customerDetailQueryProvider.getCustomerDetailByCustomerId(
                        CustomerDetailByCustomerIdRequest.builder().customerId(onlineServiceUrlRequest.getCustomerId()).build())
                        .getContext();
                if (customer.getCustomerId() == null) {
                    return BaseResponse.error("客户不存在！");
                }
                if(customerServiceSettingVO.getStatus()==DefaultFlag.YES){
                    // 还原公钥
                    PublicKey publicKey = getPubKey(customerServiceSettingVO.getServiceKey());
                    // 封装请求体
                    JSONObject extInfo = new JSONObject();
                    extInfo.put("userId", onlineServiceUrlRequest.getCustomerId());
                    extInfo.put("userName",onlineServiceUrlRequest.getCustomerName());
                    JSONObject cinfo = new JSONObject();
                    cinfo.put("userId", onlineServiceUrlRequest.getCustomerId());
                    cinfo.put("extInfo", extInfo);
                    Map<String, String> map = CustomerInfoCryptoUtil.encryptByPublicKey(cinfo.toString(), publicKey);
                    String params = "&key=" + map.get("key") + "&cinfo=" + map.get("text");
                    String aliyunChat = customerServiceSettingVO.getServiceUrl();
                    url = aliyunChat.concat(params);
                }
            }
        } catch (Exception e){
            log.error("查询阿里云客服配置报错", e);
            return BaseResponse.FAILED();
        }
        return BaseResponse.success(url);
    }

    /**
     * 获取loading页
     */
    @Operation(summary = "获取loading页")
    @RequestMapping(value = "/loadingUrl", method = RequestMethod.GET)
    public BaseResponse<LoadingUrlResponse> findLoadingUrl() {
        LoadingUrlResponse loadingUrlResponse = new LoadingUrlResponse();
        loadingUrlResponse.setLoadingUrl(baseConfigQueryProvider.getBaseConfig()
                .getContext()
                .getLoadingUrl());

        return BaseResponse.success(loadingUrlResponse);
    }


    private PublicKey getPubKey(String pubKey) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Util.decode(pubKey));
        KeyFactory keyFactory;
        keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        return key;
    }

    /**
     * 查询SEO设置
     * @return
     */
    @Operation(summary = "查询SEO设置")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME,key = "'SEO_SETTING'")
    @RequestMapping(value = "/seoSetting", method = RequestMethod.GET)
    public BaseResponse<SeoSettingVO> findSeoSetting() {
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.SEO_SETTING.toValue());
        SystemConfigTypeResponse response = systemConfigQueryProvider.findByConfigTypeAndDelFlag(request).getContext();
        if (Objects.nonNull(response.getConfig())) {
            return BaseResponse.success(JSONObject.parseObject(response.getConfig().getContext(), SeoSettingVO.class));
        }
        return BaseResponse.success(new SeoSettingVO());
    }
}
