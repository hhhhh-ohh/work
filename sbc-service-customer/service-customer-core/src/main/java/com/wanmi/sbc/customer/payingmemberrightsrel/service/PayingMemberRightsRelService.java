package com.wanmi.sbc.customer.payingmemberrightsrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.customer.payingmemberrightsrel.repository.PayingMemberRightsRelRepository;
import com.wanmi.sbc.customer.payingmemberrightsrel.model.root.PayingMemberRightsRel;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelQueryRequest;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRightsRelVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>权益与付费会员等级关联表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@Service("PayingMemberRightsRelService")
public class PayingMemberRightsRelService {
	@Autowired
	private PayingMemberRightsRelRepository payingMemberRightsRelRepository;

	/**
	 * 新增权益与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberRightsRel add(PayingMemberRightsRel entity) {
		payingMemberRightsRelRepository.save(entity);
		return entity;
	}

	/**
	 * 修改权益与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberRightsRel modify(PayingMemberRightsRel entity) {
		payingMemberRightsRelRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除权益与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberRightsRel entity) {
		payingMemberRightsRelRepository.save(entity);
	}

	/**
	 * 批量删除权益与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		payingMemberRightsRelRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询权益与付费会员等级关联表
	 * @author zhanghao
	 */
	public PayingMemberRightsRel getOne(Long id){
		return payingMemberRightsRelRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "权益与付费会员等级关联表不存在"));
	}

	/**
	 * 分页查询权益与付费会员等级关联表
	 * @author zhanghao
	 */
	public Page<PayingMemberRightsRel> page(PayingMemberRightsRelQueryRequest queryReq){
		return payingMemberRightsRelRepository.findAll(
				PayingMemberRightsRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询权益与付费会员等级关联表
	 * @author zhanghao
	 */
	public List<PayingMemberRightsRel> list(PayingMemberRightsRelQueryRequest queryReq){
		return payingMemberRightsRelRepository.findAll(PayingMemberRightsRelWhereCriteriaBuilder.build(queryReq));
	}



	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberRightsRelVO wrapperVo(PayingMemberRightsRel payingMemberRightsRel) {
		if (payingMemberRightsRel != null){
			PayingMemberRightsRelVO payingMemberRightsRelVO = KsBeanUtil.convert(payingMemberRightsRel, PayingMemberRightsRelVO.class);
			return payingMemberRightsRelVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberRightsRelQueryRequest queryReq) {
		return payingMemberRightsRelRepository.count(PayingMemberRightsRelWhereCriteriaBuilder.build(queryReq));
	}
}

