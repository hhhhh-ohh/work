package com.wanmi.sbc.order.payingmemberpayrecord.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.order.payingmemberpayrecord.repository.PayingMemberPayRecordRepository;
import com.wanmi.sbc.order.payingmemberpayrecord.model.root.PayingMemberPayRecord;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordQueryRequest;
import com.wanmi.sbc.order.bean.vo.PayingMemberPayRecordVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>付费会员支付记录表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Service("PayingMemberPayRecordService")
public class PayingMemberPayRecordService {
	@Autowired
	private PayingMemberPayRecordRepository payingMemberPayRecordRepository;

	/**
	 * 新增付费会员支付记录表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberPayRecord add(PayingMemberPayRecord entity) {
		payingMemberPayRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 修改付费会员支付记录表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberPayRecord modify(PayingMemberPayRecord entity) {
		payingMemberPayRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除付费会员支付记录表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberPayRecord entity) {
		payingMemberPayRecordRepository.save(entity);
	}

	/**
	 * 批量删除付费会员支付记录表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		payingMemberPayRecordRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询付费会员支付记录表
	 * @author zhanghao
	 */
	public PayingMemberPayRecord getOne(String id){
		return payingMemberPayRecordRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "付费会员支付记录表不存在"));
	}

	/**
	 * 分页查询付费会员支付记录表
	 * @author zhanghao
	 */
	public Page<PayingMemberPayRecord> page(PayingMemberPayRecordQueryRequest queryReq){
		return payingMemberPayRecordRepository.findAll(
				PayingMemberPayRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询付费会员支付记录表
	 * @author zhanghao
	 */
	public List<PayingMemberPayRecord> list(PayingMemberPayRecordQueryRequest queryReq){
		return payingMemberPayRecordRepository.findAll(PayingMemberPayRecordWhereCriteriaBuilder.build(queryReq));
	}



	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberPayRecordVO wrapperVo(PayingMemberPayRecord payingMemberPayRecord) {
		if (payingMemberPayRecord != null){
			PayingMemberPayRecordVO payingMemberPayRecordVO = KsBeanUtil.convert(payingMemberPayRecord, PayingMemberPayRecordVO.class);
			return payingMemberPayRecordVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberPayRecordQueryRequest queryReq) {
		return payingMemberPayRecordRepository.count(PayingMemberPayRecordWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 根据业务id查询记录
	 * @param businessId
	 * @return
	 */
	public PayingMemberPayRecord findByBusinessId(String businessId) {
		return payingMemberPayRecordRepository.findByBusinessId(businessId);
	}
}

