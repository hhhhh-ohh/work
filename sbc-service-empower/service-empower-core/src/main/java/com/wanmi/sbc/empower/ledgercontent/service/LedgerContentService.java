package com.wanmi.sbc.empower.ledgercontent.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.empower.ledgercontent.repository.LedgerContentRepository;
import com.wanmi.sbc.empower.ledgercontent.model.root.LedgerContent;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentQueryRequest;
import com.wanmi.sbc.empower.bean.vo.LedgerContentVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>拉卡拉经营内容表业务逻辑</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Service("LedgerContentService")
public class LedgerContentService {
	@Autowired
	private LedgerContentRepository ledgerContentRepository;

	/**
	 * 新增拉卡拉经营内容表
	 * @author zhanghao
	 */
	@Transactional
	public LedgerContent add(LedgerContent entity) {
		ledgerContentRepository.save(entity);
		return entity;
	}

	/**
	 * 修改拉卡拉经营内容表
	 * @author zhanghao
	 */
	@Transactional
	public LedgerContent modify(LedgerContent entity) {
		ledgerContentRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除拉卡拉经营内容表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(LedgerContent entity) {
		ledgerContentRepository.save(entity);
	}

	/**
	 * 批量删除拉卡拉经营内容表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ledgerContentRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询拉卡拉经营内容表
	 * @author zhanghao
	 */
	public LedgerContent getOne(Long id){
		return ledgerContentRepository.findByContentIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "拉卡拉经营内容表不存在"));
	}

	/**
	 * 分页查询拉卡拉经营内容表
	 * @author zhanghao
	 */
	public Page<LedgerContent> page(LedgerContentQueryRequest queryReq){
		return ledgerContentRepository.findAll(
				LedgerContentWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询拉卡拉经营内容表
	 * @author zhanghao
	 */
	public List<LedgerContent> list(LedgerContentQueryRequest queryReq){
		return ledgerContentRepository.findAll(LedgerContentWhereCriteriaBuilder.build(queryReq));
	}


	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public LedgerContentVO wrapperVo(LedgerContent ledgerContent) {
		if (ledgerContent != null){
			LedgerContentVO ledgerContentVO = KsBeanUtil.convert(ledgerContent, LedgerContentVO.class);
			return ledgerContentVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(LedgerContentQueryRequest queryReq) {
		return ledgerContentRepository.count(LedgerContentWhereCriteriaBuilder.build(queryReq));
	}
}

