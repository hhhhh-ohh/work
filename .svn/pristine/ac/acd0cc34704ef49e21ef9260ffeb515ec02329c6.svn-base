package com.wanmi.sbc.marketing.common.service;

import com.google.common.collect.Maps;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateCoincideRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.marketing.api.request.market.MarketingScopeListInvalidMarketingRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingScopeValidateRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.model.root.MarketingScope;
import com.wanmi.sbc.marketing.common.repository.MarketingScopeRepository;
import com.wanmi.sbc.marketing.common.request.MarketingQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MarketingScopeService {

    @Autowired
    private MarketingScopeRepository marketingScopeRepository;

    @Autowired
    @Lazy
    private MarketingService marketingService;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;



    /**
     * 根据营销编号查询营销等级集合
     *
     * @param marketingId
     * @return
     */
    public List<MarketingScope> findByMarketingId(Long marketingId){
        return marketingScopeRepository.findByMarketingId(marketingId);
    }

    /**
     * 根据活动Id批量查询
     * @author  wur
     * @date: 2021/11/30 17:34
     * @param marketingIdList
     * @return
     **/
    public List<MarketingScope> findByMarketingIdList(List<Long> marketingIdList){
        return marketingScopeRepository.findByMarketingIdIn(marketingIdList);
    }

    /**
     * 订单营销信息校验，返回失效的营销活动
     *
     */
    public List<Marketing> listInvalidMarketing(MarketingScopeListInvalidMarketingRequest request) {
        List<Long> marketingIds = request.getMarketingIds();
        if (CollectionUtils.isEmpty(marketingIds)) {
            return Collections.emptyList();
        }
        List<Marketing> marketingList = marketingService.queryByIds(marketingIds);
        if (CollectionUtils.isEmpty(marketingList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        //获取用户在商户店铺下的等级信息
        Map<Long, Long> levelMap = request.getLevelMap();

        List<GoodsInfoVO> goodsInfos = getGoodsInfos(request.getSkuGroup());
        //品牌
        Map<String, Long> brandMap = new HashMap<>();
        //分类
        Map<String, List<Long>> storeCateMap = new HashMap<>();
        for (GoodsInfoVO goodsInfoVO : goodsInfos) {
            brandMap.put(goodsInfoVO.getGoodsInfoId(), goodsInfoVO.getBrandId());
            storeCateMap.put(goodsInfoVO.getGoodsInfoId(), goodsInfoVO.getStoreCateIds());
        }
        //用于存放无效的营销活动
        List<Marketing> invalidIds = new ArrayList<>();
        Map<Long, List<String>> skuGroup = request.getSkuGroup();
        marketingList.forEach(i -> {
            //校验营销活动
            if (i.getIsPause() == BoolFlag.YES || i.getDelFlag() == DeleteFlag.YES || i.getBeginTime().isAfter(LocalDateTime.now())
                    || i.getEndTime().isBefore(LocalDateTime.now())) {
                invalidIds.add(i);
            } else {
                //校验关联商品是否匹配
                List<String> scopeList = this.findByMarketingId(i.getMarketingId()).stream().map(
                        MarketingScope::getScopeId).collect(Collectors.toList());
                List<String> skuList = skuGroup.get(i.getMarketingId());
                switch (i.getScopeType()) {
                    case SCOPE_TYPE_ALL:
                        break;
                    case SCOPE_TYPE_CUSTOM:
                        if (skuList.stream().anyMatch(s -> !scopeList.contains(s))) {
                            //营销活动创建后不可更改，如果关联商品与后台设置不匹配，基本是安全问题造成
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        }
                        break;
                    case SCOPE_TYPE_BRAND:
                        List<String> brandIds = skuList.stream().map(sku -> brandMap.get(sku).toString()).collect(Collectors.toList());
                        if (brandIds.stream().anyMatch(brandId -> !scopeList.contains(brandId))) {
                            //营销活动创建后不可更改，如果关联品牌与后台设置不匹配，基本是安全问题造成
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        }
                        break;
                    case SCOPE_TYPE_STORE_CATE:
                        List<String> storeCateIds = skuList.stream().flatMap(sku -> storeCateMap.get(sku).stream()).map(Objects::toString).collect(Collectors.toList());
                        if (storeCateIds.stream().anyMatch(brandId -> !scopeList.contains(brandId))) {
                            //营销活动创建后不可更改，如果关联分类与后台设置不匹配，基本是安全问题造成
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        }
                        break;
                    default:
                }
            }
            //校验用户级别
            Long level = levelMap.get(i.getStoreId());
            switch (i.getMarketingJoinLevel()) {
                case ALL_CUSTOMER:
                    break;
                case ALL_LEVEL:
                    if (level == null) {
                        invalidIds.add(i);
                    }
                    break;
                case LEVEL_LIST:
                    if (!i.getJoinLevelList().contains(level)) {
                        invalidIds.add(i);
                    }
                    break;
                default:
                    break;
            }
        });
        return invalidIds;
    }

    /**
     * @description 查询商品数据
     * @author  xuyunpeng
     * @date 2021/5/28 9:43 上午
     * @param skuGroup
     * @return
     */
    public List<GoodsInfoVO> getGoodsInfos(Map<Long, List<String>> skuGroup) {
        //查询商品数据
        List<String> skuIds = skuGroup.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(skuIds).build())
                .getContext().getGoodsInfos();
        List<String> goodsIds = goodsInfos.stream().map(GoodsInfoVO::getGoodsId).collect(Collectors.toList());

        //店铺分类
        StoreCateListByGoodsRequest storeCateRequest = new StoreCateListByGoodsRequest();
        storeCateRequest.setGoodsIds(goodsIds);
        Map<String, List<StoreCateGoodsRelaVO>> storeCateMap = storeCateQueryProvider.listByGoods(storeCateRequest).getContext().getStoreCateGoodsRelaVOList().stream()
                .collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));

        goodsInfos.forEach(goodsInfo -> {
            List<Long> storeCateIds = storeCateMap.get(goodsInfo.getGoodsId()).stream()
                    .map(StoreCateGoodsRelaVO::getStoreCateId).collect(Collectors.toList());
            goodsInfo.setStoreCateIds(storeCateIds);
        });
        return goodsInfos;
    }

    /**
     * 营销marketing互斥验证
     * @param request 入参
     * @return 验证结果
     */
    public void validate(MarketingScopeValidateRequest request) {
        MarketingQueryRequest queryRequest = new MarketingQueryRequest();
        queryRequest.setDeleteFlag(DeleteFlag.NO);
        queryRequest.setNotId(request.getNotId());
        queryRequest.setPluginTypes(Collections.singletonList(PluginType.NORMAL));
        queryRequest.setAuditStatusList(Arrays.asList(AuditStatus.WAIT_CHECK, AuditStatus.CHECKED));
        queryRequest.setStoreIds(Collections.singletonList(request.getStoreId()));
        queryRequest.setMarketingTypes(request.getMarketingTypes());
        queryRequest.setMarketingScopeTypes(request.getScopeTypes());
        //活动结束时间 >= 交叉开始时间
        queryRequest.setEndTimeBegin(request.getCrossBeginTime());
        //活动开始时间 <= 交叉结束时间
        queryRequest.setStartTimeEnd(request.getCrossEndTime());
        List<Marketing> marketingList = marketingService.listCols(queryRequest, Arrays.asList("marketingId" , "marketingType","scopeType"));
        if (CollectionUtils.isEmpty(marketingList)) {
            return;
        }
        if (Boolean.TRUE.equals(request.getAllFlag())) {
            this.throwException(marketingList.get(0).getMarketingType());
            return;
        }
        Marketing allMarketing = marketingList.stream()
                .filter(m -> MarketingScopeType.SCOPE_TYPE_ALL.equals(m.getScopeType())).findFirst().orElse(null);
        if (allMarketing != null) {
            this.throwException(allMarketing.getMarketingType());
            return;
        }
        List<Long> allMarketingIds = new ArrayList<>();
        List<Long> storeCateMarketingIds = new ArrayList<>();
        List<Long> brandMarketingIds = new ArrayList<>();
        List<Long> skuMarketingIds = new ArrayList<>();
        Map<Long, MarketingType> marketingTypeMap = Maps.newHashMap();
        marketingList.forEach(m -> {
            Long marketingId = m.getMarketingId();
            if (MarketingScopeType.SCOPE_TYPE_BRAND.equals(m.getScopeType())) {
                brandMarketingIds.add(marketingId);
            } else if (MarketingScopeType.SCOPE_TYPE_STORE_CATE.equals(m.getScopeType())) {
                storeCateMarketingIds.add(marketingId);
            } else if (MarketingScopeType.SCOPE_TYPE_CUSTOM.equals(m.getScopeType())) {
                skuMarketingIds.add(marketingId);
            }
            allMarketingIds.add(marketingId);
            marketingTypeMap.put(marketingId, m.getMarketingType());
        });

        //对应分类范围id，以及范围与营销类型对应
        List<Long> storeCateIds = new ArrayList<>();
        Map<Long, MarketingType> storeCateTypeMap = Maps.newHashMap();
        //对应品牌范围id，以及范围与营销类型对应
        List<Long> brandIds = new ArrayList<>();
        Map<Long, MarketingType> brandTypeMap = Maps.newHashMap();
        //对应sku范围id，以及范围与营销类型对应
        List<String> skuIds = new ArrayList<>();
        Map<String, MarketingType> skuTypeMap = Maps.newHashMap();

        for (int pageNo = 0; ; pageNo++) {
            PageRequest pageRequest =
                    PageRequest.of(pageNo, 200, Sort.Direction.ASC, "marketingScopeId");
            Page<MarketingScope> scopeList = this.pageByMarketingIds(allMarketingIds, pageRequest);
            if (CollectionUtils.isNotEmpty(scopeList.getContent())) {
                //记录范围id和营销类型的对应关系
                scopeList.getContent().forEach(s -> {
                    String scopeId = s.getScopeId();
                    if (storeCateMarketingIds.contains(s.getMarketingId())) {
                        Long id = NumberUtils.toLong(scopeId);
                        storeCateIds.add(id);
                        storeCateTypeMap.put(id, marketingTypeMap.get(s.getMarketingId()));
                    } else if (brandMarketingIds.contains(s.getMarketingId())) {
                        Long id = NumberUtils.toLong(scopeId);
                        brandIds.add(id);
                        brandTypeMap.put(id, marketingTypeMap.get(s.getMarketingId()));
                    } else if (skuMarketingIds.contains(s.getMarketingId())) {
                        skuIds.add(scopeId);
                        skuTypeMap.put(scopeId, marketingTypeMap.get(s.getMarketingId()));
                    }
                });
                //验证品牌范围
                if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
                    //验证品牌是否存在
                    if (CollectionUtils.isNotEmpty(brandIds)) {
                        brandIds.stream()
                                .filter(request.getBrandIds()::contains)
                                .map(brandTypeMap::get).findFirst()
                                .ifPresent(this::throwException);
                    }
                    //验证商品相关品牌是否存在
                    if (CollectionUtils.isNotEmpty(skuIds)) {
                        List<GoodsInfoVO> checkGoods = this.checkGoods(skuIds, request.getBrandIds(), null, false);
                        if (CollectionUtils.isNotEmpty(checkGoods)) {
                            checkGoods.stream()
                                    .filter(c -> skuTypeMap.containsKey(c.getGoodsInfoId()))
                                    .map(c -> skuTypeMap.get(c.getGoodsInfoId()))
                                    .findFirst().ifPresent(this::throwException);
                        }
                    }
                } else if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
                    //验证店铺分类范围
                    if (CollectionUtils.isNotEmpty(storeCateIds)) {
                        StoreCateCoincideRequest coincideRequest = new StoreCateCoincideRequest();
                        coincideRequest.setStoreCateIds(request.getStoreCateIds());
                        coincideRequest.setStoreCateSecIds(storeCateIds);
                        List<Long> cateIds = storeCateQueryProvider.coincide(coincideRequest).getContext().getStoreCateIds();
                        if (CollectionUtils.isNotEmpty(cateIds)) {
                            cateIds.stream()
                                    .map(storeCateTypeMap::get)
                                    .findFirst().ifPresent(this::throwException);
                        }
                    }
                    //验证商品相关品牌是否存在
                    if (CollectionUtils.isNotEmpty(skuIds)) {
                        List<GoodsInfoVO> checkGoods = this.checkGoods(skuIds, null, request.getStoreCateIds(), false);
                        if (CollectionUtils.isNotEmpty(checkGoods)) {
                            checkGoods.stream()
                                    .filter(c -> skuTypeMap.containsKey(c.getGoodsInfoId()))
                                    .map(c -> skuTypeMap.get(c.getGoodsInfoId()))
                                    .findFirst().ifPresent(this::throwException);
                        }
                    }
                } else if (CollectionUtils.isNotEmpty(request.getSkuIds())) {
                    //验证自定义货品范围
                    if (CollectionUtils.isNotEmpty(skuIds)) {
                        request.getSkuIds().stream().filter(skuIds::contains).map(skuTypeMap::get)
                                .findFirst().ifPresent(this::throwException);
                    }

                    //有品牌数据，抛品牌相应的异常
                    if(CollectionUtils.isNotEmpty(brandIds)){
                        List<GoodsInfoVO> checkGoods = this.checkGoods(request.getSkuIds(), brandIds, null, false);
                        if (CollectionUtils.isNotEmpty(checkGoods)) {
                            //有品牌数据，抛品牌相应的异常
                            if (CollectionUtils.isNotEmpty(brandIds)) {
                                checkGoods.stream()
                                        .filter(c -> brandIds.contains(c.getBrandId()))
                                        .map(c -> brandTypeMap.get(c.getBrandId()))
                                        .findFirst().ifPresent(this::throwException);
                            }
                        }
                    }

                    //有店铺分类数据，抛店铺分类相应的异常
                    if(CollectionUtils.isNotEmpty(storeCateIds)){
                        List<GoodsInfoVO> checkGoods = this.checkGoods(request.getSkuIds(), null, storeCateIds, true);
                        if (CollectionUtils.isNotEmpty(storeCateIds)) {
                            checkGoods.stream()
                                    .flatMap(c -> c.getStoreCateIds().stream())
                                    .filter(storeCateIds::contains)
                                    .map(storeCateTypeMap::get)
                                    .findFirst().ifPresent(this::throwException);
                        }
                    }
                }
            }
            //清理
            storeCateIds.clear();
            storeCateTypeMap.clear();
            brandIds.clear();
            brandTypeMap.clear();
            skuIds.clear();
            skuTypeMap.clear();

            // 最后一页，退出循环
            if (pageNo >= scopeList.getTotalPages() - 1) {
                break;
            }
        }
    }

    /**
     * 验证商品、品牌、店铺分类的重合
     * @param skuIds 商品skuId
     * @param brandIds 品牌Id
     * @param storeCateIds 店铺分类Id
     * @param fillStoreCate 返回店铺分类id数据
     * @return 重合结果
     */
    public List<GoodsInfoVO> checkGoods(List<String> skuIds, List<Long> brandIds,
                                        List<Long> storeCateIds, Boolean fillStoreCate) {
        GoodsInfoListByConditionRequest count = new GoodsInfoListByConditionRequest();
        count.setGoodsInfoIds(skuIds);
        count.setBrandIds(brandIds);
        count.setStoreCateIds(storeCateIds);
        //店铺分类，需要返回店铺分类id
        count.setFillStoreCate(fillStoreCate);
        count.setShowPointFlag(Boolean.FALSE);
        count.setDelFlag(DeleteFlag.NO.toValue());
        return goodsInfoQueryProvider.listByCondition(count).getContext().getGoodsInfos();
    }

    /**
     * 抛异常
     * @param marketingType 营销类型
     */
    public void throwException(MarketingType marketingType){
        String desc = marketingType.getDesc().replaceAll("优惠", "");
        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080026, new Object[]{desc});
    }

    /**
     * 分页查询范围
     * @param marketingIds 营销ids
     * @param request 分页参数
     * @return 营销范围
     */
    public Page<MarketingScope> pageByMarketingIds(List<Long> marketingIds, PageRequest request){
        return marketingScopeRepository.findAll(((rt, cq, cb) -> rt.get("marketingId").in(marketingIds)), request);
    }


}
