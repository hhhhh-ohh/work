package com.wanmi.sbc.customer.payingmemberstorerel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.customer.payingmemberstorerel.repository.PayingMemberStoreRelRepository;
import com.wanmi.sbc.customer.payingmemberstorerel.model.root.PayingMemberStoreRel;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelQueryRequest;
import com.wanmi.sbc.customer.bean.vo.PayingMemberStoreRelVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>商家与付费会员等级关联表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@Service("PayingMemberStoreRelService")
public class PayingMemberStoreRelService {
	@Autowired
	private PayingMemberStoreRelRepository payingMemberStoreRelRepository;

	/**
	 * 新增商家与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberStoreRel add(PayingMemberStoreRel entity) {
		payingMemberStoreRelRepository.save(entity);
		return entity;
	}

	/**
	 * 修改商家与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberStoreRel modify(PayingMemberStoreRel entity) {
		payingMemberStoreRelRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除商家与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberStoreRel entity) {
		payingMemberStoreRelRepository.save(entity);
	}

	/**
	 * 批量删除商家与付费会员等级关联表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		payingMemberStoreRelRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询商家与付费会员等级关联表
	 * @author zhanghao
	 */
	public PayingMemberStoreRel getOne(Long id, Long storeId){
		return payingMemberStoreRelRepository.findByIdAndStoreIdAndDelFlag(id, storeId, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商家与付费会员等级关联表不存在"));
	}

	/**
	 * 分页查询商家与付费会员等级关联表
	 * @author zhanghao
	 */
	public Page<PayingMemberStoreRel> page(PayingMemberStoreRelQueryRequest queryReq){
		return payingMemberStoreRelRepository.findAll(
				PayingMemberStoreRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询商家与付费会员等级关联表
	 * @author zhanghao
	 */
	public List<PayingMemberStoreRel> list(PayingMemberStoreRelQueryRequest queryReq){
		return payingMemberStoreRelRepository.findAll(PayingMemberStoreRelWhereCriteriaBuilder.build(queryReq));
	}


	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberStoreRelVO wrapperVo(PayingMemberStoreRel payingMemberStoreRel) {
		if (payingMemberStoreRel != null){
			PayingMemberStoreRelVO payingMemberStoreRelVO = KsBeanUtil.convert(payingMemberStoreRel, PayingMemberStoreRelVO.class);
			return payingMemberStoreRelVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberStoreRelQueryRequest queryReq) {
		return payingMemberStoreRelRepository.count(PayingMemberStoreRelWhereCriteriaBuilder.build(queryReq));
	}
}

