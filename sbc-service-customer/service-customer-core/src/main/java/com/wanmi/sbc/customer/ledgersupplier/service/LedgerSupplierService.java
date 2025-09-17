package com.wanmi.sbc.customer.ledgersupplier.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierQueryRequest;
import com.wanmi.sbc.customer.bean.vo.LedgerSupplierVO;
import com.wanmi.sbc.customer.ledgersupplier.model.root.LedgerSupplier;
import com.wanmi.sbc.customer.ledgersupplier.repository.LedgerSupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>商户分账绑定数据业务逻辑</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Service("LedgerSupplierService")
public class LedgerSupplierService {
	@Autowired
	private LedgerSupplierRepository ledgerSupplierRepository;

	/**
	 * 新增商户分账绑定数据
	 * @author 许云鹏
	 */
	@Transactional
	public LedgerSupplier add(LedgerSupplier entity) {
		ledgerSupplierRepository.save(entity);
		return entity;
	}

	/**
	 * 单个查询商户分账绑定数据
	 * @author 许云鹏
	 */
	public LedgerSupplier getOne(String id){
		return ledgerSupplierRepository.findById(id).orElse(null);
	}

	/**
	 * 分页查询商户分账绑定数据
	 * @author 许云鹏
	 */
	public Page<LedgerSupplier> page(LedgerSupplierQueryRequest queryReq){
		return ledgerSupplierRepository.findAll(
				LedgerSupplierWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询商户分账绑定数据
	 * @author 许云鹏
	 */
	public List<LedgerSupplier> list(LedgerSupplierQueryRequest queryReq){
		return ledgerSupplierRepository.findAll(LedgerSupplierWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 许云鹏
	 */
	public LedgerSupplierVO wrapperVo(LedgerSupplier ledgerSupplier) {
		if (ledgerSupplier != null){
			LedgerSupplierVO ledgerSupplierVO = KsBeanUtil.convert(ledgerSupplier, LedgerSupplierVO.class);
			return ledgerSupplierVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 许云鹏
	 */
	public Long count(LedgerSupplierQueryRequest queryReq) {
		return ledgerSupplierRepository.count(LedgerSupplierWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 修改平台绑定状态
	 * @param platBindState
	 * @param id
	 */
	@Transactional
	public void updatePlatBindStateById(Integer platBindState,String id){
		ledgerSupplierRepository.updatePlatBindStateById(platBindState,id);
	}


	/**
	 * 修改供应商数量
	 * @param id
	 */
	@Transactional
	public void updateProviderNumById(String id){
		ledgerSupplierRepository.updateProviderNumById(id);
	}


	/**
	 * 修改分销员数量
	 * @param id
	 */
	@Transactional
	public void updateDistributionNumById(String id){
		ledgerSupplierRepository.updateDistributionNumById(id);
	}
}

