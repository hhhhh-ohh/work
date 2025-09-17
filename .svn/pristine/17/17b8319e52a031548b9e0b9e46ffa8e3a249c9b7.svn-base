package com.wanmi.sbc.marketing.electroniccoupon.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponQueryRequest;
import com.wanmi.sbc.marketing.bean.constant.EletronicErrorCode;
import com.wanmi.sbc.marketing.bean.enums.CardState;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCard;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCoupon;
import com.wanmi.sbc.marketing.electroniccoupon.repository.ElectronicCardRepository;
import com.wanmi.sbc.marketing.electroniccoupon.repository.ElectronicCouponRepository;
import com.wanmi.sbc.marketing.electroniccoupon.service.criteria.ElectronicCouponWhereCriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>电子卡券表业务逻辑</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Slf4j
@Service("ElectronicCouponService")
public class ElectronicCouponService {
	@Autowired
	private ElectronicCouponRepository electronicCouponRepository;

	@Autowired
	private ElectronicCardRepository electronicCardRepository;

	/**
	 * 新增电子卡券表
	 * @author 许云鹏
	 */
	@Transactional
	public ElectronicCoupon add(ElectronicCoupon entity) {
		int existsCount = electronicCouponRepository
				.countByCouponNameAndDelFlagAndStoreId(entity.getCouponName(), DeleteFlag.NO, entity.getStoreId());
		if (existsCount > 0) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080119);
		}
		entity.setDelFlag(DeleteFlag.NO);
		entity.setSendNum(NumberUtils.LONG_ZERO);
		entity.setNotSendNum(NumberUtils.LONG_ZERO);
		entity.setInvalidNum(NumberUtils.LONG_ZERO);
		entity.setFreezeStock(NumberUtils.LONG_ZERO);
		entity.setBindingFlag(Boolean.FALSE);
		electronicCouponRepository.save(entity);
		return entity;
	}

	/***
	 * 批量新增电子卡券表
	 * @param entityList
	 * @return
	 */
	@Transactional
	public List<ElectronicCoupon> addBatch(List<ElectronicCoupon> entityList) {
		int existsCount = electronicCouponRepository.countByCouponNameListAndDelFlag(entityList.stream()
				.map(ElectronicCoupon::getCouponName).collect(Collectors.toList()), DeleteFlag.NO);
		if (existsCount > 0) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080119);
		}
		entityList.forEach(entity -> {
			entity.setDelFlag(DeleteFlag.NO);
			entity.setSendNum(NumberUtils.LONG_ZERO);
			entity.setNotSendNum(NumberUtils.LONG_ZERO);
			entity.setInvalidNum(NumberUtils.LONG_ZERO);
			entity.setFreezeStock(NumberUtils.LONG_ZERO);
			entity.setBindingFlag(Boolean.FALSE);
		});
		electronicCouponRepository.saveAll(entityList);
		return entityList;
	}

	/**
	 * 修改电子卡券名称
	 * @author 许云鹏
	 */
	@Transactional
	public ElectronicCoupon modify(Long couponId, String name) {
		ElectronicCoupon coupon = this.getOne(couponId);
		int existsCount = electronicCouponRepository.countByCouponNameNotSelf(couponId, name, coupon.getStoreId());
		if (existsCount > 0) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080119);
		}
		coupon.setCouponName(name);
		electronicCouponRepository.save(coupon);
		return coupon;
	}

	/**
	 * 单个查询电子卡券表
	 * @author 许云鹏
	 */
	public ElectronicCoupon getOne(Long id){
		return electronicCouponRepository.findByIdAndDelFlag(id, DeleteFlag.NO);
	}

	/**
	 * 分页查询电子卡券表
	 * @author 许云鹏
	 */
	public Page<ElectronicCoupon> page(ElectronicCouponQueryRequest queryReq){
		return electronicCouponRepository.findAll(
				ElectronicCouponWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询电子卡券表
	 * @author 许云鹏
	 */
	public List<ElectronicCoupon> list(ElectronicCouponQueryRequest queryReq){
		return electronicCouponRepository.findAll(ElectronicCouponWhereCriteriaBuilder.build(queryReq), queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 * @author 许云鹏
	 */
	public ElectronicCouponVO wrapperVo(ElectronicCoupon electronicCoupon) {
		if (electronicCoupon != null){
			ElectronicCouponVO electronicCouponVO = KsBeanUtil.convert(electronicCoupon, ElectronicCouponVO.class);
			return electronicCouponVO;
		}
		return null;
	}


	/***
	 * 将实体包装成VO
	 * @param electronicCoupon	集合对象
	 * @return
	 */
	public List<ElectronicCouponVO> wrapperVoList(List<ElectronicCoupon> electronicCoupon) {
		return Nutils.nonNullActionRt(electronicCoupon,
				e-> KsBeanUtil.convertList(electronicCoupon, ElectronicCouponVO.class), null);
	}

	/**
	 * 卡券各状态数据统计
	 */
	@Transactional(rollbackFor = Exception.class)
	public void cardStateStatistical(){
		ElectronicCouponQueryRequest couponQueryRequest = new ElectronicCouponQueryRequest();
		int pageNum = 0;
		couponQueryRequest.setDelFlag(DeleteFlag.NO);
		couponQueryRequest.setPageSize(1000);
		while(true) {
			//分页查询卡券
			log.info("============卡券统计：第{}页============", pageNum + 1);
			couponQueryRequest.setPageNum(pageNum);
			List<ElectronicCoupon> coupons = page(couponQueryRequest).getContent();
			if (CollectionUtils.isEmpty(coupons)) {
				log.info("============卡券数量统计结束, 共{}页============", pageNum + 1);
				break;
			}
			//每个卡券统计各状态卡密数量
			coupons.forEach(coupon -> {
				long notSendNum = electronicCardRepository.countByCouponIdAndDelFlagAndCardState(coupon.getId(), DeleteFlag.NO, CardState.NOT_SEND.toValue());
				long sendNum = electronicCardRepository.countByCouponIdAndDelFlagAndCardState(coupon.getId(), DeleteFlag.NO, CardState.SEND.toValue());
				long invalidNum = electronicCardRepository.countByCouponIdAndDelFlagAndCardState(coupon.getId(), DeleteFlag.NO, CardState.INVAILD.toValue());
				coupon.setNotSendNum(notSendNum);
				coupon.setSendNum(sendNum);
				coupon.setInvalidNum(invalidNum);
			});
			electronicCouponRepository.saveAll(coupons);
			pageNum++;
		}
	}

	/**
	 * 更新冻结库存
	 * @param freezeStock
	 * @param id
	 */
	@Transactional
	public void updateFreezeStock(Long freezeStock,Long id, String orderNo, Boolean unBindOrderFlag){
		electronicCouponRepository.updateFreezeStock(freezeStock,id);
		//冻结库存时绑定卡密
		if (freezeStock > Constants.ZERO) {
			List<ElectronicCard> electronicCards = electronicCardRepository.findSomeEffectiveCoupon(id, LocalDateTime.now(), freezeStock);
			if (CollectionUtils.isNotEmpty(electronicCards))
				electronicCards.forEach(card -> card.setOrderNo(orderNo));
			electronicCardRepository.saveAll(electronicCards);
		} else if (Boolean.TRUE.equals(unBindOrderFlag)){
			List<ElectronicCard> electronicCards = electronicCardRepository.findNotSendByOrderNo(orderNo);
			electronicCards.forEach(card -> card.setOrderNo(null));
			electronicCardRepository.saveAll(electronicCards);
		}
	}

	/**
	 * 更改卡券绑定关系
	 * @param unBindingIds
	 * @param bindingIds
	 */
	@Transactional
	public void updateBindingFlag(List<Long> unBindingIds, List<Long> bindingIds) {
		//解绑的卡券
		if (CollectionUtils.isNotEmpty(unBindingIds)) {
			electronicCouponRepository.updateBindingFlag(unBindingIds, Boolean.FALSE);
		}
		//绑定的卡券
		if (CollectionUtils.isNotEmpty(bindingIds)) {
			electronicCouponRepository.updateBindingFlag(bindingIds, Boolean.TRUE);
		}
	}
}

