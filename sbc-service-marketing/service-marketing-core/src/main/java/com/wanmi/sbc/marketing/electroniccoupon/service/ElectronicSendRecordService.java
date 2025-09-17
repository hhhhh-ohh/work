package com.wanmi.sbc.marketing.electroniccoupon.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardQueryRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponQueryRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicSendRecordQueryRequest;
import com.wanmi.sbc.marketing.bean.dto.ElectronicCardSimpleDTO;
import com.wanmi.sbc.marketing.bean.dto.ElectronicSendRecordAddDTO;
import com.wanmi.sbc.marketing.bean.dto.ElectronicSendRecordNumDTO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import com.wanmi.sbc.marketing.bean.vo.ElectronicSendRecordVO;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCard;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCoupon;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicSendRecord;
import com.wanmi.sbc.marketing.electroniccoupon.repository.ElectronicSendRecordRepository;
import com.wanmi.sbc.marketing.electroniccoupon.service.criteria.ElectronicSendRecordWhereCriteriaBuilder;

import io.seata.common.util.CollectionUtils;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>卡密发放记录表业务逻辑</p>
 * @author 许云鹏
 * @date 2022-01-26 17:37:31
 */
@Service("ElectronicSendRecordService")
public class ElectronicSendRecordService {
	@Autowired
	private ElectronicSendRecordRepository electronicSendRecordRepository;

	@Autowired
	private ElectronicCardService electronicCardService;

	@Autowired
	private ElectronicCouponService electronicCouponService;

	/**
	 * 新增卡密发放记录表
	 * @author 许云鹏
	 */
	@Transactional
	public List<ElectronicCard> batchAdd(List<ElectronicSendRecordAddDTO> dtoList) {
		//查询已过销售期的卡券
		List<Long> couponIds = dtoList.stream().map(ElectronicSendRecordAddDTO::getCouponId).distinct().collect(Collectors.toList());
		List<Long> expiredCouponIds = electronicCardService.listBySaleType(couponIds, CardSaleState.EXPIRED.toValue());
		//每个卡券需要发放的卡密数量：按照卡券分组再对数量求和
		Map<Long, Long> numMap = dtoList.stream()
				.filter(dto -> !expiredCouponIds.contains(dto.getCouponId()))
				.collect(Collectors.groupingBy(
				ElectronicSendRecordAddDTO::getCouponId,
				Collectors.reducing(0L, ElectronicSendRecordAddDTO::getNum, Long::sum)));
		Map<Long, ElectronicCoupon> couponMap = electronicCouponService.list(
				ElectronicCouponQueryRequest.builder().idList(couponIds).delFlag(DeleteFlag.NO).build())
				.stream().collect(Collectors.toMap(ElectronicCoupon::getId, Function.identity()));

		//获取未发放的卡密
		String orderNo = dtoList.get(Constants.ZERO).getOrderNo();
		Long couponId = dtoList.get(Constants.ZERO).getCouponId();
		Long num = dtoList.get(Constants.ZERO).getNum();
		List<ElectronicCard> cards = electronicCardService.findByOrderNoAndCouponId(orderNo, couponId);
		// 补充卡密扣减库存标识
		Boolean subStockFlag = Boolean.FALSE;
		// 预占卡密数量和下单数不一致，需补充数量
		if (cards.size() != num) {
			subStockFlag = Boolean.TRUE;
			if (CollectionUtils.isNotEmpty(cards)) {
				Map<Long, Long> orderCardNumMap = cards.stream().collect(
						Collectors.groupingBy(ElectronicCard::getCouponId, Collectors.counting()));
				numMap.forEach((k,v) -> {
					Long cardNum = orderCardNumMap.getOrDefault(k, NumberUtils.LONG_ZERO);
					numMap.put(k, v - cardNum);
				});
			}
			List<ElectronicCard> newCards = this.getCards(numMap, Boolean.FALSE);
			if (CollectionUtils.isNotEmpty(newCards)) {
				cards.addAll(newCards);
				subStockFlag = Boolean.TRUE;
			}
		}
		List<String> cardIds = cards.stream().map(ElectronicCard::getId).collect(Collectors.toList());
		Map<Long, List<ElectronicCard>> cardMap = cards.stream().collect(Collectors.groupingBy(ElectronicCard::getCouponId));

		//生成发放记录
		List<ElectronicSendRecord> records = new ArrayList<>();
		dtoList.forEach(dto -> {
			List<ElectronicCard> electronicCards = cardMap.get(dto.getCouponId());
			//该商品购买了num件，就生成num条记录
			for (int i = 0; i < dto.getNum(); i++) {
				ElectronicSendRecord record = this.getSendData(null, dto, couponMap, electronicCards, expiredCouponIds);
				records.add(record);
			}
		});
		this.saveForSend(cardIds, records);
		return cards;
	}

	/**
	 * 重复
	 * @param ids
	 * @return
	 */
	public Map<String, List<ElectronicCardVO>> sendAgain(List<String> ids) {
		List<ElectronicSendRecord> records = list(ElectronicSendRecordQueryRequest
				.builder().idList(ids).delFlag(DeleteFlag.NO).build());
		List<ElectronicSendRecord> sendRecordList = records.stream()
				.filter(record -> record.getSendState() == CardSendState.SUCCESS.toValue())
				.collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(sendRecordList)) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080118);
		}
		//查询已过销售期的卡券
		List<Long> couponIds = records.stream()
				.map(ElectronicSendRecord::getCouponId)
				.distinct()
				.collect(Collectors.toList());
		List<Long> expiredCouponIds = electronicCardService.listBySaleType(couponIds, CardSaleState.EXPIRED.toValue());

		//每个卡券需要发放的卡密数量：按照卡券分组再统计数量
		Map<Long, Long> numMap = records.stream()
				.filter(dto -> !expiredCouponIds.contains(dto.getCouponId()))
				.collect(Collectors.groupingBy(
						ElectronicSendRecord::getCouponId,
						Collectors.counting()));
		Map<Long, ElectronicCoupon> couponMap = electronicCouponService.list(
				ElectronicCouponQueryRequest.builder().idList(couponIds).delFlag(DeleteFlag.NO).build())
				.stream().collect(Collectors.toMap(ElectronicCoupon::getId, Function.identity()));

		//获取未发放的卡密
		List<ElectronicCard> cards = this.getCards(numMap, Boolean.FALSE);

		List<String> cardIds = cards.stream().map(ElectronicCard::getId).collect(Collectors.toList());
		Map<Long, List<ElectronicCard>> cardMap = cards
				.stream()
				.collect(Collectors.groupingBy(ElectronicCard::getCouponId));

		records.forEach(record -> {
			List<ElectronicCard> electronicCards = cardMap.get(record.getCouponId());
			//组装数据
			if (CollectionUtils.isNotEmpty(electronicCards)) {
				electronicCards = KsBeanUtil.convert(electronicCards, ElectronicCard.class);
			}
			//组装数据
			this.getSendData(record, null, couponMap, electronicCards, expiredCouponIds);
		});
		//批量修改保存
		this.saveForSend(cardIds, records);
		//按订单分组卡密数据
		Map<String, List<ElectronicSendRecord>> tradeRecordMap = records.stream()
				.filter(record -> CardSendState.SUCCESS.toValue() == record.getSendState())
				.collect(Collectors.groupingBy(ElectronicSendRecord::getOrderNo));
		Map<String, ElectronicCard> electronicCardMap = cards.stream().collect(Collectors.toMap(ElectronicCard::getId, Function.identity()));
		Map<String, List<ElectronicCardVO>> returnMap = new HashMap<>();
		tradeRecordMap.forEach((k,v) -> {
			List<ElectronicCardVO> cardList = v.stream()
					.map(record -> electronicCardService.wrapperVo(electronicCardMap.get(record.getCardId())))
					.collect(Collectors.toList());
			returnMap.put(k, cardList);
		});

		return returnMap;
	}


	/**
	 * 保存发放数据
	 * @param cardIds
	 * @param records
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveForSend(List<String> cardIds, List<ElectronicSendRecord> records) {
		//批量修改卡密状态未已发送
		if (CollectionUtils.isNotEmpty(cardIds)) {
			electronicCardService.updateCardState(cardIds, CardState.SEND);
		}
		//保存发放记录
		electronicSendRecordRepository.saveAll(records);
	}

	/**
	 * 获取卡密
	 * @param numMap
	 * @return
	 */
	public List<ElectronicCard> getCards(Map<Long, Long> numMap, Boolean orderBindFlag) {
		List<ElectronicCard> cards = new ArrayList<>();
		numMap.forEach((k,v) -> {
			//查询未发放的卡券
			ElectronicCardQueryRequest queryRequest = new ElectronicCardQueryRequest();
			queryRequest.setDelFlag(DeleteFlag.NO);
			queryRequest.setCardState(CardState.NOT_SEND.toValue());
			queryRequest.setCouponId(k);
			queryRequest.putSort("saleEndTime","asc");
			queryRequest.setPageNum(0);
			queryRequest.setPageSize(Integer.parseInt(v.toString()));
			queryRequest.setOrderBindFlag(orderBindFlag);
			List<ElectronicCard> electronicCards = electronicCardService.page(queryRequest).getContent();
			if (CollectionUtils.isNotEmpty(electronicCards)) {
				cards.addAll(electronicCards);
			}
		});
		return cards;
	}

	/**
	 * 组装发放记录数据
	 * @param record
	 * @param dto
	 * @param couponMap
	 * @param electronicCards
	 * @param expiredCouponIds
	 * @return
	 */
	public ElectronicSendRecord getSendData(ElectronicSendRecord record, ElectronicSendRecordAddDTO dto,
											Map<Long, ElectronicCoupon> couponMap,
											List<ElectronicCard> electronicCards, List<Long> expiredCouponIds) {
		//首次发放无record，重发时有record
		record = Objects.nonNull(record) ? record : KsBeanUtil.convert(dto, ElectronicSendRecord.class);
		//卡券
		ElectronicCoupon coupon = couponMap.get(record.getCouponId());
		//卡密
		ElectronicCard electronicCard = null;
		if (CollectionUtils.isNotEmpty(electronicCards)) {
			electronicCard = electronicCards.get(0);
		}

		//设置数据
		record.setDelFlag(DeleteFlag.NO);
		record.setSendTime(LocalDateTime.now());
		if (coupon != null && electronicCard != null) {
			ElectronicCardSimpleDTO simpleDTO = KsBeanUtil.convert(electronicCard, ElectronicCardSimpleDTO.class);
			record.setCouponName(coupon.getCouponName());
			record.setCardContent(JSON.toJSONString(simpleDTO));
			record.setCardId(electronicCard.getId());
			record.setSendState(CardSendState.SUCCESS.toValue());
			record.setFailReason(null);
			//生成记录成功则从list中移除该卡密
			electronicCards.remove(0);
		} else if(coupon != null) {
			record.setCouponName(coupon.getCouponName());
			if (expiredCouponIds.contains(record.getCouponId())) {
				record.setFailReason(CardFailReason.OVERDUE.toValue());
				record.setSendState(CardSendState.FAIL.toValue());
			} else {
				record.setFailReason(CardFailReason.NO_STOCK.toValue());
				record.setSendState(CardSendState.FAIL.toValue());
			}
		} else {
			record.setFailReason(CardFailReason.OTHER.toValue());
			record.setSendState(CardSendState.FAIL.toValue());
		}
		return record;
	}

	/**
	 * 分页查询卡密发放记录表
	 * @author 许云鹏
	 */
	public Page<ElectronicSendRecord> page(ElectronicSendRecordQueryRequest queryReq){
		return electronicSendRecordRepository.findAll(
				ElectronicSendRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询卡密发放记录表
	 * @author 许云鹏
	 */
	public List<ElectronicSendRecord> list(ElectronicSendRecordQueryRequest queryReq){
		return electronicSendRecordRepository.findAll(ElectronicSendRecordWhereCriteriaBuilder.build(queryReq));
	}
	/**
	 * 将实体包装成VO
	 * @author 许云鹏
	 */
	public ElectronicSendRecordVO wrapperVo(ElectronicSendRecord electronicSendRecord) {
		if (electronicSendRecord != null){
			ElectronicSendRecordVO electronicSendRecordVO = KsBeanUtil.convert(electronicSendRecord, ElectronicSendRecordVO.class);
			return electronicSendRecordVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 许云鹏
	 */
	public Long count(ElectronicSendRecordQueryRequest queryReq) {
		return electronicSendRecordRepository.count(ElectronicSendRecordWhereCriteriaBuilder.build(queryReq));
	}


	/**
	 * 查询卡券发放次数
	 * @param list
	 * @return
	 */
	public Map<String, Long> countGoodsSendNum(List<ElectronicSendRecordNumDTO> list) {
		Map<String, Long> sendMap = new HashMap<>();
		list.forEach( dto -> {
			Long count = count(ElectronicSendRecordQueryRequest.builder()
					.skuNo(dto.getSkuNo())
					.couponId(dto.getCouponId())
					.sendState(CardSendState.SUCCESS.toValue()).build());
			sendMap.put(dto.getSkuNo(), count);
		});
		return sendMap;
	}
}

