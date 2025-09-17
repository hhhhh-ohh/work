package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.common.util.SpringContextHolder;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelMapByCustomerIdAndStoreIdsRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelCustomerRequest;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelMapGetResponse;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoListRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.response.info.GoodsInfoListByGoodsInfoResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingStatus;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
import com.wanmi.sbc.marketing.common.request.MarketingRequest;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.common.service.MarketingService;
import com.wanmi.sbc.marketing.plugin.IGoodsDetailPlugin;
import com.wanmi.sbc.marketing.plugin.IGoodsListPlugin;
import com.wanmi.sbc.marketing.request.MarketingPluginRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 主插件服务
 * Created by dyt on 2016/12/2.
 */
@Data
@Slf4j
public class MarketingPluginService implements MarketingPluginServiceInterface {

    /**
     * 商品列表插件集
     */
    private List<String> goodsListPlugins;


    /**
     * 秒杀plugin
     */
    private String flashSalePlugin = "flashSalePlugin";

    /**
     * 优惠券plugin
     */
    private String couponPlugin = "couponPlugin";


    /**
     * 商品详情插件集
     */
    private List<String> goodsDetailPlugins;

    @Autowired
    protected CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    protected MarketingService marketingService;

    @Autowired
    protected StoreQueryProvider storeQueryProvider;

    @Autowired
    protected GoodsQueryProvider goodsQueryProvider;

    @Autowired
    protected StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

    @Autowired
    private BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;


    /**
     * 商品列表处理
     *
     * @param goodsInfos 商品数据
     * @param request    参数
     * @throws SbcRuntimeException
     */
    @Override
    public GoodsInfoListByGoodsInfoResponse goodsListFilter(List<GoodsInfoVO> goodsInfos, MarketingPluginRequest request) throws SbcRuntimeException {
        return goodsListFilter(goodsInfos, request, null);
    }

    /**
     * 商品列表处理
     *
     * @param goodsInfos 商品数据
     * @param request    参数
     * @throws SbcRuntimeException
     */
    public GoodsInfoListByGoodsInfoResponse goodsListFilter(List<GoodsInfoVO> goodsInfos, MarketingPluginRequest request,
                                                            Map<String, List<MarketingResponse>> marketingMap) throws SbcRuntimeException {
        if (CollectionUtils.isEmpty(goodsListPlugins) || CollectionUtils.isEmpty(goodsInfos)) {
            return new GoodsInfoListByGoodsInfoResponse();
        }
        //周期购替换价格
        goodsInfos = dealCycleGoodsMarketing(goodsInfos);
        // 设定等级
        request.setLevelMap(this.getCustomerLevels(goodsInfos,request.getCustomerId()));
        //设定付费会员等级
        request.setPayingMemberLevelVO(this.getPayingMemberLevel(request.getCustomerId()));
        // 设定营销
        if (!request.getCommitFlag()) {
            if (MapUtils.isEmpty(marketingMap)) {
                String customerId = StringUtils.EMPTY;
                if (StringUtils.isNotBlank(request.getCustomerId())){
                    customerId = request.getCustomerId();
                }
                marketingMap = getMarketing(goodsInfos, request.getLevelMap(), customerId);
            }
            request.setMarketingMap(marketingMap);
        }
        for (String plugin : goodsListPlugins) {
            if (Objects.nonNull(request.getIsFlashSaleMarketing()) && request.getIsFlashSaleMarketing()) {
                if (flashSalePlugin.equals(plugin)) {
                    continue;
                }
            }
            Object pluginObj = SpringContextHolder.getBean(plugin);
            if (pluginObj instanceof IGoodsListPlugin) {
                long start = System.currentTimeMillis();
                ((IGoodsListPlugin) pluginObj).goodsListFilter(goodsInfos, request);
                long end = System.currentTimeMillis();
                log.debug("商品列表处理【".concat(plugin).concat("】执行时间:").concat(String.valueOf((end - start))).concat("毫秒"));
            } else {
                log.error("商品列表处理【".concat(plugin).concat("】没有实现IGoodsListPlugin接口"));
            }
        }
        return new GoodsInfoListByGoodsInfoResponse(goodsInfos);
    }

    /**
     * 商品详情处理
     *
     * @param detailResponse 商品详情数据
     * @param request        参数
     * @throws SbcRuntimeException
     */
    public GoodsInfoDetailByGoodsInfoResponse goodsDetailFilter(GoodsInfoDetailByGoodsInfoResponse detailResponse, MarketingPluginRequest request) throws SbcRuntimeException {
        if (CollectionUtils.isEmpty(goodsDetailPlugins) || Objects.isNull(detailResponse.getGoodsInfo())) {
            return null;
        }
        //周期购替换价格
        List<GoodsInfoVO> goodsInfoVOS = dealCycleGoodsMarketing(Collections.singletonList(detailResponse.getGoodsInfo()));
        if (CollectionUtils.isNotEmpty(goodsInfoVOS)) {
            detailResponse.setGoodsInfo(goodsInfoVOS.get(Constants.ZERO));
        }
        //设定等级
        request.setLevelMap(this.getCustomerLevels(Collections.singletonList(detailResponse.getGoodsInfo()), request.getCustomerId()));
        //设定付费会员等级
        request.setPayingMemberLevelVO(this.getPayingMemberLevel(request.getCustomerId()));
        //设定营销
        String customerId = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(request.getCustomerId())){
            customerId = request.getCustomerId();
        }
        request.setMarketingMap(getMarketing(Collections.singletonList(detailResponse.getGoodsInfo()),
                request.getLevelMap(), customerId));
        for (String plugin : goodsDetailPlugins) {
            Object pluginObj = SpringContextHolder.getBean(plugin);
            if (pluginObj instanceof IGoodsDetailPlugin) {
                long start = System.currentTimeMillis();
                ((IGoodsDetailPlugin) pluginObj).goodsDetailFilter(detailResponse, request);
                long end = System.currentTimeMillis();
                log.debug("商品详情处理【".concat(plugin).concat("】执行时间:").concat(String.valueOf((end - start))).concat("毫秒"));
            } else {
                log.error("商品详情处理【".concat(plugin).concat("】没有实现IGoodsDetailPlugin接口"));
            }
        }
        return detailResponse;
    }

    /**
     * 热销商品列表
     *
     * @param goodsInfos
     * @param request
     * @return
     */
    public GoodsInfoListByGoodsInfoResponse distributionGoodsListFilter(List<GoodsInfoVO> goodsInfos, MarketingPluginRequest request) {
        if (!goodsDetailPlugins.contains(couponPlugin) || CollectionUtils.isEmpty(goodsInfos)) {
            return new GoodsInfoListByGoodsInfoResponse(Collections.emptyList());
        }

        //设定等级
        request.setLevelMap(this.getCustomerLevels(goodsInfos, request.getCustomerId()));
        //设定付费会员等级
        request.setPayingMemberLevelVO(this.getPayingMemberLevel(request.getCustomerId()));

        Object pluginObj = SpringContextHolder.getBean(couponPlugin);
        if (pluginObj instanceof IGoodsDetailPlugin) {
            long start = System.currentTimeMillis();
            ((IGoodsListPlugin) pluginObj).goodsListFilter(goodsInfos, request);
            long end = System.currentTimeMillis();
            log.debug("热销商品列表处理【".concat(couponPlugin).concat("】执行时间:").concat(String.valueOf((end - start))).concat("毫秒"));
        } else {
            log.error("热销商品列表处理【".concat(couponPlugin).concat("】没有实现IGoodsDetailPlugin接口"));
        }

        return new GoodsInfoListByGoodsInfoResponse(goodsInfos);

    }

    /**
     * 获取会员等级
     *
     * @param goodsInfoList 商品
     * @param customerId      客户
     */
    public HashMap<Long, CommonLevelVO> getCustomerLevels(List<GoodsInfoVO> goodsInfoList, String customerId) {
        List<Long> storeIds = goodsInfoList.stream()
                .map(GoodsInfoVO::getStoreId)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());

        if ( StringUtils.isBlank(customerId) || CollectionUtils.isEmpty(storeIds)) {
            return new HashMap<>();
        }
        CustomerLevelMapByCustomerIdAndStoreIdsRequest customerLevelMapByCustomerIdAndStoreIdsRequest = new CustomerLevelMapByCustomerIdAndStoreIdsRequest();
        customerLevelMapByCustomerIdAndStoreIdsRequest.setCustomerId(customerId);
        customerLevelMapByCustomerIdAndStoreIdsRequest.setStoreIds(storeIds);
        BaseResponse<CustomerLevelMapGetResponse> customerLevelMapGetResponseBaseResponse = customerLevelQueryProvider.listCustomerLevelMapByCustomerIdAndIds(customerLevelMapByCustomerIdAndStoreIdsRequest);
        return customerLevelMapGetResponseBaseResponse.getContext().getCommonLevelVOMap();
    }

    /**
     * 获取会员等级
     *
     * @param storeIds 店铺列表
     * @param customer 客户
     */
    public HashMap<Long, CommonLevelVO> getCustomerLevelsByStoreIds(List<Long> storeIds, CustomerVO customer) {
        if (customer == null || CollectionUtils.isEmpty(storeIds)) {
            return new HashMap<>();
        }

        CustomerLevelMapByCustomerIdAndStoreIdsRequest customerLevelMapByCustomerIdAndStoreIdsRequest = new CustomerLevelMapByCustomerIdAndStoreIdsRequest();
        customerLevelMapByCustomerIdAndStoreIdsRequest.setCustomerId(customer.getCustomerId());
        customerLevelMapByCustomerIdAndStoreIdsRequest.setStoreIds(storeIds);
        BaseResponse<CustomerLevelMapGetResponse> customerLevelMapGetResponseBaseResponse = customerLevelQueryProvider.listCustomerLevelMapByCustomerIdAndIds(customerLevelMapByCustomerIdAndStoreIdsRequest);
        return customerLevelMapGetResponseBaseResponse.getContext().getCommonLevelVOMap();
    }


    /**
     * 获取营销
     *
     * @param goodsInfoList 商品
     */
    public Map<String, List<MarketingResponse>> getMarketing(List<GoodsInfoVO> goodsInfoList, Map<Long,
            CommonLevelVO> levelMap, String customerId) {
        return getMarketing(goodsInfoList, levelMap, PluginType.NORMAL, null, customerId);
    }

    /**
     * 获取营销
     *
     * @param goodsInfoList 商品
     */
    @Override
    public Map<String, List<MarketingResponse>> getMarketing(List<GoodsInfoVO> goodsInfoList, Map<Long, CommonLevelVO> levelMap, PluginType pluginType, Long storeId, String customerId) {
        if (CollectionUtils.isEmpty(goodsInfoList)){
            return new HashMap<>();
        }
        Map<String, Integer> goodsTypeMap = goodsInfoList.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, GoodsInfoVO::getGoodsType));
        Map<String, Integer> cycleMap = new HashMap<>();
        goodsInfoList.forEach(goodsInfoVO -> {
            cycleMap.put(goodsInfoVO.getGoodsInfoId(), Objects.isNull(goodsInfoVO.getIsBuyCycle()) ? 0: goodsInfoVO.getIsBuyCycle());
        });
        // 排除预售预约
        LocalDateTime now = LocalDateTime.now();
        goodsInfoList = goodsInfoList.stream().filter(goodsInfoVO -> {
                    BookingSaleVO bookingSaleVO = goodsInfoVO.getBookingSaleVO();
                    //定金预售定金支付时间之外的不排除
                    boolean isBookingSale = Objects.nonNull(bookingSaleVO) && !(1 == bookingSaleVO.getBookingType()
                            && (now.isBefore(bookingSaleVO.getHandSelStartTime()) || now.isAfter(bookingSaleVO.getHandSelEndTime())));
                    if (Objects.isNull(goodsInfoVO.getAppointmentSaleVO()) && !isBookingSale) {
                        return true;
                    }
                    return false;
                }
        ).collect(Collectors.toList());

        //SPU
        List<String> gooodsIds = goodsInfoList.stream().map(GoodsInfoVO::getGoodsId).collect(Collectors.toList());
        Map<String, GoodsVO> goodsVOMap = goodsQueryProvider.listByIds(GoodsListByIdsRequest.builder().goodsIds(gooodsIds).build()).getContext().getGoodsVOList()
                .stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));

        //店铺分类
        StoreCateListByGoodsRequest storeCateRequest = new StoreCateListByGoodsRequest();
        storeCateRequest.setGoodsIds(gooodsIds);
        Map<String, List<StoreCateGoodsRelaVO>> storeCateMap = storeCateQueryProvider.listByGoods(storeCateRequest).getContext()
                .getStoreCateGoodsRelaVOList().stream().collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));

        List<MarketingGoods> marketingGoods = goodsInfoList.stream().map(goodsInfoVO -> {
            GoodsVO goodsVO = goodsVOMap.get(goodsInfoVO.getGoodsId());
            if(Objects.nonNull(storeCateMap.get(goodsInfoVO.getGoodsId()))){
                List<Long> storeCateIds = storeCateMap.get(goodsInfoVO.getGoodsId()).stream().map(StoreCateGoodsRelaVO::getStoreCateId).collect(Collectors.toList());
                return MarketingGoods.builder()
                        .goodsInfoId(goodsInfoVO.getGoodsInfoId())
                        .brandId(goodsVO.getBrandId())
                        .storeCateIds(storeCateIds)
                        .storeId(goodsInfoVO.getStoreId()).build();
            } else {
                return null;
            }
        }).collect(Collectors.toList()).stream().filter(marketingGoodsInfo->marketingGoodsInfo!=null).collect(Collectors.toList());

        MarketingRequest marketingRequest = MarketingRequest.builder()
                .deleteFlag(DeleteFlag.NO)
                .cascadeLevel(Boolean.TRUE)
                .marketingStatus(MarketingStatus.STARTED)
                .excludeStatus(MarketingStatus.PAUSED).build();
        marketingRequest.setMarketingGoods(marketingGoods);
        //查询正在进行中的有效营销信息
        Map<String, List<MarketingResponse>> marketingMap = marketingService.getMarketingMapByGoodsId(getMarketingRequest(goodsInfoList, null));

        Map<String, Long> goodsMap = goodsInfoList.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, GoodsInfoVO::getStoreId, (s, a) -> s));
        List<StoreVO> storeVOList;
        if (levelMap.isEmpty()) {
            List<Long> storeIds = goodsInfoList.stream().map(GoodsInfoVO::getStoreId).distinct().collect(Collectors.toList());
            storeVOList = storeQueryProvider.listNoDeleteStoreByIds(ListNoDeleteStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext().getStoreVOList();
        } else {
            storeVOList = new ArrayList<>();
        }
        Map<Long, StoreVO> storeVOMap = storeVOList.stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
        Map<String, List<MarketingResponse>> newMarketingMap = new HashMap<>();
        if (MapUtils.isNotEmpty(marketingMap)) {
            marketingMap.forEach((skuId, marketingList) -> {
                Long goodsStoreId = goodsMap.get(skuId);
                if (!Objects.isNull(goodsStoreId)) {
                    CommonLevelVO level = levelMap.get(goodsStoreId);
                    //过滤出符合等级条件的营销信息
                    newMarketingMap.put(skuId, marketingList.stream().filter(marketing -> {
                        //虚拟商品和电子卡券过滤满赠活动
                        Integer goodsType = goodsTypeMap.get(skuId);
                        Integer cycleFlag = cycleMap.get(skuId);
                        if(limitByGoodsType(goodsType, marketing.getMarketingType())
                                || limitByCycle(cycleFlag, marketing.getMarketingType())) {
                            return false;
                        }
                        // 兼容满返平台等级
                        if (marketing.getMarketingType() == MarketingType.RETURN && BoolFlag.YES == marketing.getIsBoss()){
                            //不限等级
                            if (Constants.STR_MINUS_1.equals(marketing.getJoinLevel()) || Constants.STR_0.equals(marketing.getJoinLevel())) {
                                return true;
                            }
                            // 查询平台等级
                            if (StringUtils.isNotBlank(customerId)){
                                HashMap<Long, CommonLevelVO> levelPlatFormMap =
                                        customerLevelQueryProvider.listCustomerLevelMapByCustomerId(CustomerLevelMapByCustomerIdAndStoreIdsRequest.
                                                builder().customerId(customerId).storeIds(Collections.singletonList(-1L)).build()).getContext().getCommonLevelVOMap();
                                CommonLevelVO levelPlatForm = levelPlatFormMap.get(Constants.BOSS_DEFAULT_STORE_ID);
                                if (Arrays.asList(marketing.getJoinLevel().split(",")).contains(String.valueOf(levelPlatForm.getLevelId()))) {
                                    return true;
                                }
                            }
                        }else {
                            //会员未登录状态下，对第三方商家全平台及自营商家全等级的营销是可见的
                            //全平台
                            if (Constants.STR_MINUS_1.equals(marketing.getJoinLevel())) {
                                return true;
                            } else if (levelMap.isEmpty()) {
                                StoreVO storeVO = storeVOMap.get(goodsMap.get(skuId));
                                return BoolFlag.NO == storeVO.getCompanyType() && "0".equals(marketing.getJoinLevel());
                            } else if (Objects.nonNull(level)) {
                                //不限等级
                                if (Constants.STR_0.equals(marketing.getJoinLevel())) {
                                    return true;
                                } else if (Arrays.asList(marketing.getJoinLevel().split(",")).contains(String.valueOf(level.getLevelId()))) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }).collect(Collectors.toList()));
                }
            });
        }
        return newMarketingMap;
    }

    /**
     * 虚拟商品/电子卡券不参与满赠、打包一口价、第二件半价、组合购
     * @param goodsType
     * @param marketingType
     * @return
     */
    private Boolean limitByGoodsType (Integer goodsType, MarketingType marketingType) {
        Boolean flag = Boolean.FALSE;
        if ((Objects.nonNull(goodsType) &&
                goodsType != GoodsType.REAL_GOODS.toValue())) {
            switch (marketingType) {
                case GIFT:
                case BUYOUT_PRICE:
                case SUITS:
                case HALF_PRICE_SECOND_PIECE:
                    flag = Boolean.TRUE;
                    break;
                default:
                    flag =  Boolean.FALSE;
            }
        }
        return flag;
    }

    /**
     * 周期购商品只参与满减、满折
     * @param cycleFlag
     * @param marketingType
     * @return
     */
    private Boolean limitByCycle(Integer cycleFlag, MarketingType marketingType) {
        Boolean flag = Boolean.FALSE;
        if (Objects.nonNull(cycleFlag) && Constants.yes.equals(cycleFlag)) {
            switch (marketingType) {
                case REDUCTION:
                case DISCOUNT:
                case RETURN:
                    flag = Boolean.FALSE;
                    break;
                default:
                    flag =  Boolean.TRUE;
            }
        }
        return flag;
    }

    /***
     * 过滤商品列表
     * @param goodsInfoList 商品SKU列表
     * @return              规则过滤后的结果
     */
    protected List<GoodsInfoVO> filterGoodsInfoList(List<GoodsInfoVO> goodsInfoList) {
        LocalDateTime now = LocalDateTime.now();
        // 如果是商品积分兑换--积分价不计算-(满减＞折＞赠＞打包一口价＞第二件半价生效)
        return goodsInfoList.stream().filter(goodsInfoVO -> {
            // 排除预售预约
            BookingSaleVO bookingSaleVO = goodsInfoVO.getBookingSaleVO();
            // 定金预售定金支付时间之外的不排除
            boolean isBookingSale = Objects.nonNull(bookingSaleVO) && !(1 == bookingSaleVO.getBookingType()
                    && (now.isBefore(bookingSaleVO.getHandSelStartTime()) || now.isAfter(bookingSaleVO.getHandSelEndTime())));
            return (Objects.isNull(goodsInfoVO.getAppointmentSaleVO()) && !isBookingSale);
        }).collect(Collectors.toList());
    }

    /***
     * 获得营销查询请求参数
     * @param goodsInfoList 商品SKU集合
     * @param storeId       门店ID（普通模式下为空）
     * @return              查询请求参数
     */
    public MarketingRequest getMarketingRequest(List<GoodsInfoVO> goodsInfoList, Long storeId) {
        // 过滤商品列表
        goodsInfoList = filterGoodsInfoList(goodsInfoList);

        // SPU
        List<String> gooodsIds = goodsInfoList.stream().map(GoodsInfoVO::getGoodsId).collect(Collectors.toList());
        Map<String, GoodsVO> goodsVOMap = goodsQueryProvider.listByIds(GoodsListByIdsRequest.builder().goodsIds(gooodsIds).build()).getContext().getGoodsVOList()
                .stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));

        // 店铺分类
        StoreCateListByGoodsRequest storeCateRequest = new StoreCateListByGoodsRequest();
        storeCateRequest.setGoodsIds(gooodsIds);
        Map<String, List<StoreCateGoodsRelaVO>> storeCateMap = storeCateQueryProvider.listByGoods(storeCateRequest).getContext()
                .getStoreCateGoodsRelaVOList().stream().collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));

        List<MarketingGoods> marketingGoods = goodsInfoList.stream().filter(goodsInfoVO -> DeleteFlag.NO.equals(goodsInfoVO.getDelFlag())).map(goodsInfoVO -> {
            GoodsVO goodsVO = goodsVOMap.get(goodsInfoVO.getGoodsId());
            List<Long> storeCateIds =
                    storeCateMap.getOrDefault(goodsInfoVO.getGoodsId(), new ArrayList<>())
                            .stream().map(StoreCateGoodsRelaVO::getStoreCateId).collect(Collectors.toList());
            return MarketingGoods.builder()
                    .goodsInfoId(goodsInfoVO.getGoodsInfoId())
                    .brandId(goodsVO.getBrandId())
                    .storeCateIds(storeCateIds)
                    .pluginType(goodsInfoVO.getPluginType())
                    .cateId(goodsInfoVO.getCateId())
                    .storeId(Nutils.defaultVal(storeId, goodsInfoVO.getStoreId())).build();
        }).collect(Collectors.toList());

        // 构建查询对象并返回
        MarketingRequest marketingRequest = MarketingRequest.builder()
                .deleteFlag(DeleteFlag.NO)
                .cascadeLevel(Boolean.TRUE)
                .marketingStatus(MarketingStatus.STARTED).build();
        marketingRequest.setMarketingGoods(marketingGoods);
        return marketingRequest;
    }

    /**
     * 获取付费会员等级
     * @param customerId
     * @return
     */
    public PayingMemberLevelVO getPayingMemberLevel(String customerId){

        List<PayingMemberLevelVO> payingMemberLevelVOList = payingMemberLevelQueryProvider
                .listByCustomerId(PayingMemberLevelCustomerRequest.builder()
                        .customerId(customerId)
                        .defaultFlag(Boolean.TRUE).build())
                .getContext().getPayingMemberLevelVOList();
        PayingMemberLevelVO payingMemberLevelVO = CollectionUtils.isNotEmpty(payingMemberLevelVOList)
                ? payingMemberLevelVOList.get(NumberUtils.INTEGER_ZERO) : null;
        return payingMemberLevelVO;
    }

    /**
     * 周期购替换市场价
     * @param goodsInfos
     * @return
     */
    public List<GoodsInfoVO> dealCycleGoodsMarketing(List<GoodsInfoVO> goodsInfos){
        if (CollectionUtils.isEmpty(goodsInfos)) {
           return goodsInfos;
        }
        //查询周期购商品信息
        List<String> goodsInfoIds = goodsInfos.stream()
                .filter(vo -> Objects.equals(vo.getIsBuyCycle(),Constants.yes))
                .map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        List<BuyCycleGoodsInfoVO> buyCycleGoodsInfoVOList = buyCycleGoodsInfoQueryProvider
                .list(BuyCycleGoodsInfoListRequest.builder()
                        .cycleState(Constants.ZERO)
                        .goodsInfoIdList(goodsInfoIds)
                        .delFlag(DeleteFlag.NO).build()
                ).getContext().getBuyCycleGoodsInfoVOList();
        //替换市场价、购买积分
        Map<String, BigDecimal> priceMap = buyCycleGoodsInfoVOList.stream()
                .collect(Collectors.toMap(BuyCycleGoodsInfoVO::getGoodsInfoId, BuyCycleGoodsInfoVO::getCyclePrice));
        goodsInfos.forEach(goodsInfoVO -> {
            if (Objects.equals(goodsInfoVO.getIsBuyCycle(),Constants.yes)) {
                BigDecimal price = priceMap.get(goodsInfoVO.getGoodsInfoId());
                if(price != null) {
                    goodsInfoVO.setMarketPrice(price);
                    goodsInfoVO.setSalePrice(price);
                    goodsInfoVO.setBuyPoint(BigDecimal.ZERO.longValue());
                }
            }
        });
        return goodsInfos;
    }
}
