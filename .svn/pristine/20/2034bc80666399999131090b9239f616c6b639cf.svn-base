package com.wanmi.sbc.marketing.common.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewPageRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.api.response.brand.ContractBrandListResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdsResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewPageResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByIdsResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.request.buyoutprice.MarketingBuyoutPriceSearchRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponInfoQueryRequest;
import com.wanmi.sbc.marketing.api.request.market.PauseModifyRequest;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.marketing.buyoutprice.model.entry.MarketingBuyoutPriceLevel;
import com.wanmi.sbc.marketing.buyoutprice.repository.MarketingBuyoutPriceLevelRepository;
import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.model.root.MarketingScope;
import com.wanmi.sbc.marketing.common.repository.MarketingRepository;
import com.wanmi.sbc.marketing.common.repository.MarketingScopeRepository;
import com.wanmi.sbc.marketing.common.repository.MarketingStoreRepository;
import com.wanmi.sbc.marketing.common.request.*;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.response.CouponInfoResponse;
import com.wanmi.sbc.marketing.coupon.service.CouponInfoService;
import com.wanmi.sbc.marketing.discount.model.entity.MarketingFullDiscountLevel;
import com.wanmi.sbc.marketing.discount.repository.MarketingFullDiscountLevelRepository;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnDetail;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnLevel;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnStore;
import com.wanmi.sbc.marketing.fullreturn.service.MarketingFullReturnService;
import com.wanmi.sbc.marketing.gift.service.MarketingFullGiftService;
import com.wanmi.sbc.marketing.halfpricesecondpiece.model.entry.MarketingHalfPriceSecondPieceLevel;
import com.wanmi.sbc.marketing.halfpricesecondpiece.repository.HalfPriceSecondPieceLevelRepository;
import com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialDetail;
import com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialLevel;
import com.wanmi.sbc.marketing.preferential.repository.MarketingPreferentialGoodsDetailRepository;
import com.wanmi.sbc.marketing.preferential.repository.MarketingPreferentialLevelRepository;
import com.wanmi.sbc.marketing.reduction.model.entity.MarketingFullReductionLevel;
import com.wanmi.sbc.marketing.reduction.repository.MarketingFullReductionLevelRepository;
import com.wanmi.sbc.marketing.util.XssUtils;
import com.wanmi.sbc.marketing.util.mapper.MarketingMapper;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarketingService implements MarketingServiceInterface {

    @Autowired
    protected MarketingRepository marketingRepository;

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    private MarketingScopeRepository marketingScopeRepository;

    @Autowired
    private MarketingStoreRepository marketingStoreRepository;

    @Autowired
    private MarketingFullDiscountLevelRepository marketingFullDiscountLevelRepository;

    @Autowired
    private MarketingFullReductionLevelRepository marketingFullReductionLevelRepository;

    @Autowired
    private MarketingBuyoutPriceLevelRepository marketingBuyoutPriceLevelRepository;

    @Autowired
    private MarketingFullGiftService marketingFullGiftService;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private HalfPriceSecondPieceLevelRepository halfPriceSecondPieceLevelRepository;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Resource
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private MarketingService self;

    @Autowired
    private MarketingMapper marketingMapper;

    @Autowired public SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    @Lazy
    private MarketingFullReturnService marketingFullReturnService;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private MarketingPreferentialLevelRepository marketingPreferentialLevelRepository;

    @Autowired
    private MarketingPreferentialGoodsDetailRepository marketingPreferentialGoodsDetailRepository;


    /**
     * 获取当前活动类型+时间段，是否有已经绑定的sku
     *
     * @param storeId
     * @param skuExistsRequest
     * @return
     */
    public List<String> getExistsSkuByMarketingType(Long storeId, SkuExistsRequest skuExistsRequest) {
        return marketingRepository.getExistsSkuByMarketingType(skuExistsRequest.getSkuIds(),
                skuExistsRequest.getMarketingType(),skuExistsRequest.getPluginType(), skuExistsRequest.getStartTime(),
                skuExistsRequest.getEndTime(), storeId, skuExistsRequest.getExcludeId());
    }

    /**
     * 分页查询营销列表
     *
     * @param request
     * @param storeId
     * @return
     */
    public MicroServicePage<MarketingPageVO> getMarketingPage(MarketingQueryListRequest request, Long storeId) {
        // 查询列表
        String sql = "SELECT t.* FROM marketing t ".concat(getExtendsTableSql(request));
        // 查询记录的总数量
        String countSql = "SELECT count(1) count FROM marketing t ".concat(getExtendsTableSql(request));
        // 获得where语句
        String whereSql = getMarketingWhereSql(request, storeId);

        Query query = entityManager.createNativeQuery(sql.concat(whereSql).concat(getMarketingOrderSql()));
        // 组装查询参数
        this.wrapperQueryParam(query, request, storeId);
        query.setFirstResult(request.getPageNum() * request.getPageSize());
        query.setMaxResults(request.getPageSize());
        query.unwrap(NativeQuery.class).addEntity("t", Marketing.class);
        List<Marketing> resultList = (List<Marketing>)query.getResultList();
        List<MarketingPageVO> responsesList = resultList.stream().map(source -> {
            MarketingPageVO response = new MarketingPageVO();
            BeanUtils.copyProperties(source, response);
            return response;
        }).collect(Collectors.toList());

        long count = 0;

        if (CollectionUtils.isNotEmpty(responsesList)) {
            Query queryCount = entityManager.createNativeQuery(countSql.concat(whereSql));
            // 组装查询参数
            this.wrapperQueryParam(queryCount, request, storeId);
            count = Long.parseLong(queryCount.getSingleResult().toString());
        }

        return new MicroServicePage<>(responsesList, request.getPageable(), count);
    }

    /**
     * 保存营销信息
     *
     * @param request
     * @return
     * @throws SbcRuntimeException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Marketing addMarketing(MarketingSaveRequest request) throws SbcRuntimeException {
        this.validParam(request);

        Marketing marketing = request.generateMarketing();

        if (request.getMarketingId() != null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        marketing.setCreateTime(LocalDateTime.now());
        marketing.setDelFlag(DeleteFlag.NO);
        marketing.setIsPause(BoolFlag.NO);
        // 保存之前修改对象
        populateMarketingBeforeSave(marketing,request.getIsBoss());
        marketing.setMarketingType(request.getMarketingType());

        // 营销规则
        marketing = marketingRepository.save(marketing);

        // 保存营销和商品关联关系
        if (MarketingScopeType.SCOPE_TYPE_ALL != marketing.getScopeType()) {
            this.saveScopeList(request.generateMarketingScopeList(marketing.getMarketingId()));
        }

        // 保存营销和店铺关联关系
        if (MarketingStoreType.STORE_TYPE_APPOINT == marketing.getStoreType()) {
            this.saveStoreList(request.generateMarketingStoreList(marketing.getMarketingId()));
        }

        return marketing;
    }

    /**
     * 修改营销信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Marketing modifyMarketing(MarketingSaveRequest request) throws SbcRuntimeException {
        this.validParam(request);

        Marketing marketing = marketingRepository.findById(request.getMarketingId())
                .orElseThrow(() -> new SbcRuntimeException(MarketingErrorCodeEnum.K080001));

        if(BoolFlag.NO == marketing.getIsBoss()) {
            if (!Objects.equals(request.getStoreId(), marketing.getStoreId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000018);
            }
        }

        // 判断活动的状态
        if (marketing.getBeginTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080015);
        }

        marketing.setMarketingName(request.getMarketingName());
        marketing.setBeginTime(request.getBeginTime());
        marketing.setEndTime(request.getEndTime());
        marketing.setMarketingType(request.getMarketingType());

        marketing.setSubType(request.getSubType());
        marketing.setUpdatePerson(request.getUpdatePerson());
        marketing.setUpdateTime(LocalDateTime.now());
        marketing.setJoinLevel(request.getJoinLevel());
        marketing.setScopeType(request.getScopeType());
        if(Objects.nonNull(request.getAuditStatus())){
            marketing.setAuditStatus(request.getAuditStatus());
        }
        if(Objects.nonNull(request.getParticipateType())){
            marketing.setParticipateType(request.getParticipateType());
        }
        if (Objects.nonNull(request.getStoreType())){
            marketing.setStoreType(request.getStoreType());
        }

        // 营销规则
        marketingRepository.save(marketing);

        // 先删除已有的营销和商品关联关系，然后再保存
        marketingScopeRepository.deleteByMarketingId(marketing.getMarketingId());

        // 自定义商品才需要保存
        if (request.getScopeType() != MarketingScopeType.SCOPE_TYPE_ALL) {
            this.saveScopeList(request.generateMarketingScopeList(marketing.getMarketingId()));
        }

        return marketing;
    }

    /**
     * 参数校验
     *
     * @param request
     */
    protected void validParam(MarketingSaveRequest request) {
        // 默认正常
        if(Objects.isNull(request.getPluginType())){
            request.setPluginType(PluginType.NORMAL);
        }
        boolean invalidParam = true;

        if (request.getMarketingType() == MarketingType.REDUCTION) {
            invalidParam =
                    request.getSubType() != MarketingSubType.REDUCTION_FULL_AMOUNT &&
                            request.getSubType() != MarketingSubType.REDUCTION_FULL_COUNT;
        } else if (request.getMarketingType() == MarketingType.DISCOUNT) {
            invalidParam =
                    request.getSubType() != MarketingSubType.DISCOUNT_FULL_AMOUNT &&
                            request.getSubType() != MarketingSubType.DISCOUNT_FULL_COUNT;
        } else if (request.getMarketingType() == MarketingType.GIFT) {
            invalidParam =
                    request.getSubType() != MarketingSubType.GIFT_FULL_AMOUNT &&
                            request.getSubType() != MarketingSubType.GIFT_FULL_COUNT;
        } else if (request.getMarketingType() == MarketingType.BUYOUT_PRICE) {//一口价优惠活动
            invalidParam = request.getSubType() != MarketingSubType.BUYOUT_PRICE;
        } else if (request.getMarketingType() == MarketingType.HALF_PRICE_SECOND_PIECE) {//一口价优惠活动
            invalidParam = request.getSubType() != MarketingSubType.HALF_PRICE_SECOND_PIECE;
        } else if (request.getMarketingType() == MarketingType.SUITS) {
            invalidParam = request.getSubType() != MarketingSubType.SUITS_GOODS;
        } else if (request.getMarketingType() == MarketingType.RETURN){
            invalidParam = request.getSubType() != MarketingSubType.RETURN;
        } else if (MarketingType.PREFERENTIAL.equals(request.getMarketingType())){
            invalidParam =
                    request.getSubType() != MarketingSubType.PREFERENTIAL_FULL_AMOUNT &&
                            request.getSubType() != MarketingSubType.PREFERENTIAL_FULL_COUNT;
        }

        if (invalidParam) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 全局互斥开启时，不走老的冲突校验
        if(mutexFlag()){
            return;
        }

        //活动时间
        if (request.getBeginTime().isAfter(request.getEndTime()) || request.getBeginTime().isEqual(request.getEndTime())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //店铺分类检验
        if (Constants.BOSS_DEFAULT_STORE_ID.compareTo(request.getStoreId()) != 0) {
            if (MarketingScopeType.SCOPE_TYPE_STORE_CATE == request.getScopeType()) {
                List<String> cateIds = request.getScopeIds();
                StoreCateListByIdsRequest storeCateListByIdsRequest = new StoreCateListByIdsRequest();
                storeCateListByIdsRequest.setCateIds(cateIds.stream().map(Long::valueOf).collect(Collectors.toList()));
                if (Objects.nonNull(cateIds)) {
                    StoreCateListByIdsResponse storeCateListByIdsResponse = storeCateQueryProvider.listByIds(storeCateListByIdsRequest).getContext();
                    if (Objects.nonNull(storeCateListByIdsResponse)) {
                        List<StoreCateVO> storeCateVOList = storeCateListByIdsResponse.getStoreCateVOList();
                        if (Objects.nonNull(storeCateVOList)) {
                            boolean b = storeCateVOList
                                    .stream()
                                    .map(StoreCateVO::getStoreId)
                                    .distinct()
                                    .anyMatch(storeId -> storeId.equals(request.getStoreId()));
                            if (!b) {
                                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                            }
                        }
                    }
                }
            }
            //品牌信息校验
            if (MarketingScopeType.SCOPE_TYPE_BRAND == request.getScopeType()) {
                List<String> brandIds = request.getScopeIds();
                if (Objects.nonNull(brandIds)) {
                    List<Long> brandIdList = brandIds.stream().map(Long::valueOf).collect(Collectors.toList());
                    ContractBrandListRequest contractBrandListRequest = new ContractBrandListRequest();
                    contractBrandListRequest.setGoodsBrandIds(brandIdList);
                    BaseResponse<ContractBrandListResponse> response = contractBrandQueryProvider.list(contractBrandListRequest);
                    if (Objects.nonNull(response)) {
                        List<ContractBrandVO> contractBrandVOList = response.getContext().getContractBrandVOList();
                        if (Objects.nonNull(contractBrandVOList)) {
                            boolean b = contractBrandVOList
                                    .stream()
                                    .map(ContractBrandVO::getStoreId)
                                    .distinct()
                                    .anyMatch(storeId -> storeId.equals(request.getStoreId()));
                            if (!b) {
                                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                            }
                        }
                    }
                }
            }
            // 自定义商品才需要校验
            if (MarketingScopeType.SCOPE_TYPE_CUSTOM == request.getScopeType()) {
                List<String> skuIds = request.getScopeIds();
                if (Objects.nonNull(skuIds)){
                    GoodsInfoListByIdsResponse goodsInfoListByIdsResponse = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder()
                            .goodsInfoIds(skuIds).build()).getContext();
                    if (Objects.nonNull(goodsInfoListByIdsResponse)) {
                        List<GoodsInfoVO> goodsInfos = goodsInfoListByIdsResponse.getGoodsInfos();
                        if (Objects.nonNull(goodsInfos)) {
                            boolean b = goodsInfos.stream()
                                    .map(GoodsInfoVO::getStoreId).distinct()
                                    .anyMatch(storeId -> storeId.equals(request.getStoreId()));
                            if (!b) {
                                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
                            }
                        }
                    }
                }
            }
        }

        // 一口价，第二件半价，组合购不走校验
        if (request.getSubType() != MarketingSubType.BUYOUT_PRICE
                && request.getSubType() != MarketingSubType.HALF_PRICE_SECOND_PIECE
                && request.getScopeType() == MarketingScopeType.SCOPE_TYPE_CUSTOM && request.getMarketingType() != MarketingType.SUITS) {
            Long storeId = BoolFlag.YES == request.getIsBoss() ? null : request.getStoreId();
            List<String> existsList = marketingRepository.getExistsSkuByMarketingType(request.getScopeIds(), request.getMarketingType(),request.getPluginType(),
                    request.getBeginTime(), request.getEndTime(), storeId, request.getMarketingId());
            if (!existsList.isEmpty()) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080004,
                        new Object[]{existsList.size()});
            }
        }
    }

    /**
     * 保存营销和商品关联关系
     */
    private void saveScopeList(List<MarketingScope> marketingScopeList) {
        if (CollectionUtils.isNotEmpty(marketingScopeList)) {
            marketingScopeRepository.saveAll(marketingScopeList);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 保存营销和店铺关联关系
     */
    public void saveStoreList(List<MarketingFullReturnStore> marketingFullReturnStoreList) {
        if (CollectionUtils.isNotEmpty(marketingFullReturnStoreList)) {
            marketingStoreRepository.saveAll(marketingFullReturnStoreList);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 删除营销活动
     *
     * @param marketingId
     * @return
     */
    @Transactional
    public int deleteMarketingById(Long marketingId) {
        return marketingRepository.deleteMarketing(marketingId);
    }

    /**
     * 暂停营销活动
     *
     * @param marketingId
     * @return
     */
    @Transactional
    public int pauseMarketingById(Long marketingId) {
        return marketingRepository.pauseOrStartMarketing(marketingId, BoolFlag.YES);
    }

    /**
     * 启动营销活动
     *
     * @param marketingId
     * @return
     */
    @Transactional
    public int startMarketingById(Long marketingId) {
        return marketingRepository.pauseOrStartMarketing(marketingId, BoolFlag.NO);
    }

    /**
     * 获取营销实体
     *
     * @param marketingId
     * @return
     */
    public Marketing queryById(Long marketingId) {
        return marketingRepository.findById(marketingId).orElse(null);
    }

    public List<Marketing> queryByIds(List<Long> marketingIds) {
        return marketingRepository.findAllById(marketingIds);
    }

    // 关联营销活动级别
    protected void joinMarketingLevels(Marketing marketing, MarketingResponse marketingResponse) {
        switch (marketing.getMarketingType()) {
            case REDUCTION:
                marketingResponse.setFullReductionLevelList(KsBeanUtil.convertList(marketingFullReductionLevelRepository.findByMarketingIdOrderByFullAmountAscFullCountAsc(marketing.getMarketingId()), MarketingFullReductionLevelVO.class));
                break;
            case DISCOUNT:
                marketingResponse.setFullDiscountLevelList(KsBeanUtil.convertList(marketingFullDiscountLevelRepository.findByMarketingIdOrderByFullAmountAscFullCountAsc(marketing.getMarketingId()), MarketingFullDiscountLevelVO.class));
                break;
            case GIFT:
                marketingResponse.setFullGiftLevelList(marketingFullGiftService.getLevelsByMarketingId(marketing.getMarketingId()));
                break;
            case BUYOUT_PRICE:
                marketingResponse.setBuyoutPriceLevelList(KsBeanUtil.convertList(marketingBuyoutPriceLevelRepository.findByMarketingIdOrderByFullAmountAsc(marketing.getMarketingId()), MarketingBuyoutPriceLevelVO.class));
                break;
            case HALF_PRICE_SECOND_PIECE:
                marketingResponse.setHalfPriceSecondPieceLevel(KsBeanUtil.convertList(halfPriceSecondPieceLevelRepository.findByMarketingIdOrderByNumberAsc(marketing.getMarketingId()), MarketingHalfPriceSecondPieceLevelVO.class));
                break;
            case RETURN:
                marketingResponse.setFullReturnLevelList(marketingFullReturnService.getLevelsByMarketingId(marketing.getMarketingId()));
                marketingResponse.setMarketingFullReturnStoreList(marketingFullReturnService.findStoreInfoByMarketingId(marketing.getMarketingId()));
                marketingResponse.setStoreType(marketing.getStoreType());
                break;
            case PREFERENTIAL:
                List<MarketingPreferentialLevel> marketingPreferentialLevelList =
                        marketingPreferentialLevelRepository.findByMarketingId(marketing.getMarketingId());
                List<MarketingPreferentialLevelVO> marketingPreferentialLevelVOS =
                        KsBeanUtil.convert(marketingPreferentialLevelList, MarketingPreferentialLevelVO.class);
                List<MarketingPreferentialDetail> marketingPreferentialDetailList =
                        marketingPreferentialGoodsDetailRepository.findByMarketingId(marketing.getMarketingId());
                List<MarketingPreferentialGoodsDetailVO> marketingPreferentialGoodsDetailVOS =
                        KsBeanUtil.convert(marketingPreferentialDetailList, MarketingPreferentialGoodsDetailVO.class);
                // <阶梯等级ID，对应等级商品>
                Map<Long, List<MarketingPreferentialGoodsDetailVO>> marketingLevelIdToGoodsRefDetailsMap =
                        marketingPreferentialGoodsDetailVOS.stream()
                                .collect(Collectors.groupingBy(MarketingPreferentialGoodsDetailVO::getPreferentialLevelId));
                // 阶梯信息填充关联商品
                marketingPreferentialLevelVOS.forEach(preferentialLevel -> {
                    preferentialLevel.setPreferentialDetailList(marketingLevelIdToGoodsRefDetailsMap.get(preferentialLevel.getPreferentialLevelId()));
                });
                marketingResponse.setPreferentialLevelList(marketingPreferentialLevelVOS);
                break;
            default:
                break;
        }
    }

    /**
     * 关联营销活动级别<br/>
     * 总→分→总
     * @author  lixc lixuecheng@wanmi.com
     * @date 2024/8/6 16:09
     * @param marketingList 营销活动集合
     **/
    protected void joinMarketingListLevels(List<MarketingResponse> marketingList) {
        // 提取数据，降低圈复杂度
        List<Long> reductionMarketingIds = new ArrayList<>();
        List<Long> discountMarketingIds = new ArrayList<>();
        List<Long> giftMarketingIds = new ArrayList<>();
        List<Long> returnMarketingIds = new ArrayList<>();
        List<Long> buyoutPriceMarketingIds = new ArrayList<>();
        List<Long> halfPriceSecondPriceMarketingIds = new ArrayList<>();
        List<Long> preferentialMarketingIds = new ArrayList<>();

        marketingList.stream()
                .filter(Objects::nonNull)
                .forEach(marketing -> {
                    switch (marketing.getMarketingType()) {
                        case REDUCTION:
                            reductionMarketingIds.add(marketing.getMarketingId());
                            break;
                        case DISCOUNT:
                            discountMarketingIds.add(marketing.getMarketingId());
                            break;
                        case GIFT:
                            giftMarketingIds.add(marketing.getMarketingId());
                            break;
                        case RETURN:
                            returnMarketingIds.add(marketing.getMarketingId());
                            break;
                        case BUYOUT_PRICE:
                            buyoutPriceMarketingIds.add(marketing.getMarketingId());
                            break;
                        case HALF_PRICE_SECOND_PIECE:
                            halfPriceSecondPriceMarketingIds.add(marketing.getMarketingId());
                            break;
                        case PREFERENTIAL:
                            preferentialMarketingIds.add(marketing.getMarketingId());
                            break;
                        default:
                            break;
                    }
                });

        Map<Long, List<MarketingFullReductionLevelVO>> marketingFullReductionLevelMap =
                Maps.newHashMap();
        Map<Long, List<MarketingFullDiscountLevelVO>> marketingFullDiscountLevelMap =
                Maps.newHashMap();
        Map<Long, List<MarketingFullGiftLevelVO>> marketingFullGiftLevelMap = Maps.newHashMap();
        Map<Long, List<MarketingFullReturnLevel>> marketingFullReturnLevelMap = Maps.newHashMap();
        Map<Long, List<MarketingBuyoutPriceLevelVO>> marketingBuyoutPriceLevelMap = Maps.newHashMap();
        Map<Long, List<MarketingHalfPriceSecondPieceLevelVO>> marketingHalfPriceSecondPieceLevelMap =
                Maps.newHashMap();
        Map<Long, List<MarketingPreferentialLevelVO>> marketingPreferentialLevelMap =
                Maps.newHashMap();

        if (CollectionUtils.isNotEmpty(reductionMarketingIds)) {
            List<MarketingFullReductionLevelVO> marketingFullReductionLevelList =
                    KsBeanUtil.convertList(marketingFullReductionLevelRepository
                            .findByMarketingIdInOrderByFullAmountAscFullCountAsc(
                                    reductionMarketingIds), MarketingFullReductionLevelVO.class);
            if (CollectionUtils.isNotEmpty(marketingFullReductionLevelList)) {
                marketingFullReductionLevelMap.putAll(
                        marketingFullReductionLevelList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                MarketingFullReductionLevelVO::getMarketingId)));
            }
        }

        if (CollectionUtils.isNotEmpty(discountMarketingIds)) {
            List<MarketingFullDiscountLevelVO> marketingFullDiscountLevelList =
                    KsBeanUtil.convertList(marketingFullDiscountLevelRepository
                            .findByMarketingIdInOrderByFullAmountAscFullCountAsc(
                                    discountMarketingIds), MarketingFullDiscountLevelVO.class);
            if (CollectionUtils.isNotEmpty(marketingFullDiscountLevelList)) {
                marketingFullDiscountLevelMap.putAll(
                        marketingFullDiscountLevelList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                MarketingFullDiscountLevelVO::getMarketingId)));
            }
        }

        if (CollectionUtils.isNotEmpty(giftMarketingIds)) {
            List<MarketingFullGiftLevelVO> marketingFullGiftLevelList = marketingFullGiftService.getLevelsByMarketingIds(giftMarketingIds);
            if (CollectionUtils.isNotEmpty(marketingFullGiftLevelList)) {
                marketingFullGiftLevelMap.putAll(
                        marketingFullGiftLevelList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                MarketingFullGiftLevelVO::getMarketingId)));
            }
        }

        if (CollectionUtils.isNotEmpty(returnMarketingIds)) {
            List<MarketingFullReturnLevel> marketingFullReturnLevelList =
                    marketingFullReturnService.getLevelsByMarketingIds(returnMarketingIds);
            if (CollectionUtils.isNotEmpty(marketingFullReturnLevelList)) {
                marketingFullReturnLevelMap.putAll(
                        marketingFullReturnLevelList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                MarketingFullReturnLevel::getMarketingId)));
            }
        }

        if (CollectionUtils.isNotEmpty(buyoutPriceMarketingIds)) {
            List<MarketingBuyoutPriceLevelVO> marketingBuyoutPriceLevelList =
                    KsBeanUtil.convertList(marketingBuyoutPriceLevelRepository.findByMarketingIdInOrderByFullAmountAsc(
                            buyoutPriceMarketingIds), MarketingBuyoutPriceLevelVO.class);
            if (CollectionUtils.isNotEmpty(marketingBuyoutPriceLevelList)) {
                marketingBuyoutPriceLevelMap.putAll(
                        marketingBuyoutPriceLevelList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                MarketingBuyoutPriceLevelVO::getMarketingId)));
            }
        }

        if (CollectionUtils.isNotEmpty(halfPriceSecondPriceMarketingIds)) {
            List<MarketingHalfPriceSecondPieceLevelVO> marketingHalfPriceSecondPieceLevelList =
                    KsBeanUtil.convertList(halfPriceSecondPieceLevelRepository.findByMarketingIdInOrderByNumberAsc(
                            halfPriceSecondPriceMarketingIds), MarketingHalfPriceSecondPieceLevelVO.class);
            if (CollectionUtils.isNotEmpty(marketingHalfPriceSecondPieceLevelList)) {
                marketingHalfPriceSecondPieceLevelMap.putAll(
                        marketingHalfPriceSecondPieceLevelList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                MarketingHalfPriceSecondPieceLevelVO
                                                        ::getMarketingId)));
            }
        }

        if (CollectionUtils.isNotEmpty(preferentialMarketingIds)) {
            List<MarketingFullGiftLevelVO> marketingFullGiftLevelList = marketingFullGiftService.getLevelsByMarketingIds(giftMarketingIds);
            if (CollectionUtils.isNotEmpty(marketingFullGiftLevelList)) {
                marketingFullGiftLevelMap.putAll(
                        marketingFullGiftLevelList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                MarketingFullGiftLevelVO::getMarketingId)));
            }

            List<MarketingPreferentialLevel> marketingPreferentialLevelList =
                    marketingPreferentialLevelRepository.findByMarketingIdIn(preferentialMarketingIds);
            List<MarketingPreferentialLevelVO> marketingPreferentialLevelVOS =
                    KsBeanUtil.convert(marketingPreferentialLevelList, MarketingPreferentialLevelVO.class);
            List<MarketingPreferentialDetail> marketingPreferentialDetailList =
                    marketingPreferentialGoodsDetailRepository.findByMarketingIdIn(preferentialMarketingIds);
            List<MarketingPreferentialGoodsDetailVO> marketingPreferentialGoodsDetailVOS =
                    KsBeanUtil.convert(marketingPreferentialDetailList, MarketingPreferentialGoodsDetailVO.class);
            // <阶梯等级ID，对应等级商品>
            Map<Long, List<MarketingPreferentialGoodsDetailVO>> marketingLevelIdToGoodsRefDetailsMap =
                    marketingPreferentialGoodsDetailVOS.stream()
                            .collect(Collectors.groupingBy(MarketingPreferentialGoodsDetailVO::getPreferentialLevelId));
            // 阶梯信息填充关联商品
            marketingPreferentialLevelVOS.forEach(preferentialLevel -> {
                preferentialLevel.setPreferentialDetailList(marketingLevelIdToGoodsRefDetailsMap.get(preferentialLevel.getPreferentialLevelId()));
            });
            marketingPreferentialLevelMap.putAll(marketingPreferentialLevelVOS.stream()
                    .collect(Collectors.groupingBy(MarketingPreferentialLevelVO::getMarketingId)));
        }


        marketingList.forEach(
                marketingResponse -> {
                    switch (marketingResponse.getMarketingType()) {
                        case REDUCTION:
                            marketingResponse.setFullReductionLevelList(
                                    marketingFullReductionLevelMap.getOrDefault(
                                            marketingResponse.getMarketingId(),
                                            Lists.newArrayList()));
                            break;
                        case DISCOUNT:
                            marketingResponse.setFullDiscountLevelList(
                                    marketingFullDiscountLevelMap.getOrDefault(
                                            marketingResponse.getMarketingId(),
                                            Lists.newArrayList()));
                            break;
                        case GIFT:
                            marketingResponse.setFullGiftLevelList(
                                    marketingFullGiftLevelMap.getOrDefault(
                                            marketingResponse.getMarketingId(),
                                            Lists.newArrayList()));
                            break;
                        case RETURN:
                            marketingResponse.setFullReturnLevelList(
                                    marketingFullReturnLevelMap.getOrDefault(
                                            marketingResponse.getMarketingId(),
                                            Lists.newArrayList()));
                            break;
                        case BUYOUT_PRICE:
                            marketingResponse.setBuyoutPriceLevelList(
                                    marketingBuyoutPriceLevelMap.getOrDefault(
                                            marketingResponse.getMarketingId(),
                                            Lists.newArrayList()));
                            break;
                        case HALF_PRICE_SECOND_PIECE:
                            marketingResponse.setHalfPriceSecondPieceLevel(
                                    marketingHalfPriceSecondPieceLevelMap.getOrDefault(
                                            marketingResponse.getMarketingId(),
                                            Lists.newArrayList()));
                            break;
                        case PREFERENTIAL:
                            marketingResponse.setPreferentialLevelList(
                                    marketingPreferentialLevelMap
                                    .getOrDefault(marketingResponse.getMarketingId(), Lists.newArrayList()));
                            break;
                        default:
                            break;
                    }
                });
    }

    // 批量关联营销活动级别
    public void joinMarketingLevels(List<MarketingViewVO> vos) {
        //批量满减
        List<Long> reductionIds = vos.stream()
                .filter(m -> MarketingType.REDUCTION == m.getMarketingType()).map(MarketingViewVO::getMarketingId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(reductionIds)) {
            List<MarketingFullReductionLevel> levels = marketingFullReductionLevelRepository.findAll((root, cq, cb) -> root.get("marketingId").in(reductionIds));
            if (CollectionUtils.isNotEmpty(levels)) {
                List<MarketingFullReductionLevelVO> levelVOS = KsBeanUtil.convert(levels, MarketingFullReductionLevelVO.class);
                vos.stream().filter(v -> reductionIds.contains(v.getMarketingId()))
                        .forEach(v -> v.setFullReductionLevelList(levelVOS.stream().filter(l -> l.getMarketingId().equals(v.getMarketingId())).collect(Collectors.toList())));
            }
        }

        //批量满折
        List<Long> discountIds = vos.stream()
                .filter(m -> MarketingType.DISCOUNT == m.getMarketingType()).map(MarketingViewVO::getMarketingId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(discountIds)) {
            List<MarketingFullDiscountLevel> levels = marketingFullDiscountLevelRepository.findAll((root, cq, cb) -> root.get("marketingId").in(discountIds));
            if (CollectionUtils.isNotEmpty(levels)) {
                List<MarketingFullDiscountLevelVO> levelVOS = KsBeanUtil.convert(levels, MarketingFullDiscountLevelVO.class);
                vos.stream().filter(v -> discountIds.contains(v.getMarketingId()))
                        .forEach(v -> v.setFullDiscountLevelList(levelVOS.stream().filter(l -> l.getMarketingId().equals(v.getMarketingId())).collect(Collectors.toList())));
            }
        }

        //批量满赠
        List<Long> giftIds = vos.stream()
                .filter(m -> MarketingType.GIFT == m.getMarketingType()).map(MarketingViewVO::getMarketingId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(giftIds)) {
            List<MarketingFullGiftLevelVO> levelVOS = marketingFullGiftService.getLevelsByMarketingIds(giftIds);
            if (CollectionUtils.isNotEmpty(levelVOS)) {
                vos.stream().filter(v -> giftIds.contains(v.getMarketingId()))
                        .forEach(v -> v.setFullGiftLevelList(levelVOS.stream().filter(l -> l.getMarketingId().equals(v.getMarketingId())).collect(Collectors.toList())));
            }
        }

        //批量满返
        List<Long> couponIds = vos.stream()
                .filter(m -> MarketingType.RETURN == m.getMarketingType()).map(MarketingViewVO::getMarketingId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(couponIds)) {
            List<MarketingFullGiftLevelVO> levels = marketingFullGiftService.getLevelsByMarketingIds(couponIds);
            if (CollectionUtils.isNotEmpty(levels)) {
                List<MarketingFullGiftLevelVO> levelVOS = KsBeanUtil.convert(levels, MarketingFullGiftLevelVO.class);
                vos.stream().filter(v -> giftIds.contains(v.getMarketingId()))
                        .forEach(v -> v.setFullGiftLevelList(levelVOS.stream().filter(l -> l.getMarketingId().equals(v.getMarketingId())).collect(Collectors.toList())));
            }
        }

        //批量一口价
        List<Long> buyoutIds = vos.stream()
                .filter(m -> MarketingType.BUYOUT_PRICE == m.getMarketingType()).map(MarketingViewVO::getMarketingId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(buyoutIds)) {
            List<MarketingBuyoutPriceLevel> levels = marketingBuyoutPriceLevelRepository.findAll((root, cq, cb) -> root.get("marketingId").in(buyoutIds));
            if (CollectionUtils.isNotEmpty(levels)) {
                List<MarketingBuyoutPriceLevelVO> levelVOS = KsBeanUtil.convert(levels, MarketingBuyoutPriceLevelVO.class);
                vos.stream().filter(v -> buyoutIds.contains(v.getMarketingId()))
                        .forEach(v -> v.setBuyoutPriceLevelList(levelVOS.stream().filter(l -> l.getMarketingId().equals(v.getMarketingId())).collect(Collectors.toList())));
            }
        }

        //批量第二件半价
        List<Long> halfIds = vos.stream()
                .filter(m -> MarketingType.HALF_PRICE_SECOND_PIECE == m.getMarketingType()).map(MarketingViewVO::getMarketingId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(halfIds)) {
            List<MarketingHalfPriceSecondPieceLevel> levels = halfPriceSecondPieceLevelRepository.findAll((root, cq, cb) -> root.get("marketingId").in(halfIds));
            if (CollectionUtils.isNotEmpty(levels)) {
                List<MarketingHalfPriceSecondPieceLevelVO> levelVOS = KsBeanUtil.convert(levels, MarketingHalfPriceSecondPieceLevelVO.class);
                vos.stream().filter(v -> halfIds.contains(v.getMarketingId()))
                        .forEach(v -> v.setHalfPriceSecondPieceLevel(levelVOS.stream().filter(l -> l.getMarketingId().equals(v.getMarketingId())).collect(Collectors.toList())));
            }
        }
    }

    /**
     * 将营销活动集合，map成 { goodsId - list<Marketing> } 结构
     *
     * @param marketingRequest
     * @return
     */
    @Override
    public Map<String, List<MarketingResponse>> getMarketingMapByGoodsId(MarketingRequest marketingRequest) {
        Map<String, List<MarketingResponse>> map = new HashMap<>();
        //满返查询storeId为-1的
        List<Long> storeIds = marketingRequest.getStoreIds();
        if (CollectionUtils.isNotEmpty(storeIds)){
            storeIds.add(Constant.BOSS_DEFAULT_STORE_ID);
            marketingRequest.setStoreIds(storeIds);
        }else {
            marketingRequest.setStoreIds(Collections.singletonList(Constant.BOSS_DEFAULT_STORE_ID));
        }
        // 满返需获取平台分类
        List<Long> cateIds =
                marketingRequest.getMarketingGoods().stream().map(MarketingGoods::getCateId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        marketingRequest.setCateIds(cateIds);
        List<Marketing> marketingList = marketingRepository.findAll(marketingRequest.getWhereCriteria());
        if (marketingList != null && !marketingList.isEmpty()) {
            //关联活动级别
            List<MarketingResponse> marketingResponseList = marketingMapper.marketingListToMarketingResponseList(marketingList);

                if (marketingRequest.getCascadeLevel() != null && marketingRequest.getCascadeLevel()) {
                    joinMarketingListLevels(marketingResponseList);
                }

            //按活动类型分组
            Map<MarketingType, List<MarketingResponse>> marketingTypeListMap = marketingResponseList.stream().collect(Collectors.groupingBy(MarketingResponse::getMarketingType));

            marketingRequest.getMarketingGoods().forEach(marketingGoods -> {

                marketingTypeListMap.forEach((k, v) -> {
                    //同一种活动同一时间最早创建的生效
                    List<MarketingResponse> marketings =
                            v.stream().filter(marketing -> marketingGoods.getStoreId().equals(marketing.getStoreId())
                                    || Constants.BOSS_DEFAULT_STORE_ID.equals(marketing.getStoreId()))
                            .filter(marketing -> {
                                // 平台满返先看店铺设置
                                if (MarketingType.RETURN == marketing.getMarketingType() &&
                                        Constants.BOSS_DEFAULT_STORE_ID.equals(marketing.getStoreId())){
                                    if(MarketingStoreType.STORE_TYPE_ALL.equals(marketing.getStoreType())){
                                        return dealMarketLabel(marketingGoods, marketing);
                                    } else if (MarketingStoreType.STORE_TYPE_APPOINT.equals(marketing.getStoreType())){
                                        List<Long> fullReturnStoreIds =
                                                marketing.getMarketingFullReturnStoreList().stream().map(MarketingFullReturnStore::getStoreId).toList();
                                        if (fullReturnStoreIds.contains(marketingGoods.getStoreId())){
                                            return dealMarketLabel(marketingGoods, marketing);
                                        }else {
                                            return false;
                                        }
                                    }
                                }
                                return dealMarketLabel(marketingGoods, marketing);
                            }).sorted(Comparator.comparing(MarketingResponse::getCreateTime)).collect(Collectors.toList());

                    //取第一个(最早创建的)
                    if (CollectionUtils.isNotEmpty(marketings)) {
                        MarketingResponse marketingResponse = marketings.get(NumberUtils.INTEGER_ZERO);
                        if (MarketingType.RETURN == marketingResponse.getMarketingType()){
                            MarketingResponse marketingBoss =
                                    marketings.stream().filter(marketing -> marketing.getIsBoss() == BoolFlag.YES).findFirst().orElse(null);
                            if (Objects.nonNull(marketingBoss)){
                                dealPauseMarketing(map, marketingGoods, marketingBoss);
                            }
                            MarketingResponse marketingSupplier =
                                    marketings.stream().filter(marketing -> marketing.getIsBoss() == BoolFlag.NO).findFirst().orElse(null);
                            if (Objects.nonNull(marketingSupplier)){
                                dealPauseMarketing(map, marketingGoods, marketingSupplier);
                            }
                        }else {
                            dealPauseMarketing(map, marketingGoods, marketingResponse);
                        }
                    }
                });
            });
        }
        return map;
    }

    private void dealPauseMarketing(Map<String, List<MarketingResponse>> map, MarketingGoods marketingGoods,
                                    MarketingResponse marketingResponse) {
        //活动是否生效
        if (marketingResponse.getIsPause() == BoolFlag.NO) {
            List<MarketingResponse> list;
            if (map.get(marketingGoods.getGoodsInfoId()) == null) {
                list = new LinkedList<>();
                map.put(marketingGoods.getGoodsInfoId(), list);
            } else {
                list = map.get(marketingGoods.getGoodsInfoId());
            }
            list.add(marketingResponse);
        }
    }

    private boolean dealMarketLabel(MarketingGoods marketingGoods, MarketingResponse marketing) {
        if (marketing.getScopeType() == MarketingScopeType.SCOPE_TYPE_ALL) {
            return true;
        }
        List<String> scopeIds = marketing.getMarketingScopeList().stream().map(MarketingScopeVO::getScopeId).toList();
        List<String> storeCateIds = marketingGoods.getStoreCateIds().stream().map(Objects::toString).toList();
        //平台分类
        List<String> cateIds = Lists.newArrayList();
        if (Objects.nonNull(marketingGoods.getCateId())){
            cateIds.add(String.valueOf(marketingGoods.getCateId()));
        }
        if ((marketing.getScopeType().equals(MarketingScopeType.SCOPE_TYPE_CUSTOM)
                    && scopeIds.contains(marketingGoods.getGoodsInfoId()))
                || (marketing.getScopeType().equals(MarketingScopeType.SCOPE_TYPE_BRAND)
                    && marketingGoods.getBrandId() != null
                    && scopeIds.contains(marketingGoods.getBrandId().toString()))
                || (marketing.getScopeType().equals(MarketingScopeType.SCOPE_TYPE_STORE_CATE)
                    && storeCateIds.stream().anyMatch(scopeIds::contains))
                || (marketing.getScopeType().equals(MarketingScopeType.SCOPE_TYPE_GOODS_CATE)
                && cateIds.stream().anyMatch(scopeIds::contains))) {
            return true;
        }
        return false;
    }

    /**
     * 提供管理端使用
     * 获取营销实体，包括详细信息，level，detail等
     *
     * @param marketingId
     * @return
     */
    @Transactional
    public MarketingResponse getMarketingByIdForSupplier(Long marketingId) {
        Marketing marketing = marketingRepository.findById(marketingId).orElse(null);
        MarketingResponse marketingResponse = new MarketingResponse();
        // 不存在, 已删除, 未开始均认为不存在
        if (marketing == null || marketing.getDelFlag() == DeleteFlag.YES) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        }
        return getManagerMarketing(marketing, marketingResponse);
    }

    /**
     * 提供用户端使用
     * 获取营销实体，包括详细信息，level，detail等
     *
     * @param marketingId
     * @return
     */
    @Transactional
    public MarketingResponse getMarketingByIdForCustomer(Long marketingId) {
        return getMarketingByIdForCustomer(marketingId, null);
    }

    /**
     * 提供用户端使用
     * 获取营销实体，包括详细信息，level，detail等
     *
     * @param marketingId
     * @return
     */
    @Transactional
    public MarketingResponse getMarketingByIdForCustomer(Long marketingId, Long storeId) {
        Marketing marketing = marketingRepository.findById(marketingId).orElse(null);
        MarketingResponse marketingResponse = new MarketingResponse();
        // 不存在, 已删除, 未开始均认为不存在
        if (marketing == null || marketing.getDelFlag() == DeleteFlag.YES ||
                marketing.getBeginTime().isAfter(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        } else if (marketing.getIsPause() == BoolFlag.YES) { // 暂停
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080017);
        } else if (marketing.getEndTime().isBefore(LocalDateTime.now())) { // 结束
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080018);
        }

        // SBC为空方法，O2O时校验门店是否属于该营销
        validateMarketingContainStoreId(marketing, storeId);
        return getMarketing(marketing, marketingResponse, storeId);
    }

    /**
     * 提供用户端使用
     * 获取营销实体，包括详细信息，level，detail等
     *
     * @param marketingId
     * @return
     */
    @Transactional
    public MarketingResponse getMarketingSimpleByIdForCustomer(Long marketingId, Long storeId) {
        Marketing marketing = marketingRepository.findById(marketingId).orElse(null);
        MarketingResponse marketingResponse = new MarketingResponse();
        // 不存在, 已删除, 未开始均认为不存在
        if (marketing == null || marketing.getDelFlag() == DeleteFlag.YES ||
                marketing.getBeginTime().isAfter(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        } else if (marketing.getIsPause() == BoolFlag.YES) { // 暂停
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080017);
        } else if (marketing.getEndTime().isBefore(LocalDateTime.now())) { // 结束
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080018);
        }

        // SBC为空方法，O2O时校验门店是否属于该营销
        validateMarketingContainStoreId(marketing, storeId);
        return marketingMapper.marketingToMarketingResponse(marketing);
    }

    /**
     * 提供用户端使用 获取营销实体
     * @param marketingId
     * @return
     */
    public MarketingSimpleVO getMarketingSimpleByIdForCustomer(Long marketingId) {
        Marketing marketing = marketingRepository.findById(marketingId).orElse(null);

        // 不存在, 已删除, 未开始均认为不存在
        if (marketing == null
                || marketing.getDelFlag() == DeleteFlag.YES
                || marketing.getBeginTime().isAfter(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        }
        MarketingResponse marketingResponse = marketingMapper.marketingToMarketingResponse(marketing);
        joinMarketingLevels(marketing,marketingResponse);

        return marketingMapper.marketingToMarketingSimpleVO(marketingResponse);
    }

        /**
         * 管理端获取营销（和C端不同，允许获取下架 禁售 删除的商品）
         *
         * @param marketing
         * @param marketingResponse
         * @return
         */
    public MarketingResponse getManagerMarketing(Marketing marketing, MarketingResponse marketingResponse) {
        //组装营销类型信息
        joinMarketingLevels(marketing, marketingResponse);
        BeanUtils.copyProperties(marketing, marketingResponse);
        List<MarketingScope> scopeList = marketing.getMarketingScopeList();
        marketingResponse.setMarketingScopeList(KsBeanUtil.convertList(scopeList, MarketingScopeVO.class));
        List<String> goodsInfoIds = new ArrayList<>();

        switch (marketing.getScopeType()) {
            case SCOPE_TYPE_ALL:
                break;
            case SCOPE_TYPE_CUSTOM:
                List<String> skuIds = scopeList.stream().map(MarketingScope::getScopeId).collect(Collectors.toList());
                goodsInfoIds.addAll(skuIds);
                break;
            case SCOPE_TYPE_BRAND:
                List<Long> brandIds = scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).collect(Collectors.toList());
                dealBrandScope(brandIds, marketing, marketingResponse);
                break;
            case SCOPE_TYPE_STORE_CATE:
                List<Long> storeCateIds = scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).collect(Collectors.toList());
                dealStoreCateScope(storeCateIds, marketingResponse);
                break;
             case SCOPE_TYPE_GOODS_CATE:
                List<Long> goodsCateIds = scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).collect(Collectors.toList());
                dealGoodsCateScope(goodsCateIds, marketingResponse);
                break;
             case SCOPE_TYPE_O2O_CATE:
                List<Long> o2oCateIds = scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).collect(Collectors.toList());
                self.dealO2oCateScope(o2oCateIds, marketingResponse);
                break;
            default:
        }

        //组装商品数据(包含赠品)
        dealSkuScope(goodsInfoIds, marketing, marketingResponse, Boolean.FALSE);
        if (CollectionUtils.isNotEmpty(marketingResponse.getMarketingScopeList())){
            List<MarketingScopeVO> marketingScopeList =
                    marketingResponse.getMarketingScopeList().stream().sorted(Comparator.comparing(MarketingScopeVO::getScopeId)).collect(Collectors.toList());
            marketingResponse.setMarketingScopeList(marketingScopeList);
        }
        return marketingResponse;
    }

    /**
     * C端获取营销
     *
     * @param marketing
     * @param marketingResponse
     * @return
     */
    protected MarketingResponse getMarketing(Marketing marketing, MarketingResponse marketingResponse, Long storeId) {
        //组装营销类型信息
        joinMarketingLevels(marketing, marketingResponse);
        BeanUtils.copyProperties(marketing, marketingResponse);
        List<MarketingScope> scopeList = marketing.getMarketingScopeList();
        marketingResponse.setMarketingScopeList(KsBeanUtil.convertList(scopeList, MarketingScopeVO.class));
        List<String> goodsInfoIds = new ArrayList<>();
// marketing_scope
        // marketing_scope
        // marketing_scope
        // marketing_full_return_store
        // marketing_full_return_store
        // marketing_full_return_store
        //
        switch (marketing.getScopeType()) {
            case SCOPE_TYPE_ALL:
                break;
            case SCOPE_TYPE_CUSTOM:
                List<String> skuIds = scopeList.stream().map(MarketingScope::getScopeId).toList();
                goodsInfoIds.addAll(skuIds);
                break;
            case SCOPE_TYPE_BRAND:
                List<Long> brandIds = scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).collect(Collectors.toList());
                dealBrandScope(brandIds, marketing, marketingResponse);
                break;
            case SCOPE_TYPE_STORE_CATE:
                List<Long> storeCateIds = scopeList.stream().map(item -> Long.valueOf(item.getScopeId())).collect(Collectors.toList());
                dealStoreCateScope(storeCateIds, marketingResponse);
                break;
            default:
        }
        dealSkuScope(goodsInfoIds, marketing, marketingResponse, Boolean.TRUE, storeId);
        if (MarketingType.RETURN == marketingResponse.getMarketingType()){
            List<MarketingFullReturnLevel> levelList = marketingResponse.getFullReturnLevelList();
            if (CollectionUtils.isNotEmpty(levelList)){
                List<String> couponIds = marketingResponse.getFullReturnLevelList().stream()
                        .map(MarketingFullReturnLevel::getFullReturnDetailList)
                        .flatMap(Collection::stream)
                        .map(MarketingFullReturnDetail::getCouponId)
                        .collect(Collectors.toList());
                //获取优惠券信息
                List<CouponInfo> returnList = couponInfoService.queryCouponInfos(CouponInfoQueryRequest.builder().couponIds(couponIds).build());

                if (CollectionUtils.isNotEmpty(returnList)){
                    marketingResponse.setReturnList(KsBeanUtil.copyListProperties(returnList, CouponInfoVO.class));
                    marketingResponse.getReturnList().forEach(t->{
                        CouponInfoResponse couponInfoResponse = couponInfoService.queryCouponInfoDetail(t.getCouponId(),t.getStoreId());
                        t.setScopeNames(couponInfoResponse.getCouponInfo().getScopeNames());
                    });
                }
            }
        }
        return marketingResponse;
    }

    /**
     * 获取营销对应的商品信息
     *
     * @param marketingId
     * @return
     */
    @Transactional
    public GoodsInfoResponse getGoodsByMarketingId(Long marketingId) {
        Marketing marketing = marketingRepository.findById(marketingId).orElse(null);
        if (marketing != null && marketing.getDelFlag() == DeleteFlag.NO) {
            List<MarketingScope> scopeList = marketing.getMarketingScopeList();
            if (CollectionUtils.isNotEmpty(scopeList)) {
                List<String> goodsInfoIds =
                        scopeList.stream().map(MarketingScope::getScopeId).collect(Collectors.toList());
                GoodsInfoViewPageRequest queryRequest = new GoodsInfoViewPageRequest();
                //FIXME 营销是平铺展示，但是数量达到一定层级，还是需要分页，先暂时这么控制
                queryRequest.setPageSize(10000);
                queryRequest.setStoreId(marketing.getStoreId());
                queryRequest.setAddedFlag(AddedFlag.YES.toValue());//上架
                queryRequest.setDelFlag(DeleteFlag.NO.toValue());//可用
                queryRequest.setAuditStatus(CheckStatus.CHECKED);//已审核
                queryRequest.setGoodsInfoIds(goodsInfoIds);
                GoodsInfoViewPageResponse pageResponse = goodsInfoQueryProvider.pageView(queryRequest).getContext();
                return GoodsInfoResponse.builder()
                        .goodsInfoPage(pageResponse.getGoodsInfoPage())
                        .brands(pageResponse.getBrands())
                        .cates(pageResponse.getCates())
                        .goodses(pageResponse.getGoodses())
                        .build();
            } else {
                return new GoodsInfoResponse();
            }
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        }
    }

    /**
     * 获取验证进行中的营销
     *
     * @param marketingIds 参数
     */
    public List<String> queryStartingMarketing(List<Long> marketingIds) {
        return marketingRepository.queryStartingMarketing(marketingIds);
    }

    /**
     * 根据skuId获取进行中组合购活动的id
     *
     * @param skuId
     * @param startTime
     * @param endTime
     * @param storeId
     * @param excludeId
     * @return
     */
    public List<String> getMarketingSuitsExists(String skuId, LocalDateTime startTime, LocalDateTime endTime, Long storeId, Long excludeId) {
        return marketingRepository.getMarketingSuitsExists(skuId, startTime, endTime, storeId, excludeId);
    }

    /**
     * 根据skuId查询正在进行中的组合购活动
     *
     * @param skuId
     * @return
     */
    public List<Marketing> getMarketingBySuitsSkuId(String skuId) {
        return marketingRepository.getMarketingBySuitsSkuId(skuId);
    }


    /**
     * 根据skuId查询进行中和未开始的组合购活动
     *
     * @param skuId
     * @return
     */
    public List<Marketing> getMarketingNotEndBySuitsSkuId(String skuId) {
        return marketingRepository.getMarketingNotEndBySuitsSkuId(skuId);
    }

    /**
     * 模糊搜索营销列表
     *
     * @param request
     * @return
     */
    public Page<Marketing> page(MarketingBuyoutPriceSearchRequest request,
                                List<Long> listStoreId, Long storeId) {
        request.setStoreIds(listStoreId);
        request.setStoreId(storeId);
        return marketingRepository.findAll(getWhereCriteria(request), request.getPageRequest());
    }


    public Specification<Marketing> getWhereCriteria(MarketingBuyoutPriceSearchRequest request) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>(16);

            if (StringUtils.isNotBlank(request.getMarketingName())) {
                predicates.add(cbuild.like(root.get("marketingName"),
                        StringUtil.SQL_LIKE_CHAR.concat(XssUtils.replaceLikeWildcard(request.getMarketingName().trim())).concat(StringUtil.SQL_LIKE_CHAR)));
            }

            if (Objects.nonNull(request.getStartTime())) {
                Predicate p1 = cbuild.greaterThanOrEqualTo(root.get("beginTime"), request.getStartTime());
                predicates.add(p1);
            }
            if (Objects.nonNull(request.getEndTime())) {
                Predicate p1 = cbuild.lessThanOrEqualTo(root.get("endTime"), request.getEndTime());
                predicates.add(p1);
            }


            if (Objects.nonNull(request.getStoreId())) {
                predicates.add(cbuild.equal(root.get("storeId"), request.getStoreId()));
            }

            if(Objects.nonNull(request.getPluginType())){
                predicates.add(cbuild.equal(root.get("pluginType"), request.getPluginType()));
            }

            if (request.getMarketingSubType() != null) {
                predicates.add(cbuild.equal(root.get("subType"), request.getMarketingSubType()));
            }

            if (Objects.nonNull(request.getPlatform())) {
                if (Platform.SUPPLIER.equals(request.getPlatform()) && request.getTargetLevelId() != null) {
                    Expression<Integer> expression = cbuild.function("FIND_IN_SET", Integer.class, cbuild.literal(request.getTargetLevelId()), root.get("joinLevel"));
                    predicates.add(cbuild.greaterThan(expression, 0));
                } else if (Platform.BOSS.equals(request.getPlatform()) && request.getTargetLevelId() != null) {
                    if (request.getTargetLevelId() != -1) {

                        predicates.add(cbuild.notEqual(root.get("joinLevel"), "-1"));
                    } else {

                        predicates.add(cbuild.equal(root.get("joinLevel"), request.getTargetLevelId()));
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(request.getStoreIds())) {
                predicates.add(root.get("storeId").in(request.getStoreIds()));
            }

            switch (request.getQueryTab()) {
                case STARTED://进行中
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("beginTime"), LocalDateTime.now()));
                    predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
                    predicates.add(cbuild.equal(root.get("isPause"), BoolFlag.NO));
                    break;
                case PAUSED://暂停中
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("beginTime"), LocalDateTime.now()));
                    predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
                    predicates.add(cbuild.equal(root.get("isPause"), BoolFlag.YES));
                    break;
                case NOT_START://未开始
                    predicates.add(cbuild.greaterThan(root.get("beginTime"), LocalDateTime.now()));
                    break;
                case ENDED://已结束
                    predicates.add(cbuild.lessThan(root.get("endTime"), LocalDateTime.now()));
                    break;
                case S_NS: // 进行中&未开始
                    predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
                    predicates.add(cbuild.equal(root.get("isPause"), BoolFlag.NO));
                    break;
                default:
                    break;
            }

            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }

    /**
     * @param scopeIds
     * @param marketing
     * @param marketingResponse
     * @return
     * @description 组装商品信息
     * @author xuyunpeng
     * @date 2021/5/26 10:00 上午
     */
    public void dealSkuScope(List<String> scopeIds, Marketing marketing, MarketingResponse marketingResponse, Boolean isCustomer) {
        dealSkuScope(scopeIds, marketing, marketingResponse, isCustomer, null);
    }

    /**
     * @description 组装商品信息
     * @author  xuyunpeng
     * @date 2021/5/26 10:00 上午
     * @param scopeIds
     * @param marketing
     * @param marketingResponse
     * @return
     */
    public void dealSkuScope(List<String> scopeIds, Marketing marketing, MarketingResponse marketingResponse, Boolean isCustomer, Long storeId) {
        //将满赠赠品的商品信息也带出
        if (marketingResponse.getMarketingType() == MarketingType.GIFT && CollectionUtils.isNotEmpty(marketingResponse.getFullGiftLevelList())) {
            List<String> detailGoodsInfoIds =
                    marketingResponse.getFullGiftLevelList().stream().flatMap(level -> level.getFullGiftDetailList().stream())
                            .map(MarketingFullGiftDetailVO::getProductId).collect(Collectors.toList());
            scopeIds.addAll(detailGoodsInfoIds);
        }

        // 加价购商品
        if (MarketingType.PREFERENTIAL.equals(marketingResponse.getMarketingType()) &&
                CollectionUtils.isNotEmpty(marketingResponse.getPreferentialLevelList())){
            List<String> detailGoodsInfoIds =
                    marketingResponse.getPreferentialLevelList().stream().flatMap(level ->
                                    level.getPreferentialDetailList().stream())
                            .map(MarketingPreferentialGoodsDetailVO::getGoodsInfoId).collect(Collectors.toList());
            scopeIds.addAll(detailGoodsInfoIds);
        }

        //组装商品信息
        if (CollectionUtils.isNotEmpty(scopeIds)) {
            GoodsInfoViewPageRequest queryRequest = new GoodsInfoViewPageRequest();
            queryRequest.setPageSize(10000);
            if(marketing.getPluginType() != PluginType.O2O){
                queryRequest.setStoreId(marketing.getStoreId());
            }
            List<CheckStatus> auditStatuses = new ArrayList<>();
            auditStatuses.add(CheckStatus.CHECKED);//已审核
            if (Boolean.TRUE.equals(isCustomer)) {
                queryRequest.setAddedFlag(AddedFlag.YES.toValue());//上架
                queryRequest.setDelFlag(DeleteFlag.NO.toValue());//可用
            } else {
                auditStatuses.add(CheckStatus.FORBADE);//禁售
            }
            queryRequest.setAuditStatuses(auditStatuses);
            queryRequest.setGoodsInfoIds(scopeIds);
            queryRequest.setIsMarketing(Boolean.TRUE);
            GoodsInfoViewPageResponse pageResponse = goodsInfoQueryProvider.pageView(queryRequest).getContext();
            marketingResponse.setGoodsList(GoodsInfoResponseVO.builder()
                    .goodsInfoPage(pageResponse.getGoodsInfoPage())
                    .goodses(pageResponse.getGoodses())
                    .brands(CollectionUtils.isEmpty(pageResponse.getBrands()) ? Collections.emptyList() :
                            pageResponse.getBrands())
                    .cates(pageResponse.getCates())
                    .build());
            Long marketingStoreId = marketing.getStoreId();
            if(Objects.nonNull(marketingStoreId)){
                StoreByIdResponse storeByIdResponse =
                        storeQueryProvider.getById(StoreByIdRequest.builder().storeId(marketingStoreId).build()).getContext();
                if (Objects.nonNull(storeByIdResponse) && Objects.nonNull(storeByIdResponse.getStoreVO())) {
                    marketingResponse.setStoreName(storeByIdResponse.getStoreVO().getStoreName());
                }
            }
        }

    }

    /**
     * @param scopeIds
     * @param marketing
     * @param marketingResponse
     * @return
     * @description 品牌数据
     * @author xuyunpeng
     * @date 2021/5/26 10:02 上午
     */
    public void dealBrandScope(List<Long> scopeIds, Marketing marketing, MarketingResponse marketingResponse) {
        if (CollectionUtils.isNotEmpty(scopeIds)) {
            ContractBrandListRequest contractBrandListRequest = new ContractBrandListRequest();
            if (!Objects.equals(BoolFlag.YES,marketing.getIsBoss())){
                contractBrandListRequest.setStoreId(marketing.getStoreId());
            }
            contractBrandListRequest.setGoodsBrandIds(scopeIds);
            List<String> brandNames = getBrandsName(contractBrandListRequest,scopeIds,marketing.getIsBoss());
            marketingResponse.setScopeNames(brandNames);

        }
    }


    /**
     * @param scopeIds
     * @param marketingResponse
     * @return
     * @description 店铺分类数据
     * @author xuyunpeng
     * @date 2021/5/26 10:02 上午
     */
    public void dealStoreCateScope(List<Long> scopeIds, MarketingResponse marketingResponse) {
        if (CollectionUtils.isNotEmpty(scopeIds)) {
            List<StoreCateVO> storeCateList = storeCateQueryProvider.listByIds(new StoreCateListByIdsRequest(scopeIds)).getContext().getStoreCateVOList();
            storeCateList = storeCateList.stream().filter(cate -> cate.getDelFlag() == DeleteFlag.NO).collect(Collectors.toList());
            List<StoreCateVO> newCateList = storeCateList;
            //只显示父级的节点的名称
            List<StoreCateVO> nameGoodsCateList = storeCateList.stream().filter(item -> newCateList.stream().noneMatch(i -> i.getStoreCateId().equals(item.getCateParentId()))).collect(Collectors.toList());
            List<String> cateNames = nameGoodsCateList.stream().map(StoreCateVO::getCateName).collect(Collectors.toList());
            marketingResponse.setScopeNames(cateNames);
        }
    }

    /**
     * @description 平台类目数据
     * @param scopeIds
     * @param marketingResponse
     * @return
     */
    public void dealGoodsCateScope(List<Long> scopeIds, MarketingResponse marketingResponse) {
        if (CollectionUtils.isNotEmpty(scopeIds)) {
            List<GoodsCateVO> goodsCateVOList = goodsCateQueryProvider.getByIds(new GoodsCateByIdsRequest(scopeIds)).getContext().getGoodsCateVOList();
            goodsCateVOList = goodsCateVOList.stream().filter(cate -> cate.getDelFlag() == DeleteFlag.NO).collect(Collectors.toList());
            List<GoodsCateVO> newCateList = goodsCateVOList;
            //只显示父级的节点的名称
            List<GoodsCateVO> nameGoodsCateList = goodsCateVOList.stream().filter(item -> newCateList.stream().noneMatch(i -> i.getCateId().equals(item.getCateParentId()))).collect(Collectors.toList());
            List<String> cateNames = nameGoodsCateList.stream().map(GoodsCateVO::getCateName).collect(Collectors.toList());
            marketingResponse.setScopeNames(cateNames);
        }
    }

    /**
     * 处理门店分类
     * @param scopeIds
     * @param marketingResponse
     */
    public void dealO2oCateScope(List<Long> scopeIds, MarketingResponse marketingResponse) {
        //环绕处理
    }

    /**
     * @param marketingId 活动id
     * @return
     * @description 关闭活动
     * @author xuyunpeng
     * @date 2021/6/24 11:32 上午
     */
    @Transactional
    public void closeActivity(Long marketingId, Long storeId,Platform platform) {
        Marketing marketing = marketingRepository.findById(marketingId).orElseThrow(() -> new SbcRuntimeException(MarketingErrorCodeEnum.K080001));
        if (Objects.equals(Platform.PLATFORM,platform)
                && Objects.equals(BoolFlag.NO, marketing.getIsBoss())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }else if (Objects.equals(Platform.SUPPLIER,platform)
                && !Objects.equals(storeId, marketing.getStoreId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        if (marketing.getIsPause() == BoolFlag.YES
                || marketing.getBeginTime().isAfter(LocalDateTime.now())
                || marketing.getEndTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080022);
        }

        marketingRepository.closeActivity(marketingId);
    }

    /**
     * 修改暂停中活动
     *
     * @param request
     */
    @Transactional(rollbackFor = {Exception.class})
    public void pauseModify(PauseModifyRequest request) {
        Marketing marketing = queryById(request.getMarketingId());

        //判断当前活动是否已经结束
        if (LocalDateTime.now().isAfter(marketing.getEndTime())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080018);
        }

        if (BoolFlag.NO.equals(marketing.getIsPause()) || marketing.getEndTime().isAfter(request.getEndTime())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (StringUtils.isNotBlank(request.getJoinLevel())){
            List<String> oldJoinLevelList = Arrays.stream(marketing.getJoinLevel().split(",")).collect(Collectors.toList());
            List<String> newJoinLevelList = Arrays.stream(request.getJoinLevel().split(",")).collect(Collectors.toList());

            int oldLevel = Integer.parseInt(oldJoinLevelList.get(0));
            int newLevel = Integer.parseInt(newJoinLevelList.get(0));

            //当初始目标客户为全平台客户时,不做修改
            if (oldLevel != MarketingJoinLevel.ALL_CUSTOMER.toValue()) {
                if (newLevel == MarketingJoinLevel.ALL_CUSTOMER.toValue()) {
                    //当修改后目标客户为全平台客户时,直接修改
                    marketing.setJoinLevel(newJoinLevelList.get(0));
                } else if (oldLevel != MarketingJoinLevel.ALL_LEVEL.toValue()) {
                    //当初始目标客户不为所有等级时
                    if (newLevel != MarketingJoinLevel.ALL_LEVEL.toValue() && oldJoinLevelList.size() > newJoinLevelList.size()) {
                        //当修改后目标客户比初始范围缩小时,报错
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    if (newLevel == MarketingJoinLevel.ALL_LEVEL.toValue()) {
                        //当修改后目标客户为所有等级时,直接修改
                        marketing.setJoinLevel(newJoinLevelList.get(0));
                    } else {
                        marketing.setJoinLevel(StringUtils.join(CollectionUtils.union(oldJoinLevelList, newJoinLevelList), ","));
                    }
                }
            }
        }

        marketing.setEndTime(request.getEndTime());
        marketing.setUpdatePerson(request.getUpdatePerson());
        marketing.setUpdateTime(LocalDateTime.now());

        marketingRepository.save(marketing);
    }

    /**
     * 扩展点，获得扩展表SQL
     * @param request
     * @return
     */
    protected String getExtendsTableSql(MarketingQueryListRequest request){
        return "";
    }


    /***
     * 获得查询促销活动分页列表的where语句
     * @param request   请求参数
     * @param storeId   店铺ID
     * @return
     */
    protected String getMarketingWhereSql(MarketingQueryListRequest request, Long storeId) {
        // 条件查询
        StringBuilder whereSql = new StringBuilder("WHERE 1 = 1");
        if (storeId != null && !PluginType.O2O.equals(request.getPluginType())) {
            whereSql.append(" AND t.store_id = :storeId");
        }

        if (request.getDelFlag() != null) {
            whereSql.append(" AND t.del_flag = :delFlag");
        }

        if (StringUtils.isNotBlank(request.getMarketingName())) {
            whereSql.append(" AND t.marketing_name LIKE concat('%',:marketingName,'%') ");
        }
        if (request.getMarketingType() != null) {
            whereSql.append(" AND t.marketing_type = :marketingType");
        }
        if (CollectionUtils.isNotEmpty(request.getMarketingTypeList())) {
            whereSql.append(" AND t.marketing_type in (:marketingTypeList)");
        }

        if (request.getMarketingSubType() != null) {
            whereSql.append(" AND t.sub_type = :marketingSubType");
        }
        if (request.getStartTime() != null) {
            whereSql.append(" AND :startTime <= t.begin_time ");
        }
        if (request.getEndTime() != null) {
            whereSql.append(" AND :endTime >= t.end_time ");
        }
        if (request.getTargetLevelId() != null) {
            whereSql.append(" AND find_in_set( :targetLevelId , t.join_level)");
        }
        if (request.getBossJoinLevel() != null){
            if (request.getBossJoinLevel() == -1){
                whereSql.append(" AND  t.join_level = -1");
            }else if (request.getBossJoinLevel() == 0){
                whereSql.append(" AND  t.join_level != -1");
            }else {
                whereSql.append(" AND find_in_set( :bossJoinLevel , t.join_level)");
            }
        }

        if (CollectionUtils.isNotEmpty(request.getStoreIds())) {
            whereSql.append(" AND t.store_id in (:storeIds)");
        }

        if (request.getStoreType() != null){
            whereSql.append(" AND t.store_type = :storeType");
        }

        if (BoolFlag.YES.equals(request.getIsBoss())){
            whereSql.append(" AND t.is_boss = :isBoss");
        }else {
            whereSql.append(" AND t.is_boss = 0");
        }

        switch (request.getQueryTab()) {
            case STARTED://进行中
                whereSql.append(" AND now() >= t.begin_time AND now() <= t.end_time AND t.is_pause = 0 AND audit_status = 1");
                break;
            case PAUSED://暂停中
                whereSql.append(" AND now() >= t.begin_time AND now() <= t.end_time AND t.is_pause = 1 AND audit_status = 1");
                break;
            case NOT_START://未开始
                whereSql.append(" AND now() < t.begin_time AND audit_status = 1");
                break;
            case ENDED://已结束
                whereSql.append(" AND now() > t.end_time AND audit_status = 1");
                break;
            case S_NS: // 进行中&未开始
                whereSql.append(" AND now() <= t.end_time AND t.is_pause = 0 AND audit_status = 1");
                break;
            case REQUEST_WAIT_CHECK://待审核
                whereSql.append(" AND audit_status = 0");
                break;
            case REQUEST_NOT_PASS://审核未通过
                whereSql.append(" AND audit_status = 2");
                break;
            default:
                break;
        }
        whereSql.append(" AND t.plugin_type = :pluginType ");
        return whereSql.toString();
    }

    /***
     * 获得排序语句
     */
    protected String getMarketingOrderSql() {
        return " order by t.create_time desc";
    }

    /**
     * 组装查询参数
     *
     * @param query
     * @param request
     */
    protected void wrapperQueryParam(Query query, MarketingQueryListRequest request, Long storeId) {
        if (StringUtils.isNotBlank(request.getMarketingName())) {
            query.setParameter("marketingName", request.getMarketingName());
        }
        if (storeId != null) {
            query.setParameter("storeId", storeId);
        }
        if (request.getDelFlag() != null) {
            query.setParameter("delFlag", request.getDelFlag().toValue());
        }
        if (request.getMarketingType() != null) {
            query.setParameter("marketingType", request.getMarketingType().toValue());
        }
        if (CollectionUtils.isNotEmpty(request.getMarketingTypeList())) {
            List<String> value = request.getMarketingTypeList().stream().map(s -> String.valueOf(s.toValue())).collect(Collectors.toList());
            query.setParameter("marketingTypeList", value);
        }

        if (request.getMarketingSubType() != null) {
            query.setParameter("marketingSubType", request.getMarketingSubType().toValue());
        }
        if (request.getStartTime() != null) {
            query.setParameter("startTime", DateUtil.format(request.getStartTime(), DateUtil.FMT_TIME_1));
        }
        if (request.getEndTime() != null) {
            query.setParameter("endTime", DateUtil.format(request.getEndTime(), DateUtil.FMT_TIME_1));
        }
        if (request.getTargetLevelId() != null) {
            query.setParameter("targetLevelId", request.getTargetLevelId());
        }

        if (CollectionUtils.isNotEmpty(request.getStoreIds())) {
            query.setParameter("storeIds", request.getStoreIds());
        }
        if (request.getBossJoinLevel() != null
            && request.getBossJoinLevel() != -1
            && request.getBossJoinLevel() != 0){
            query.setParameter("bossJoinLevel", request.getBossJoinLevel());
        }
        if (request.getIsBoss() != null){
            query.setParameter("isBoss", request.getIsBoss().toValue());
        }
        if (request.getStoreType() != null){
            query.setParameter("storeType", request.getStoreType());
        }

        query.setParameter("pluginType", Nutils.defaultVal(request.getPluginType(), PluginType.NORMAL).toValue());
    }

    /***
     * 保存之前填充营销属性切面
     * @param marketing
     */
    protected void populateMarketingBeforeSave(Marketing marketing, BoolFlag isBoss) {
        // SBC营销不涉及审核，因此全部设为审核通过
        marketing.setPluginType(PluginType.NORMAL);
        marketing.setAuditStatus(AuditStatus.CHECKED);
    }

    protected List<String> getBrandsName(ContractBrandListRequest contractBrandListRequest, List<Long> scopeIds, BoolFlag isBoss){
        List<String> brandNames;
        if (!Objects.equals(BoolFlag.YES,isBoss)){
            List<ContractBrandVO> brandList = contractBrandQueryProvider.list(contractBrandListRequest).getContext().getContractBrandVOList();
            //筛选出店铺签约的品牌
            brandNames = brandList.stream().filter(item ->
                    scopeIds.stream().anyMatch(i ->
                            i.equals(item.getGoodsBrand().getBrandId())
                    )
            ).map(item -> item.getGoodsBrand().getBrandName()).collect(Collectors.toList());
        }else {
            List<GoodsBrandVO> goodsBrandVoList = goodsBrandQueryProvider
                    .list(GoodsBrandListRequest.builder().brandIds(contractBrandListRequest.getGoodsBrandIds()).build())
                    .getContext()
                    .getGoodsBrandVOList();
            brandNames =
                    goodsBrandVoList.stream().sorted(Comparator.comparing(GoodsBrandVO::getBrandId)).map(GoodsBrandVO::getBrandName).collect(Collectors.toList());
        }
        return brandNames;
    }

    /***
     * 校验营销活动是否包含指定门店
     * 如果不包含则抛出MarketingErrorCode.NOT_EXIST错误
     * @param marketing 营销活动
     * @param storeId   门店ID
     */
    protected void validateMarketingContainStoreId(Marketing marketing, Long storeId){
        //环绕处理
    }

    // 关联营销活动级别
    public void joinMarketingLevels(MarketingPageVO  marketingPageVO) {
        List<String> rulesList = marketingPageVO.getRulesList();
        if(CollectionUtils.isNotEmpty(rulesList)){
            String rule = String.join(",", rulesList);
            marketingPageVO.setRuleText(rule);
        }
        switch (marketingPageVO.getMarketingType()) {
            case REDUCTION:
                List<MarketingFullReductionLevel> fullReductionLevelList = marketingFullReductionLevelRepository.findByMarketingIdOrderByFullAmountAscFullCountAsc(marketingPageVO.getMarketingId());
                if(CollectionUtils.isNotEmpty(fullReductionLevelList)){
                    String text = fullReductionLevelList.stream().map(fullReductionLevel -> {
                        Long fullCount = fullReductionLevel.getFullCount();
                        if (Objects.nonNull(fullCount)) {
                            return String.format("满%s件，减%s元", fullCount,
                                    fullReductionLevel.getReduction().floatValue());
                        }
                        return String.format("满%s元，减%s元",
                                fullReductionLevel.getFullAmount(),
                                fullReductionLevel.getReduction().floatValue());
                    }).collect(Collectors.joining(","));
                    marketingPageVO.setRuleText(text);
                }
                break;
            case DISCOUNT:
                List<MarketingFullDiscountLevel> marketingFullDiscountLevelList = marketingFullDiscountLevelRepository.findByMarketingIdOrderByFullAmountAscFullCountAsc(marketingPageVO.getMarketingId());
                if(CollectionUtils.isNotEmpty(marketingFullDiscountLevelList)){
                    String text = marketingFullDiscountLevelList.stream().map(marketingFullDiscountLevel -> {
                        Long fullCount = marketingFullDiscountLevel.getFullCount();
                        if (Objects.nonNull(fullCount)) {
                            return String.format("满%s件，打%s折", fullCount,
                                    marketingFullDiscountLevel.getDiscount().multiply(BigDecimal.valueOf(10)).setScale(1, RoundingMode.HALF_UP).floatValue());
                        }
                        return String.format("满%s元，打%s折",
                                marketingFullDiscountLevel.getFullAmount(),
                                marketingFullDiscountLevel.getDiscount().multiply(BigDecimal.valueOf(10)).setScale(1, RoundingMode.HALF_UP).floatValue());
                    }).collect(Collectors.joining(","));
                    marketingPageVO.setRuleText(text);
                }
                break;
            case GIFT:
                List<MarketingFullGiftLevelVO> marketingFullGiftLevelList = marketingFullGiftService.getLevelsByMarketingId(marketingPageVO.getMarketingId());
                if(CollectionUtils.isNotEmpty(marketingFullGiftLevelList)){
                    String text = marketingFullGiftLevelList.stream().map(marketingFullGiftLevel -> {
                        Long fullCount = marketingFullGiftLevel.getFullCount();
                        if (Objects.nonNull(fullCount)) {
                            return String.format("满%s件，可参与满赠活动", fullCount);
                        }
                        return String.format("满%s元，可参与满赠活动",
                                marketingFullGiftLevel.getFullAmount());
                    }).collect(Collectors.joining(","));
                    marketingPageVO.setRuleText(text);
                }
                break;
            case RETURN:
                List<MarketingFullReturnLevel> marketingFullReturnLevelList = marketingFullReturnService.getLevelsByMarketingId(marketingPageVO.getMarketingId());
                if(CollectionUtils.isNotEmpty(marketingFullReturnLevelList)){
                    String text =
                            marketingFullReturnLevelList.stream().map(marketingFullReturnLevel -> String.format("满%s元，可参与满返活动",
                            marketingFullReturnLevel.getFullAmount())).collect(Collectors.joining(","));
                    marketingPageVO.setRuleText(text);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 自定义字段的列表查询
     * @param request 参数
     * @param cols 列名
     * @return 列表
     */
    public List<Marketing> listCols(MarketingQueryRequest request, List<String> cols) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Marketing> rt = cq.from(Marketing.class);
        cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
        Specification<Marketing> spec = request.getWhereCriteria();
        Predicate predicate = spec.toPredicate(rt, cq, cb);
        if (predicate != null) {
            cq.where(predicate);
        }
        Sort sort = request.getSort();
        if (sort.isSorted()) {
            cq.orderBy(QueryUtils.toOrders(sort, rt, cb));
        }
        return this.converter(entityManager.createQuery(cq).getResultList(), cols);
    }

    /**
     * 查询对象转换
     * @param result
     * @return
     */
    private List<Marketing> converter(List<Tuple> result, List<String> cols) {
        return result.stream().map(item -> {
            Marketing marketing = new Marketing();
            marketing.setMarketingId(JpaUtil.toLong(item,"marketingId", cols));
            marketing.setScopeType(JpaUtil.toClass(item,"scopeType", cols, MarketingScopeType.class));
            marketing.setMarketingType(JpaUtil.toClass(item,"marketingType", cols, MarketingType.class));
            return marketing;
        }).collect(Collectors.toList());
    }

    /**
     * 是否全局互斥
     * @return true:是 false:否
     */
    private Boolean mutexFlag() {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.MARKETING_MUTEX.toValue());
        ConfigVO configVO = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext().getConfig();
        //营销互斥不验证标识
        return Objects.nonNull(configVO) && NumberUtils.INTEGER_ONE.equals(configVO.getStatus());
    }
}
