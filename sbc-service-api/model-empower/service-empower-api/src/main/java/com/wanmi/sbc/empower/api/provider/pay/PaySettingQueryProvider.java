package com.wanmi.sbc.empower.api.provider.pay;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.pay.TradeRecordChannelByOrderIdRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdsRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.OpenedChannelItemRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.*;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemListResponse;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>支付查询Provider</p>
 * Created by of628-wenzhi on 2018-08-13-下午3:12.
 */
@FeignClient(value = "${application.empower.name}", contextId = "PaySettingQueryProvider")
public interface PaySettingQueryProvider {

    /**
     * 获取网关列表
     *
     * @return 网关列表，只包含网关基础信息，不带出该网关支付渠道项与配置信息 {@link PayGatewayListResponse}
     */
    @PostMapping("/empower/${application.empower.version}/list-gateway")
    BaseResponse<PayGatewayListResponse> listGatewayByStoreId(@RequestBody @Valid GatewayByStoreIdRequest request);

    /**
     * 根据id获取支付网关
     *
     * @param gatewayByIdRequest 包含支付网关id {@link GatewayByIdRequest}
     * @return 支付网关信息 {@link PayGatewayResponse}
     */
    @PostMapping("/empower/${application.empower.version}/get-gateway-by-id")
    BaseResponse<PayGatewayResponse> getGatewayById(@RequestBody @Valid GatewayByIdRequest gatewayByIdRequest);


    /**
     * 根据支付网关id查询支付渠道项列表
     *
     * @param channelItemByGatewayRequest 包含网关id {@link ChannelItemByGatewayRequest}
     * @return 支付渠道项列表 {@link PayChannelItemListResponse}
     */
    @PostMapping("/empower/${application.empower.version}/list-channel-item-by-gateway-name")
    BaseResponse<PayChannelItemListResponse> listChannelItemByGatewayName(@RequestBody @Valid
                                                                                  ChannelItemByGatewayRequest
                                                                                  channelItemByGatewayRequest);

    /**
     * 根据网关id和终端类型获取已开启的支付渠道项列表
     *
     * @param openedChannelItemRequest 包含网关id和终端类型 {@link OpenedChannelItemRequest}
     * @return 支付渠道项列表 {@link PayChannelItemListResponse}
     */
    @PostMapping("/empower/${application.empower.version}/list-opened-channel-item-by-gateway-name")
    BaseResponse<PayChannelItemListResponse> listOpenedChannelItemByGatewayName(@RequestBody @Valid
                                                                                        OpenedChannelItemRequest
                                                                                        openedChannelItemRequest);

    /**
     * 根据id查询支付渠道
     *
     * @param channelItemByIdRequest 包含支付渠道项id
     * @return 支付渠道项列表 {@link PayChannelItemResponse}
     */
    @PostMapping("/empower/${application.empower.version}/get-channel-item-by-id")
    BaseResponse<PayChannelItemResponse> getChannelItemById(@RequestBody @Valid ChannelItemByIdRequest
                                                                    channelItemByIdRequest);


    /**
     * 根据id查询支付网关配置
     *
     * @param gatewayConfigByIdRequest 包含支付网关配置id {@link GatewayConfigByIdRequest}
     * @return 支付网关配置信息 {@link PayGatewayConfigResponse}
     */
    @PostMapping("/empower/${application.empower.version}/get-gateway-config-by-id")
    BaseResponse<PayGatewayConfigResponse> getGatewayConfigById(@RequestBody @Valid GatewayConfigByIdRequest
                                                                        gatewayConfigByIdRequest);

    /**
     * 根据网关名称查询支付网关配置
     *
     * @param gatewayConfigByGatewayRequest 包含支付网关枚举 {@link GatewayConfigByGatewayRequest}
     * @return 支付网关配置信息 {@link PayGatewayConfigResponse}
     */
    @PostMapping("/empower/${application.empower.version}/get-gateway-config-by-gateway")
    BaseResponse<PayGatewayConfigResponse> getGatewayConfigByGateway(@RequestBody @Valid GatewayConfigByGatewayRequest
                                                                             gatewayConfigByGatewayRequest);

    /**
     * 根据网关id查询支付网关配置
     *
     * @param gatewayConfigByGatewayIdRequest 包含支付网关枚举 {@link GatewayConfigByGatewayIdRequest}
     * @return 支付网关配置信息 {@link PayGatewayConfigResponse}
     */
    @PostMapping("/empower/${application.empower.version}/get-gateway-config-by-gateway-id")
    BaseResponse<PayGatewayConfigResponse> getGatewayConfigByGatewayId(@RequestBody @Valid
                                                                               GatewayConfigByGatewayIdRequest
                                                                               gatewayConfigByGatewayIdRequest);

    /**
     * 查询所有已开启网关的配置列表
     *
     * @return 支付网关配置信息列表 {@link PayGatewayConfigListResponse}
     */
    @PostMapping("/empower/${application.empower.version}/list-opened-gateway-config")
    BaseResponse<PayGatewayConfigListResponse> listOpenedGatewayConfig(@RequestBody @Valid GatewayOpenedByStoreIdRequest gatewayOpenedByStoreIdRequest);





    /**
     * 初始化店铺获取网关列表
     *
     * @return 网关列表，只包含网关基础信息，不带出该网关支付渠道项与配置信息 {@link PayGatewayListResponse}
     */
    @PostMapping("/empower/${application.empower.version}/init-list-gateway")
    BaseResponse<PayGatewayListResponse> initGatewayByStoreId(@RequestBody @Valid GatewayInitByStoreIdRequest request);


    /**
     * 获取授信支付名称
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/get-credit-name")
    BaseResponse<String> getCreditName();

    /**
     * @description
     * @author  edz
     * @date: 2021-04-13 18:06
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<java.lang.String>
     **/
    @PostMapping("/empower/${application.empower.version}/get-pay-channel-item-by-business-id")
    BaseResponse<String> getPayChannelItemByBusinessId(@RequestBody @Valid TradeRecordChannelByOrderIdRequest request);



    @PostMapping("/empower/${application.empower.version}/find-pay-channel-list-by-ids")
    BaseResponse<PayChannelItemListResponse> findPayChannelItemListByIds(@RequestBody ChannelItemByIdsRequest channelItemByIdsRequest);


    @PostMapping("/empower/${application.empower.version}/get-wechat-config-by-store")
    BaseResponse<WechatConfigResponse> getWechatConfigByStoreId(@RequestBody @Valid QueryWechatConfigByStoreIdRequest request);

}
