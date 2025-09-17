package com.wanmi.sbc.customerserver;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.empower.api.provider.customerservice.CustomerServiceSettingQueryProvider;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingByStoreIdRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingByStoreIdResponse;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@RestController
@Validated
@RequestMapping("/customerService")
@Tag(name = "QQServiceController", description = "S2B web公用-QQ客户信息API")
public class QQServiceController {

    @Autowired
    private CustomerServiceSettingQueryProvider customerServiceSettingQueryProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;
    /**
     * 查询qq客服列表
     * @param storeId
     * @param type    0：PC, 1： H5, 2： APP;
     * @return
     */
    @Operation(summary = "查询qq客服列表")
    @Parameters({
            @Parameter( name = "storeId",
                    description = "店铺id", required = true),
            @Parameter(name = "type",
                    description = "生效终端，0：PC, 1： H5, 2： APP", required = true)
    })
    @RequestMapping(value = {"/qq/detail/{storeId}/{type}"}, method = RequestMethod.GET)
    public BaseResponse<CustomerServiceSettingByStoreIdResponse> qqDetail(@PathVariable Long storeId, @PathVariable Integer type) {
        if (storeId == null || type == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BaseResponse<CustomerServiceSettingByStoreIdResponse> response =
                customerServiceSettingQueryProvider.getByStoreId(CustomerServiceSettingByStoreIdRequest.builder()
                        .storeId(storeId)
                        .bffWeb(true)
                        .platformType(CustomerServicePlatformType.QQ)
                        .build());
        if (response == null
                || response.getContext() == null
                || response.getContext().getQqOnlineServerRop() == null) {
            return BaseResponse.success(null);
        }
        CustomerServiceSettingByStoreIdResponse onlineServiceListResponse = response.getContext();
        CustomerServiceSettingVO onlineServiceVO = onlineServiceListResponse.getQqOnlineServerRop();
        if (TerminalType.APP.toValue()==type) {
            if (Objects.equals(DefaultFlag.NO, onlineServiceVO.getEffectiveApp())) {
                return BaseResponse.success(null);
            }
        } else if (TerminalType.H5.toValue()==type) {
            if (Objects.equals(DefaultFlag.NO, onlineServiceVO.getEffectiveMobile())) {
                return BaseResponse.success(null);
            }
        } else if (TerminalType.PC.toValue()==type) {
            if (Objects.equals(DefaultFlag.NO, onlineServiceVO.getEffectivePc())) {
                return BaseResponse.success(null);
            }
        }

        return BaseResponse.success(onlineServiceListResponse);
    }

    /**
     * 查询企微客服列表
     * @param storeId
     * @return
     */
    @Operation(summary = "查询企微客服列表")
    @Parameters({
            @Parameter(name = "storeId",
                    description = "店铺id", required = true)
    })
    @RequestMapping(value = {"/weChat/detail/{storeId}"}, method = RequestMethod.GET)
    public BaseResponse<CustomerServiceSettingByStoreIdResponse> weChatDetail(@PathVariable Long storeId) {
        if (storeId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //查询店铺是否存在
        if (storeId != Constants.ZERO) {
            storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder().storeId(storeId).build());
        }
        BaseResponse<CustomerServiceSettingByStoreIdResponse> response =
                customerServiceSettingQueryProvider.getByStoreId(CustomerServiceSettingByStoreIdRequest.builder()
                        .storeId(storeId)
                        .bffWeb(true)
                        .platformType(CustomerServicePlatformType.WECHAT)
                        .build());
        if (response == null
                || response.getContext() == null
                || response.getContext().getWeChatOnlineServerRop() == null) {
            return BaseResponse.success(null);
        }
        CustomerServiceSettingByStoreIdResponse customerServiceSettingByStoreIdResponse = response.getContext();
        return BaseResponse.success(customerServiceSettingByStoreIdResponse);
    }

    /**
     * 查询网易七鱼客服列表
     * @param storeId
     * @return
     */
    @Operation(summary = "查询网易七鱼客服列表")
    @Parameters({
            @Parameter(name = "storeId",
                    description = "店铺id", required = true)
    })
    @RequestMapping(value = {"/qiYu/detail/{storeId}"}, method = RequestMethod.GET)
    public BaseResponse<CustomerServiceSettingByStoreIdResponse> qiYuDetail(@PathVariable Long storeId) {
        if (storeId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //查询店铺是否存在
        if (storeId != Constants.ZERO) {
            storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder().storeId(storeId).build());
        }
        BaseResponse<CustomerServiceSettingByStoreIdResponse> response =
                customerServiceSettingQueryProvider.getByStoreId(CustomerServiceSettingByStoreIdRequest.builder()
                        .storeId(storeId)
                        .bffWeb(true)
                        .platformType(CustomerServicePlatformType.QIYU)
                        .build());
        if (response == null
                || response.getContext() == null
                || response.getContext().getQiYuOnlineServerRop() == null) {
            return BaseResponse.success(null);
        }
        CustomerServiceSettingByStoreIdResponse customerServiceSettingByStoreIdResponse = response.getContext();
        return BaseResponse.success(customerServiceSettingByStoreIdResponse);
    }

}
