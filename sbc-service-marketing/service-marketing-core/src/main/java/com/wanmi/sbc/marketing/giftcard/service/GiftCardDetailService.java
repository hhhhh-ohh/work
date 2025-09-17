package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardCancelRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailQueryRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailJoinVO;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailVO;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBill;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardDetail;
import com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard;
import com.wanmi.sbc.marketing.giftcard.repository.GiftCardBillRepository;
import com.wanmi.sbc.marketing.giftcard.repository.GiftCardDetailRepository;
import com.wanmi.sbc.marketing.giftcard.repository.UserGiftCardRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>礼品卡详情业务逻辑</p>
 * @author 马连峰
 * @date 2022-12-09 14:08:26
 */
@Service("GiftCardDetailService")
public class GiftCardDetailService {
	@Autowired
	private GiftCardDetailRepository giftCardDetailRepository;

	@Autowired
	private UserGiftCardRepository userGiftCardRepository;

	@Autowired
	private GiftCardBillRepository giftCardBillRepository;

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private EntityManager entityManager;

	/**
	 * 根据礼品卡卡号查询详情
	 * @author 马连峰
	 */
	public GiftCardDetail findByGiftCardNo(String giftCardNo) {
		return giftCardDetailRepository.findById(giftCardNo).orElse(null);
	}

	/**
	 * 新增礼品卡详情
	 * @author 马连峰
	 */
	@Transactional
	public GiftCardDetail add(GiftCardDetail entity) {
		giftCardDetailRepository.save(entity);
		return entity;
	}

	/**
	 * 修改礼品卡详情
	 * @author 马连峰
	 */
	@Transactional
	public GiftCardDetail modify(GiftCardDetail entity) {
		giftCardDetailRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除礼品卡详情
	 * @author 马连峰
	 */
	@Transactional
	public void deleteById(GiftCardDetail entity) {
		giftCardDetailRepository.save(entity);
	}

	/**
	 * 批量删除礼品卡详情
	 * @author 马连峰
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		giftCardDetailRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询礼品卡详情
	 * @author 马连峰
	 */
	public GiftCardDetail getOne(String id){
		return giftCardDetailRepository.findByGiftCardNoAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "礼品卡详情不存在"));
	}

	/**
	 * 分页查询礼品卡详情
	 * @author 马连峰
	 */
	public Page<GiftCardDetail> page(GiftCardDetailQueryRequest queryReq){
		return giftCardDetailRepository.findAll(
				GiftCardDetailWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询礼品卡详情
	 * @author 马连峰
	 */
	public List<GiftCardDetail> list(GiftCardDetailQueryRequest queryReq){
		return giftCardDetailRepository.findAll(GiftCardDetailWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 马连峰
	 */
	public GiftCardDetailVO wrapperVo(GiftCardDetail giftCardDetail) {
		if (giftCardDetail != null){
			GiftCardDetailVO giftCardDetailVO = KsBeanUtil.convert(giftCardDetail, GiftCardDetailVO.class);
			return giftCardDetailVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 马连峰
	 */
	public Long count(GiftCardDetailQueryRequest queryReq) {
		return giftCardDetailRepository.count(GiftCardDetailWhereCriteriaBuilder.build(queryReq));
	}

	@Transactional(rollbackFor = Exception.class)
	public void cancelCard(GiftCardCancelRequest request ,String giftCardNo){
		String lock = CacheKeyConstant.MARKETING_GIFT_CARD_NO_LOCK+giftCardNo;
		RLock rLock = redissonClient.getFairLock(lock);
		rLock.lock();
		try{
			//获取礼品卡详情
			Optional<GiftCardDetail>  giftCardDetailOpt = giftCardDetailRepository.findByGiftCardNoAndDelFlag(giftCardNo,DeleteFlag.NO);
			if(!giftCardDetailOpt.isPresent()){
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
			}
			GiftCardDetail giftCardDetail = giftCardDetailOpt.get();
			// 记录原始卡状态
			GiftCardDetailStatus originalDetailStatus = giftCardDetail.getCardDetailStatus();
			// 卡来源
			GiftCardSourceType sourceType = giftCardDetail.getSourceType();
			//判断礼品卡状态是否为已销卡
			if(GiftCardDetailStatus.CANCELED == giftCardDetail.getCardDetailStatus()){
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080042);
			}

            // 发卡失败的礼品卡，不允许销卡
            if (GiftCardSourceType.SEND == sourceType && GiftCardSendStatus.FAILED == giftCardDetail.getSendStatus()) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080038);
			}

			//将礼品卡详情更新，状态为已销卡
			giftCardDetail.setCancelPerson(request.getCancelPerson());
			giftCardDetail.setCancelTime(LocalDateTime.now());
			giftCardDetail.setCardDetailStatus(GiftCardDetailStatus.CANCELED);
			giftCardDetail.setStatusReason(request.getCancelDesc());
			giftCardDetailRepository.save(giftCardDetail);
			//增加礼品卡使用记录--销卡
			GiftCardBill giftCardBill = new GiftCardBill();
			giftCardBill.setCustomerId(null);
			giftCardBill.setGiftCardId(giftCardDetail.getGiftCardId());
			giftCardBill.setUserGiftCardId(null);
			giftCardBill.setGiftCardNo(giftCardDetail.getGiftCardNo());
			giftCardBill.setTradeBalance(BigDecimal.ZERO);
			giftCardBill.setBeforeBalance(BigDecimal.ZERO);
			giftCardBill.setAfterBalance(BigDecimal.ZERO);
			giftCardBill.setBusinessId(null);
			giftCardBill.setBusinessType(GiftCardBusinessType.CANCEL_CARD);
			giftCardBill.setTradeTime(LocalDateTime.now());
			if (Objects.nonNull(giftCardDetail.getBelongPerson())) {
				giftCardBill.setTradePerson(giftCardDetail.getBelongPerson());
				giftCardBill.setTradePersonType(DefaultFlag.NO);
			}
			if(GiftCardDetailStatus.NOT_EXCHANGE != originalDetailStatus){
				//处理会员礼品卡销卡逻辑
				List<UserGiftCard> userGiftCardList = userGiftCardRepository.findAll(
						UserGiftCardWhereCriteriaBuilder.build(
								UserGiftCardQueryRequest.builder().giftCardNo(giftCardNo).build()));
				if (CollectionUtils.isEmpty(userGiftCardList)) {
					throw new SbcRuntimeException(MarketingErrorCodeEnum.K080043);
				}
				//有可能一张卡可能会出现在很多人名下，获取到最后获取的该卡的卡记录
				Optional<UserGiftCard> userGiftCardMaxAcquireTimeOpt =
						userGiftCardList.stream().filter(userGiftCardInfo -> userGiftCardInfo.getAcquireTime() != null)
						.max(Comparator.comparing(UserGiftCard::getAcquireTime));
				if (userGiftCardMaxAcquireTimeOpt.isPresent()) {
					UserGiftCard userGiftCard = userGiftCardMaxAcquireTimeOpt.get();
					//更新会员礼品卡详情状态为已销卡
					BigDecimal balance = userGiftCard.getBalance();
					userGiftCard.setCancelBalance(balance);
					userGiftCard.setBalance(BigDecimal.ZERO);
					userGiftCard.setCardStatus(GiftCardStatus.CANCELED);
					userGiftCard.setCancelPerson(request.getCancelPerson());
					userGiftCard.setCancelDesc(request.getCancelDesc());
					userGiftCardRepository.save(userGiftCard);
					giftCardBill.setCustomerId(userGiftCard.getBelongPerson());
					giftCardBill.setUserGiftCardId(userGiftCard.getUserGiftCardId());
					giftCardBill.setGiftCardNo(userGiftCard.getGiftCardNo());
					giftCardBill.setTradeBalance(balance);
					giftCardBill.setBeforeBalance(balance);
				}
			}
			giftCardBillRepository.save(giftCardBill);
		} catch (Exception e){
			throw e;
		} finally {
			rLock.unlock();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void batchInsert(List<GiftCardDetail> infos) {
		if(CollectionUtils.isEmpty(infos)){
			return;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("insert INTO gift_card_detail(gift_card_no,gift_card_id,gift_card_type,batch_no,source_type,exchange_code,expiration_time,acquire_time,activation_time,belong_person,activation_person,card_detail_status,cancel_person,cancel_time,send_status,status_reason,del_flag,create_time,create_person,update_time,update_person) values");
		String sqlValue = " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			GiftCardDetail item = infos.get(i);
			query.setParameter(paramIndex++, item.getGiftCardNo());
			query.setParameter(paramIndex++, item.getGiftCardId());
			query.setParameter(paramIndex++, item.getGiftCardType().toValue());
			query.setParameter(paramIndex++, item.getBatchNo());
			query.setParameter(paramIndex++, Objects.nonNull(item.getSourceType()) ? item.getSourceType().toValue() : null);
			query.setParameter(paramIndex++, item.getExchangeCode());
			query.setParameter(paramIndex++, item.getExpirationTime());
			query.setParameter(paramIndex++, item.getAcquireTime());
			query.setParameter(paramIndex++, item.getActivationTime());
			query.setParameter(paramIndex++, item.getBelongPerson());
			query.setParameter(paramIndex++, item.getActivationPerson());
			query.setParameter(paramIndex++, Objects.nonNull(item.getCardDetailStatus()) ? item.getCardDetailStatus().toValue() : null);
			query.setParameter(paramIndex++, item.getCancelPerson());
			query.setParameter(paramIndex++, item.getCancelTime());
			query.setParameter(paramIndex++, Objects.nonNull(item.getSendStatus()) ? item.getSendStatus().toValue() : null);
			query.setParameter(paramIndex++, item.getStatusReason());
			query.setParameter(paramIndex++, Objects.nonNull(item.getDelFlag()) ? item.getDelFlag().toValue() : null);
			query.setParameter(paramIndex++, item.getCreateTime());
			query.setParameter(paramIndex++, item.getCreatePerson());
			query.setParameter(paramIndex++, item.getUpdateTime());
			query.setParameter(paramIndex++, item.getUpdatePerson());
		}
		query.executeUpdate();
	}


	/**
	 * 分页查询单卡明细
	 * @param request
	 * @return
	 */
	public Page<GiftCardDetailJoinVO> getGiftCardDetailPage(GiftCardDetailJoinWhereCriteriaBuilder request) {
		//分页查询单卡明细
		Query query = entityManager.createNativeQuery(request.getQuerySql().concat(request.getQueryConditionSql()).concat(request.getQuerySort()));
		request.wrapperQueryParam(query);
		query.setFirstResult(request.getPageNum() * request.getPageSize());
		query.setMaxResults(request.getPageSize());
		query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 查询单卡明细列表
		List<GiftCardDetailJoinVO> giftCardDetailJoinVOS = GiftCardDetailJoinWhereCriteriaBuilder.converter(query.getResultList());

		//查询单卡明细列表总数
		Query totalCountRes =
				entityManager.createNativeQuery(request.getQueryTotalCountSql().concat(request.getQueryConditionSql()).concat(request.getQueryTotalTemp()));
		request.wrapperQueryParam(totalCountRes);
		long totalCount = Long.parseLong(totalCountRes.getSingleResult().toString());

		return new PageImpl<>(giftCardDetailJoinVOS, request.getPageable(), totalCount);
	}
}

