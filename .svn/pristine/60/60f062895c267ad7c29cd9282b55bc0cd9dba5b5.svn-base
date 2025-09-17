package com.wanmi.sbc.customer.ledgercontract.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.LedgerContractVO;
import com.wanmi.sbc.customer.ledgercontract.model.root.LedgerContract;
import com.wanmi.sbc.customer.ledgercontract.repository.LedgerContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>分账合同协议配置业务逻辑</p>
 * @author 许云鹏
 * @date 2022-07-07 17:54:08
 */
@Service("LedgerContractService")
public class LedgerContractService {
	@Autowired
	private LedgerContractRepository ledgerContractRepository;

	/**
	 * 单个查询分账合同协议配置
	 * @author 许云鹏
	 */
	public LedgerContract getOne(){
		return ledgerContractRepository.findFirstByOrderById();
	}

	/**
	 * 将实体包装成VO
	 * @author 许云鹏
	 */
	public LedgerContractVO wrapperVo(LedgerContract ledgerContract) {
		if (ledgerContract != null){
			LedgerContractVO ledgerContractVO = KsBeanUtil.convert(ledgerContract, LedgerContractVO.class);
			return ledgerContractVO;
		}
		return null;
	}

	@Transactional
	public void save(String body, String content){
		LedgerContract contract = getOne();
		if (contract == null) {
			contract = new LedgerContract();
		}
		contract.setBody(body);
		contract.setContent(content);
		ledgerContractRepository.save(contract);
	}
}

