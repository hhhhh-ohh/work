package com.wanmi.sbc.customerserver;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.PlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreInfoByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreInfoResponse;
import com.wanmi.sbc.customer.bean.enums.CompanyType;
import com.wanmi.sbc.empower.api.provider.customerservice.CustomerServiceSettingProvider;
import com.wanmi.sbc.empower.api.provider.customerservice.CustomerServiceSettingQueryProvider;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingByStoreIdRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingModifyRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingByStoreIdResponse;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name = "QQServiceController", description = "QQ服务 API")
@RestController
@Validated
@RequestMapping("/customerService")
public class QQServiceController {

    @Autowired
    private CustomerServiceSettingQueryProvider customerServiceSettingQueryProvider;
    @Autowired
    private CustomerServiceSettingProvider customerServiceSettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    /**
     * 查询qq客服配置明细
     *
     * @return
     */
    @Operation(summary = "查询qq客服配置明细")
    @RequestMapping(value = {"/qq/detail"}, method = RequestMethod.GET)
    public BaseResponse<CustomerServiceSettingByStoreIdResponse> qqDetail() {
        return customerServiceSettingQueryProvider.getByStoreId(CustomerServiceSettingByStoreIdRequest.builder()
                .storeId(commonUtil.getStoreId())
                .platformType(CustomerServicePlatformType.QQ)
                .build());
    }

    /**
     * 查询企微客服配置明细
     *
     * @return
     */
    @Operation(summary = "查询企微客服配置明细")
    @RequestMapping(value = {"/weChat/detail"}, method = RequestMethod.GET)
    public BaseResponse<CustomerServiceSettingByStoreIdResponse> weChatDetail() {
        return customerServiceSettingQueryProvider.getByStoreId(CustomerServiceSettingByStoreIdRequest.builder()
                .storeId(commonUtil.getStoreId())
                .platformType(CustomerServicePlatformType.WECHAT)
                .build());
    }

    /**
     * 查询企微客服配置明细
     *
     * @return
     */
    @Operation(summary = "查询企微客服配置明细")
    @RequestMapping(value = {"/qiyu/detail"}, method = RequestMethod.GET)
    public BaseResponse<CustomerServiceSettingByStoreIdResponse> qiyuDetail() {
        return customerServiceSettingQueryProvider.getByStoreId(CustomerServiceSettingByStoreIdRequest.builder()
                .storeId(commonUtil.getStoreId())
                .platformType(CustomerServicePlatformType.QIYU)
                .build());
    }

    /**
     * 查询qq客服开关
     *
     * @return
     */
    @Operation(summary = "查询qq客服开关")
    @RequestMapping(value = {"/qq/switch"}, method = RequestMethod.GET)
    public BaseResponse<ConfigResponse> qqSwitch() {
        BaseResponse<CustomerServiceSettingByStoreIdResponse> response =
                customerServiceSettingQueryProvider.getByStoreId(CustomerServiceSettingByStoreIdRequest.builder()
                        .storeId(commonUtil.getStoreId())
                        .platformType(CustomerServicePlatformType.QQ)
                        .build());

        BaseResponse<CustomerServiceSettingByStoreIdResponse> weChatResponse =
                customerServiceSettingQueryProvider.getByStoreId(CustomerServiceSettingByStoreIdRequest.builder()
                        .storeId(commonUtil.getStoreId())
                        .platformType(CustomerServicePlatformType.WECHAT)
                        .build());
        BaseResponse<CustomerServiceSettingByStoreIdResponse> qiYuResponse =
                customerServiceSettingQueryProvider.getByStoreId(CustomerServiceSettingByStoreIdRequest.builder()
                        .storeId(commonUtil.getStoreId())
                        .platformType(CustomerServicePlatformType.QIYU)
                        .build());

        return BaseResponse.success(
                ConfigResponse.builder()
                        .onlineServiceVO(response.getContext().getQqOnlineServerRop())
                        .weChatServiceVO(weChatResponse.getContext().getWeChatOnlineServerRop())
                        .qiYuServiceVO(qiYuResponse.getContext().getQiYuOnlineServerRop())
                        .build());
    }

    /**
     * 保存qq客服配置明细
     *
     * @return
     */
    @Operation(summary = "保存qq客服配置明细")
    @RequestMapping(value = {"/qq/saveDetail"}, method = RequestMethod.POST)
    public BaseResponse qqSaveDetail(@RequestBody CustomerServiceSettingModifyRequest ropRequest) {
        commonUtil.checkStoreId(ropRequest.getQqOnlineServerRop().getStoreId());
        ropRequest.setStoreId(commonUtil.getStoreId());
        operateLogMQUtil.convertAndSend("设置","编辑在线客服","编辑在线客服");
        if(ropRequest.getQqOnlineServerRop() != null){
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
    @RequestMapping(value = {"/weChat/saveDetail"}, method = RequestMethod.POST)
    public BaseResponse weChatSaveDetail(@RequestBody CustomerServiceSettingModifyRequest ropRequest) {
        Long storeId = commonUtil.getStoreId();
        ropRequest.setStoreId(storeId);
        StoreInfoResponse storeInfoResponse = storeQueryProvider.getStoreInfoById(new StoreInfoByIdRequest(storeId))
                .getContext();
        if (CompanyType.PLATFORM.toValue() != storeInfoResponse.getCompanyType().toValue()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        operateLogMQUtil.convertAndSend("设置", "编辑在线企微客服", "编辑在线企微客服");
        return customerServiceSettingProvider.weChatModify(ropRequest);
    }

    /**
     * 保存企微客服配置明细
     *
     * @param ropRequest
     * @return
     */
    @Operation(summary = "保存网易七鱼客服配置明细")
    @RequestMapping(value = {"/qiyu/saveDetail"}, method = RequestMethod.POST)
    public BaseResponse qiyuSaveDetail(@RequestBody CustomerServiceSettingModifyRequest ropRequest) {
        Long storeId = commonUtil.getStoreId();
        ropRequest.setStoreId(storeId);
        StoreInfoResponse storeInfoResponse = storeQueryProvider.getStoreInfoById(new StoreInfoByIdRequest(storeId))
                .getContext();
        if(Objects.isNull(ropRequest.getQiYuOnlineServerRop())
                || (Objects.nonNull(ropRequest.getQiYuOnlineServerRop()) && (Objects.isNull(ropRequest.getQiYuOnlineServerRop().getServiceType())
        || (ropRequest.getQiYuOnlineServerRop().getServiceType() == PlatformType.BOSS && StringUtils.isBlank(ropRequest.getQiYuOnlineServerRop().getServiceKey()))
                || (ropRequest.getQiYuOnlineServerRop().getServiceType() == PlatformType.STORE && StringUtils.isBlank(ropRequest.getQiYuOnlineServerRop().getServiceUrl()))))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        operateLogMQUtil.convertAndSend("设置", "编辑在线网易七鱼客服", "编辑在线网易七鱼客服");
        return customerServiceSettingProvider.qiYuModify(ropRequest);
    }

}
