package com.wanmi.sbc.customer.ledgerreceiverrelrecord.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelRecordQueryRequest;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelRecordVO;
import com.wanmi.sbc.customer.ledgerreceiverrelrecord.model.root.LedgerReceiverRelRecord;
import com.wanmi.sbc.customer.ledgerreceiverrelrecord.repository.LedgerReceiverRelRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>分账绑定关系补偿记录业务逻辑</p>
 * @author xuyunpeng
 * @date 2022-07-14 15:15:45
 */
@Service("LedgerReceiverRelRecordService")
public class LedgerReceiverRelRecordService {
	@Autowired
	private LedgerReceiverRelRecordRepository ledgerReceiverRelRecordRepository;

	/**
	 * 新增分账绑定关系补偿记录
	 * @author xuyunpeng
	 */
	@Transactional
	public void add(LedgerReceiverRelRecord entity) {
		LedgerReceiverRelRecord record = ledgerReceiverRelRecordRepository.findByAccountId(entity.getAccountId());
		if (record != null) {
			record.setUpdateTime(LocalDateTime.now());
			ledgerReceiverRelRecordRepository.save(record);
		} else {
			ledgerReceiverRelRecordRepository.save(entity);
		}
	}

	/**
	 * 批量删除分账绑定关系补偿记录
	 * @author xuyunpeng
	 */
	@Transactional
	public void deleteByAccountId(String accountId) {
		ledgerReceiverRelRecordRepository.deleteByAccountId(accountId);
	}

	/**
	 * 单个查询分账绑定关系补偿记录
	 * @author xuyunpeng
	 */
	public LedgerReceiverRelRecord getOne(String id){
		return ledgerReceiverRelRecordRepository.findById(id).orElse(null);
	}

	/**
	 * 分页查询分账绑定关系补偿记录
	 * @author xuyunpeng
	 */
	public Page<LedgerReceiverRelRecord> page(LedgerReceiverRelRecordQueryRequest queryReq){
		return ledgerReceiverRelRecordRepository.findAll(
				LedgerReceiverRelRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询分账绑定关系补偿记录
	 * @author xuyunpeng
	 */
	public List<LedgerReceiverRelRecord> list(LedgerReceiverRelRecordQueryRequest queryReq){
		return ledgerReceiverRelRecordRepository.findAll(LedgerReceiverRelRecordWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author xuyunpeng
	 */
	public LedgerReceiverRelRecordVO wrapperVo(LedgerReceiverRelRecord ledgerReceiverRelRecord) {
		if (ledgerReceiverRelRecord != null){
			LedgerReceiverRelRecordVO ledgerReceiverRelRecordVO = KsBeanUtil.convert(ledgerReceiverRelRecord, LedgerReceiverRelRecordVO.class);
			return ledgerReceiverRelRecordVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author xuyunpeng
	 */
	public Long count(LedgerReceiverRelRecordQueryRequest queryReq) {
		return ledgerReceiverRelRecordRepository.count(LedgerReceiverRelRecordWhereCriteriaBuilder.build(queryReq));
	}
}

