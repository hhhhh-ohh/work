package com.wanmi.sbc.customer.ledgererrorrecord.service;

import com.wanmi.sbc.customer.api.request.ledgererrorrecord.LedgerErrorRecordQueryRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerErrorState;
import com.wanmi.sbc.customer.bean.enums.LedgerFunctionType;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.customer.ledgererrorrecord.model.root.LedgerErrorRecord;
import com.wanmi.sbc.customer.ledgererrorrecord.repository.LedgerErrorRecordRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>分账接口错误记录业务逻辑</p>
 * @author 许云鹏
 * @date 2022-07-09 12:34:25
 */
@Service("LedgerErrorRecordService")
public class LedgerErrorRecordService {
	@Autowired
	private LedgerErrorRecordRepository ledgerErrorRecordRepository;


	/**
	 * 记录失败信息
	 * @param account
	 * @param message
	 * @param type
	 */
	@Transactional
	public void addErrorRecord(LedgerAccountVO account, String message, LedgerFunctionType type){
		LedgerErrorRecord record = this.findByBusiness(account.getBusinessId(), type);
		if (record == null) {
			record = new LedgerErrorRecord();
			record.setBusinessId(account.getBusinessId());
			record.setType(type.toValue());
			record.setErrorInfo(message);
			record.setState(LedgerErrorState.UNDO.toValue());
			record.setRetryCount(NumberUtils.INTEGER_ONE);
		} else {
			record.setErrorInfo(message);
			record.setRetryCount(record.getRetryCount() + NumberUtils.INTEGER_ONE);
			record.setState(LedgerErrorState.FAIL.toValue());
		}
		ledgerErrorRecordRepository.save(record);
	}

	/**
	 * 单个查询分账接口错误记录
	 * @author 许云鹏
	 */
	public LedgerErrorRecord getOne(String id){
		return ledgerErrorRecordRepository.findById(id).orElse(null);
	}

	/**
	 * 根据业务id和类型查询
	 * @param businessId
	 * @param type
	 * @return
	 */
	public LedgerErrorRecord findByBusiness(String businessId, LedgerFunctionType type) {
		return ledgerErrorRecordRepository.findByBusinessIdAndType(businessId, type.toValue());
	}

	/**
	 * 分页查询分账接口错误记录
	 * @author 许云鹏
	 */
	public Page<LedgerErrorRecord> page(LedgerErrorRecordQueryRequest queryReq){
		return ledgerErrorRecordRepository.findAll(
				LedgerErrorRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询分账接口错误记录
	 * @author 许云鹏
	 */
	public List<LedgerErrorRecord> list(LedgerErrorRecordQueryRequest queryReq){
		return ledgerErrorRecordRepository.findAll(LedgerErrorRecordWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * @description 查询总数量
	 * @author 许云鹏
	 */
	public Long count(LedgerErrorRecordQueryRequest queryReq) {
		return ledgerErrorRecordRepository.count(LedgerErrorRecordWhereCriteriaBuilder.build(queryReq));
	}

	@Transactional
	public void modifyState(String recordId, LedgerErrorState state) {
		ledgerErrorRecordRepository.modifyState(recordId, state.toValue());
	}

	/**
	 * 根据业务id和类型修改状态
	 * @param businessId
	 * @param type
	 */
	@Transactional
	public void modifyState(String businessId, LedgerFunctionType type, LedgerErrorState state) {
		LedgerErrorRecord record = this.findByBusiness(businessId, type);
		if (record != null) {
			record.setState(state.toValue());
		}
	}
}

