package com.wanmi.sbc.setting.provider.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigContextModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.GoodsSecondaryAuditRequest;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.*;
import com.wanmi.sbc.setting.audit.AuditService;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.setting.config.Config;
import com.wanmi.sbc.setting.config.ConfigMapper;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuditQueryController implements AuditQueryProvider {
    @Autowired
    private AuditService auditService;

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public BaseResponse<BossGoodsAuditResponse> isBossGoodsAudit() {
        BossGoodsAuditResponse response = new BossGoodsAuditResponse();
        response.setAudit(auditService.isBossGoodsAudit());

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<SupplierGoodsAuditResponse> isSupplierGoodsAudit() {
        SupplierGoodsAuditResponse response = new SupplierGoodsAuditResponse();
        response.setAudit(auditService.isSupplierGoodsAudit());

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<SupplierOrderAuditResponse> isSupplierOrderAudit() {
        SupplierOrderAuditResponse response = new SupplierOrderAuditResponse();
        response.setAudit(auditService.isSupplierOrderAudit());

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<AuditConfigListResponse> listAuditConfig() {
        AuditConfigListResponse response = new AuditConfigListResponse();
        response.setConfigVOList(auditService.listAuditConfigs());

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CustomerAuditResponse> isCustomerAudit() {
        CustomerAuditResponse response = new CustomerAuditResponse();
        response.setAudit(auditService.isCustomerAudit());

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<TicketAuditResponse> isTicketAudit() {
        TicketAuditResponse response = new TicketAuditResponse();
        response.setAduit(auditService.isTicketAudit());

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CustomerInfoPerfectResponse> isPerfectCustomerInfo() {
        CustomerInfoPerfectResponse response = new CustomerInfoPerfectResponse();
        response.setPerfect(auditService.isPerfectCustomerInfo());

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<InvoiceConfigGetResponse> getInvoiceConfig() {
        InvoiceConfigGetResponse response = new InvoiceConfigGetResponse();

        Config config = auditService.getInvoiceConfig();

        KsBeanUtil.copyPropertiesThird(config, response);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<TradeConfigListResponse> listTradeConfig() {
        TradeConfigListResponse response = new TradeConfigListResponse();

        List<Config> configList = auditService.listTradeConfig();

        response.setConfigVOList(KsBeanUtil.convertList(configList, ConfigVO.class));

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<TradeConfigGetByTypeResponse> getTradeConfigByType(@RequestBody @Valid TradeConfigGetByTypeRequest request) {
        TradeConfigGetByTypeResponse response = new TradeConfigGetByTypeResponse();
        Config config = auditService.getTradeConfigByType(request.getConfigType());
        if (config != null) {
            response = configMapper.ConfigToTradeConfigGetByTypeResponse(config);
        }
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<OrderAutoReceiveConfigGetResponse> getOrderAutoReceiveConfig() {
        OrderAutoReceiveConfigGetResponse response = new OrderAutoReceiveConfigGetResponse();

        Config config = auditService.getOrderAutoReceiveConfig();

        KsBeanUtil.copyPropertiesThird(config, response);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<UserAuditResponse> getIsVisitWithLogin() {
        UserAuditResponse response = new UserAuditResponse();
        response.setAudit(auditService.isUserAudit());
        return BaseResponse.success(response);
    }

    /**
     * 查询pc端商品列表默认展示维度详情
     *
     * @return
     */
    @Override
    public BaseResponse<GoodsDisplayConfigGetResponse> getGoodsDisplayConfigForPc() {
        GoodsDisplayConfigGetResponse response = auditService.getGoodsDisplay(0);
        return BaseResponse.success(response);
    }

    /**
     * 查询移动端商品列表默认展示维度详情
     *
     * @return
     */
    @Override
    public BaseResponse<GoodsDisplayConfigGetResponse> getGoodsDisplayConfigForMobile() {
        GoodsDisplayConfigGetResponse response = auditService.getGoodsDisplay(1);
        return BaseResponse.success(response);
    }

    /**
     * 根据键查询配置
     *
     * @param configQueryRequest
     * @return
     */
    @Override
    public BaseResponse<AuditConfigListResponse> getByConfigKey(@RequestBody @Valid ConfigQueryRequest configQueryRequest) {
        AuditConfigListResponse response = new AuditConfigListResponse();
        List<Config> configList = null;
        if (StringUtils.isNotBlank(configQueryRequest.getConfigKey())) {
            configList = auditService.findByConfigKey(configQueryRequest.getConfigKey());
        } else {
            configList = auditService.findByConfigKeyList(configQueryRequest.getConfigKeyList());
        }
        response.setConfigVOList(KsBeanUtil.convertList(configList, ConfigVO.class));
        return BaseResponse.success(response);
    }

    /**
     * 是否开启商品审核
     *
     * @return true:开启 false:不开启
     */
    @Override
    public BaseResponse<BossGoodsEvaluateResponse> isGoodsEvaluate() {
        BossGoodsEvaluateResponse response = new BossGoodsEvaluateResponse();
        response.setEvaluate(auditService.isGoodsEvaluate());

        return BaseResponse.success(response);
    }

    /**
     * 无货商品是否展示
     *
     * @return true:展示 false:不展示
     */
    @Override
    public BaseResponse<BossGoodsOutOfStockShowResponse> isGoodsOutOfStockShow() {
        return BaseResponse.success(BossGoodsOutOfStockShowResponse.builder()
                .outOfStockShow(auditService.isGoodsOutOfStockShow()).build());
    }

    /**
     * 移动端商品展示字段设置
     * @param request
     * @return
     */
    @Override
    public BaseResponse<GoodsColumnShowResponse> goodsColumnShowForMobile(ConfigContextModifyByTypeAndKeyRequest request) {
        request.setConfigKey(ConfigKey.GOODS_COLUMN_SHOW);
        return BaseResponse.success(auditService.goodsColumnShowForMobile(request));
    }

    @Override
    public BaseResponse<BossGoodsAuditResponse> isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest request) {
        BossGoodsAuditResponse response = new BossGoodsAuditResponse();
        response.setAudit(auditService.isBossGoodsSecondaryAudit(request));
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<ConfigVO> posterList() {
        Config config = auditService
                .findByConfigTypeAndConfigKeyAndDelFlag(ConfigType.FLASH_GOODS_SALE_POSTER.toValue()
                        ,ConfigKey.FLASH_GOODS_SALE.toString()
                        , DeleteFlag.NO);

        return BaseResponse.success(KsBeanUtil.convert(config,ConfigVO.class));
    }

    @Override
    public BaseResponse<ConfigVO> recommendConfig() {
        Config config = auditService
                .findByConfigTypeAndConfigKeyAndDelFlag(ConfigType.RECOMMEND_STATUS.toValue()
                        ,ConfigKey.RECOMMEND.toString()
                        , DeleteFlag.NO);
        return BaseResponse.success(KsBeanUtil.convert(config,ConfigVO.class));
    }

    @Override
    public BaseResponse<ConfigVO> isAllowFlashPromotionOriginalPrice() {
        Config config = auditService
                .findByConfigTypeAndConfigKeyAndDelFlag(ConfigType.FLASH_PROMOTION_ORIGINAL_PRICE.toValue()
                        ,ConfigKey.FLASH_GOODS_SALE.toString()
                        ,DeleteFlag.NO);
        return BaseResponse.success(KsBeanUtil.convert(config,ConfigVO.class));
    }

    @Override
    public BaseResponse<ConfigVO> isAllowFlashSaleOriginalPrice() {
        Config config = auditService
                .findByConfigTypeAndConfigKeyAndDelFlag(ConfigType.FLASH_SALE_ORIGINAL_PRICE.toValue()
                        ,ConfigKey.FLASH_GOODS_SALE.toString()
                        ,DeleteFlag.NO);
        return BaseResponse.success(KsBeanUtil.convert(config,ConfigVO.class));
    }

    @Override
    public BaseResponse<ConfigVO> findShowType() {
        Config config = auditService
                .findByConfigTypeAndConfigKeyAndDelFlag(ConfigType.FIND_SHOW_TYPE.toValue()
                        ,ConfigKey.RECOMMEND.toString()
                        , DeleteFlag.NO);
        return BaseResponse.success(KsBeanUtil.convert(config,ConfigVO.class));
    }

    @Cacheable(key = "'ORDER_CONFIG_SIMPLIFY'", value = CacheConstants.GLOBAL_CACHE_NAME)
    @Override
    public BaseResponse<RecommendStatusConfigResponse> config() {
        RecommendStatusConfigResponse recommendConfigResponse = new RecommendStatusConfigResponse();
        Config config = auditService
                .findByConfigTypeAndConfigKeyAndDelFlag(ConfigType.RECOMMEND_STATUS.toValue()
                        ,ConfigKey.RECOMMEND.toString()
                        , DeleteFlag.NO);
        recommendConfigResponse.setRecommendStatus(config.getStatus());
        config = auditService
                .findByConfigTypeAndConfigKeyAndDelFlag(ConfigType.FIND_SHOW_TYPE.toValue()
                        ,ConfigKey.RECOMMEND.toString()
                        , DeleteFlag.NO);
        recommendConfigResponse.setShowSort(config.getStatus());
        return BaseResponse.success(recommendConfigResponse);
    }

    @Cacheable(key = "'ORDER_CONFIG_LIST'", value = CacheConstants.GLOBAL_CACHE_NAME)
    @Override
    public BaseResponse<TradeConfigListResponse> cachedListTradeConfig() {
        TradeConfigListResponse response = new TradeConfigListResponse();

        List<Config> configList = auditService.listTradeConfig();

        response.setConfigVOList(KsBeanUtil.convertList(configList, ConfigVO.class));

        return BaseResponse.success(response);
    }
}
