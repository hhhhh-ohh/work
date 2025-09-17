package com.wanmi.sbc.empower.api.provider.pay;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemSaveRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>支付Provider</p>
 * Created by of628 on 2018-08-13-下午8:15.
 */
@FeignClient(value = "${application.empower.name}", contextId = "PaySettingProvider")
public interface PaySettingProvider {

    /**
     * 新增支付网关
     *
     * @param gatewayAddRequest 新增支付网关request{@link GatewayAddRequest}
     * @return BaseResponse
     */
    @PostMapping("/empower/${application.empower.version}/add-gateway")
    BaseResponse addGateway(@RequestBody @Valid GatewayAddRequest gatewayAddRequest);

    /**
     * 修改支付网关基础信息
     *
     * @param gatewayModifyRequest
     * @return BaseResponse
     */
    @PostMapping("/empower/${application.empower.version}/modify-gateway")
    BaseResponse modifyGateway(@RequestBody @Valid GatewayModifyRequest gatewayModifyRequest);

    /**
     * 保存支付渠道项，若id为空，默认新增
     *
     * @param channelItemSaveRequest 支付渠道项新增结构 {@link ChannelItemSaveRequest}
     * @return BaseResponse
     */
    @PostMapping("/empower/${application.empower.version}/add-channel-item")
    BaseResponse saveChannelItem(@RequestBody @Valid ChannelItemSaveRequest channelItemSaveRequest);

    /**
     * 保存网关配置信息，如果id为空，则默认新增
     *
     * @param gatewayConfigSaveRequest 网关配置新增数据结构 {@link GatewayAddRequest}
     * @return BaseResponse
     */
    @PostMapping("/empower/${application.empower.version}/add-gateway-config")
    BaseResponse saveGatewayConfig(@RequestBody @Valid GatewayConfigSaveRequest gatewayConfigSaveRequest);


    /**
     * 保存支付配置
     *
     * @param payGatewaySaveRequest 保存支付配置request{@link PayGatewaySaveRequest}
     * @return BaseResponse
     */
    @PostMapping("/empower/${application.empower.version}/save-pay-gateway")
    BaseResponse savePayGateway(@RequestBody @Valid PayGatewaySaveRequest payGatewaySaveRequest);


    /**
     * 根据不同终端保存支付配置
     *
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/add-gateway-config-terminal-type")
    BaseResponse savePayGatewayByTerminalType(@RequestBody @Valid PayGatewaySaveByTerminalTypeRequest request);

    /**
     * @description    只更新配置不新增
     * @author  wur
     * @date: 2022/12/3 11:08
     * @param request
     * @return
     **/
    @PostMapping("/empower/${application.empower.version}/update-wechat-config")
    BaseResponse updateWechatConfig(@RequestBody @Valid WechatConfigSaveRequest request);

    /**
     * @description   如果配置不存在则新增
     * @author  wur
     * @date: 2022/12/3 11:08
     * @param request
     * @return
     **/
    @PostMapping("/empower/${application.empower.version}/save-wechat-config")
    BaseResponse saveWechatConfig(@RequestBody @Valid WechatConfigSaveRequest request);

}
