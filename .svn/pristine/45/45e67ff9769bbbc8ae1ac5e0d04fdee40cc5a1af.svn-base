package com.wanmi.sbc.customer.payingmemberrecommendrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.customer.payingmemberrecommendrel.repository.PayingMemberRecommendRelRepository;
import com.wanmi.sbc.customer.payingmemberrecommendrel.model.root.PayingMemberRecommendRel;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelQueryRequest;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRecommendRelVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>推荐商品与付费会员等级关联表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@Service("PayingMemberRecommendRelService")
public class PayingMemberRecommendRelService {
	@Autowired
	private PayingMemberRecommendRelRepository payingMemberRecommendRelRepository;

	/**
	 * 新增推荐商品与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberRecommendRel add(PayingMemberRecommendRel entity) {
		payingMemberRecommendRelRepository.save(entity);
		return entity;
	}

	/**
	 * 修改推荐商品与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberRecommendRel modify(PayingMemberRecommendRel entity) {
		payingMemberRecommendRelRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除推荐商品与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberRecommendRel entity) {
		payingMemberRecommendRelRepository.save(entity);
	}

	/**
	 * 批量删除推荐商品与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		payingMemberRecommendRelRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询推荐商品与付费会员等级关联表
	 * @author zhanghao
	 */
	public PayingMemberRecommendRel getOne(Long id){
		return payingMemberRecommendRelRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "推荐商品与付费会员等级关联表不存在"));
	}

	/**
	 * 分页查询推荐商品与付费会员等级关联表
	 * @author zhanghao
	 */
	public Page<PayingMemberRecommendRel> page(PayingMemberRecommendRelQueryRequest queryReq){
		return payingMemberRecommendRelRepository.findAll(
				PayingMemberRecommendRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询推荐商品与付费会员等级关联表
	 * @author zhanghao
	 */
	public List<PayingMemberRecommendRel> list(PayingMemberRecommendRelQueryRequest queryReq){
		return payingMemberRecommendRelRepository.findAll(PayingMemberRecommendRelWhereCriteriaBuilder.build(queryReq));
	}


	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberRecommendRelVO wrapperVo(PayingMemberRecommendRel payingMemberRecommendRel) {
		if (payingMemberRecommendRel != null){
			PayingMemberRecommendRelVO payingMemberRecommendRelVO = KsBeanUtil.convert(payingMemberRecommendRel, PayingMemberRecommendRelVO.class);
			return payingMemberRecommendRelVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberRecommendRelQueryRequest queryReq) {
		return payingMemberRecommendRelRepository.count(PayingMemberRecommendRelWhereCriteriaBuilder.build(queryReq));
	}
}

