package com.wanmi.sbc.order.refundcallbackresult.service;

import com.wanmi.sbc.common.constant.ErrorCodeConstant;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.handler.aop.MasterRouteOnly;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultQueryRequest;
import com.wanmi.sbc.order.bean.constant.ConstantContent;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.vo.RefundCallBackResultVO;
import com.wanmi.sbc.order.refundcallbackresult.model.root.RefundCallBackResult;
import com.wanmi.sbc.order.refundcallbackresult.repository.RefundCallBackResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * <p>退款回调结果业务逻辑</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@Service
public class RefundCallBackResultService {

	@Autowired
	private RefundCallBackResultRepository refundCallBackResultRepository;

	/**
	 * 分页查询退款回调结果
	 * @author lvzhenwei
	 */
	@MasterRouteOnly
	public Page<RefundCallBackResult> page(RefundCallBackResultQueryRequest queryReq){
		return refundCallBackResultRepository.findAll(RefundCallBackResultWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 新增退款回调结果
	 */
	@Transactional
	public RefundCallBackResult add(RefundCallBackResult entity) {
		try {
			refundCallBackResultRepository.save(entity);
		} catch (Exception e) {
			try{
				if(((SQLIntegrityConstraintViolationException) e.getCause().getCause()).getSQLState().equals(ConstantContent.UNI_KEY_ERROR_CODE)){
					throw new SbcRuntimeException(OrderErrorCodeEnum.K050147);
				}
			} catch (SbcRuntimeException ex){
				throw ex;
			} catch (Exception ep) {
				throw e;
			}
			throw e;
		}
		return entity;
	}

	/**
	 * 根据businessId更新退款回调状态
	 * @param businessId
	 * @param resultStatus
	 * @return
	 */
	@Transactional
	public int updateStatus(String businessId, PayCallBackResultStatus resultStatus){
		if(resultStatus == PayCallBackResultStatus.FAILED){
			return refundCallBackResultRepository.updateStatusFailedByBusinessId(businessId,resultStatus);
		} else {
			return refundCallBackResultRepository.updateStatusSuccessByBusinessId(businessId,resultStatus);
		}
	}

	/**
	 * 将实体包装成VO
	 * @author lvzhenwei
	 */
	public RefundCallBackResultVO wrapperVo(RefundCallBackResult refundCallBackResult) {
		if (refundCallBackResult != null){
			RefundCallBackResultVO refundCallBackResultVO = KsBeanUtil.convert(refundCallBackResult, RefundCallBackResultVO.class);
			return refundCallBackResultVO;
		}
		return null;
	}
}

