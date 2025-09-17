package com.wanmi.sbc.customer.payingmemberdiscountrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.customer.payingmemberdiscountrel.repository.PayingMemberDiscountRelRepository;
import com.wanmi.sbc.customer.payingmemberdiscountrel.model.root.PayingMemberDiscountRel;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelQueryRequest;
import com.wanmi.sbc.customer.bean.vo.PayingMemberDiscountRelVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>折扣商品与付费会员等级关联表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@Service("PayingMemberDiscountRelService")
public class PayingMemberDiscountRelService {
	@Autowired
	private PayingMemberDiscountRelRepository payingMemberDiscountRelRepository;

	/**
	 * 新增折扣商品与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberDiscountRel add(PayingMemberDiscountRel entity) {
		payingMemberDiscountRelRepository.save(entity);
		return entity;
	}

	/**
	 * 修改折扣商品与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberDiscountRel modify(PayingMemberDiscountRel entity) {
		payingMemberDiscountRelRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除折扣商品与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberDiscountRel entity) {
		payingMemberDiscountRelRepository.save(entity);
	}

	/**
	 * 批量删除折扣商品与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		payingMemberDiscountRelRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询折扣商品与付费会员等级关联表
	 * @author zhanghao
	 */
	public PayingMemberDiscountRel getOne(Long id){
		return payingMemberDiscountRelRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "折扣商品与付费会员等级关联表不存在"));
	}

	/**
	 * 分页查询折扣商品与付费会员等级关联表
	 * @author zhanghao
	 */
	public Page<PayingMemberDiscountRel> page(PayingMemberDiscountRelQueryRequest queryReq){
		return payingMemberDiscountRelRepository.findAll(
				PayingMemberDiscountRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询折扣商品与付费会员等级关联表
	 * @author zhanghao
	 */
	public List<PayingMemberDiscountRel> list(PayingMemberDiscountRelQueryRequest queryReq){
		return payingMemberDiscountRelRepository.findAll(PayingMemberDiscountRelWhereCriteriaBuilder.build(queryReq));
	}



	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberDiscountRelVO wrapperVo(PayingMemberDiscountRel payingMemberDiscountRel) {
		if (payingMemberDiscountRel != null){
			PayingMemberDiscountRelVO payingMemberDiscountRelVO = KsBeanUtil.convert(payingMemberDiscountRel, PayingMemberDiscountRelVO.class);
			return payingMemberDiscountRelVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberDiscountRelQueryRequest queryReq) {
		return payingMemberDiscountRelRepository.count(PayingMemberDiscountRelWhereCriteriaBuilder.build(queryReq));
	}
}

