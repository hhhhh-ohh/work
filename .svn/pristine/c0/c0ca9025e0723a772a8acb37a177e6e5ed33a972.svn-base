package com.wanmi.sbc.marketing.newplugin;

import com.alibaba.fastjson.JSON;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.SpringContextHolder;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.MarketingPluginBaseRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsCartPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.constant.MarketingPluginConstant;
import com.wanmi.sbc.marketing.bean.dto.MarketingPluginConfigDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.newplugin.bean.MarketingPluginBaseParam;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.common.MarketingResponseProcess;
import com.wanmi.sbc.marketing.pluginconfig.service.MarketingPluginConfigService;
import com.wanmi.sbc.marketing.util.mapper.MarketingGoodsInfoMapper;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import jakarta.annotation.Resource;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className MarketingPluginAdapter
 * @description
 * @date 2021/5/19 10:41
 */
@Slf4j
@Service
@Scope("prototype")
public class MarketingPluginAdapter implements MarketingPlugin {

    @Autowired MarketingPluginConfigService marketingPluginConfigService;

    @Autowired MarketingPluginBaseService marketingPluginBaseService;

    @Autowired MarketingGoodsInfoMapper marketingGoodsInfoMapper;

    @Resource
    MarketingPluginProxy pluginProxy;

    private Map<MarketingPluginType, MarketingPluginConfigDTO> map = new HashMap<>();

    private List<MarketingPluginConfigDTO> pluginConfigList;

    @Autowired private AuditQueryProvider auditQueryProvider;

    @Autowired private CustomerQueryProvider customerQueryProvider;

    static final String GOODS_DETAIL = "goodsDetail";
    static final String CHECK = "check";
    static final String CART_MARKETING = "cartMarketing";
    static final String TRADE_MARKETING = "tradeMarketing";

    @Autowired
    private Map<String, MarketingPluginInterface> marketingPluginInterface;

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {
        try {
            // 先设置初始值
            MarketingResponseProcess.setGoodsDetailDefaultResponse(
                    request.getGoodsInfoPluginRequest());

            request.setGoodsInfoPluginRequest(
                    marketingPluginBaseService.setGoodsStoreCateIdList(
                            request.getGoodsInfoPluginRequest()));
            // 获取当前方法名称
//            String method = Thread.currentThread().getStackTrace()[1].getMethodName();
            initContext();
            process(request, GOODS_DETAIL);
            GoodsInfoDetailPluginResponse response = MarketingContext.getResponse();
            // 当存在周期购时剔除加价购
            List<MarketingPluginLabelVO> pluginLabelVOS = response.getMarketingLabels();
            if (Objects.nonNull(pluginLabelVOS) &&
                    pluginLabelVOS.stream().anyMatch(g -> Objects.equals(g.getMarketingType(),
                        MarketingPluginType.BUY_CYCLE.getId()))){
                    pluginLabelVOS = pluginLabelVOS.stream().filter(g -> !Objects.equals(g.getMarketingType(),
                            MarketingPluginType.PREFERENTIAL.getId())).collect(Collectors.toList());
            }
            removeContext();
            return response;
        } catch (Exception e) {
            removeContext();
            log.error("{}", e);
            throw new SbcRuntimeException(e);
        }
    }

    @Override
    public List<MarketingPluginSimpleLabelVO> check(GoodsInfoPluginRequest request) {
//        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        process(request, CHECK);
        return MarketingContext.getResponse();
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {

        GoodsListPluginResponse response = new GoodsListPluginResponse();
        Map<String, List<MarketingPluginSimpleLabelVO>> retMap = new HashMap<>();
//        Method method =
//                ReflectionUtils.findMethod(this.getClass(), "check", GoodsInfoPluginRequest.class);
        retMap = this.listProcess(request, CHECK,MarketingPluginSimpleLabelVO.class);
        response.setSkuMarketingLabelMap(retMap);
        return response;
    }

    @Override
    public List<MarketingPluginLabelDetailVO> cartMarketing(GoodsInfoPluginRequest request) {
//        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        process(request, CART_MARKETING);
        return MarketingContext.getResponse();
    }

    @Override
    public List<MarketingPluginLabelDetailVO> tradeMarketing(GoodsInfoPluginRequest request) {
//        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        process(request, TRADE_MARKETING);
        return MarketingContext.getResponse();
    }

    /**
     * 购物车列表
     *
     * @param request
     * @return
     */
    public GoodsCartPluginResponse cartList(GoodsListPluginRequest request) {

        GoodsCartPluginResponse response = new GoodsCartPluginResponse();
        Map<String, List<MarketingPluginLabelDetailVO>> retMap = new HashMap<>();
//        Method method =
//                ReflectionUtils.findMethod(
//                        this.getClass(), CART_MARKETING, GoodsInfoPluginRequest.class);
        retMap = this.listProcess(request, CART_MARKETING,MarketingPluginLabelDetailVO.class);
        Map<String,Integer> originalPriceFlagMap = new HashMap<>();

        retMap.entrySet()
                .forEach(
                        entry -> {
                            if (CollectionUtils.isNotEmpty(entry.getValue())) {
                                List<MarketingPluginLabelDetailVO> retList = new ArrayList<>();
                                entry.getValue()
                                        .forEach(
                                                m -> {
                                                    if (!MarketingPluginConstant
                                                            .IMMEDIATE_BUY_FILTER
                                                            .contains(m.getMarketingType())) {
                                                        if (!request.getIepCustomerFlag()
                                                                && m.getMarketingType()
                                                                .equals(
                                                                        MarketingPluginType
                                                                                .ENTERPRISE_PRICE
                                                                                .getId())) {
                                                            return;
                                                        }
                                                        retList.add(m);
                                                    } else {
                                                        if(m.getMarketingType() == MarketingPluginType.FLASH_SALE.getId() ||
                                                                m.getMarketingType() == MarketingPluginType.FLASH_PROMOTION.getId()){
                                                            ConfigVO configVO = new ConfigVO();
                                                            //整点秒杀
                                                            if(m.getMarketingType() == MarketingPluginType.FLASH_SALE.getId()){
                                                                configVO = auditQueryProvider.isAllowFlashSaleOriginalPrice().getContext();
                                                            }
                                                            if(m.getMarketingType() == MarketingPluginType.FLASH_PROMOTION.getId()){
                                                                configVO = auditQueryProvider.isAllowFlashPromotionOriginalPrice().getContext();
                                                            }
                                                            if(Objects.nonNull(configVO) && Objects.nonNull(configVO.getStatus())){
                                                                originalPriceFlagMap.put(entry.getKey(),configVO.getStatus());
                                                            }
                                                        }
                                                    }
                                                });
                                entry.setValue(retList);
                            }
                        });

        response.setSkuMarketingLabelMap(retMap);
        response.setOriginalPriceFlagMap(originalPriceFlagMap);
        return response;
    }

    public GoodsTradePluginResponse tradeList(GoodsListPluginRequest request) {
        GoodsTradePluginResponse response = new GoodsTradePluginResponse();
        Map<String, List<MarketingPluginLabelDetailVO>> retMap = new HashMap<>();
//        Method method =
//                ReflectionUtils.findMethod(
//                        this.getClass(), CART_MARKETING, GoodsInfoPluginRequest.class);
        retMap = this.listProcess(request, CART_MARKETING,MarketingPluginLabelDetailVO.class);

        response.setSkuMarketingLabelMap(retMap);
        return response;
    }

    /**
     * 立即购买
     *
     * @param request
     * @return
     */
    public GoodsTradePluginResponse immediateBuy(GoodsListPluginRequest request) {
        GoodsTradePluginResponse response = tradeList(request);
        response.getSkuMarketingLabelMap()
                .entrySet()
                .forEach(
                        entry -> {
                            if (CollectionUtils.isNotEmpty(entry.getValue())) {
                                List<MarketingPluginLabelDetailVO> retList = new ArrayList<>();
                                final boolean[] flag = {true};
                                entry.getValue()
                                        .forEach(
                                                m -> {
                                                    if (!MarketingPluginConstant
                                                            .IMMEDIATE_BUY_FILTER
                                                            .contains(m.getMarketingType())) {
                                                        if (!request.getIepCustomerFlag()
                                                                && m.getMarketingType()
                                                                        .equals(
                                                                                MarketingPluginType
                                                                                        .ENTERPRISE_PRICE
                                                                                        .getId())) {
                                                            return;
                                                        }
                                                        retList.add(m);
                                                    }
                                                });
                                entry.setValue(retList);
                            }
                        });
        return response;
    }

    /**
     * 购物车去结算
     *
     * @param request
     * @return
     */
    public GoodsTradePluginResponse confirm(GoodsListPluginRequest request) {
        GoodsTradePluginResponse response = tradeList(request);

        response.getSkuMarketingLabelMap()
                .entrySet()
                .forEach(
                        entry -> {
                            if (CollectionUtils.isNotEmpty(entry.getValue())) {
                                List<MarketingPluginLabelDetailVO> retList = new ArrayList<>();
                                entry.getValue()
                                        .forEach(
                                                m -> {
                                                    if (!MarketingPluginConstant.CONFIRM_FILTER
                                                            .contains(m.getMarketingType())) {
                                                        if (!request.getIepCustomerFlag()
                                                                && m.getMarketingType()
                                                                        .equals(
                                                                                MarketingPluginType
                                                                                        .ENTERPRISE_PRICE
                                                                                        .getId())) {
                                                            return;
                                                        }
                                                        retList.add(m);
                                                    }
                                                });
                                entry.setValue(retList);
                            }
                        });

        return response;
    }

    /**
     * 购物车去结算
     *
     * @param request
     * @return
     */
    public GoodsTradePluginResponse commit(GoodsListPluginRequest request) {
        GoodsTradePluginResponse response = tradeList(request);

        response.getSkuMarketingLabelMap()
                .entrySet()
                .forEach(
                        entry -> {
                            if (CollectionUtils.isNotEmpty(entry.getValue())) {
                                List<MarketingPluginLabelDetailVO> retList = new ArrayList<>();

                                entry.getValue().stream()
                                        .forEach(
                                                m -> {
                                                    if (!MarketingPluginConstant.COMMIT_FILTER
                                                            .contains(m.getMarketingType())) {
                                                        if (!request.getIepCustomerFlag()
                                                                && m.getMarketingType()
                                                                        .equals(
                                                                                MarketingPluginType
                                                                                        .ENTERPRISE_PRICE
                                                                                        .getId())) {
                                                            return;
                                                        }
                                                        retList.add(m);
                                                    }
                                                });
                                entry.setValue(retList);
                            }
                        });

        return response;
    }


    private <T extends MarketingPluginSimpleLabelVO> Map<String, List<T>> listProcess(
            GoodsListPluginRequest request, String method,Class<T> t) {
        try {
            Map<String, List<T>> retMap = new HashMap<>();
            // 先设置初始值
            initContext();
            pluginConfigList = marketingPluginConfigService.getListByCache();

            marketingPluginBaseService.setBaseRequest(
                    request.getGoodsInfoPluginRequests(),
                    request.getCustomerId(),
                    true,
                    pluginConfigList,
                    request.getStoreId());

            GoodsInfoPluginRequest goodsInfoPluginRequest = new GoodsInfoPluginRequest();

            goodsInfoPluginRequest.setCustomerId(request.getCustomerId());
            CustomerVO customerVo = MarketingContext.getBaseRequest().getCustomerVO();
            if (Objects.nonNull(customerVo)){
                goodsInfoPluginRequest.setEnterpriseCheckState(customerVo.getEnterpriseCheckState());
            }

            goodsInfoPluginRequest.setMarketingPluginType(request.getMarketingPluginType());
            goodsInfoPluginRequest.setTerminalSource(request.getTerminalSource());
            goodsInfoPluginRequest.setStoreId(request.getStoreId());
            for (GoodsInfoSimpleVO goodsInfoSimpleVO : request.getGoodsInfoPluginRequests()) {
                goodsInfoPluginRequest.setGoodsInfoPluginRequest(goodsInfoSimpleVO);
                goodsInfoPluginRequest.setExcludeMarketingPluginList(Lists.newArrayList());
                switch (method){
                    case CART_MARKETING -> {
                        var marketingPluginLabelDetailVOS = this.cartMarketing(goodsInfoPluginRequest);
                        if (CollectionUtils.isNotEmpty(marketingPluginLabelDetailVOS)) {
                            retMap.put(goodsInfoSimpleVO.getGoodsInfoId(), (List<T>) marketingPluginLabelDetailVOS);
                        }
                    }
                    case CHECK -> {
                        List<MarketingPluginSimpleLabelVO> marketingPluginSimpleLabelVOList = this.check(goodsInfoPluginRequest);
                        if (CollectionUtils.isNotEmpty(marketingPluginSimpleLabelVOList)) {
                            retMap.put(goodsInfoSimpleVO.getGoodsInfoId(), (List<T>) marketingPluginSimpleLabelVOList);
                        }
                    }
                    default -> log.error("listProcess: 未匹配到方法");
                }
//                List<T> labelList =
//                        (List<T>)
////                                ReflectionUtils.invokeMethod(method, this, goodsInfoPluginRequest);
//                if (CollectionUtils.isNotEmpty(labelList)) {
//                    retMap.put(goodsInfoSimpleVO.getGoodsInfoId(), labelList);
//                }
                MarketingContext.removeResponse();
            }
            return retMap;
        } catch (Exception e) {
            log.error("MarketingPluginAdapter => goodsList is error => ", e);
            throw new SbcRuntimeException(e);
        } finally {
            removeContext();
        }
    }

    /**
     * 主处理方法
     *
     * @param request 泛型的request请求，需要继承MarketingPluginBaseRequest
     * @param method 方法类型
     * @param <T> 泛型
     */
    private <T extends MarketingPluginBaseRequest> void process(T request, String method) {
        if (CollectionUtils.isEmpty(pluginConfigList)) {
            pluginConfigList = marketingPluginConfigService.getListByCache();
        }
        if (Objects.nonNull(MarketingContext.getBaseRequest()) && Objects.nonNull(MarketingContext.getBaseRequest().getCustomerVO())){
            CustomerVO customerVO = MarketingContext.getBaseRequest().getCustomerVO();
            request.setEnterpriseCheckState(customerVO.getEnterpriseCheckState());
        } else {
            if(StringUtils.isNotBlank(request.getCustomerId())){
                //企业会员信息维护
                CustomerGetByIdResponse customerGetByIdResponse = customerQueryProvider.getCustomerNoThirdImgById(CustomerGetByIdRequest.builder().customerId(request.getCustomerId()).build()).getContext();
                if(Objects.nonNull(customerGetByIdResponse) && Objects.nonNull(customerGetByIdResponse.getEnterpriseCheckState())){
                    request.setEnterpriseCheckState(customerGetByIdResponse.getEnterpriseCheckState());
                }
            }
        }

        if (CollectionUtils.isNotEmpty(pluginConfigList)) {
            map =
                    pluginConfigList.stream()
                            .collect(
                                    Collectors.toMap(
                                            MarketingPluginConfigDTO::getMarketingType, a -> a));

            // 如果指定营销
            if (request != null && request.getMarketingPluginType() != null) {
                MarketingPluginConfigDTO marketingPluginConfig =
                        map.get(request.getMarketingPluginType());
                if (marketingPluginConfig != null) {
                    pluginConfigList = new ArrayList<>();
                    if (Objects.isNull(request.getHandlePosit())
                            || request.getHandlePosit()) {
                        for (MarketingPluginType marketingPluginType :
                                marketingPluginConfig.getCoexist()) {
                            pluginConfigList.add(map.get(marketingPluginType));
                        }
                    }
                    pluginConfigList.add(marketingPluginConfig);
                    pluginConfigList.sort(
                            Comparator.comparingInt(MarketingPluginConfigDTO::getSort));
                    execSinglePlugin(request, pluginConfigList, method);
                    return;
                }
            }

            execPlugin(request, pluginConfigList, method, -1);
        }
    }

    /**
     * 不指定类型走营销插件
     *
     * @param request 泛型请求参数
     * @param pluginConfigList 插件集合
     * @param method 方法名称
     * @param <T>
     */
    @SneakyThrows
    private <T extends MarketingPluginBaseRequest> void execPlugin(
            T request,
            List<MarketingPluginConfigDTO> pluginConfigList,
            String method,
            int currentSort) {
        //需要强制排除的营销
        List<MarketingPluginType> excludeMarketingPluginList = request.getExcludeMarketingPluginList();
        log.info("营销插件execPlugin---------，excludeMarketingPluginList={}", JSON.toJSONString(excludeMarketingPluginList));
        for (MarketingPluginConfigDTO pluginConfig : pluginConfigList) {
            log.info("营销插件execPlugin---------，pluginConfig={}， currentSort={}", JSON.toJSONString(pluginConfig), currentSort);
            if (pluginConfig.getSort() <= currentSort) {
                continue;
            }
            if (CollectionUtils.isNotEmpty(excludeMarketingPluginList) &&
                    excludeMarketingPluginList.contains(pluginConfig.getMarketingType())) {
                continue;
            }
            if (marketingPluginInterface.containsKey(pluginConfig.getMarketingType().getServiceType())) {

                pluginProxy.setPlugin(marketingPluginInterface.get(pluginConfig.getMarketingType().getServiceType()));
//                pluginProxy.setPlugin(
//                        SpringContextHolder.getBean(
//                                pluginConfig.getMarketingType().getServiceType()));
                pluginProxy.setMarketingPluginType(pluginConfig.getMarketingType());
                /**
                 * 注意MarketingPluginProxy新增方法，在此处添加@see，此处为动态调用方法，为防止下层查找调用方找不到
                 *
                 * @see MarketingPluginProxy#goodsDetail(GoodsInfoPluginRequest)
                 * @see MarketingPluginProxy#check(GoodsInfoPluginRequest)
                 * @see MarketingPluginProxy#cartMarketing(GoodsInfoPluginRequest) 
                 * @see MarketingPluginProxy#tradeMarketing(GoodsInfoPluginRequest) 
                 */
//                Boolean flag =
//                        (Boolean)
//                                pluginProxy
//                                        .getClass()
//                                        .getDeclaredMethod(method, GoodsInfoPluginRequest.class)
//                                        .invoke(pluginProxy, request);
                var flag = Boolean.FALSE;
                switch (method){
                    case CHECK -> {
                        GoodsInfoPluginRequest goodsInfoPluginRequest = (GoodsInfoPluginRequest) request;
                        flag = pluginProxy.check(goodsInfoPluginRequest);
                    }
                    case GOODS_DETAIL -> {
                        GoodsInfoPluginRequest goodsInfoPluginRequest = (GoodsInfoPluginRequest) request;
                        flag = pluginProxy.goodsDetail(goodsInfoPluginRequest);
                    }
                    case CART_MARKETING -> {
                        GoodsInfoPluginRequest goodsInfoPluginRequest = (GoodsInfoPluginRequest) request;
                        flag = pluginProxy.cartMarketing(goodsInfoPluginRequest);
                    }
                    case TRADE_MARKETING -> {
                        GoodsInfoPluginRequest goodsInfoPluginRequest = (GoodsInfoPluginRequest) request;
                        flag = pluginProxy.tradeMarketing(goodsInfoPluginRequest);
                    }
                    default -> log.error("execSinglePlugin: 未匹配相关方法");
                }
                currentSort = pluginConfig.getSort();
                if (flag) {
                    if (CollectionUtils.isEmpty(pluginConfig.getCoexist())) {
                        break;
                    }
                    List<MarketingPluginConfigDTO> list = new ArrayList<>();

                    for (MarketingPluginType marketingPluginType : pluginConfig.getCoexist()) {
                        list.add(map.get(marketingPluginType));
                    }
                    if (CollectionUtils.isNotEmpty(list)) {
                        list.sort(Comparator.comparingInt(MarketingPluginConfigDTO::getSort));
                        execPlugin(request, list, method, currentSort);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 指定营销类型进行处理
     *
     * @param request 泛型请求参数
     * @param pluginConfigList 插件集合
     * @param method 方法名称
     * @param <T>
     */
    @SneakyThrows
    private <T extends MarketingPluginBaseRequest> void execSinglePlugin(
            T request, List<MarketingPluginConfigDTO> pluginConfigList, String method) {

        for (MarketingPluginConfigDTO pluginConfig : pluginConfigList) {

            if (SpringContextHolder.getApplicationContext()
                    .containsBean(pluginConfig.getMarketingType().getServiceType())) {
                pluginProxy.setPlugin(
                        marketingPluginInterface.get(
                                pluginConfig.getMarketingType().getServiceType()));
                pluginProxy.setMarketingPluginType(pluginConfig.getMarketingType());
                /**
                 * 注意MarketingPluginProxy新增方法，在此处添加@see，此处为动态调用方法，为防止下层查找调用方找不到
                 *
                 * @see MarketingPluginProxy#goodsDetail(GoodsInfoPluginRequest)
                 * @see MarketingPluginProxy#check(GoodsInfoPluginRequest)
                 * @see MarketingPluginProxy#cartMarketing(GoodsInfoPluginRequest) 
                 * @see MarketingPluginProxy#tradeMarketing(GoodsInfoPluginRequest) 
                 * 
                 */
//                pluginProxy
//                        .getClass()
//                        .getDeclaredMethod(method, GoodsInfoPluginRequest.class)
//                        .invoke(pluginProxy, request);
                switch (method){
                    case CHECK -> {
                        GoodsInfoPluginRequest goodsInfoPluginRequest = (GoodsInfoPluginRequest) request;
                        pluginProxy.check(goodsInfoPluginRequest);
                    }
                    case GOODS_DETAIL -> {
                        GoodsInfoPluginRequest goodsInfoPluginRequest = (GoodsInfoPluginRequest) request;
                        pluginProxy.goodsDetail(goodsInfoPluginRequest);
                    }
                    case CART_MARKETING -> {
                        GoodsInfoPluginRequest goodsInfoPluginRequest = (GoodsInfoPluginRequest) request;
                        pluginProxy.cartMarketing(goodsInfoPluginRequest);
                    }
                    case TRADE_MARKETING -> {
                        GoodsInfoPluginRequest goodsInfoPluginRequest = (GoodsInfoPluginRequest) request;
                        pluginProxy.tradeMarketing(goodsInfoPluginRequest);
                    }
                    default -> log.error("execSinglePlugin: 未匹配相关方法");
                }
            }
        }
    }

    private void removeContext() {
        MarketingContext.removeBaseRequest();
        MarketingContext.removeResponse();
    }

    private void initContext() {
        MarketingContext.setBaseRequest(MarketingPluginBaseParam.builder().build());
    }
}
