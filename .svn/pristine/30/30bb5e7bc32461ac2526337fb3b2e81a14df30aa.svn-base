package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardBalanceQueryResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardListResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardTradeResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardUseResponse;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.marketing.giftcard.model.root.*;
import com.wanmi.sbc.marketing.giftcard.repository.*;
import com.wanmi.sbc.order.api.provider.small.SmallProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lvzhenwei
 * @className UserGiftCardService
 * @description 会员礼品卡信息
 * @date 2022/12/10 9:37 上午
 */
@Service
@Slf4j
@RefreshScope
public class UserGiftCardService {

    @Autowired private GiftCardDetailRepository giftCardDetailRepository;

    @Autowired private UserGiftCardRepository userGiftCardRepository;

    @Autowired private GiftCardRepository giftCardRepository;

    @Autowired private GiftCardBillRepository giftCardBillRepository;

    @Autowired private GiftCardScopeRepository giftCardScopeRepository;

    @Autowired private EntityManager entityManager;

    @Autowired SmallProvider smallProvider;

    @Value("${oldSendNew.afterDays:90}")
    private Integer afterDays;


    public UserGiftCardInfoVO checkExchangeGiftCard(ExchangeGiftCardRequest request) {
        UserGiftCardInfoVO userGiftCardInfoVO = new UserGiftCardInfoVO();
        Optional<GiftCardDetail> giftCardDetailOptional =
                giftCardDetailRepository.findByGiftCardNoAndDelFlag(
                        request.getGiftCardNo(), DeleteFlag.NO);
        if (!giftCardDetailOptional.isPresent()) {
            // 抛出异常
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080025);
        }
        GiftCardDetail giftCardDetail = giftCardDetailOptional.get();
        GiftCardDetailStatus cardDetailStatus = giftCardDetail.getCardDetailStatus();
        GiftCardSourceType cardSourceType = giftCardDetail.getSourceType();
        // 查询卡详情
        GiftCard giftCard = giftCardRepository.getById(giftCardDetail.getGiftCardId());
        List<GiftCardScope> giftCardScopeList = giftCardScopeRepository.queryListByGiftCardId(giftCard.getGiftCardId());
        if (Objects.nonNull(giftCard.getScopeGoodsNum()) && giftCard.getScopeGoodsNum() == -99){
            giftCardScopeList = giftCardScopeRepository.queryListByGiftCardId(giftCard.getGiftCardId());
        }
        // 校验兑换码是否正确，校验状态是否为带兑换，校验过期时间为null 或者 当前时间是否小于过期时间 发卡类型的礼品卡没有兑换码
        if (GiftCardSourceType.SEND != cardSourceType
                && giftCardDetail.getExchangeCode().equals(request.getExchangeCode())
                && GiftCardDetailStatus.NOT_EXCHANGE == cardDetailStatus
                && (Objects.isNull(giftCard.getExpirationTime())
                        || LocalDateTime.now().isBefore(giftCard.getExpirationTime()))) {
            userGiftCardInfoVO.setCustomerId(request.getCustomerId());
            userGiftCardInfoVO.setName(giftCard.getName());
            userGiftCardInfoVO.setParValue(giftCard.getParValue());
            userGiftCardInfoVO.setGiftCardNo(giftCardDetail.getGiftCardNo());
            userGiftCardInfoVO.setExpirationType(giftCard.getExpirationType());
            userGiftCardInfoVO.setRangeMonth(giftCard.getRangeMonth());
            userGiftCardInfoVO.setExpirationTime(giftCard.getExpirationTime());
            userGiftCardInfoVO.setUseDesc(giftCard.getUseDesc());
            userGiftCardInfoVO.setScopeType(giftCard.getScopeType());
            userGiftCardInfoVO.setContactType(giftCard.getContactType());
            userGiftCardInfoVO.setContactPhone(giftCard.getContactPhone());
            userGiftCardInfoVO.setBackgroundDetail(giftCard.getBackgroundDetail());
            userGiftCardInfoVO.setBackgroundType(giftCard.getBackgroundType());
            userGiftCardInfoVO.setGiftCardType(giftCard.getGiftCardType());
            userGiftCardInfoVO.setScopeGoodsNum(giftCard.getScopeGoodsNum());
            userGiftCardInfoVO = this.wrapperStatus(userGiftCardInfoVO);
            if (Objects.nonNull(giftCard.getScopeGoodsNum()) && giftCard.getScopeGoodsNum() == -1){
                userGiftCardInfoVO.setTotalGoodsNum(1);
            } else if (Objects.nonNull(giftCard.getScopeGoodsNum()) && giftCard.getScopeGoodsNum() > 0) {
                userGiftCardInfoVO.setTotalGoodsNum(giftCard.getScopeGoodsNum());
            } else if (Objects.nonNull(giftCard.getScopeGoodsNum()) && giftCard.getScopeGoodsNum() == -99) {
                userGiftCardInfoVO.setTotalGoodsNum(giftCardScopeList.size());
            }
        } else {
            // 抛出异常：卡号卡密无效
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080025);
        }
        return userGiftCardInfoVO;
    }
    /**
     * @description 礼品卡兑换
     * @author lvzhenwei
     * @date 2022/12/10 9:42 上午
     * @return void
     */
    @Transactional(rollbackFor = {Exception.class})
    public UserGiftCardInfoVO exchangeGiftCard(ExchangeGiftCardRequest request) {
        UserGiftCardInfoVO userGiftCardInfoVO = new UserGiftCardInfoVO();
        Optional<GiftCardDetail> giftCardDetailOptional =
                giftCardDetailRepository.findByGiftCardNoAndDelFlag(
                        request.getGiftCardNo(), DeleteFlag.NO);
        if (!giftCardDetailOptional.isPresent()) {
            // 抛出异常
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080025);
        }
        GiftCardDetail giftCardDetail = giftCardDetailOptional.get();
        GiftCardDetailStatus cardDetailStatus = giftCardDetail.getCardDetailStatus();
        // 校验兑换码是否正确，校验状态是否为带兑换，校验过期时间为null 或者 当前时间是否小于过期时间
        if (giftCardDetail.getExchangeCode().equals(request.getExchangeCode())
                && GiftCardDetailStatus.NOT_EXCHANGE == cardDetailStatus
                && (Objects.isNull(giftCardDetail.getExpirationTime())
                        || LocalDateTime.now()
                                .isBefore(giftCardDetail.getExpirationTime()))) {
            // 查询卡详情
            GiftCard giftCard = giftCardRepository.getById(giftCardDetail.getGiftCardId());
            GiftCardType giftCardType = giftCard.getGiftCardType();
            // 封装用户礼品卡信息
            UserGiftCard userGiftCard = new UserGiftCard();
            userGiftCard.setCustomerId(request.getCustomerId());
            userGiftCard.setGiftCardId(giftCardDetail.getGiftCardId());
            userGiftCard.setGiftCardName(giftCard.getName());
            userGiftCard.setGiftCardType(giftCard.getGiftCardType());
            userGiftCard.setBatchNo(giftCardDetail.getBatchNo());
            userGiftCard.setGiftCardNo(giftCardDetail.getGiftCardNo());
            userGiftCard.setParValue(giftCard.getParValue());
            userGiftCard.setBalance(GiftCardType.PICKUP_CARD.equals(giftCardType) ? null : BigDecimal.ZERO);
            userGiftCard.setCardStatus(GiftCardStatus.NOT_ACTIVE);
            userGiftCard.setExpirationType(giftCard.getExpirationType());
            userGiftCard.setExpirationTime(giftCard.getExpirationTime());
            userGiftCard.setSourceType(giftCardDetail.getSourceType());
            userGiftCard.setAcquireTime(LocalDateTime.now());
            userGiftCard.setBelongPerson(request.getCustomerId());
            userGiftCard.setCancelTime(null);
            userGiftCard.setCancelPerson(null);
            userGiftCard.setCancelDesc(null);
            userGiftCard.setGiftCardType(giftCardType);
            userGiftCardRepository.save(userGiftCard);
            userGiftCardInfoVO.setUserGiftCardId(userGiftCard.getUserGiftCardId());
            // 更新礼品卡详情礼品卡状态
            giftCardDetail.setCardDetailStatus(GiftCardDetailStatus.NOT_ACTIVE);
            giftCardDetail.setAcquireTime(LocalDateTime.now());
            giftCardDetail.setBelongPerson(request.getCustomerId());
            giftCardDetail.setGiftCardType(giftCard.getGiftCardType());
            giftCardDetailRepository.save(giftCardDetail);
        } else {
            // 抛出异常：卡号卡密无效
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080025);
        }
        return userGiftCardInfoVO;
    }

    /**
     * @description 获取会员礼品卡详情
     * @author lvzhenwei
     * @date 2022/12/10 2:53 下午
     * @param request
     * @return com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO
     */
    public UserGiftCardInfoVO getUserGiftCardDetail(UserGiftCardDetailQueryRequest request) {
        UserGiftCardInfoVO userGiftCardInfoVO = new UserGiftCardInfoVO();
        Optional<UserGiftCard> userGiftCardOpt =
                userGiftCardRepository.getByUserGiftCardId(request.getUserGiftCardId());
        // 判断该卡品是否属于当前会员
        if (userGiftCardOpt.isPresent()
                && request.getCustomerId().equals(userGiftCardOpt.get().getCustomerId())) {
            List<GiftCardScope> giftCardScopeList =
                    giftCardScopeRepository.queryListByGiftCardId(userGiftCardOpt.get().getGiftCardId());
            List<String> goodsInfoIds =
                    giftCardScopeList.stream().map(GiftCardScope::getScopeId).collect(Collectors.toList());
            UserGiftCard userGiftCard = userGiftCardOpt.get();
            GiftCard giftCard = giftCardRepository.getById(userGiftCard.getGiftCardId());
            userGiftCardInfoVO.setCustomerId(userGiftCard.getCustomerId());
            userGiftCardInfoVO.setUserGiftCardId(userGiftCard.getUserGiftCardId());
            userGiftCardInfoVO.setGiftCardId(giftCard.getGiftCardId());
            userGiftCardInfoVO.setName(giftCard.getName());
            userGiftCardInfoVO.setBackgroundType(giftCard.getBackgroundType());
            userGiftCardInfoVO.setBackgroundDetail(giftCard.getBackgroundDetail());
            userGiftCardInfoVO.setParValue(userGiftCard.getParValue());
            userGiftCardInfoVO.setBalance(userGiftCard.getBalance());
            userGiftCardInfoVO.setGiftCardNo(userGiftCard.getGiftCardNo());
            userGiftCardInfoVO.setActivationTime(userGiftCard.getActivationTime());
            userGiftCardInfoVO.setExpirationType(userGiftCard.getExpirationType());
            userGiftCardInfoVO.setRangeMonth(giftCard.getRangeMonth());
            userGiftCardInfoVO.setExpirationTime(userGiftCard.getExpirationTime());
            userGiftCardInfoVO.setUseDesc(giftCard.getUseDesc());
            userGiftCardInfoVO.setScopeType(giftCard.getScopeType());
            userGiftCardInfoVO.setContactType(giftCard.getContactType());
            userGiftCardInfoVO.setContactPhone(giftCard.getContactPhone());
            userGiftCardInfoVO.setCardStatus(userGiftCard.getCardStatus());
            userGiftCardInfoVO.setAppointmentShipmentFlag(userGiftCard.getAppointmentShipmentFlag());
            if (userGiftCard.getAppointmentShipmentFlag()==1){
                LocalDateTime appointmentShipmentStartTime = userGiftCard.getAcquireTime().plusDays(afterDays);
                userGiftCardInfoVO.setAppointmentShipmentStartTime(appointmentShipmentStartTime);
            }
            userGiftCardInfoVO.setScopeGoodsNum(giftCard.getScopeGoodsNum());
            userGiftCardInfoVO.setGiftCardType(giftCard.getGiftCardType());
            if (Objects.nonNull(giftCard.getScopeGoodsNum()) && giftCard.getScopeGoodsNum() == -1){
                userGiftCardInfoVO.setTotalGoodsNum(1);
            } else if (Objects.nonNull(giftCard.getScopeGoodsNum()) && giftCard.getScopeGoodsNum() > 0){
                userGiftCardInfoVO.setTotalGoodsNum(giftCard.getScopeGoodsNum());
            } else if (Objects.nonNull(giftCard.getScopeGoodsNum()) && giftCard.getScopeGoodsNum() == -99){
                userGiftCardInfoVO.setTotalGoodsNum(goodsInfoIds.size());
            }
            userGiftCardInfoVO.setSkuIdList(goodsInfoIds);
            userGiftCardInfoVO = this.wrapperStatus(userGiftCardInfoVO);
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        return userGiftCardInfoVO;
    }

    /**
     * @description 会员礼品卡激活方法
     * @author lvzhenwei
     * @date 2022/12/10 4:58 下午
     * @param request
     * @return void
     */
    @Transactional(rollbackFor = {Exception.class})
    public void activateGiftCard(ActivateGiftCardRequest request) {
        // 获取用户卡详情
        Optional<UserGiftCard> userGiftCardOpt =
                userGiftCardRepository.getByGiftCardNoAndCustomerId(
                        request.getGiftCardNo(), request.getCustomerId());
        if (!userGiftCardOpt.isPresent()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        UserGiftCard userGiftCard = userGiftCardOpt.get();
        //判断是否是以旧送新活动的提货卡
        if (userGiftCard.getAppointmentShipmentFlag() == 1){
            checkOldOrderIsAfterSale(userGiftCard.getOrderSn());
        }
        // 判断当前礼品卡是否属于当前会员同时判断当前礼品卡状态是否为待激活未过期的状态
        if (request.getCustomerId().equals(userGiftCard.getCustomerId())
                && GiftCardStatus.NOT_ACTIVE == userGiftCard.getCardStatus()
                && (Objects.isNull(userGiftCard.getExpirationTime())
                        || (Objects.nonNull(userGiftCard.getExpirationTime())
                                && LocalDateTime.now()
                                        .isBefore(userGiftCard.getExpirationTime())))) {
            // 更新会员礼品卡状态以及余额数据
            userGiftCard.setActivationTime(LocalDateTime.now());
            userGiftCard.setCardStatus(GiftCardStatus.ACTIVATED);
            if (GiftCardType.CASH_CARD.equals(userGiftCard.getGiftCardType())){
                userGiftCard.setBalance(new BigDecimal(userGiftCard.getParValue()));
            }
            // 判断过期类型是否为激活后多少月内有效
            GiftCard giftCard = giftCardRepository.getById(userGiftCard.getGiftCardId());
            LocalDateTime expirationTime = null;
            if (ExpirationType.MONTH == giftCard.getExpirationType()) {
                expirationTime = LocalDateTime.now().plusMonths(giftCard.getRangeMonth());
                expirationTime = expirationTime.withHour(23).withMinute(59).withSecond(59).withNano(0);
                log.info("UserGiftCardService-activateGiftCard::礼品卡过期时间:{}, 请求参数:{}", expirationTime, request);
                userGiftCard.setExpirationTime(expirationTime);
            } else if (ExpirationType.SPECIFIC_TIME == giftCard.getExpirationType()) {
                expirationTime = giftCard.getExpirationTime();
            }
            userGiftCardRepository.save(userGiftCard);
            // 更新礼品卡详情数据
            giftCardDetailRepository.updateGiftCardDetailActivated(
                    GiftCardDetailStatus.ACTIVATED,
                    request.getCustomerId(),
                    expirationTime,
                    userGiftCard.getGiftCardNo());
            // 封装礼品卡使用记录
            GiftCardBill giftCardBill = new GiftCardBill();
            giftCardBill.setCustomerId(request.getCustomerId());
            giftCardBill.setUserGiftCardId(userGiftCard.getUserGiftCardId());
            giftCardBill.setGiftCardId(userGiftCard.getGiftCardId());
            giftCardBill.setGiftCardNo(userGiftCard.getGiftCardNo());
            if (GiftCardSourceType.MAKE == userGiftCard.getSourceType()) {
                giftCardBill.setBusinessType(GiftCardBusinessType.ACTIVATE_CARD_FOR_EXCHANGE_CARD);
            } else if (GiftCardSourceType.SEND == userGiftCard.getSourceType()) {
                giftCardBill.setBusinessType(GiftCardBusinessType.ACTIVATE_CARD_FOR_SEND_CARD);
            }
            if (GiftCardType.CASH_CARD.equals(userGiftCard.getGiftCardType())) {
                giftCardBill.setTradeBalance(new BigDecimal(userGiftCard.getParValue()));
                giftCardBill.setAfterBalance(new BigDecimal(userGiftCard.getParValue()));
                giftCardBill.setBeforeBalance(BigDecimal.ZERO);
            }
            giftCardBill.setTradeTime(LocalDateTime.now());
            giftCardBill.setTradePersonType(DefaultFlag.NO);
            giftCardBill.setTradePerson(request.getCustomerId());
            giftCardBillRepository.save(giftCardBill);
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080040);
        }
    }

    private void checkOldOrderIsAfterSale(String orderSn) {
        //查询对应旧订单是否过了售后时间
        try {
            BaseResponse<Boolean> booleanBaseResponse = smallProvider.checkOrderIsAfterSale(orderSn);
            log.info("调用旧平台small查询订单是否过了售后结束,参数为:{},结果为:{}", orderSn, booleanBaseResponse);
            Boolean isAfterSale = booleanBaseResponse.getContext();
            if(!isAfterSale){
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080300);
            }
        } catch (Exception e) {
            log.info("以旧送新激活提货卡查询 旧订单状态失败异常",e);
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080300);
        }
    }

    /**
     * @description 获取会员礼品卡余额
     * @author lvzhenwei
     * @date 2022/12/12 10:37 上午
     * @param request
     * @return com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardBalanceQueryResponse
     */
    public UserGiftCardBalanceQueryResponse getGiftCardBalanceByCustomerId(
            UserGiftCardBalanceQueryRequest request) {
        BigDecimal availableBalance =
                userGiftCardRepository.getGiftCardBalanceByCustomerId(
                        request.getCustomerId(), request.getGiftCardStatus());
        return UserGiftCardBalanceQueryResponse.builder()
                .customerId(request.getCustomerId())
                .giftCardBalance(availableBalance)
                .build();
    }

    /**
     * @description 查询有效礼品卡的面值
     * @author wur
     * @date: 2022/12/23 16:50
     * @param request
     * @return
     */
    public Long getGiftCardParValueByCustomerId(UserGiftCardBalanceQueryRequest request) {
        return userGiftCardRepository.getGiftCardParValueByCustomerId(
                request.getCustomerId(), request.getGiftCardStatus());
    }

    /**
     * @description 查询用户可用礼品卡数量
     * @author wur
     * @date: 2022/12/12 16:07
     * @param customerId
     * @return
     */
    public Long getUseNumCustomerId(String customerId, GiftCardType giftCardType) {
        if (Objects.nonNull(giftCardType)){
            return userGiftCardRepository.getUseNumCustomerId(customerId, giftCardType);
        } else {
            return userGiftCardRepository.getUseNumCustomerId(customerId);
        }
    }

    /**
     * @description 查询用户不可用礼品卡数量
     * @author wur
     * @date: 2022/12/12 16:07
     * @param customerId
     * @return
     */
    public Long getInvalidCustomerId(String customerId, GiftCardType giftCardType) {
        if (Objects.nonNull(giftCardType)){
            return userGiftCardRepository.getInvalidCustomerId(customerId, giftCardType);
        } else {
            return userGiftCardRepository.getInvalidCustomerId(customerId);
        }
    }

    /**
     * @description 查询待激活礼品卡数量
     * @author wur
     * @date: 2022/12/12 16:08
     * @param customerId
     * @return
     */
    public Long getNotActiveNumCustomerId(String customerId, GiftCardType giftCardType) {
        if (Objects.nonNull(giftCardType)){
            return userGiftCardRepository.getNotActiveNumCustomerId(customerId, giftCardType);
        } else {
            return userGiftCardRepository.getNotActiveNumCustomerId(customerId);
        }
    }

    /**
     * @description 分页查询
     * @author wur
     * @date: 2022/12/12 11:43
     * @param request
     * @return
     */
    public MicroServicePage<UserGiftCardInfoVO> queryPage(UserGiftCardQueryRequest request) {
        Page<UserGiftCard> pickupSettingPage =
                userGiftCardRepository.findAll(
                        UserGiftCardWhereCriteriaBuilder.build(request), request.getPageRequest());
        Page<UserGiftCardInfoVO> newPage = pickupSettingPage.map(entity -> this.wrapperVo(entity));
        if (CollectionUtils.isNotEmpty(newPage.getContent())) {
            List<Long> giftCardIfList =
                    newPage.getContent().stream()
                            .map(UserGiftCardInfoVO::getGiftCardId)
                            .collect(Collectors.toList());
            List<GiftCard> giftCardList = giftCardRepository.queryAllByGiftCardIdIn(giftCardIfList);
            List<Long> idList =giftCardList.stream()
                    .filter(g -> Objects.nonNull(g.getScopeGoodsNum()) && g.getScopeGoodsNum() == -99)
                    .map(GiftCard::getGiftCardId)
                    .collect(Collectors.toList());
            Map<Long, List<GiftCardScope>> giftCardIdToScopeMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(idList)){
                List<GiftCardScope> giftCardScopeList = giftCardScopeRepository.queryListByGiftCardIdIn(idList);
                giftCardIdToScopeMap =
                        giftCardScopeList.stream().collect(Collectors.groupingBy(GiftCardScope::getGiftCardId));
            }
            if (CollectionUtils.isNotEmpty(giftCardList)) {
                Map<Long, GiftCard> giftCardMap =
                        giftCardList.stream()
                                .collect(Collectors.toMap(GiftCard::getGiftCardId, card -> card));
                Map<Long, List<GiftCardScope>> finalGiftCardIdToScopeMap = giftCardIdToScopeMap;
                newPage.getContent()
                        .forEach(
                                userGiftCardInfoVO -> {
                                    if (giftCardMap.containsKey(
                                            userGiftCardInfoVO.getGiftCardId())) {
                                        GiftCard giftCard =
                                                giftCardMap.get(userGiftCardInfoVO.getGiftCardId());
                                        userGiftCardInfoVO.setName(giftCard.getName());
                                        userGiftCardInfoVO.setBackgroundType(
                                                giftCard.getBackgroundType());
                                        userGiftCardInfoVO.setBackgroundDetail(
                                                giftCard.getBackgroundDetail());
                                        userGiftCardInfoVO.setExpirationType(
                                                giftCard.getExpirationType());
                                        userGiftCardInfoVO.setRangeMonth(giftCard.getRangeMonth());
                                        userGiftCardInfoVO.setUseDesc(giftCard.getUseDesc());
                                        userGiftCardInfoVO.setScopeType(giftCard.getScopeType());
                                        userGiftCardInfoVO.setContactType(
                                                giftCard.getContactType());
                                        userGiftCardInfoVO.setContactPhone(
                                                giftCard.getContactPhone());
                                        userGiftCardInfoVO.setScopeGoodsNum(giftCard.getScopeGoodsNum());
                                        if (finalGiftCardIdToScopeMap.containsKey(userGiftCardInfoVO.getGiftCardId())){
                                            userGiftCardInfoVO.setTotalGoodsNum(finalGiftCardIdToScopeMap.get(userGiftCardInfoVO.getGiftCardId()).size());
                                        } else if (Objects.nonNull(giftCard.getScopeGoodsNum()) && giftCard.getScopeGoodsNum() == -1){
                                            userGiftCardInfoVO.setTotalGoodsNum(1);
                                        } else if (Objects.nonNull(giftCard.getScopeGoodsNum()) && giftCard.getScopeGoodsNum() > 0){
                                            userGiftCardInfoVO.setTotalGoodsNum(giftCard.getScopeGoodsNum());
                                        }
                                    }
                                });
            }
        }
        MicroServicePage<UserGiftCardInfoVO> microPage =
                new MicroServicePage<>(newPage, newPage.getPageable());
        return microPage;
    }

    /**
     * @description 将实体包装成VO
     * @author wur
     * @date: 2022/12/12 10:17
     * @param userGiftCard
     * @return
     */
    public UserGiftCardInfoVO wrapperVo(UserGiftCard userGiftCard) {
        if (userGiftCard != null) {
            UserGiftCardInfoVO userGiftCardVO =
                    KsBeanUtil.convert(userGiftCard, UserGiftCardInfoVO.class);
            userGiftCardVO = this.wrapperStatus(userGiftCardVO);
            return userGiftCardVO;
        }
        return null;
    }

    /**
     * @description 封装C端状态
     * @author wur
     * @date: 2022/12/12 14:28
     * @param userGiftCardInfoVO
     * @return
     */
    public UserGiftCardInfoVO wrapperStatus(UserGiftCardInfoVO userGiftCardInfoVO) {
        if (userGiftCardInfoVO.getCardStatus() == GiftCardStatus.CANCELED) {
            userGiftCardInfoVO.setInvalidStatus(GiftCardInvalidStatus.CANCELED);
        } else {
            if (!Objects.equals(ExpirationType.FOREVER, userGiftCardInfoVO.getExpirationType())
                    && Objects.nonNull(userGiftCardInfoVO.getExpirationTime())
                    && userGiftCardInfoVO.getExpirationTime().isBefore(LocalDateTime.now())) {
                userGiftCardInfoVO.setInvalidStatus(GiftCardInvalidStatus.TIME_OVER);
            } else {
                if (userGiftCardInfoVO.getCardStatus() == GiftCardStatus.ACTIVATED
                        && userGiftCardInfoVO.getBalance() != null
                        && userGiftCardInfoVO.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                    userGiftCardInfoVO.setInvalidStatus(GiftCardInvalidStatus.USE_OVER);
                }
            }
        }
        return userGiftCardInfoVO;
    }

    /**
     * @description 获取用户购确认订单页礼品卡信息
     * @author wur
     * @date: 2022/12/13 9:24
     * @return
     */
    public UserGiftCardTradeResponse tradeUserGiftCard(UserGiftCardTradeRequest request) {
        UserGiftCardTradeResponse response = new UserGiftCardTradeResponse();
        // 1. 查询用户可用礼品卡
        UserGiftCardQueryRequest queryRequest =
                UserGiftCardQueryRequest.builder()
                        .customerId(request.getCustomerId())
                        .userGiftCardIdList(request.getUserGiftCardIdList())
                        .status(GiftCardUseStatus.USE)
                        .giftCardType(GiftCardType.CASH_CARD)
                        .build();
        List<UserGiftCard> userGiftCardList =
                userGiftCardRepository.findAll(
                        UserGiftCardWhereCriteriaBuilder.build(queryRequest));
        if (CollectionUtils.isEmpty(userGiftCardList)) {
            return response;
        }

        // 2. 封装礼品卡详情
        List<UserGiftCardInfoVO> allUserGiftCardInfoVO = new ArrayList<>();
        List<Long> giftCardIfList =
                userGiftCardList.stream()
                        .map(UserGiftCard::getGiftCardId)
                        .collect(Collectors.toList());
        List<GiftCard> giftCardList = giftCardRepository.queryAllByGiftCardIdIn(giftCardIfList);
        if (CollectionUtils.isNotEmpty(giftCardList)) {
            Map<Long, GiftCard> giftCardMap =
                    giftCardList.stream()
                            .collect(Collectors.toMap(GiftCard::getGiftCardId, card -> card));
            userGiftCardList.forEach(
                    userGiftCard -> {
                        UserGiftCardInfoVO userGiftCardInfoVO = this.wrapperVo(userGiftCard);
                        if (giftCardMap.containsKey(userGiftCardInfoVO.getGiftCardId())) {
                            GiftCard giftCard = giftCardMap.get(userGiftCardInfoVO.getGiftCardId());
                            userGiftCardInfoVO.setName(giftCard.getName());
                            userGiftCardInfoVO.setBackgroundType(giftCard.getBackgroundType());
                            userGiftCardInfoVO.setBackgroundDetail(giftCard.getBackgroundDetail());
                            userGiftCardInfoVO.setExpirationType(giftCard.getExpirationType());
                            userGiftCardInfoVO.setRangeMonth(giftCard.getRangeMonth());
                            userGiftCardInfoVO.setUseDesc(giftCard.getUseDesc());
                            userGiftCardInfoVO.setScopeType(giftCard.getScopeType());
                            userGiftCardInfoVO.setContactType(giftCard.getContactType());
                            userGiftCardInfoVO.setContactPhone(giftCard.getContactPhone());
                        }
                        allUserGiftCardInfoVO.add(userGiftCardInfoVO);
                    });
        }

        // 3. 查询礼品卡适用商品配置
        List<GiftCardScope> scopeList =
                giftCardScopeRepository.queryListByGiftCardIdIn(giftCardIfList);
        Map<Long, List<GiftCardScope>> scopeMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(scopeList)) {
            scopeMap =
                    scopeList.stream().collect(Collectors.groupingBy(GiftCardScope::getGiftCardId));
        }
        // 4. 循环处理用户每个礼品卡
        List<UserGiftCardInfoVO> useGiftCardInfoVO = new ArrayList<>();
        List<UserGiftCardInfoVO> invalidGiftCardInfoVO = new ArrayList<>();
        Map<Long, List<GiftCardScope>> finalScopeMap = scopeMap;
        for (UserGiftCardInfoVO userGiftCardInfoVO : allUserGiftCardInfoVO) {
            // 4.1 如果全部商品适用
            if (Objects.equals(GiftCardScopeType.ALL, userGiftCardInfoVO.getScopeType())) {
                userGiftCardInfoVO.setSkuIdList(
                        request.getGoodsInfoVOList().stream()
                                .map(GoodsInfoVO::getGoodsInfoId)
                                .collect(Collectors.toList()));
                useGiftCardInfoVO.add(userGiftCardInfoVO);
                continue;
            }
            // 4.2 根据适用条件进行过滤
            if (!finalScopeMap.containsKey(userGiftCardInfoVO.getGiftCardId())) {
                invalidGiftCardInfoVO.add(userGiftCardInfoVO);
                continue;
            }
            List<String> scopeIdList =
                    finalScopeMap.get(userGiftCardInfoVO.getGiftCardId()).stream()
                            .map(GiftCardScope::getScopeId)
                            .collect(Collectors.toList());
            List<String> skuIdList =
                    this.validateGiftCard(
                            request.getGoodsInfoVOList(),
                            scopeIdList,
                            userGiftCardInfoVO.getScopeType());
            if (CollectionUtils.isNotEmpty(skuIdList)) {
                userGiftCardInfoVO.setSkuIdList(skuIdList);
                useGiftCardInfoVO.add(userGiftCardInfoVO);
            } else {
                invalidGiftCardInfoVO.add(userGiftCardInfoVO);
            }
        }
        if (CollectionUtils.isNotEmpty(useGiftCardInfoVO)) {
            response.setValidGiftCardInfoVO(
                    useGiftCardInfoVO.stream()
                            .sorted(
                                    Comparator.comparing(
                                            UserGiftCardInfoVO::getExpirationTime,
                                            Comparator.nullsLast(LocalDateTime::compareTo))
                                            .thenComparing(
                                                    Comparator.comparing(
                                                            UserGiftCardInfoVO::getAcquireTime)
                                                            .reversed()))
                            .collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(invalidGiftCardInfoVO)) {
            response.setInvalidGiftCardInfoVO(
                    invalidGiftCardInfoVO.stream()
                            .sorted(
                                    Comparator.comparing(
                                            UserGiftCardInfoVO::getExpirationTime,
                                            Comparator.nullsLast(LocalDateTime::compareTo))
                                            .thenComparing(
                                                    Comparator.comparing(
                                                            UserGiftCardInfoVO::getAcquireTime)
                                                            .reversed()))
                            .collect(Collectors.toList()));
        }
        response.setValidNum(Long.valueOf(useGiftCardInfoVO.size()));
        response.setInvalidNum(Long.valueOf(invalidGiftCardInfoVO.size()));
        return response;
    }

    /**
     * @description 过滤礼品卡适用商品
     * @author wur
     * @date: 2022/12/13 9:24
     * @param goodsInfoVOList
     * @param scopeIdList
     * @param scopeType
     * @return 返回适用商品SKUID 如果为空则没有可使用商品
     */
    public List<String> validateGiftCard(
            List<GoodsInfoVO> goodsInfoVOList,
            List<String> scopeIdList,
            GiftCardScopeType scopeType) {
        List<String> skuIdList = new ArrayList<>();
        // 处理所属品牌
        if (Objects.equals(GiftCardScopeType.BRAND, scopeType)) {
            List<GoodsInfoVO> goodsInfoList =
                    goodsInfoVOList.stream()
                            .filter(
                                    goodsInfoVO ->
                                            Objects.nonNull(goodsInfoVO.getBrandId())
                                                    && scopeIdList.contains(
                                                            goodsInfoVO.getBrandId().toString()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsInfoList)) {
                skuIdList =
                        goodsInfoList.stream()
                                .map(GoodsInfoVO::getGoodsInfoId)
                                .collect(Collectors.toList());
            }
            return skuIdList;
        }
        // 处理所属类目
        if (Objects.equals(GiftCardScopeType.CATE, scopeType)) {
            List<GoodsInfoVO> goodsInfoList =
                    goodsInfoVOList.stream()
                            .filter(
                                    goodsInfoVO ->
                                            Objects.nonNull(goodsInfoVO.getCateId())
                                                    && scopeIdList.contains(
                                                            goodsInfoVO.getCateId().toString()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsInfoList)) {
                skuIdList =
                        goodsInfoList.stream()
                                .map(GoodsInfoVO::getGoodsInfoId)
                                .collect(Collectors.toList());
            }
            return skuIdList;
        }
        // 处理所属商家
        if (Objects.equals(GiftCardScopeType.STORE, scopeType)) {
            List<GoodsInfoVO> goodsInfoList =
                    goodsInfoVOList.stream()
                            .filter(
                                    goodsInfoVO ->
                                            Objects.nonNull(goodsInfoVO.getStoreId())
                                                    && scopeIdList.contains(
                                                            goodsInfoVO.getStoreId().toString()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsInfoList)) {
                skuIdList =
                        goodsInfoList.stream()
                                .map(GoodsInfoVO::getGoodsInfoId)
                                .collect(Collectors.toList());
            }
            return skuIdList;
        }

        // 处理指定SKU
        if (Objects.equals(GiftCardScopeType.GOODS, scopeType)) {
            List<GoodsInfoVO> goodsInfoList =
                    goodsInfoVOList.stream()
                            .filter(
                                    goodsInfoVO ->
                                            scopeIdList.contains(goodsInfoVO.getGoodsInfoId()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsInfoList)) {
                skuIdList =
                        goodsInfoList.stream()
                                .map(GoodsInfoVO::getGoodsInfoId)
                                .collect(Collectors.toList());
            }
            return skuIdList;
        }
        return skuIdList;
    }

    /**
     * @description 用户礼品卡使用验证
     * @author wur
     * @date: 2022/12/13 13:43
     * @param request
     * @return
     */
    public void checkUserGiftCard(UserGiftCardUseCheckRequest request) {
        if (CollectionUtils.isEmpty(request.getCheckVOList())) {
            return;
        }
        Map<Long, BigDecimal> checkBalanceMap =
                request.getCheckVOList().stream()
                        .collect(
                                Collectors.toMap(
                                        UserGiftCardUseCheckVO::getUserGiftCardId,
                                        UserGiftCardUseCheckVO::getUsePrice));
        List<Long> userGiftCardIdList =
                request.getCheckVOList().stream()
                        .map(UserGiftCardUseCheckVO::getUserGiftCardId)
                        .collect(Collectors.toList());

        // 1. 查询用户可用礼品卡
        UserGiftCardQueryRequest queryRequest =
                UserGiftCardQueryRequest.builder()
                        .customerId(request.getCustomerId())
                        .userGiftCardIdList(userGiftCardIdList)
                        .build();
        List<UserGiftCard> userGiftCardList =
                userGiftCardRepository.findAll(
                        UserGiftCardWhereCriteriaBuilder.build(queryRequest));
        if (CollectionUtils.isEmpty(userGiftCardList)
                || request.getCheckVOList().size() != userGiftCardList.size()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080041);
        }
        LocalDateTime now = LocalDateTime.now();
        for (UserGiftCard userGiftCard : userGiftCardList) {
            // 验证卡是否有效
            if (BigDecimal.ZERO.compareTo(userGiftCard.getBalance()) >= 0
                    || !Objects.equals(GiftCardStatus.ACTIVATED, userGiftCard.getCardStatus())
                    || Objects.nonNull(userGiftCard.getExpirationTime())
                            && now.isAfter(userGiftCard.getExpirationTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080041);
            }
            // 验证预估使用余额
            if (checkBalanceMap.containsKey(userGiftCard.getUserGiftCardId())
                    && userGiftCard
                                    .getBalance()
                                    .compareTo(
                                            checkBalanceMap.get(userGiftCard.getUserGiftCardId()))
                            < 0) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080041);
            }
        }
        return;
    }

    /**
     * @description
     * @author wur
     * @date: 2022/12/14 10:34
     * @param request
     * @return
     */
    public UserGiftCardListResponse getUserGiftCardList(UserGiftCardQueryRequest request) {
        List<UserGiftCard> userGiftCardList =
                userGiftCardRepository.findAll(UserGiftCardWhereCriteriaBuilder.build(request));
        if (CollectionUtils.isEmpty(userGiftCardList)) {
            return UserGiftCardListResponse.builder().build();
        }
        List<UserGiftCardVO> userGiftCardVO =
                KsBeanUtil.convertList(userGiftCardList, UserGiftCardVO.class);
        return UserGiftCardListResponse.builder().userGiftCardVO(userGiftCardVO).build();
    }

    @Autowired RedissonClient redissonClient;

    /**
     * @description 余额扣减失败 如果返回实际扣除金额为0 则扣除失败
     * @author wur
     * @date: 2022/12/14 15:24
     * @param request
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public UserGiftCardUseResponse useUserGiftCard(UserGiftCardTransRequest request) {
        UserGiftCardUseResponse response =
                UserGiftCardUseResponse.builder()
                        .userGiftCardId(request.getUserGiftCardId())
                        .usePrice(BigDecimal.ZERO)
                        .build();
        List<GiftCardTransBusinessVO> returnBusinessVOList = new ArrayList<>();
        // 1. 根据卡号加锁
        String joinLock =
                CacheKeyConstant.MARKETING_GIFT_CARD_NO_LOCK.concat(request.getGiftCardNo());
        RLock rLock = redissonClient.getFairLock(joinLock);
        rLock.lock();
        try {
            // 2. 查询卡信息
            Optional<UserGiftCard> userGiftCardOpt =
                    userGiftCardRepository.getByCustomerIdAndGiftCardNo(
                            request.getCustomerId(), request.getGiftCardNo());
            if (!userGiftCardOpt.isPresent()) {
                return response;
            }
            UserGiftCard userGiftCard = userGiftCardOpt.get();
            if (Objects.isNull(userGiftCard.getBalance())
                    || BigDecimal.ZERO.compareTo(userGiftCard.getBalance()) >= 0) {
                return response;
            }
            // 3. 计算可扣除金额
            BigDecimal beforeBalance = userGiftCard.getBalance();
            BigDecimal tradeBalance =
                    request.getSumTradePrice().compareTo(userGiftCard.getBalance()) <= 0
                            ? request.getSumTradePrice()
                            : userGiftCard.getBalance();
            // 4. 验证是否强制执行
            if (!Objects.equals(Boolean.TRUE, request.getForceCommit())
                    && tradeBalance.compareTo(request.getSumTradePrice()) != 0) {
                return response;
            }
            // 5. 扣除余额
            userGiftCard.setBalance(userGiftCard.getBalance().subtract(tradeBalance));
            userGiftCardRepository.save(userGiftCard);
            BigDecimal afterBalance = userGiftCard.getBalance();
            // 6. 计算均摊
            List<GiftCardTransBusinessItemVO> itemVOList = new ArrayList<>();
            for (GiftCardTransBusinessVO businessVO : request.getTransBusinessVOList()) {
                itemVOList.addAll(businessVO.getBusinessItemVOList());
            }
            this.calcSplitPrice(itemVOList, tradeBalance);

            // 6. 封装交易记录入库 按照每个业务记录封装数据
            List<GiftCardBill> billList = new ArrayList<>();
            for (GiftCardTransBusinessVO businessVO : request.getTransBusinessVOList()) {
                // 6.1 获取均摊后数据 并计算业务记录抵扣金额
                BigDecimal businessTradeBalance = BigDecimal.ZERO;
                for (GiftCardTransBusinessItemVO itemVO : businessVO.getBusinessItemVOList()) {
                    for (GiftCardTransBusinessItemVO item : itemVOList) {
                        if (Objects.equals(itemVO.getItemId(), item.getItemId())
                                && (Objects.isNull(itemVO.getPreferentialMarketingId())
                                                && Objects.isNull(item.getPreferentialMarketingId())
                                        || Objects.equals(
                                                itemVO.getPreferentialMarketingId(),
                                                item.getPreferentialMarketingId()))) {
                            itemVO.setDeductionPrice(item.getDeductionPrice());
                            businessTradeBalance = businessTradeBalance.add(itemVO.getDeductionPrice());
                        }
                    }
                }
                if (BigDecimal.ZERO.compareTo(businessTradeBalance) >= 0) {
                    continue;
                }
                // 6.2 封装返回
                businessVO.setTradePrice(businessTradeBalance);
                GiftCardBill giftCardBill = new GiftCardBill();
                giftCardBill.setCustomerId(request.getCustomerId());
                giftCardBill.setUserGiftCardId(userGiftCard.getUserGiftCardId());
                giftCardBill.setGiftCardNo(userGiftCard.getGiftCardNo());
                giftCardBill.setTradeBalance(businessTradeBalance);
                giftCardBill.setBeforeBalance(beforeBalance);
                giftCardBill.setAfterBalance(afterBalance);
                giftCardBill.setBusinessId(businessVO.getBusinessId());
                giftCardBill.setBusinessType(request.getBusinessType());
                giftCardBill.setTradeTime(LocalDateTime.now());
                giftCardBill.setTradePerson(request.getCustomerId());
                giftCardBill.setTradePersonType(request.getTradePersonType());
                giftCardBill.setGiftCardId(userGiftCard.getGiftCardId());
                billList.add(giftCardBill);
                returnBusinessVOList.add(businessVO);
            }
            if (CollectionUtils.isNotEmpty(billList)) {
                giftCardBillRepository.saveAll(billList);
            }
            response.setUsePrice(tradeBalance);
            response.setUserGiftCardId(userGiftCard.getUserGiftCardId());
            response.setGiftCardNo(userGiftCard.getGiftCardNo());
            response.setTransBusinessVOList(returnBusinessVOList);
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
    }

    /**
     * @description 提货卡使用
     * @author  edz
     * @date: 2023/7/3 16:42
     * @param userPickupCardRequest
     * @return void
     */
    @Transactional
    public void userPickupCard(UserPickupCardRequest userPickupCardRequest){
        UserGiftCard userGiftCard =
                userGiftCardRepository.getByUserGiftCardId(userPickupCardRequest.getUserGiftCardId())
                        .orElseThrow(() -> new SbcRuntimeException(MarketingErrorCodeEnum.K080039));
        if (Objects.nonNull(userGiftCard.getBalance()) && userGiftCard.getBalance().equals(BigDecimal.ZERO)){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080202);
        }
        List<GiftCardBill> giftCardBills = new ArrayList<>();
        userPickupCardRequest.getTIds().forEach(id -> {
            GiftCardBill giftCardBill = new GiftCardBill();
            giftCardBill.setCustomerId(userGiftCard.getCustomerId());
            giftCardBill.setUserGiftCardId(userGiftCard.getUserGiftCardId());
            giftCardBill.setGiftCardNo(userGiftCard.getGiftCardNo());
            giftCardBill.setTradeBalance(BigDecimal.ZERO);
            giftCardBill.setBeforeBalance(BigDecimal.ZERO);
            giftCardBill.setAfterBalance(BigDecimal.ZERO);
            giftCardBill.setBusinessId(id);
            giftCardBill.setBusinessType(GiftCardBusinessType.ORDER_DEDUCTION);
            giftCardBill.setTradeTime(LocalDateTime.now());
            giftCardBill.setTradePerson(userGiftCard.getCustomerId());
            giftCardBill.setTradePersonType(DefaultFlag.NO);
            giftCardBill.setGiftCardId(userGiftCard.getGiftCardId());
            giftCardBills.add(giftCardBill);
        });

        giftCardBillRepository.saveAll(giftCardBills);
        userGiftCard.setBalance(BigDecimal.ZERO);
        userGiftCardRepository.save(userGiftCard);
    }

    /**
     * @description
     * @author wur
     * @date: 2022/12/16 9:46
     * @param itemVOList
     * @param tradeBalance 交易金额
     * @return
     */
    private void calcSplitPrice(
            List<GiftCardTransBusinessItemVO> itemVOList, BigDecimal tradeBalance) {
        BigDecimal totalPrice =
                itemVOList.stream()
                        .map(GiftCardTransBusinessItemVO::getSplitPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        int size = itemVOList.size();
        BigDecimal deductionPrice = BigDecimal.ZERO; // 累积平摊价，将剩余扣给最后一个元素
        for (int i = 0; i < size; i++) {
            GiftCardTransBusinessItemVO tradeItem = itemVOList.get(i);
            if (i == size - 1) {
                tradeItem.setDeductionPrice(tradeBalance.subtract(deductionPrice));
            } else {
                // 计算抵扣金额
                BigDecimal splitPrice =
                        tradeItem.getSplitPrice() != null
                                ? tradeItem.getSplitPrice()
                                : BigDecimal.ZERO;
                BigDecimal scale = splitPrice.divide(totalPrice, 10, RoundingMode.DOWN);
                BigDecimal newSplitPrice =
                        tradeBalance.multiply(scale).setScale(2, RoundingMode.HALF_UP);
                tradeItem.setDeductionPrice(newSplitPrice);
                deductionPrice = deductionPrice.add(tradeItem.getDeductionPrice());
            }
        }
    }

    /**
     * @description
     * @author wur
     * @date: 2022/12/14 15:25
     * @param request
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public void returnUserGiftCard(UserGiftCardTransRequest request) {
        // 1. 根据卡号加锁
        String joinLock =
                CacheKeyConstant.MARKETING_GIFT_CARD_NO_LOCK.concat(request.getGiftCardNo());
        RLock rLock = redissonClient.getFairLock(joinLock);
        rLock.lock();
        try {
            // 2. 查询卡信息
            Optional<UserGiftCard> userGiftCardOpt =
                    userGiftCardRepository.getByCustomerIdAndGiftCardNo(
                            request.getCustomerId(), request.getGiftCardNo());
            if (!userGiftCardOpt.isPresent()) {
                return;
            }
            UserGiftCard userGiftCard = userGiftCardOpt.get();
            BigDecimal beforeBalance = userGiftCard.getBalance();
            // 3. 退还余额
            // 验证礼品不卡是否已经销卡
            BigDecimal balance;
            if (Objects.equals(GiftCardStatus.CANCELED, userGiftCard.getCardStatus())) {
                balance = userGiftCard.getCancelBalance().add(request.getSumTradePrice());
                userGiftCard.setCancelBalance(balance);
            } else {
                balance = userGiftCard.getBalance().add(request.getSumTradePrice());
                userGiftCard.setBalance(balance);
            }
            if (balance.compareTo(new BigDecimal(userGiftCard.getParValue().toString())) > 0){
                log.error("现金卡退还余额大于面值, 请求参数:{}", request);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            userGiftCardRepository.save(userGiftCard);
            BigDecimal afterBalance = userGiftCard.getBalance();
            // 4. 封装交易记录入库  验证是否业务回滚
            if(Objects.equals(Boolean.TRUE, request.getRollback())) {
                List<String> businessIdList = request.getTransBusinessVOList().stream().map(GiftCardTransBusinessVO::getBusinessId).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(businessIdList)){
                    giftCardBillRepository.deleteBusiness(request.getCustomerId(), userGiftCard.getUserGiftCardId(), request.getRollbackBusinessType(), businessIdList);
                }
            } else {
                List<GiftCardBill> billList = new ArrayList<>();
                for (GiftCardTransBusinessVO businessVO : request.getTransBusinessVOList()) {
                    GiftCardBill giftCardBill = new GiftCardBill();
                    giftCardBill.setCustomerId(request.getCustomerId());
                    giftCardBill.setUserGiftCardId(userGiftCard.getUserGiftCardId());
                    giftCardBill.setGiftCardNo(userGiftCard.getGiftCardNo());
                    giftCardBill.setTradeBalance(businessVO.getTradePrice());
                    giftCardBill.setBeforeBalance(beforeBalance);
                    giftCardBill.setAfterBalance(afterBalance);
                    giftCardBill.setBusinessId(businessVO.getBusinessId());
                    giftCardBill.setBusinessType(request.getBusinessType());
                    giftCardBill.setTradeTime(LocalDateTime.now());
                    giftCardBill.setTradePerson(request.getCustomerId());
                    giftCardBill.setTradePersonType(request.getTradePersonType());
                    giftCardBill.setGiftCardId(userGiftCard.getGiftCardId());
                    billList.add(giftCardBill);
                }
                giftCardBillRepository.saveAll(billList);
            }
            // 5. 如果已销卡-更新销卡金额
            if (Objects.equals(GiftCardStatus.CANCELED, userGiftCard.getCardStatus())) {
                Optional<GiftCardBill> opt =
                        giftCardBillRepository.findByUserGiftCardIdAndBusinessType(
                                userGiftCard.getUserGiftCardId(), GiftCardBusinessType.CANCEL_CARD);
                if (opt.isPresent()) {
                    GiftCardBill cancelBill = opt.get();
                    cancelBill.setTradeBalance(
                            cancelBill.getTradeBalance().add(request.getSumTradePrice()));
                    giftCardBillRepository.save(cancelBill);
                }
            }
            return;
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
    }

    /**
     * @description 查询指定礼品卡卡号列表余额之和
     * @author malianfeng
     * @date 2022/12/15 20:09
     * @param giftCardNoList 礼品卡卡号列表
     * @return java.math.BigDecimal
     */
    public BigDecimal queryBalance(List<String> giftCardNoList) {
        return userGiftCardRepository.queryBalance(giftCardNoList);
    }

    /**
     * @description 查询指定礼品卡卡号的记录
     * @author malianfeng
     * @date 2022/12/16 12:30
     * @param giftCardNoList 礼品卡卡号列表
     * @return java.util.List<com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard>
     */
    public List<UserGiftCard> findByGiftCardNoList(List<String> giftCardNoList) {
        if (CollectionUtils.isEmpty(giftCardNoList)) {
            return Collections.emptyList();
        }
        return userGiftCardRepository.findByGiftCardNoIn(giftCardNoList);
    }

    /**
     * @description 查询指定用户未销卡的卡号列表
     * @author malianfeng
     * @date 2022/12/26 14:23
     * @param customerId 用户id
     * @return java.util.List<java.lang.String>
     */
    public List<String> findNotCancelGiftCardNoByCustomerId(String customerId) {
        return userGiftCardRepository.findNotCancelGiftCardNoByCustomerId(customerId);
    }

    /**
     * @description 查询总数量
     * @author 马连峰
     */
    public Long count(UserGiftCardQueryRequest queryReq) {
        return userGiftCardRepository.count(UserGiftCardWhereCriteriaBuilder.build(queryReq));
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<UserGiftCard> infos) {
        if (CollectionUtils.isEmpty(infos)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append(
                "insert INTO user_gift_card(user_gift_card_id,customer_id,gift_card_id,gift_card_name,batch_no," +
                        "gift_card_no,par_value,balance,card_status,expiration_type,expiration_time,acquire_time," +
                        "belong_person,activation_time,cancel_balance,cancel_time,cancel_person,cancel_desc," +
                        "source_type, gift_card_type, appointment_shipment_flag, order_sn, order_detail_retail_id) values ");
        String sqlValue = " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int len = infos.size();
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                sql.append(" , ");
            }
            sql.append(sqlValue);
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        int paramIndex = 1;
        for (int i = 0; i < len; i++) {
            UserGiftCard item = infos.get(i);
            query.setParameter(paramIndex++, item.getUserGiftCardId());
            query.setParameter(paramIndex++, item.getCustomerId());
            query.setParameter(paramIndex++, item.getGiftCardId());
            query.setParameter(paramIndex++, item.getGiftCardName());
            query.setParameter(paramIndex++, item.getBatchNo());
            query.setParameter(paramIndex++, item.getGiftCardNo());
            query.setParameter(paramIndex++, item.getParValue());
            query.setParameter(paramIndex++, item.getBalance());
            query.setParameter(
                    paramIndex++,
                    Objects.nonNull(item.getCardStatus()) ? item.getCardStatus().toValue() : null);
            query.setParameter(
                    paramIndex++,
                    Objects.nonNull(item.getExpirationType())
                            ? item.getExpirationType().toValue()
                            : null);
            query.setParameter(paramIndex++, item.getExpirationTime());
            query.setParameter(paramIndex++, item.getAcquireTime());
            query.setParameter(paramIndex++, item.getBelongPerson());
            query.setParameter(paramIndex++, item.getActivationTime());
            query.setParameter(paramIndex++, item.getCancelBalance());
            query.setParameter(paramIndex++, item.getCancelTime());
            query.setParameter(paramIndex++, item.getCancelPerson());
            query.setParameter(paramIndex++, item.getCancelDesc());
            query.setParameter(
                    paramIndex++,
                    Objects.nonNull(item.getSourceType()) ? item.getSourceType().toValue() : null);
            query.setParameter(paramIndex++, Objects.nonNull(item.getGiftCardType())  ? item.getGiftCardType().toValue() : 0);
            //旧校服送新校服提货卡新增参数
            query.setParameter(paramIndex++, Objects.nonNull(item.getAppointmentShipmentFlag())  ? item.getAppointmentShipmentFlag() : 0);
            query.setParameter(paramIndex++, Objects.nonNull(item.getOrderSn())  ? item.getOrderSn(): null);
            query.setParameter(paramIndex++, Objects.nonNull(item.getOrderDetailRetailId())  ? item.getOrderDetailRetailId() : null);
        }
        query.executeUpdate();
    }

    public List<String> getGiftCardNoList(OldSendNewGiftCardCancelRequest request) {
        List<String> giftCardNoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(request.getOldSendNewGiftCardCancelChildRequestList())) {
            List<OldSendNewGiftCardCancelChildRequest> oldSendNewGiftCardCancelChildRequestList = request.getOldSendNewGiftCardCancelChildRequestList();
            for (OldSendNewGiftCardCancelChildRequest oldSendNewGiftCardCancelChildRequest : oldSendNewGiftCardCancelChildRequestList) {
                List<String> cardNoList = userGiftCardRepository.selectGiftCardNoList(request.getOrderSn(), oldSendNewGiftCardCancelChildRequest.getOrderDetailRetailId(), Integer.valueOf(oldSendNewGiftCardCancelChildRequest.getNumber()));
                giftCardNoList.addAll(cardNoList);
            }
        }else {
            giftCardNoList = userGiftCardRepository.selectGiftCardNoList(request.getOrderSn(), null, 999999);
        }
        return giftCardNoList;
    }

    /**
     * @param userReturnPickupCardRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @description 订单取消返还提货卡
     * @author 刘方鑫
     * @date: 2025/8/12 16:45
     */
    @Transactional(rollbackFor = {Exception.class})
    public void returnPickupCard(UserReturnPickupCardRequest userReturnPickupCardRequest) {
        log.info("订单:{}取消返还提货卡:{}开始",userReturnPickupCardRequest.getTIds(),userReturnPickupCardRequest.getUserGiftCardId());
        //更改userGiftCard 的 balance为null
        userGiftCardRepository.updateBalanceNull(userReturnPickupCardRequest.getUserGiftCardId());
        //删除 giftCardBill 对应的使用记录
        giftCardBillRepository.deleteBusiness(userReturnPickupCardRequest.getCustomerId(),userReturnPickupCardRequest.getUserGiftCardId(),GiftCardBusinessType.ORDER_DEDUCTION, userReturnPickupCardRequest.getTIds());
    }

    @Transactional(rollbackFor = {Exception.class})
    public void oldSendNewAutoActivateGiftCard(List<String> orderSns) {
        List<UserGiftCard>  userGiftCardList = userGiftCardRepository.findByOrderSnInAndCardStatus(orderSns,GiftCardStatus.NOT_ACTIVE);
        for (UserGiftCard userGiftCard : userGiftCardList) {
            String customerId = userGiftCard.getCustomerId();
            // 判断当前礼品卡是否属于当前会员同时判断当前礼品卡状态是否为待激活未过期的状态
            if (customerId.equals(userGiftCard.getCustomerId())
                    && GiftCardStatus.NOT_ACTIVE == userGiftCard.getCardStatus()
                    && (Objects.isNull(userGiftCard.getExpirationTime())
                    || (Objects.nonNull(userGiftCard.getExpirationTime())
                    && LocalDateTime.now()
                    .isBefore(userGiftCard.getExpirationTime())))) {
                // 更新会员礼品卡状态以及余额数据
                userGiftCard.setActivationTime(LocalDateTime.now());
                userGiftCard.setCardStatus(GiftCardStatus.ACTIVATED);
                if (GiftCardType.CASH_CARD.equals(userGiftCard.getGiftCardType())){
                    userGiftCard.setBalance(new BigDecimal(userGiftCard.getParValue()));
                }
                // 判断过期类型是否为激活后多少月内有效
                GiftCard giftCard = giftCardRepository.getById(userGiftCard.getGiftCardId());
                LocalDateTime expirationTime = null;
                if (ExpirationType.MONTH == giftCard.getExpirationType()) {
                    expirationTime = LocalDateTime.now().plusMonths(giftCard.getRangeMonth());
                    expirationTime = expirationTime.withHour(23).withMinute(59).withSecond(59).withNano(0);
                    log.info("UserGiftCardService-activateGiftCard::礼品卡过期时间:{}, 请求参数:{}", expirationTime, userGiftCard);
                    userGiftCard.setExpirationTime(expirationTime);
                } else if (ExpirationType.SPECIFIC_TIME == giftCard.getExpirationType()) {
                    expirationTime = giftCard.getExpirationTime();
                }
                userGiftCardRepository.save(userGiftCard);
                // 更新礼品卡详情数据
                giftCardDetailRepository.updateGiftCardDetailActivated(
                        GiftCardDetailStatus.ACTIVATED,
                        customerId,
                        expirationTime,
                        userGiftCard.getGiftCardNo());
                // 封装礼品卡使用记录
                GiftCardBill giftCardBill = new GiftCardBill();
                giftCardBill.setCustomerId(customerId);
                giftCardBill.setUserGiftCardId(userGiftCard.getUserGiftCardId());
                giftCardBill.setGiftCardId(userGiftCard.getGiftCardId());
                giftCardBill.setGiftCardNo(userGiftCard.getGiftCardNo());
                if (GiftCardSourceType.MAKE == userGiftCard.getSourceType()) {
                    giftCardBill.setBusinessType(GiftCardBusinessType.ACTIVATE_CARD_FOR_EXCHANGE_CARD);
                } else if (GiftCardSourceType.SEND == userGiftCard.getSourceType()) {
                    giftCardBill.setBusinessType(GiftCardBusinessType.ACTIVATE_CARD_FOR_SEND_CARD);
                }
                if (GiftCardType.CASH_CARD.equals(userGiftCard.getGiftCardType())) {
                    giftCardBill.setTradeBalance(new BigDecimal(userGiftCard.getParValue()));
                    giftCardBill.setAfterBalance(new BigDecimal(userGiftCard.getParValue()));
                    giftCardBill.setBeforeBalance(BigDecimal.ZERO);
                }
                giftCardBill.setTradeTime(LocalDateTime.now());
                giftCardBill.setTradePersonType(DefaultFlag.NO);
                giftCardBill.setTradePerson(customerId);
                giftCardBillRepository.save(giftCardBill);
            }
        }
    }

}
