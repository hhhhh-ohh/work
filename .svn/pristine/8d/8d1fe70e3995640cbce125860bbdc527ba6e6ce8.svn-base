package com.wanmi.sbc.account.ledgerfunds.service;

import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsAmountRequest;
import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsQueryRequest;
import com.wanmi.sbc.account.bean.vo.LedgerFundsVO;
import com.wanmi.sbc.account.ledgerfunds.model.root.LedgerFunds;
import com.wanmi.sbc.account.ledgerfunds.repository.LedgerFundsRepository;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>会员分账资金业务逻辑</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@Service("LedgerFundsService")
public class LedgerFundsService {
	@Autowired
	private LedgerFundsRepository ledgerFundsRepository;

	/**
	 * 资金提现
	 * @author xuyunpeng
	 */
	@Transactional
	public void grantAmount(LedgerFundsAmountRequest request) {
		LedgerFunds ledgerFunds = ledgerFundsRepository.findByCustomerId(request.getCustomerId());
		if (ledgerFunds == null) {
			init(request.getCustomerId(), request.getAmount(), BigDecimal.ZERO);
		} else {
			ledgerFundsRepository.grantAmount(request.getCustomerId(), request.getAmount());
		}
	}

	/**
	 * 修改可提现金额
	 * @param request
	 */
	@Transactional
	public void updateWithdrawnAmount(LedgerFundsAmountRequest request) {
		LedgerFunds ledgerFunds = ledgerFundsRepository.findByCustomerId(request.getCustomerId());
		if (ledgerFunds == null) {
			init(request.getCustomerId(), BigDecimal.ZERO, request.getAmount());
		} else {
			ledgerFundsRepository.updateWithdrawnAmount(request.getCustomerId(), request.getAmount());
		}
	}
	/**
	 * 初始化分账资金
	 * @param customerId
	 * @param withdrawnAmount
	 */
	public void init(String customerId, BigDecimal alreadyDrawAmount, BigDecimal withdrawnAmount){
		LedgerFunds ledgerFunds = new LedgerFunds();
		ledgerFunds.setCustomerId(customerId);
		ledgerFunds.setAlreadyDrawAmount(alreadyDrawAmount);
		ledgerFunds.setWithdrawnAmount(withdrawnAmount);
		ledgerFundsRepository.save(ledgerFunds);
	}

	/**
	 * 单个查询会员分账资金
	 * @author xuyunpeng
	 */
	public LedgerFunds getOne(String id){
		return ledgerFundsRepository.findById(id).orElse(null);
	}

	/**
	 * 分页查询会员分账资金
	 * @author xuyunpeng
	 */
	public Page<LedgerFunds> page(LedgerFundsQueryRequest queryReq){
		return ledgerFundsRepository.findAll(
				LedgerFundsWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询会员分账资金
	 * @author xuyunpeng
	 */
	public List<LedgerFunds> list(LedgerFundsQueryRequest queryReq){
		return ledgerFundsRepository.findAll(LedgerFundsWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author xuyunpeng
	 */
	public LedgerFundsVO wrapperVo(LedgerFunds ledgerFunds) {
		if (ledgerFunds != null){
			LedgerFundsVO ledgerFundsVO = KsBeanUtil.convert(ledgerFunds, LedgerFundsVO.class);
			return ledgerFundsVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author xuyunpeng
	 */
	public Long count(LedgerFundsQueryRequest queryReq) {
		return ledgerFundsRepository.count(LedgerFundsWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 查询会员分账资金数据
	 * @param customerId
	 * @return
	 */
	public LedgerFunds findByCustomerId(String customerId){
		return ledgerFundsRepository.findByCustomerId(customerId);
	}
}

