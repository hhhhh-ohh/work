package com.wanmi.sbc.marketing.fullreturn.service;

import static java.time.Duration.between;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponInfoQueryRequest;
import com.wanmi.sbc.marketing.api.request.market.PauseModifyRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingStoreType;
import com.wanmi.sbc.marketing.bean.enums.RangeDayType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.repository.MarketingRepository;
import com.wanmi.sbc.marketing.common.repository.MarketingStoreRepository;
import com.wanmi.sbc.marketing.common.service.MarketingService;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.repository.CouponInfoRepository;
import com.wanmi.sbc.marketing.coupon.response.CouponInfoResponse;
import com.wanmi.sbc.marketing.coupon.service.CouponInfoService;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnDetail;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnLevel;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnStore;
import com.wanmi.sbc.marketing.fullreturn.repository.MarketingFullReturnDetailRepository;
import com.wanmi.sbc.marketing.fullreturn.repository.MarketingFullReturnLevelRepository;
import com.wanmi.sbc.marketing.fullreturn.request.MarketingFullReturnSaveRequest;
import com.wanmi.sbc.marketing.fullreturn.response.MarketingFullReturnLevelResponse;
import com.wanmi.sbc.marketing.plugin.impl.CustomerLevelPlugin;

import jakarta.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 营销满返业务
 */
@Service
public class MarketingFullReturnService {

    @Autowired
    private MarketingFullReturnLevelRepository marketingFullReturnLevelRepository;

    @Autowired
    private MarketingFullReturnDetailRepository marketingFullReturnDetailRepository;

    @Lazy
    @Autowired
    private MarketingService marketingService;

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    protected CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    protected CustomerLevelPlugin customerLevelPlugin;

    @Autowired
    protected GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired
    private MarketingStoreRepository marketingStoreRepository;

    @Autowired
    private CouponInfoRepository couponInfoRepository;

    @Autowired
    private MarketingRepository marketingRepository;

    @Autowired
    protected RedisUtil redisService;

    public MarketingService getMarketingService() {
        return marketingService;
    }

    /**
     * 新增满返
     */
    @Transactional(rollbackFor = Exception.class)
    public Marketing addMarketingFullReturn(MarketingFullReturnSaveRequest request) throws SbcRuntimeException {

        //boss端校验店铺
        if (Objects.equals(BoolFlag.YES,request.getIsBoss())){
            checkStore(null,request.getStoreType(),request.getStoreIds(),request.getBeginTime(),request.getEndTime());
        }

        //校验优惠券是否有效
        Set<String> couponIds = new HashSet<>();
        for (MarketingFullReturnLevel marketingFullReturnLevel : request.getFullReturnLevelList()) {
            for (MarketingFullReturnDetail detail : marketingFullReturnLevel.getFullReturnDetailList()) {
                couponIds.add(detail.getCouponId());
            }
        }
        checkCoupon(couponIds,request.getEndTime());

        Marketing marketing = getMarketingService().addMarketing(request);

        // 保存多级优惠信息
        this.saveLevelList(request.generateFullReturnLevelList(marketing.getMarketingId()));

        this.saveLevelReturnDetailList(request.generateFullReturnDetailList(request.getFullReturnLevelList()));

        return marketing;
    }

    /**
     * 修改满返
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyMarketingFullReturn(MarketingFullReturnSaveRequest request) throws SbcRuntimeException {
        Marketing marketing = marketingRepository.findById(request.getMarketingId())
                .orElseThrow(() -> new SbcRuntimeException(MarketingErrorCodeEnum.K080001));

        if (Objects.equals(BoolFlag.YES,marketing.getIsBoss())){
            checkStore(request.getMarketingId(),request.getStoreType(),request.getStoreIds(),request.getBeginTime(),request.getEndTime());
        }

        if (Objects.equals(BoolFlag.YES,marketing.getIsPause())){
            Map<Long, Long> increaseMap = checkReturnPause(request, marketing);
            marketingService.pauseModify(PauseModifyRequest.builder()
                    .marketingId(marketing.getMarketingId())
                    .endTime(request.getEndTime())
                    .joinLevel(request.getJoinLevel())
                    .updatePerson(request.getUpdatePerson())
                    .build());

            ArrayList<MarketingFullReturnDetail> newDetailList = request.getFullReturnLevelList()
                    .stream()
                    .map(MarketingFullReturnLevel::getFullReturnDetailList)
                    .collect(ArrayList<MarketingFullReturnDetail>::new, ArrayList::addAll, ArrayList::addAll);

            marketingFullReturnDetailRepository.saveAll(newDetailList);

            for (Long id : increaseMap.keySet()) {
                String fullReturnCouponNumKey =
                        RedisKeyConstant.FULL_RETURN_COUPON_NUM_KEY.concat(String.valueOf(id));
                if (redisService.hasKey(fullReturnCouponNumKey)){
                    redisService.incrByKey(fullReturnCouponNumKey,increaseMap.get(id));
                    Duration duration = between(LocalDateTime.now(), request.getEndTime());
                    long existSeconds = duration.getSeconds();
                    if (existSeconds<=0){
                        existSeconds = Constants.FIVE;
                    }
                    redisService.expireBySeconds(fullReturnCouponNumKey,existSeconds);
                }
            }

        }else {
            //校验优惠券是否有效
            Set<String> couponIds = new HashSet<>();
            for (MarketingFullReturnLevel marketingFullReturnLevel : request.getFullReturnLevelList()) {
                for (MarketingFullReturnDetail detail : marketingFullReturnLevel.getFullReturnDetailList()) {
                    couponIds.add(detail.getCouponId());
                }
            }
            checkCoupon(couponIds, request.getEndTime());

            marketingService.modifyMarketing(request);
            // 先删除已有的多级优惠信息，然后再保存
            marketingFullReturnLevelRepository.deleteByMarketingId(request.getMarketingId());
            this.saveLevelList(request.generateFullReturnLevelList(request.getMarketingId()));

            // 先删除已有的赠券信息，然后再保存
            marketingFullReturnDetailRepository.deleteByMarketingId(request.getMarketingId());
            this.saveLevelReturnDetailList(request.generateFullReturnDetailList(request.getFullReturnLevelList()));
        }

        // 先删除已有的店铺信息，然后再保存
        marketingStoreRepository.deleteByMarketingId(request.getMarketingId());
        if (MarketingStoreType.STORE_TYPE_APPOINT == request.getStoreType()) {
            getMarketingService().saveStoreList(request.generateMarketingStoreList(request.getMarketingId()));
        }
    }

    /**
     * boss端创建满返校验店铺
     * @param marketingId
     * @param storeType
     * @param storeIds
     * @param beginTime
     * @param endTime
     */
    private void checkStore(Long marketingId, MarketingStoreType storeType
            , List<Long> storeIds,LocalDateTime beginTime,LocalDateTime endTime) {
        Integer num = Constants.ZERO;
        if (Objects.equals(MarketingStoreType.STORE_TYPE_ALL,storeType)){
            if (Objects.isNull(marketingId)){
                num = marketingRepository.checkMarketingFullStore(beginTime, endTime);
            }else {
                num = marketingRepository.checkMarketingFullStore(marketingId, beginTime, endTime);
            }
        }else {
            if (Objects.isNull(marketingId)){
                num = marketingRepository.checkMarketingFullStore(beginTime, endTime,storeIds);
            }else {
                num = marketingRepository.checkMarketingFullStore(marketingId,beginTime,endTime,storeIds);
            }
        }
        if (num > 0){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080022);
        }
    }

    /**
     * 校验优惠券
     * @param couponIds
     * @param endTime
     */
    private void checkCoupon(Set<String> couponIds, @NotNull LocalDateTime endTime) {
        List<String> errorList = new ArrayList<>();
        List<CouponInfo> couponInfos = couponInfoRepository.findAllById(couponIds)
                .stream()
                .filter(v->Objects.equals(RangeDayType.RANGE_DAY,v.getRangeDayType()))
                .collect(Collectors.toList());
        for (CouponInfo couponInfo : couponInfos) {
            if (Objects.equals(DeleteFlag.YES,couponInfo.getDelFlag())
                  || couponInfo.getEndTime().isBefore(endTime)){
                errorList.add(couponInfo.getCouponName());
            }
        }
        if (CollectionUtils.isNotEmpty(errorList)){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080048, new Object[]{errorList});
        }
    }

    /**
     * 校验活动暂停参数
     * @param request
     */
    private Map<Long,Long> checkReturnPause(MarketingFullReturnSaveRequest request,Marketing marketing) {
        Map<Long,Long> increaseMap = new HashMap<>();
        //boss端校验关联店铺数量
        if (Objects.equals(BoolFlag.YES,marketing.getIsBoss())
            && !Objects.equals(MarketingStoreType.STORE_TYPE_ALL,request.getStoreType())){
            if (Objects.equals(MarketingStoreType.STORE_TYPE_ALL,marketing.getStoreType())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            List<Long> oldStoreIds = marketingStoreRepository
                    .findByMarketingId(marketing.getMarketingId())
                    .stream()
                    .map(MarketingFullReturnStore::getStoreId)
                    .collect(Collectors.toList());

            if (!request.getStoreIds().containsAll(oldStoreIds)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        //校验优惠券数量
        List<MarketingFullReturnDetail> oldDetailList = marketingFullReturnDetailRepository
                .findByMarketingId(marketing.getMarketingId());

        ArrayList<MarketingFullReturnDetail> newDetailList = request.getFullReturnLevelList()
                .stream()
                .map(MarketingFullReturnLevel::getFullReturnDetailList)
                .collect(ArrayList<MarketingFullReturnDetail>::new, ArrayList::addAll, ArrayList::addAll);

        oldDetailList.forEach(v->{
            Optional<MarketingFullReturnDetail> opt = newDetailList.stream()
                    .filter(n -> Objects.equals(v.getReturnDetailId(), n.getReturnDetailId()))
                    .findFirst();
            if (opt.isPresent()){
                if (opt.get().getCouponNum()<v.getCouponNum()){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }else {
                    increaseMap.put(v.getReturnDetailId(),opt.get().getCouponNum()-v.getCouponNum());
                }
            }else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        });
        return increaseMap;
    }

    /**
     * 保存多级优惠信息
     */
    private void saveLevelList(List<MarketingFullReturnLevel> fullReturnLevelList) {
        if(CollectionUtils.isNotEmpty(fullReturnLevelList)) {
            marketingFullReturnLevelRepository.saveAll(fullReturnLevelList);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 保存多级优惠赠券信息
     */
    private void saveLevelReturnDetailList(List<MarketingFullReturnDetail> returnDetailList) {
        if(CollectionUtils.isNotEmpty(returnDetailList)) {
            List<MarketingFullReturnDetail> result = marketingFullReturnDetailRepository.saveAll(returnDetailList);

        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 获取营销等级信息，包含等级下的detail
     *
     * @param marketingIds
     * @return
     */
    public List<MarketingFullReturnLevel> getLevelsByMarketingIds(List<Long> marketingIds){
        List<MarketingFullReturnLevel> levelList = marketingFullReturnLevelRepository.findAll((root, cq, cb) -> root.get("marketingId").in(marketingIds));
        if(CollectionUtils.isNotEmpty(levelList)){
            List<MarketingFullReturnDetail> detailList = marketingFullReturnDetailRepository.findAll((root, cq, cb) -> root.get("marketingId").in(marketingIds));
            if(CollectionUtils.isNotEmpty(detailList)){
                Map<Long, List<MarketingFullReturnDetail>> map = detailList.stream().collect(Collectors.groupingBy(MarketingFullReturnDetail::getReturnLevelId));
                levelList.forEach(level -> level.setFullReturnDetailList(map.get(level.getReturnLevelId())));
            }
        }
        return levelList;
    }

    /**
     * 根据活动获取赠券列表
     * @param marketingId
     * @param customer
     * @return
     */
    public MarketingFullReturnLevelResponse getReturnList(Long marketingId){
        MarketingFullReturnLevelResponse response = new MarketingFullReturnLevelResponse();
        List<MarketingFullReturnLevel> levelList = getLevelsByMarketingId(marketingId);
        if (CollectionUtils.isEmpty(levelList)) {
            return response;
        }
        List<String> couponIds = levelList.stream()
                .map(MarketingFullReturnLevel::getFullReturnDetailList)
                .flatMap(Collection::stream)
                .map(MarketingFullReturnDetail::getCouponId)
                .collect(Collectors.toList());
        //获取优惠券信息
        List<CouponInfo> returnList = couponInfoService.queryCouponInfos(CouponInfoQueryRequest.builder().couponIds(couponIds).build());

        response.setLevelList(levelList);
        if (CollectionUtils.isNotEmpty(returnList)){
            response.setReturnList(KsBeanUtil.copyListProperties(returnList, CouponInfoVO.class));
            response.getReturnList().forEach(t->{
                CouponInfoResponse couponInfoResponse = couponInfoService.queryCouponInfoDetail(t.getCouponId(),t.getStoreId());
                t.setScopeNames(couponInfoResponse.getCouponInfo().getScopeNames());
            });
        }
        return response;
    }

    /**
     * 根据活动和阶梯信息获取对应赠券列表
     * @param marketingId
     * @param returnLevelId
     * @return
     */
    public List<MarketingFullReturnDetail> getReturnList(Long marketingId,Long returnLevelId){
        return marketingFullReturnDetailRepository.findByMarketingIdAndReturnLevelId(marketingId,returnLevelId);
    }

    /**
     * 根据活动和阶梯信息批量获取对应赠券列表
	 *
     * @param marketingIds 批量营销id
     * @param returnLevelIds 批量赠券等级id
     * @return
     */
    public List<MarketingFullReturnDetail> getReturnList(List<Long> marketingIds,List<Long> returnLevelIds){
        return marketingFullReturnDetailRepository.findByMarketingIdInAndReturnLevelIdIn(marketingIds,returnLevelIds);
    }

    /**
     * 获取满返详情
     * @param marketingId
     * @return
     */
    public List<MarketingFullReturnLevel> getLevelsByMarketingId(Long marketingId) {
        List<MarketingFullReturnLevel> levelList = marketingFullReturnLevelRepository
                .findByMarketingIdOrderByFullAmountAsc(marketingId);
        if(CollectionUtils.isNotEmpty(levelList)){
            List<MarketingFullReturnDetail> detailList = marketingFullReturnDetailRepository.findByMarketingId(marketingId);
            if(CollectionUtils.isNotEmpty(detailList)){
                List<String> couponIds = detailList.stream().map(MarketingFullReturnDetail::getCouponId).collect(Collectors.toList());
                List<CouponInfo> couponInfoList = couponInfoRepository.findAllById(couponIds);
                Map<String, CouponInfo> coupInfoMap = couponInfoList.stream().collect(Collectors.toMap(CouponInfo::getCouponId, Function.identity()));
                detailList.forEach(v->{
                    v.setCouponInfo(coupInfoMap.get(v.getCouponId()));
                });
                Map<Long, List<MarketingFullReturnDetail>> map = detailList.stream().collect(Collectors.groupingBy(MarketingFullReturnDetail::getReturnLevelId));
                levelList.forEach(level -> level.setFullReturnDetailList(map.get(level.getReturnLevelId())));
            }
        }
        return levelList;
    }

    public List<MarketingFullReturnStore> findStoreInfoByMarketingId(Long marketingId) {
        List<MarketingFullReturnStore> storeList = marketingStoreRepository.findByMarketingId(marketingId);
        return storeList;
    }
}
