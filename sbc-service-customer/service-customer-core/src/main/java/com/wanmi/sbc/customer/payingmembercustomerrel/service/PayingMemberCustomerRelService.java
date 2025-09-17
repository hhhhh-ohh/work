package com.wanmi.sbc.customer.payingmembercustomerrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelQueryRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelVO;
import com.wanmi.sbc.customer.payingmembercustomerrel.model.root.PayingMemberCustomerRel;
import com.wanmi.sbc.customer.payingmembercustomerrel.repository.PayingMemberCustomerRelRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>客户与付费会员等级关联表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@Service("PayingMemberCustomerRelService")
public class PayingMemberCustomerRelService {
	@Autowired
	private PayingMemberCustomerRelRepository payingMemberCustomerRelRepository;

	/**
	 * 新增客户与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberCustomerRel add(PayingMemberCustomerRel entity) {
		payingMemberCustomerRelRepository.save(entity);
		return entity;
	}

	/**
	 * 修改客户与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberCustomerRel modify(PayingMemberCustomerRel entity) {
		payingMemberCustomerRelRepository.save(entity);
		return entity;
	}

	/**
	 * 更新客户与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void updateExpirationDate(LocalDate expirationDate, LocalDateTime openTime, Long id) {
		if (Objects.nonNull(openTime)) {
			payingMemberCustomerRelRepository.updateExpirationDateAndOpenTime(expirationDate,openTime,id);
		} else {
			payingMemberCustomerRelRepository.updateExpirationDate(expirationDate,id);
		}

	}



	/**
	 * 单个删除客户与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberCustomerRel entity) {
		payingMemberCustomerRelRepository.save(entity);
	}

	/**
	 * 批量删除客户与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		payingMemberCustomerRelRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询客户与付费会员等级关联表
	 * @author zhanghao
	 */
	public PayingMemberCustomerRel getOne(Long id){
		return payingMemberCustomerRelRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "客户与付费会员等级关联表不存在"));
	}

	/**
	 * 单个查询客户与付费会员等级关联表
	 * @author zhanghao
	 */
	public PayingMemberCustomerRel findByCustomerId(String customerId){
		return payingMemberCustomerRelRepository.findByCustomerIdAndDelFlag(customerId, DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CustomerErrorCodeEnum.K010130));
	}

	/**
	 * 分页查询客户与付费会员等级关联表
	 * @author zhanghao
	 */
	public Page<PayingMemberCustomerRel> page(PayingMemberCustomerRelQueryRequest queryReq){
		return payingMemberCustomerRelRepository.findAll(
				PayingMemberCustomerRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询客户与付费会员等级关联表
	 * @author zhanghao
	 */
	public List<PayingMemberCustomerRel> list(PayingMemberCustomerRelQueryRequest queryReq){
		return payingMemberCustomerRelRepository.findAll(PayingMemberCustomerRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberCustomerRelVO wrapperVo(PayingMemberCustomerRel payingMemberCustomerRel) {
		if (payingMemberCustomerRel != null){
			PayingMemberCustomerRelVO payingMemberCustomerRelVO = KsBeanUtil.convert(payingMemberCustomerRel, PayingMemberCustomerRelVO.class);
			return payingMemberCustomerRelVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberCustomerRelQueryRequest queryReq) {
		return payingMemberCustomerRelRepository.count(PayingMemberCustomerRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 修改会员优惠金额
	 * @param customerId
	 * @param levelId
	 */
	@Transactional
	public void updateCustomerDiscount(String customerId, Integer levelId, BigDecimal discount) {
		payingMemberCustomerRelRepository.updateCustomerDiscount(customerId, levelId, discount);
	}

	/**
	 * 根据等级id查询会员优惠金额
	 * @param customerId
	 * @param levelId
	 * @return
	 */
	public BigDecimal findDiscountByLevelId(String customerId, Integer levelId) {
		PayingMemberCustomerRel rel = payingMemberCustomerRelRepository.findDiscountByLevelId(customerId, levelId);
		if (rel == null) {
//			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			return BigDecimal.ZERO;
		}
		return rel.getDiscountAmount();
	}

	/**
	 * 单个查询付费会员最高的等级的到期日期
	 * @return
	 */
	public LocalDate findMostExpirationDate(String customerId, Integer levelId){
		return payingMemberCustomerRelRepository.findMostExpirationDate(levelId,customerId);
	}

	/**
	 *单个查询付费会员最高的等级的名称
	 * @return
	 */
	public String findMostLevelName(Integer levelId){
		return payingMemberCustomerRelRepository.findMostLevelName(levelId);
	}

	/**
	 *单个查询付费会员最高的等级
	 * @return
	 */
	public Integer findMostLevelId(String customerId){
		return payingMemberCustomerRelRepository.findMostLevelId(customerId,LocalDate.now());
	}

	/**
	 * 根据指定时间查询有效的付费会员ids
	 * @param customerIds
	 * @param date
	 * @return
	 */
	public List<String> findActiveCustomerIdByWeek(List<String> customerIds, LocalDate date) {
		return payingMemberCustomerRelRepository.findActiveCustomerIdsByWeek(customerIds, date);
	}

	/**
	 * 检查当前用户是否为付费会员
	 * @param customerId
	 * @return
	 */
	public Boolean checkPayMember(String customerId) {
		List<PayingMemberCustomerRel> rels = payingMemberCustomerRelRepository.findByCustomer(customerId, LocalDate.now());
		if (CollectionUtils.isNotEmpty(rels)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public List<PayingMemberCustomerRel> findExpirationList() {
		List<String> customerList = payingMemberCustomerRelRepository.findExpirationList();

		return customerList.stream().map(customerId -> {
			PayingMemberCustomerRel rel = new PayingMemberCustomerRel();
			rel.setCustomerId(customerId);
			return rel;
		}).toList();
	}

	public Integer deleteByCustomerId(String customerId) {
		return payingMemberCustomerRelRepository.deleteByCustomerId(customerId);
	}
}

