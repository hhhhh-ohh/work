package com.wanmi.sbc.empower.pay.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.pay.gateway.PayGatewaySaveByTerminalTypeRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.PayGatewaySaveRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.WechatConfigSaveRequest;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayConfigVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.empower.bean.vo.WechatConfigVO;
import com.wanmi.sbc.empower.pay.constant.RedisConstant;
import com.wanmi.sbc.empower.pay.model.root.PayChannelItem;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.model.root.WechatConfig;
import com.wanmi.sbc.empower.pay.repository.ChannelItemRepository;
import com.wanmi.sbc.empower.pay.repository.GatewayConfigRepository;
import com.wanmi.sbc.empower.pay.repository.GatewayRepository;
import com.wanmi.sbc.empower.pay.repository.WechatConfigRepository;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.bean.dto.ReturnOrderDTO;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by sunkun on 2017/8/9.
 */
@Service
public class PayDataService {


    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private ChannelItemRepository channelItemRepository;

    @Autowired
    private GatewayConfigRepository gatewayConfigRepository;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired private WechatConfigRepository payWechatConfigRepository;


    /**
     * 获取网关列表
     *
     * @return
     */
    public List<PayGateway> queryGatewaysByStoreId(Long storeId) {
        return gatewayRepository.findByStoreId(storeId);
    }

    /***
     * 获得授信支付名
     * @return
     */
    public String getCreditName() {
        String creditName = redisUtil.getString(RedisConstant.CREDIT_NAME);
        if (StringUtils.isNotBlank(creditName)) {
            return creditName;
        }
        return "授信支付";
    }

    /***
     * 保存授信支付名
     * @return
     */
    public void saveCreditName(String name) {
        redisUtil.setString(RedisConstant.CREDIT_NAME, name);
    }

    /**
     * 获取网关
     *
     * @param id 网关id
     * @return
     */
    public PayGateway queryGateway(Long id) {
        return gatewayRepository.getOne(id);
    }

    /**
     * 保存网关
     *
     * @param payGateway
     */
    @Transactional
    public void saveGateway(PayGateway payGateway) {
        gatewayRepository.save(payGateway);
    }

    @Transactional
    public void modifyGateway(PayGateway payGateway) {
        gatewayRepository.update(payGateway.getId(), payGateway.getIsOpen(), payGateway.getType(),
                payGateway.getName());
    }

    /**
     * 获取网关下支付渠道项
     *
     * @param payGatewayEnum
     * @return
     */
    public List<PayChannelItem> queryItemByGatewayName(PayGatewayEnum payGatewayEnum) {
        return channelItemRepository.findByGatewayName(payGatewayEnum);
    }

    /**
     * 获取网关下开启的支付渠道项
     *
     * @param payGatewayEnum
     * @return
     */
    public List<PayChannelItem> queryOpenItemByGatewayName(PayGatewayEnum payGatewayEnum, TerminalType terminalType) {
        return channelItemRepository.findOpenItemByGatewayName(payGatewayEnum, terminalType);
    }

    /**
     * 获取渠道支付项
     *
     * @param id 渠道支付项id
     * @return
     */
    public PayChannelItem queryItemById(Long id) {
        return channelItemRepository.findById(id).orElse(null);
    }

    /**
     * 保存支付渠道项
     *
     * @param payChannelItem
     */
    @Transactional
    public void saveItem(PayChannelItem payChannelItem) {
        channelItemRepository.save(payChannelItem);
    }

    /**
     * 获取网关配置
     *
     * @param id 网关配置id
     * @return
     */
    public PayGatewayConfig queryConfig(Long id) {
        return gatewayConfigRepository.getOne(id);
    }

    /**
     * 根据网关名称获取网关配置
     *
     * @return
     */
    public PayGatewayConfig queryConfigByNameAndStoreId(PayGatewayEnum payGatewayEnum, Long storeId) {
        return gatewayConfigRepository.queryConfigByNameAndStoreId(payGatewayEnum, storeId);
    }


    // /**
    //  * 根据网关名称获取网关配置
    //  *
    //  * @return
    //  */
    // // todo 退款改造完 queryConfigByName 用 queryConfigByNameAndStoreId 代替
    // public PayGatewayConfig queryConfigByName(PayGatewayEnum payGatewayEnum) {
    //     return gatewayConfigRepository.queryConfigByName(payGatewayEnum);
    // }

    /**
     * 根据网关id获取网关配置
     *
     * @param gatewayId 网关id
     * @return
     */
    public PayGatewayConfig queryConfigByGatwayIdAndStoreId(Long gatewayId, Long storeId) {
        return gatewayConfigRepository.queryConfigByGatwayIdAndStoreId(gatewayId, storeId);
    }

    public List<PayGatewayConfig> queryConfigByOpenAndStoreId(Long storeId) {
        return gatewayConfigRepository.queryConfigByOpenAndStoreId(storeId);
    }

    /**
     * 保存网关配置
     *
     * @param payGatewayConfig
     */
    @Transactional
    public void saveConfig(PayGatewayConfig payGatewayConfig) {
        gatewayConfigRepository.save(payGatewayConfig);
    }

    /**
     * 保存支付配置
     *
     * @param payGatewayRequest
     */
    @Transactional
    public void savePayGateway(PayGatewaySaveRequest payGatewayRequest) {
        //保存网关
        PayGateway gateway = new PayGateway();
        gateway.setId(payGatewayRequest.getId());
        gateway.setIsOpen(payGatewayRequest.getIsOpen());
        gateway.setName(PayGatewayEnum.valueOf(payGatewayRequest.getName()));
        gateway.setType(payGatewayRequest.getType());
        this.modifyGateway(gateway);
        // 判断是否授信支付，如果是直接返回
        if (PayGatewayEnum.CREDIT.toValue().equals(payGatewayRequest.getName())) {
            return;
        }
        //保存网关配置
        PayGatewayConfig payGatewayConfig = this.queryConfigByGatwayIdAndStoreId(payGatewayRequest.getId(), payGatewayRequest.getStoreId());
        // if(Objects.nonNull(payGatewayConfig)){
        PayGatewayConfig config = new PayGatewayConfig();
        config.setApiKey(payGatewayRequest.getPayGatewayConfig().getApiKey());
        config.setApiV3Key(payGatewayRequest.getPayGatewayConfig().getApiV3Key());
        config.setMerchantSerialNumber(payGatewayRequest.getPayGatewayConfig().getMerchantSerialNumber());
        config.setSecret(payGatewayRequest.getPayGatewayConfig().getSecret());
        config.setAppId(payGatewayRequest.getPayGatewayConfig().getAppId());
        config.setPrivateKey(payGatewayRequest.getPayGatewayConfig().getPrivateKey());
        config.setPublicKey(payGatewayRequest.getPayGatewayConfig().getPublicKey());
        config.setAppId2(payGatewayRequest.getPayGatewayConfig().getAppId2());
        config.setPayGateway(PayGateway.builder().id(payGatewayRequest.getId()).build());
        config.setBossBackUrl(payGatewayRequest.getPayGatewayConfig().getBossBackUrl());
        config.setPcBackUrl(payGatewayRequest.getPayGatewayConfig().getPcBackUrl());
        config.setPcWebUrl(payGatewayRequest.getPayGatewayConfig().getPcWebUrl());
        config.setAccount(payGatewayRequest.getPayGatewayConfig().getAccount());
        config.setOpenPlatformAppId(payGatewayRequest.getPayGatewayConfig().getOpenPlatformAppId());
        config.setOpenPlatformSecret(payGatewayRequest.getPayGatewayConfig().getOpenPlatformSecret());
        config.setOpenPlatformAccount(payGatewayRequest.getPayGatewayConfig().getOpenPlatformAccount());
        config.setOpenPlatformApiKey(payGatewayRequest.getPayGatewayConfig().getOpenPlatformApiKey());
        if (payGatewayConfig != null) {
            config.setId(payGatewayConfig.getId());
            config.setCreateTime(payGatewayConfig.getCreateTime());
            config.setWxPayCertificate(payGatewayConfig.getWxPayCertificate());
            config.setWxOpenPayCertificate(payGatewayConfig.getWxOpenPayCertificate());
            config.setStoreId(payGatewayConfig.getStoreId());
            if (StringUtils.isBlank(config.getAppId())) {
                config.setAppId(payGatewayConfig.getAppId());
            }
            if (StringUtils.isBlank(config.getSecret())) {
                config.setSecret(payGatewayConfig.getSecret());
            }
            if (StringUtils.isBlank(config.getOpenPlatformAppId())) {
                config.setOpenPlatformAppId(payGatewayConfig.getOpenPlatformAppId());
            }
            if (StringUtils.isBlank(config.getOpenPlatformSecret())) {
                config.setOpenPlatformSecret(payGatewayConfig.getOpenPlatformSecret());
            }
        }
        if (config.getId() == null || config.getCreateTime() == null) {
            config.setCreateTime(LocalDateTime.now());
            config.setStoreId(payGatewayRequest.getStoreId());
        }
        this.saveConfig(config);

        // payChannelItem 为系统预置数据 --保存渠道支付项
        if (PayGatewayEnum.LAKALA.toValue().equals(payGatewayRequest.getName())) {
             payGatewayRequest.getChannelItemList().forEach(item -> {
                 PayChannelItem payChannelItem = this.queryItemById(item.getId());
                 payChannelItem.setIsOpen(item.getIsOpen());
                 this.saveItem(payChannelItem);
             });
         }
    }
    /**
     * @description 获取用户唤起的第三方支付类型
     * @author  edz
     * @date: 2021-04-13 18:00
     * @param businessId
     * @return java.lang.String
     **/
    public String getPayChannelItemByBusinessId(String businessId){
        PayTradeRecordResponse response = payTradeRecordQueryProvider.getTradeRecordByOrderCode(TradeRecordByOrderCodeRequest.builder()
                .orderId(businessId).build()).getContext();


        if (Objects.isNull(response)){
            return "";
        }
        PayChannelItem payChannelItem = channelItemRepository.findById(response.getChannelItemId()).orElseGet(PayChannelItem::new);
        String channel = payChannelItem.getChannel();
        return StringUtils.isNotBlank(channel) ? channel.toUpperCase() : "";
    }


    /**
     * 保存支付配置
     *
     * @param saveRequest
     */
    @Transactional
    public void savePayGatewayByTerminalType(PayGatewaySaveByTerminalTypeRequest saveRequest) {
        PayGatewayConfig config = this.queryConfigByNameAndStoreId(saveRequest.getPayGatewayEnum(), saveRequest.getStoreId());
        if (config == null) {
            config = new PayGatewayConfig();
            config.setStoreId(saveRequest.getStoreId());
            config.setPayGateway(gatewayRepository.queryByNameAndStoreId(saveRequest.getPayGatewayEnum(), saveRequest.getStoreId()));
        }
        if (TerminalType.APP == saveRequest.getTerminalType()) {
            config.setOpenPlatformAppId(saveRequest.getAppId());
            config.setOpenPlatformSecret(saveRequest.getSecret());
            gatewayConfigRepository.save(config);
        } else if (TerminalType.H5 == saveRequest.getTerminalType()) {
            config.setAppId(saveRequest.getAppId());
            config.setSecret(saveRequest.getSecret());
            gatewayConfigRepository.save(config);
        }
    }

    public List<PayChannelItemVO> findByIdIn(List<Long> ids){
        return KsBeanUtil.convert(channelItemRepository.findByIdIn(ids), PayChannelItemVO.class);
    }

    /**
     * 缓存配置
     * @param id
     */
    public void setCache(Long id) {
        PayGateway payGateway = this.queryGateway(id);
        PayGatewayVO payGatewayVO = KsBeanUtil.convert(payGateway, PayGatewayVO.class);
        payGatewayVO.setConfig(KsBeanUtil.convert(payGateway.getConfig(), PayGatewayConfigVO.class));
        payGatewayVO.setPayChannelItemList(KsBeanUtil.convertList(payGateway.getPayChannelItemList(), PayChannelItemVO.class));
        redisUtil.setString(RedisKeyConstant.LAKALA_PAY_SETTING, JSONObject.toJSONString(payGatewayVO));
    }

    /**
     * 更新证书
     * @param payCertificateType
     * @param certificate
     */
    public void updateLakalaPayCache(Integer payCertificateType, byte[] certificate){
        String cache = redisUtil.getString(RedisKeyConstant.LAKALA_PAY_SETTING);
        if(StringUtils.isBlank(cache)) {
            return;
        }
        PayGateway payGateway = JSONObject.parseObject(cache, PayGateway.class);
        if (Constants.THREE == payCertificateType) {
            payGateway.getConfig().setWxPayCertificate(certificate);
        } else {
            payGateway.getConfig().setWxOpenPayCertificate(certificate);
        }
        PayGatewayVO payGatewayVO = KsBeanUtil.convert(payGateway, PayGatewayVO.class);
        payGatewayVO.setConfig(KsBeanUtil.convert(payGateway.getConfig(), PayGatewayConfigVO.class));
        payGatewayVO.setPayChannelItemList(KsBeanUtil.convertList(payGateway.getPayChannelItemList(), PayChannelItemVO.class));
        redisUtil.setString(RedisKeyConstant.LAKALA_PAY_SETTING, JSONObject.toJSONString(payGateway));
    }

    /**
     * 更新拉卡拉收银台证书
     * @param payCertificateType
     * @param certificate
     */
    public void updateLakalaCasherPayCache(Integer payCertificateType, byte[] certificate){
        String cache = redisUtil.getString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING);
        if(StringUtils.isBlank(cache)) {
            return;
        }
        PayGateway payGateway = JSONObject.parseObject(cache, PayGateway.class);
        if (Constants.THREE == payCertificateType) {
            payGateway.getConfig().setWxPayCertificate(certificate);
        } else {
            payGateway.getConfig().setWxOpenPayCertificate(certificate);
        }
        redisUtil.setString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING, JSONObject.toJSONString(payGateway));
    }

    /**
     * =========================================== - 微信配置 - ===============================================
     */
    /**
     * @description 更新商家指定场景的微信配置
     * @author wur
     * @date: 2022/12/2 17:56
     * @param request
     * @return
     */
    @Transactional
    public void updatePayWechatConfig(WechatConfigSaveRequest request) {
        payWechatConfigRepository.update(
                request.getSceneType(),
                request.getStoreId(),
                request.getAppId(),
                request.getSecret());
    }

    /**
     * @description
     * @author  wur
     * @date: 2023/4/4 9:52
     * @param request 
     * @return 
     **/
    @Transactional
    public void savePayWechatConfig(WechatConfigSaveRequest request) {
        WechatConfig wechatConfig = payWechatConfigRepository.findBySceneTypeAndStoreId(request.getSceneType(), request.getStoreId());
        if (Objects.isNull(wechatConfig)) {
            wechatConfig = new WechatConfig();
        }
        wechatConfig.setStoreId(request.getStoreId());
        wechatConfig.setSceneType(request.getSceneType());
        wechatConfig.setAppId(request.getAppId());
        wechatConfig.setSecret(request.getSecret());
        payWechatConfigRepository.save(wechatConfig);
    }

    public WechatConfig findByWxPayTypeAndStoreId(Integer wxPayType, Long storeId) {
        Integer sceneType = this.getSceneTypeByWxPayType(wxPayType);
        if (Objects.isNull(storeId)) {
            storeId = Constants.BOSS_DEFAULT_STORE_ID;
        }
        return payWechatConfigRepository.findBySceneTypeAndStoreId(sceneType, storeId);
    }

    /**
     * @description  查询商家指定场景的微信配置
     * @author  wur
     * @date: 2022/12/2 17:56
     * @param sceneType
     * @param storeId
     * @return
     **/
    public WechatConfig findBySceneTypeAndStoreId(Integer sceneType, Long storeId) {
        if (Objects.isNull(storeId)) {
            storeId = Constants.BOSS_DEFAULT_STORE_ID;
        }
        return payWechatConfigRepository.findBySceneTypeAndStoreId(sceneType, storeId);
    }

    /**
     * @description   查询商家所有场景的微信配置
     * @author  wur
     * @date: 2022/12/2 17:57
     * @param sceneTypeList
     * @param storeId
     * @return
     **/
    public List<WechatConfig> findBySceneTypeInAndStoreId(List<Integer> sceneTypeList, Long storeId) {
        if (Objects.isNull(storeId)) {
            storeId = Constants.BOSS_DEFAULT_STORE_ID;
        }
        return payWechatConfigRepository.findBySceneTypeInAndStoreId(sceneTypeList, storeId);
    }

    /**
     * @description   查询商家所有场景的微信配置
     * @author  wur
     * @date: 2022/12/2 17:57
     * @param storeId
     * @return
     **/
    public List<WechatConfig> findByStoreId( Long storeId) {
        return payWechatConfigRepository.findByStoreId(storeId);
    }

    /**
     * @description
     * @author  wur
     * @date: 2022/12/3 10:08
     * @return
     **/
    private Integer getSceneTypeByWxPayType(Integer wxPayType) {
        Integer sceneType = NumberUtils.INTEGER_ZERO;
        if (Objects.equals(wxPayType, 3)) {
            sceneType = NumberUtils.INTEGER_ONE;
        } else if(Objects.equals(wxPayType, 4)) {
            sceneType = 2;
        }
        return sceneType;
    }

    public WechatConfigVO wrapperWechatConfigVo(WechatConfig config) {
        if (config != null) {
            WechatConfigVO configVO = new WechatConfigVO();
            KsBeanUtil.copyPropertiesThird(config, configVO);
            return configVO;
        }
        return null;
    }

    /**
     * 缓存配置
     * @param id
     */
    public void setLklCasherCache(Long id) {
        PayGateway payGateway = this.queryGateway(id);
        PayGatewayVO payGatewayVO = KsBeanUtil.convert(payGateway, PayGatewayVO.class);
        payGatewayVO.setConfig(KsBeanUtil.convert(payGateway.getConfig(), PayGatewayConfigVO.class));
        payGatewayVO.setPayChannelItemList(KsBeanUtil.convertList(payGateway.getPayChannelItemList(), PayChannelItemVO.class));
        redisUtil.setString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING, JSONObject.toJSONString(payGatewayVO));
    }


}
