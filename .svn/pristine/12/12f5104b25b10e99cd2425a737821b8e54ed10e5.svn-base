package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardScopeType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.GiftCardVO;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCard;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardScope;
import com.wanmi.sbc.marketing.giftcard.repository.GiftCardDetailRepository;
import com.wanmi.sbc.marketing.giftcard.repository.GiftCardRepository;
import com.wanmi.sbc.marketing.giftcard.repository.GiftCardScopeRepository;
import com.wanmi.sbc.marketing.giftcard.repository.UserGiftCardRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className GiftCardService
 * @description 礼品卡业务处理
 * @date 2022/12/8 16:21
 **/
@Service
public class GiftCardService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GiftCardRepository giftCardRepository;

    @Autowired
    private GiftCardScopeRepository giftCardScopeRepository;

    @Autowired
    private UserGiftCardRepository userGiftCardRepository;

    @Autowired
    private GiftCardDetailRepository giftCardDetailRepository;

    @Transactional(rollbackFor = Exception.class)
    public void addGiftCard(GiftCardNewRequest request) {
        //新增礼品卡
        GiftCard giftCard = new GiftCard();
        BeanUtils.copyProperties(request, giftCard);
        giftCard.setOriginStock(request.getStock());
        giftCard.setMakeNum(0L);
        giftCard.setSendNum(0L);
        giftCard.setCreateTime(LocalDateTime.now());
        giftCard.setCreatePerson(request.getUserId());
        giftCard.setDelFlag(DeleteFlag.NO);
        giftCard.setGiftCardType(request.getGiftCardType() == null ? GiftCardType.CASH_CARD : request.getGiftCardType());
        giftCard = giftCardRepository.save(giftCard);

        //处理可用商品
        if ((Objects.isNull(request.getScopeType())
                || Objects.equals(GiftCardScopeType.ALL, request.getScopeType())
                || CollectionUtils.isEmpty(request.getScopeIdList()))
                && Objects.isNull(request.getScopeGoodsNum())) {
           return;
        }
        List<GiftCardScope> scopeList = new ArrayList<>();
        Long giftCardId = giftCard.getGiftCardId();
        request.getScopeIdList().forEach(scopeId->{
            GiftCardScope scope = new GiftCardScope();
            scope.setScopeId(scopeId);
            scope.setGiftCardId(giftCardId);
            scopeList.add(scope);
        });
        giftCardScopeRepository.saveAll(scopeList);
    }

    /**
     * @description   礼品卡编辑
     * @author  wur
     * @date: 2022/12/9 11:08
     * @param request
     * @return
     **/
    @Transactional(rollbackFor = Exception.class)
    public void saveGiftCard(GiftCardSaveRequest request) {
        //查询礼品卡是否存在
        Optional<GiftCard> opt = giftCardRepository.findById(request.getGiftCardId());
        if (!opt.isPresent()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        GiftCard giftCard = opt.get();
        if (Objects.isNull(giftCard)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }

        if (Objects.nonNull(request.getStock())) {
            if (request.getStock().compareTo(giftCard.getOriginStock()) < 0) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            } else {
                giftCard.setStock(request.getStock());
                giftCard.setOriginStock(request.getStock());
            }
        }
        if (StringUtils.isNotBlank(request.getName())) {
            giftCard.setName(request.getName());
            userGiftCardRepository.updateGiftCardName(giftCard.getGiftCardId(), request.getName());
        }
        if (Objects.nonNull(request.getBackgroundType()) && StringUtils.isNotBlank(request.getBackgroundDetail())) {
            giftCard.setBackgroundType(request.getBackgroundType());
            giftCard.setBackgroundDetail(request.getBackgroundDetail());
        }
        // 领取多少月内有效
        if (Objects.equals(ExpirationType.MONTH, giftCard.getExpirationType())
                && Objects.nonNull(request.getRangeMonth())) {
            if (request.getRangeMonth().compareTo(giftCard.getRangeMonth()) < 0) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(giftCard.getRangeMonth().compareTo(request.getRangeMonth()) != 0) {
                // 修改用户礼品卡过期时间
                userGiftCardRepository.updateExpirationTimeByRangeMonth(giftCard.getGiftCardId(), request.getRangeMonth().intValue());
                // 修改礼品卡详情过期时间
                giftCardDetailRepository.updateExpirationTimeByRangeMonth(giftCard.getGiftCardId(), request.getRangeMonth().intValue());
                giftCard.setRangeMonth(request.getRangeMonth());
            }
        }
        // 具体过期时间
        if (Objects.equals(ExpirationType.SPECIFIC_TIME, giftCard.getExpirationType())
                && Objects.nonNull(request.getExpirationTime())) {
            if(giftCard.getExpirationTime().isAfter(request.getExpirationTime())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 修改用户礼品卡过期时间
            userGiftCardRepository.updateExpirationTime(giftCard.getGiftCardId(), request.getExpirationTime());
            // 修改礼品卡详情过期时间
            giftCardDetailRepository.updateExpirationTime(giftCard.getGiftCardId(), request.getExpirationTime());
            giftCard.setExpirationTime(request.getExpirationTime());
        }
        // 修改客服信息
        if (Objects.nonNull(request.getContactType()) && StringUtils.isNotBlank(request.getContactPhone())) {
            giftCard.setContactType(request.getContactType());
            giftCard.setContactPhone(request.getContactPhone());
        }
        // 修改使用说明
        if (StringUtils.isNotBlank(request.getUseDesc())) {
            giftCard.setUseDesc(request.getUseDesc());
        }
        giftCard.setUpdatePerson(request.getUserId());
        giftCard.setUpdateTime(LocalDateTime.now());
        giftCardRepository.save(giftCard);
    }

    /**
     * @description   礼品卡删除
     * @author  wur
     * @date: 2022/12/9 11:09
     * @param request
     * @return
     **/
    public void delGiftCard(GiftCardDeleteRequest request) {
        Optional<GiftCard> opt = giftCardRepository.findById(request.getGiftCardId());
        if (!opt.isPresent()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        GiftCard giftCard = opt.get();
        if (Objects.isNull(giftCard)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        giftCard.setDelFlag(DeleteFlag.YES);
        giftCard.setUpdatePerson(request.getUserId());
        giftCard.setUpdateTime(LocalDateTime.now());
        giftCardRepository.save(giftCard);
    }

    /**
     * @description 查询礼品卡详情
     * @author wur
     * @date: 2022/12/9 15:27
     * @param request
     * @return
     */
    public GiftCardVO queryGiftCardInfo(GiftCardInfoRequest request) {
        Optional<GiftCard> opt = giftCardRepository.findById(request.getGiftCardId());
        if (!opt.isPresent()) {
            return null;
        }
        GiftCard giftCard = opt.get();
        if (Objects.isNull(giftCard) || Objects.equals(DeleteFlag.YES, giftCard.getDelFlag())) {
            return null;
        }
        GiftCardVO giftCardVO = new GiftCardVO();
        BeanUtils.copyProperties(giftCard, giftCardVO);
        if (!Objects.equals(GiftCardScopeType.ALL, giftCard.getScopeType())) {
            List<GiftCardScope> scopeList =
                    giftCardScopeRepository.queryListByGiftCardId(giftCard.getGiftCardId());
            if (CollectionUtils.isNotEmpty(scopeList)) {
                giftCardVO.setScopeIdList(
                        scopeList.stream()
                                .map(GiftCardScope::getScopeId)
                                .collect(Collectors.toList()));
            }
        }
        return giftCardVO;
    }

    /**
     * @description 查询礼品卡详情
     * @author wur
     * @date: 2022/12/9 15:27
     * @param request
     * @return
     */
    public GiftCardVO queryGiftCardDetail(GiftCardInfoRequest request) {
        Optional<GiftCard> opt = giftCardRepository.findById(request.getGiftCardId());
        if (!opt.isPresent()) {
            return null;
        }
        GiftCard giftCard = opt.get();
        if (Objects.isNull(giftCard)) {
            return null;
        }
        GiftCardVO giftCardVO = new GiftCardVO();
        BeanUtils.copyProperties(giftCard, giftCardVO);
        if (!Objects.equals(GiftCardScopeType.ALL, giftCard.getScopeType())) {
            List<GiftCardScope> scopeList =
                    giftCardScopeRepository.queryListByGiftCardId(giftCard.getGiftCardId());
            if (CollectionUtils.isNotEmpty(scopeList)) {
                giftCardVO.setScopeIdList(
                        scopeList.stream()
                                .map(GiftCardScope::getScopeId)
                                .collect(Collectors.toList()));
            }
        }
        return giftCardVO;
    }

    /**
     * @description
     * @author  wur
     * @date: 2022/12/8 20:32
     * @param request
     * @return
     **/
    public MicroServicePage<GiftCardVO> queryPage(GiftCardPageRequest request) {
        Page<GiftCard> pickupSettingPage = giftCardRepository.findAll(
                GiftCardWhereCriteriaBuilder.build(request),
                request.getPageRequest());
        Page<GiftCardVO> newPage = pickupSettingPage.map(entity -> this.wrapperVo(entity));
        List<Long> idList =newPage.getContent().stream()
                .filter(g -> Objects.nonNull(g.getScopeGoodsNum()) && g.getScopeGoodsNum() == -99)
                .map(GiftCardVO::getGiftCardId)
                .collect(Collectors.toList());
        Map<Long, List<GiftCardScope>> giftCardIdToScopeMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(idList)){
            List<GiftCardScope> giftCardScopeList = giftCardScopeRepository.queryListByGiftCardIdIn(idList);
            giftCardIdToScopeMap =
                    giftCardScopeList.stream().collect(Collectors.groupingBy(GiftCardScope::getGiftCardId));
        }
        Map<Long, List<GiftCardScope>> finalGiftCardIdToScopeMap = giftCardIdToScopeMap;
        newPage.getContent().stream().forEach(giftCardVO -> {
            if(giftCardVO.getGiftCardType() == GiftCardType.PICKUP_CARD &&
                    Objects.nonNull(finalGiftCardIdToScopeMap.get(giftCardVO.getGiftCardId()))){
                giftCardVO.setTotalGoodsNum(finalGiftCardIdToScopeMap.get(giftCardVO.getGiftCardId()).size());
            }
        });
        MicroServicePage<GiftCardVO> microPage = new MicroServicePage<>(newPage, newPage.getPageable());
        return microPage;
    }

    /**
     * @description   将实体包装成VO
     * @author  wur
     * @date: 2022/12/12 10:17
     * @param giftCard
     * @return
     **/
    public GiftCardVO wrapperVo(GiftCard giftCard) {
        if (giftCard != null) {
            GiftCardVO giftCardVO = KsBeanUtil.convert(giftCard, GiftCardVO.class);
            return giftCardVO;
        }
        return null;
    }

    /**
     * @description 针对批量发卡，校验并获取礼品卡信息
     * @author malianfeng
     * @date 2022/12/19 10:43
     * @param giftCardId 礼品卡id
     * @return com.wanmi.sbc.marketing.giftcard.model.root.GiftCard
     */
    public GiftCard checkAndGetForBatchSend(Long giftCardId) {
        // 1. 查询礼品卡信息
        GiftCard giftCard = giftCardRepository.findByGiftCardIdAndDelFlag(giftCardId, DeleteFlag.NO);
        if (Objects.isNull(giftCard)) {
            // 礼品卡不存在
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        // 2. 校验过期，仅校验 指定具体时间 > 当前时间
        if (ExpirationType.SPECIFIC_TIME == giftCard.getExpirationType()
                && Objects.nonNull(giftCard.getExpirationTime())
                && giftCard.getExpirationTime().isBefore(LocalDateTime.now())) {
            // 礼品卡已过期
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080033);
        }
        return giftCard;
    }
    public GiftCard checkAndGetForBatchSendByName(String name) {
        // 1. 查询礼品卡信息
        GiftCard giftCard = giftCardRepository.findByNameAndDelFlag(name, DeleteFlag.NO);
        if (Objects.isNull(giftCard)) {
            // 礼品卡不存在
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        // 2. 校验过期，仅校验 指定具体时间 > 当前时间
        if (ExpirationType.SPECIFIC_TIME == giftCard.getExpirationType()
                && Objects.nonNull(giftCard.getExpirationTime())
                && giftCard.getExpirationTime().isBefore(LocalDateTime.now())) {
            // 礼品卡已过期
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080033);
        }
        return giftCard;
    }

    /**
     * @description 查询指定id礼品卡列表
     * @author malianfeng
     * @date 2022/12/16 12:30
     * @param giftCardIdList 礼品卡id列表
     * @return java.util.List<com.wanmi.sbc.marketing.giftcard.model.root.GiftCard>
     */
    public List<GiftCard> findByGiftCardIdList(List<Long> giftCardIdList) {
        if (CollectionUtils.isEmpty(giftCardIdList)) {
            return Collections.emptyList();
        }
        return giftCardRepository.findAllById(giftCardIdList);
    }
}