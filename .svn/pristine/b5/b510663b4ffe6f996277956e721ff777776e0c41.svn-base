package com.wanmi.sbc.marketing.electroniccoupon.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsCheckBindRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoBatchMinusStockRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMinusStockDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardQueryRequest;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicImportCheckResponse;
import com.wanmi.sbc.marketing.bean.constant.EletronicErrorCode;
import com.wanmi.sbc.marketing.bean.enums.CardSaleState;
import com.wanmi.sbc.marketing.bean.enums.CardState;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCard;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCoupon;
import com.wanmi.sbc.marketing.electroniccoupon.repository.ElectronicCardRepository;
import com.wanmi.sbc.marketing.electroniccoupon.repository.ElectronicCouponRepository;
import com.wanmi.sbc.marketing.electroniccoupon.service.criteria.ElectronicCardWhereCriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>电子卡密表业务逻辑</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Slf4j
@Service("ElectronicCardService")
public class ElectronicCardService {
	@Autowired
	private ElectronicCardRepository electronicCardRepository;

	@Autowired
	private GoodsQueryProvider goodsQueryProvider;

	@Autowired
	private GoodsInfoProvider goodsInfoProvider;

	@Autowired
	private ElectronicCouponRepository electronicCouponRepository;

	@Autowired
	private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

	/**
	 * 新增电子卡密表
	 * @author 许云鹏
	 */
	@Transactional
	public ElectronicCard add(ElectronicCard entity) {
		entity.setDelFlag(DeleteFlag.NO);
		electronicCardRepository.save(entity);
		return entity;
	}

	/**
	 * 批量新增电子卡密表
	 * @author 许云鹏
	 */
	@Transactional
	public void saveAll(List<ElectronicCard> entitys) {
		electronicCardRepository.saveAll(entitys);
	}

	/**
	 * 修改电子卡密表
	 * @author 许云鹏
	 */
	@Transactional
	public ElectronicCard modify(ElectronicCard entity, Long baseStoreId) {
		ElectronicCard oldCard = getOne(entity.getId());
		ElectronicCoupon coupon = electronicCouponRepository.getOne(oldCard.getCouponId());
		if (!Objects.equals(baseStoreId,coupon.getStoreId())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
		}

		//已发送卡密不支持修改
		if (oldCard.getCardState() == CardState.SEND.toValue()) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080114);
		}
		//卡号、卡密、优惠码至少填一个
		if (StringUtils.isBlank(entity.getCardNumber()) &&
				StringUtils.isBlank(entity.getCardPassword()) &&
				StringUtils.isBlank(entity.getCardPromoCode())) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080113);
		}
		//卡号重复性校验
		if (StringUtils.isNotBlank(entity.getCardNumber())) {
			int count = electronicCardRepository.countByCouponIdAndCardNumberAndDelFlagAndIdNot(
					oldCard.getCouponId(), entity.getCardNumber(), DeleteFlag.NO, entity.getId());
			if (count > 0) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080115);
			}
		}
		//卡密重复性校验
		if (StringUtils.isNotBlank(entity.getCardPassword())) {
			int count = electronicCardRepository.countByCouponIdAndCardPasswordAndDelFlagAndIdNot(
					oldCard.getCouponId(), entity.getCardPassword(), DeleteFlag.NO, entity.getId());
			if (count > 0) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080116);
			}
		}
		//优惠码重复性校验
		if (StringUtils.isNotBlank(entity.getCardPromoCode())) {
			int count = electronicCardRepository.countByCouponIdAndCardPromoCodeAndDelFlagAndIdNot(
					oldCard.getCouponId(), entity.getCardPromoCode(), DeleteFlag.NO, entity.getId());
			if(count > 0) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080117);
			}
		}

		oldCard.setCardNumber(entity.getCardNumber());
		oldCard.setCardPassword(entity.getCardPassword());
		oldCard.setCardPromoCode(entity.getCardPromoCode());
		electronicCardRepository.save(oldCard);
		return oldCard;
	}

	/**
	 * 批量删除电子卡密表
	 * @author 许云鹏
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteByIdList(List<String> ids, Long baseStoreId) {
		List<String> errIds = electronicCardRepository.findByIdListNotSend(ids);
		if (CollectionUtils.isNotEmpty(errIds)) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080112);
		}
		List<ElectronicCard> cards = electronicCardRepository.findAllById(ids);
		if (CollectionUtils.isEmpty(cards)) {
			new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		ElectronicCard electronicCard = cards.get(Constants.ZERO);
		ElectronicCoupon electronicCoupon = electronicCouponRepository.findByIdAndDelFlag(electronicCard.getCouponId(), DeleteFlag.NO);
		if (Objects.nonNull(electronicCoupon) && !Objects.equals(baseStoreId,electronicCoupon.getStoreId())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
		}
		electronicCardRepository.deleteByIdList(ids);
		long count = cards.stream().filter(c -> StringUtils.isBlank(c.getOrderNo())).count();
		minusStock(electronicCard.getCouponId(), count);
	}

	/**
	 * 根据卡券或批次批量删除电子卡密表
	 * @author 许云鹏
	 */
	@Transactional
	public void deleteAll(Long couponId, String recordId) {
		Long count = countEffectiveCoupon(couponId,  LocalDate.now().atTime(LocalDateTime.now().getHour(), 0, 0));
		if (StringUtils.isNotBlank(recordId)) {
			electronicCardRepository.deleteByRecordId(recordId);
		}else if (couponId != null) {
			electronicCardRepository.deleteByCouponId(couponId);
		}
		//减掉所有库存
		minusStock(couponId,count);
	}

	/**
	 * 单个查询电子卡密表
	 * @author 许云鹏
	 */
	public ElectronicCard getOne(String id){
		return electronicCardRepository.findByIdAndDelFlag(id, DeleteFlag.NO);
	}

	/**
	 * 分页查询电子卡密表
	 * @author 许云鹏
	 */
	public Page<ElectronicCard> page(ElectronicCardQueryRequest queryReq){
		return electronicCardRepository.findAll(
				ElectronicCardWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 将实体包装成VO
	 * @author 许云鹏
	 */
	public ElectronicCardVO wrapperVo(ElectronicCard electronicCard) {
		if (electronicCard != null){
			ElectronicCardVO electronicCardVO = KsBeanUtil.convert(electronicCard, ElectronicCardVO.class);
			return electronicCardVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 许云鹏
	 */
	public Long count(ElectronicCardQueryRequest queryReq) {
		return electronicCardRepository.count(ElectronicCardWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 校验重复数据
	 * @param numbers
	 * @param passwords
	 * @param codes
	 * @return
	 */
	public ElectronicImportCheckResponse getExistsData(List<String> numbers, List<String> passwords, List<String> codes, Long couponId) {
		List<String> existsCardNumber = electronicCardRepository.findExistsCardNumber(numbers, couponId);
		List<String> existsCardPassword = electronicCardRepository.findExistsCardPassword(passwords, couponId);
		List<String> existsCardPromoCode = electronicCardRepository.findExistsCardPromoCode(codes, couponId);
		return ElectronicImportCheckResponse.builder()
				.numbers(existsCardNumber)
				.passwords(existsCardPassword)
				.codes(existsCardPromoCode)
				.build();
	}

	/**
	 * 批量修改状态未已失效
	 * @param time
	 */
	@Transactional
	public void updateCardInvalid(LocalDateTime time){
		electronicCardRepository.updateCardInvalid(time);
	}

	/**
	 * 根据卡券id批量修改状态未已失效
	 * @param time
	 */
	@Transactional
	public Long countCardInvalidByCouponId(Long couponId,LocalDateTime time){
		return electronicCardRepository.countCardInvalidByCouponId(couponId,time);
	}

	/**
	 * 根据销售期类型查询卡券id
	 * @param couponIds
	 * @param saleType
	 * @return
	 */
	public List<Long> listBySaleType(List<Long> couponIds, Integer saleType) {
		LocalDateTime now = LocalDateTime.now();
		if (NumberUtils.INTEGER_ZERO.equals(saleType)) {
			return electronicCardRepository.findSaleCoupon(couponIds, now);
		} else {
			return electronicCardRepository.findOverdueCoupon(couponIds, now);
		}
	}

	/**
	 * 获取卡券销售状态
	 * @param ids
	 * @return
	 */
	public Map<Long, CardSaleState> getSaleStateMap(List<Long> ids) {
		Map<Long, CardSaleState> saleStateMap = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();
		ids.forEach(id -> {
			Long count = count(ElectronicCardQueryRequest.builder().delFlag(DeleteFlag.NO).couponId(id).build());
			if (count == 0 ) {
				saleStateMap.put(id, CardSaleState.NO_STATE);
			} else {
				count = electronicCardRepository.countSaleCouponById(id, now);
				saleStateMap.put(id, count > 0 ? CardSaleState.NOT_EXPIRED : CardSaleState.EXPIRED);
			}
		});
		return saleStateMap;
	}

	/**
	 * 更新卡密状态
	 * @param ids
	 * @param cardState
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateCardState(List<String> ids, CardState cardState) {
		electronicCardRepository.updateCardState(ids, cardState.toValue());
	}

	/**
	 * 统计有效的卡密数量
	 * @param couponId
	 * @param time
	 * @return
	 */
	public Long countEffectiveCoupon(Long couponId, LocalDateTime time) {
		ElectronicCoupon electronicCoupon = electronicCouponRepository.findByIdAndDelFlag(couponId, DeleteFlag.NO);
		Long stock = electronicCardRepository.countEffectiveCoupon(couponId, time);
		stock = stock - electronicCoupon.getFreezeStock();
		return stock;
	}



	/**
	 * 删除卡密时减绑定商品的库存
	 */
	public void minusStock(Long electronicCouponsId,Long stock) {
		//查询绑定sku
		GoodsInfoVO goodsInfoVO = goodsQueryProvider.findByElectronicCouponsId(GoodsCheckBindRequest.builder()
				.electronicCouponsId(electronicCouponsId)
				.build()).getContext().getGoodsInfoVO();
		//非空 就减库存
		if (Objects.nonNull(goodsInfoVO)) {
			String goodsInfoId = goodsInfoVO.getGoodsInfoId();
			goodsInfoProvider.batchMinusStock(
					GoodsInfoBatchMinusStockRequest.builder()
							.stockList(Lists.newArrayList(GoodsInfoMinusStockDTO.builder()
									.stock(stock)
									.goodsInfoId(goodsInfoId)
									.build()))
							.build());

			//同步es
			esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().
					deleteIds(Collections.singletonList(goodsInfoVO.getGoodsId())).build());
			esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(goodsInfoVO.getGoodsId()).build());

		}
	}

	/**
	 * 数据处理
	 * @param data
	 * @return
	 */
	public String encryptData(String data){
		if (data.length() < 3) {
			return "***";
		}
		return data.replaceAll("(.{1}).*(.{1})","$1***$2");
	}

	/**
	 * 根据订单号和卡券id查询数量
	 * @param orderNo
	 * @param couponId
	 * @return
	 */
	public Integer countByOrderNoAndCouponId(String orderNo, Long couponId) {
		return findByOrderNoAndCouponId(orderNo, couponId).size();
	}

	/**
	 * 根据订单号和卡券id查询卡密
	 * @param orderNo
	 * @param couponId
	 * @return
	 */
	public List<ElectronicCard> findByOrderNoAndCouponId(String orderNo, Long couponId) {
		return electronicCardRepository.findByOrderNoAndCouponId(orderNo, couponId);
	}
}

