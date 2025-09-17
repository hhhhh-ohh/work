package com.wanmi.sbc.empower.ledgermcc.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.empower.ledgermcc.repository.LedgerMccRepository;
import com.wanmi.sbc.empower.ledgermcc.model.root.LedgerMcc;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccQueryRequest;
import com.wanmi.sbc.empower.bean.vo.LedgerMccVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>拉卡拉mcc表业务逻辑</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Service("LedgerMccService")
public class LedgerMccService {
	@Autowired
	private LedgerMccRepository ledgerMccRepository;

	/**
	 * 新增拉卡拉mcc表
	 * @author zhanghao
	 */
	@Transactional
	public LedgerMcc add(LedgerMcc entity) {
		ledgerMccRepository.save(entity);
		return entity;
	}

	/**
	 * 修改拉卡拉mcc表
	 * @author zhanghao
	 */
	@Transactional
	public LedgerMcc modify(LedgerMcc entity) {
		ledgerMccRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除拉卡拉mcc表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(LedgerMcc entity) {
		ledgerMccRepository.save(entity);
	}

	/**
	 * 批量删除拉卡拉mcc表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ledgerMccRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询拉卡拉mcc表
	 * @author zhanghao
	 */
	public LedgerMcc getOne(Long id){
		return ledgerMccRepository.findByMccIdAndDelFlag(id, DeleteFlag.NO).orElse(null);
	}

	/**
	 * 分页查询拉卡拉mcc表
	 * @author zhanghao
	 */
	public Page<LedgerMcc> page(LedgerMccQueryRequest queryReq){
		return ledgerMccRepository.findAll(
				LedgerMccWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询拉卡拉mcc表
	 * @author zhanghao
	 */
	public List<LedgerMcc> list(LedgerMccQueryRequest queryReq){
		return ledgerMccRepository.findAll(LedgerMccWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public LedgerMccVO wrapperVo(LedgerMcc ledgerMcc) {
		if (ledgerMcc != null){
			LedgerMccVO ledgerMccVO = KsBeanUtil.convert(ledgerMcc, LedgerMccVO.class);
			return ledgerMccVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(LedgerMccQueryRequest queryReq) {
		return ledgerMccRepository.count(LedgerMccWhereCriteriaBuilder.build(queryReq));
	}
}

