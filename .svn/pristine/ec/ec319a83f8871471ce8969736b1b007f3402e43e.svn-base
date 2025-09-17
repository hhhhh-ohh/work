package com.wanmi.sbc.empower.provider.impl.pay;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.TradeRecordChannelByOrderIdRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdsRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.OpenedChannelItemRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.*;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemListResponse;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.*;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayConfigVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.empower.bean.vo.WechatConfigVO;
import com.wanmi.sbc.empower.pay.model.root.PayChannelItem;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.model.root.WechatConfig;
import com.wanmi.sbc.empower.pay.service.PayDataService;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigResponse;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>支付查询接口实现</p>
 * Created by of628-wenzhi on 2018-08-18-下午4:45.
 */
@RestController
@Validated
@Slf4j
public class PaySettingQueryController implements PaySettingQueryProvider {

    @Autowired
    private PayDataService payDataService;


    @Override
    public BaseResponse<PayGatewayListResponse> listGatewayByStoreId(GatewayByStoreIdRequest request) {
        List<PayGateway> list = payDataService.queryGatewaysByStoreId(request.getStoreId());
        List<PayGatewayVO> results = list.stream().map(i -> {
            PayGatewayVO payGatewayVO = new PayGatewayResponse();
            KsBeanUtil.copyPropertiesThird(i, payGatewayVO);
            return payGatewayVO;
        }).collect(Collectors
                .toList());
        results.stream().forEach(vo -> {
            if (PayGatewayEnum.CREDIT == vo.getName()) {
                vo.setAlias(payDataService.getCreditName());
            }
        });
        return BaseResponse.success(new PayGatewayListResponse(results));
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponse<PayGatewayResponse> getGatewayById(@RequestBody @Valid GatewayByIdRequest gatewayByIdRequest) {
        PayGateway gateway = payDataService.queryGateway(gatewayByIdRequest.getGatewayId());
        return BaseResponse.success(wrapperResponseForGateway(gateway));
    }

    @Override
    public BaseResponse<PayChannelItemListResponse> listChannelItemByGatewayName(@RequestBody @Valid
                                                                                             ChannelItemByGatewayRequest
                                                                                         channelItemByGatewayRequest) {
        List<PayChannelItem> channelItems = payDataService.queryItemByGatewayName(channelItemByGatewayRequest
                .getGatewayName());
        return BaseResponse.success(wrapperResponseListForChannelItems(channelItems));
    }

    @Override
    public BaseResponse<PayChannelItemListResponse> listOpenedChannelItemByGatewayName(@RequestBody @Valid
                                                                                                   OpenedChannelItemRequest
                                                                                               openedChannelItemRequest) {
        List<PayChannelItem> channelItems = payDataService.queryOpenItemByGatewayName(openedChannelItemRequest
                .getGatewayName(), openedChannelItemRequest.getTerminalType());
        return BaseResponse.success(wrapperResponseListForChannelItems(channelItems));
    }

    @Override
    public BaseResponse<PayChannelItemResponse> getChannelItemById(@RequestBody @Valid ChannelItemByIdRequest
                                                                           channelItemByIdRequest) {
        PayChannelItem channelItem = payDataService.queryItemById(channelItemByIdRequest.getChannelItemId());
        return BaseResponse.success(wrapperResponseForChannelItem(channelItem));
    }

    @Override
    public BaseResponse<PayGatewayConfigResponse> getGatewayConfigById(@RequestBody @Valid GatewayConfigByIdRequest
                                                                               gatewayConfigByIdRequest) {
        PayGatewayConfig config = payDataService.queryConfig(gatewayConfigByIdRequest.getGatewayConfigId());
        return BaseResponse.success(wrapperResponseForGatewayConfig(config));
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponse<PayGatewayConfigResponse> getGatewayConfigByGateway(@RequestBody @Valid
                                                                                        GatewayConfigByGatewayRequest
                                                                                    gatewayConfigByGatewayRequest) {
        PayGatewayConfig config = payDataService.queryConfigByNameAndStoreId(gatewayConfigByGatewayRequest.getGatewayEnum(), gatewayConfigByGatewayRequest.getStoreId());
        return BaseResponse.success(wrapperResponseForGatewayConfig(config));
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponse<PayGatewayConfigResponse> getGatewayConfigByGatewayId(@RequestBody @Valid
                                                                                          GatewayConfigByGatewayIdRequest
                                                                                      gatewayConfigByGatewayIdRequest) {
        PayGatewayConfig config = payDataService.queryConfigByGatwayIdAndStoreId(gatewayConfigByGatewayIdRequest.getGatewayId(), gatewayConfigByGatewayIdRequest.getStoreId());
        return BaseResponse.success(wrapperResponseForGatewayConfig(config));
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponse<PayGatewayConfigListResponse> listOpenedGatewayConfig(@RequestBody @Valid GatewayOpenedByStoreIdRequest request) {
        // todo Saas 独立收款根据storeid过滤开启的在线支付方式
        List<PayGatewayConfig> configs = payDataService.queryConfigByOpenAndStoreId(request.getStoreId());
        List<PayGatewayConfigVO> responseList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(configs)) {
            responseList = configs.stream().map(this::wrapperVoForGatewayConfig).collect(Collectors.toList());
        }
        return BaseResponse.success(new PayGatewayConfigListResponse(responseList));
    }


    /**
     * 初始化店铺获取网关列表
     * 以boss生效的数据为基准 生成店铺的数据
     * 默认未启用
     */
    @Override
    public BaseResponse<PayGatewayListResponse> initGatewayByStoreId(GatewayInitByStoreIdRequest request) {
        List<PayGateway> list = payDataService.queryGatewaysByStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        if (CollectionUtils.isNotEmpty(list)) {
            List<PayGateway> storeList = list.stream().map(i -> {
                PayGateway payGateway = new PayGateway();
                KsBeanUtil.copyPropertiesThird(i, payGateway);
                payGateway.setId(null);
                payGateway.setPayChannelItemList(null);
                payGateway.setConfig(null);
                payGateway.setStoreId(request.getStoreId());
                payGateway.setIsOpen(IsOpen.NO);
                payDataService.saveGateway(payGateway);
                PayGatewayConfig payGatewayConfig = new PayGatewayConfig();
                payGatewayConfig.setStoreId(request.getStoreId());
                payGatewayConfig.setPayGateway(payGateway);
                payDataService.saveConfig(payGatewayConfig);
                return payGateway;
            }).collect(Collectors
                    .toList());

            List<PayGatewayVO> results = storeList.stream().map(i -> {
                PayGatewayVO payGatewayVO = new PayGatewayResponse();
                KsBeanUtil.copyPropertiesThird(i, payGatewayVO);
                return payGatewayVO;
            }).collect(Collectors
                    .toList());
            return BaseResponse.success(new PayGatewayListResponse(results));
        }
        return BaseResponse.success(new PayGatewayListResponse(new ArrayList<>()));
    }

    @Override
    public BaseResponse<String> getCreditName() {
        return BaseResponse.success(payDataService.getCreditName());
    }

    @Override
    public BaseResponse<String> getPayChannelItemByBusinessId(@RequestBody @Valid TradeRecordChannelByOrderIdRequest tradeRecordByOrderOrParentCodeRequest){
        String channel = null;
        if (StringUtils.isNotBlank(tradeRecordByOrderOrParentCodeRequest.getOrderId())) {
            channel = payDataService.getPayChannelItemByBusinessId(tradeRecordByOrderOrParentCodeRequest.getOrderId());
        }
        if (channel == null && StringUtils.isNotBlank(tradeRecordByOrderOrParentCodeRequest.getParentId())) {
            channel = payDataService.getPayChannelItemByBusinessId(tradeRecordByOrderOrParentCodeRequest.getParentId());
        }
        return BaseResponse.success(channel);
    }


    @Override
    public BaseResponse<PayChannelItemListResponse> findPayChannelItemListByIds(@RequestBody ChannelItemByIdsRequest channelItemByIdsRequest) {
        PayChannelItemListResponse response = new PayChannelItemListResponse();
        response.setPayChannelItemVOList(payDataService.findByIdIn(channelItemByIdsRequest.getChannelItemIds()));
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<WechatConfigResponse> getWechatConfigByStoreId(@Valid QueryWechatConfigByStoreIdRequest request) {
        List<WechatConfig> configList = payDataService.findByStoreId(request.getStoreId());
        List<WechatConfigVO> systemConfigVOList =
                configList.stream().map(config -> payDataService.wrapperWechatConfigVo(config)).collect(Collectors.toList());
        return BaseResponse.success(WechatConfigResponse.builder().configVOList(systemConfigVOList).build());
    }


    /**
     * @description 封装网关Response
     * @author  songhanlin
     * @date: 2021/6/3 上午11:34
     * @param payGateway 网关
     * @return 网关Response
     **/
    private PayGatewayResponse wrapperResponseForGateway(PayGateway payGateway) {
        if (payGateway != null) {
            PayGatewayResponse response = new PayGatewayResponse();
            KsBeanUtil.copyPropertiesThird(payGateway, response);
            if (payGateway.getConfig() != null) {
                PayGatewayConfigVO configVO = new PayGatewayConfigVO();
                KsBeanUtil.copyPropertiesThird(payGateway.getConfig(), configVO);
                response.setConfig(configVO);
            }
            if (CollectionUtils.isNotEmpty(payGateway.getPayChannelItemList())) {
                List<PayChannelItemVO> voList = payGateway.getPayChannelItemList().stream().map(
                        i -> {
                            PayChannelItemVO channelItemVO = new PayChannelItemVO();
                            KsBeanUtil.copyPropertiesThird(i, channelItemVO);
                            return channelItemVO;
                        }
                ).collect(Collectors.toList());
                response.setPayChannelItemList(voList);
            }
            if (PayGatewayEnum.CREDIT == response.getName()) {
                response.setAlias(payDataService.getCreditName());
            }
            return response;
        }
        return null;
    }

    /**
     * @description 封装支付项Response
     * @author  songhanlin
     * @date: 2021/6/3 上午11:34
     * @param channelItem 支付项
     * @return 支付项Response
     **/
    private PayChannelItemResponse wrapperResponseForChannelItem(PayChannelItem channelItem) {
        if (channelItem != null) {
            PayChannelItemResponse response = new PayChannelItemResponse();
            KsBeanUtil.copyPropertiesThird(channelItem, response);
            if (channelItem.getGateway() != null) {
                PayGatewayVO gatewayVO = new PayGatewayVO();
                KsBeanUtil.copyPropertiesThird(channelItem.getGateway(), gatewayVO);
                response.setGateway(gatewayVO);
            }
            return response;
        }
        return null;
    }

    /**
     * @description 封装支付渠道Response
     * @author  songhanlin
     * @date: 2021/6/3 上午11:34
     * @param channelItem 支付渠道
     * @return 支付渠道Response
     **/
    private PayChannelItemVO wrapperVoForChannelItem(PayChannelItem channelItem) {
        if (channelItem != null) {
            PayChannelItemVO vo = new PayChannelItemVO();
            KsBeanUtil.copyPropertiesThird(channelItem, vo);
            if (channelItem.getGateway() != null) {
                PayGatewayVO gatewayVO = new PayGatewayVO();
                KsBeanUtil.copyPropertiesThird(channelItem.getGateway(), gatewayVO);
                vo.setGateway(gatewayVO);
            }
            return vo;
        }
        return null;
    }

    /**
     * @description 封装支付渠道Response
     * @author  songhanlin
     * @date: 2021/6/3 上午11:34
     * @param channelItems 支付渠道
     * @return 支付渠道Response
     **/
    private PayChannelItemListResponse wrapperResponseListForChannelItems(List<PayChannelItem> channelItems) {
        PayChannelItemListResponse response = new PayChannelItemListResponse();
        List<PayChannelItemVO> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(channelItems)) {
            voList = channelItems.stream().map(this::wrapperVoForChannelItem)
                    .collect(Collectors
                            .toList());
        }
        response.setPayChannelItemVOList(voList);
        return response;
    }

    /**
     * @description 封装网关配置Response
     * @author  songhanlin
     * @date: 2021/6/3 上午11:34
     * @param payGatewayConfig 网关配置
     * @return 网关配置Response
     **/
    private PayGatewayConfigResponse wrapperResponseForGatewayConfig(PayGatewayConfig payGatewayConfig) {
        if (payGatewayConfig != null) {
            PayGatewayConfigResponse response = new PayGatewayConfigResponse();
            KsBeanUtil.copyPropertiesThird(payGatewayConfig, response);
            if (payGatewayConfig.getPayGateway() != null) {
                PayGatewayVO payGatewayVO = new PayGatewayVO();
                KsBeanUtil.copyPropertiesThird(payGatewayConfig.getPayGateway(), payGatewayVO);
                response.setPayGateway(payGatewayVO);
                return response;
            }
        }
        return null;
    }

    /**
     * @description 封装网关配置Response
     * @author  songhanlin
     * @date: 2021/6/3 上午11:34
     * @param payGatewayConfig 网关配置
     * @return 网关配置Response
     **/
    private PayGatewayConfigVO wrapperVoForGatewayConfig(PayGatewayConfig payGatewayConfig) {
        if (payGatewayConfig != null) {
            PayGatewayConfigVO vo = new PayGatewayConfigVO();
            KsBeanUtil.copyPropertiesThird(payGatewayConfig, vo);
            if (payGatewayConfig.getPayGateway() != null) {
                PayGatewayVO payGatewayVO = new PayGatewayVO();
                KsBeanUtil.copyPropertiesThird(payGatewayConfig.getPayGateway(), payGatewayVO);
                if (payGatewayVO.getName() == PayGatewayEnum.CREDIT) {
                    payGatewayVO.setAlias(payDataService.getCreditName());
                }
                vo.setPayGateway(payGatewayVO);
                return vo;
            }
        }
        return null;
    }

}
