package com.wanmi.sbc.message.storenoticescope.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.message.storenoticescope.repository.StoreNoticeScopeRepository;
import com.wanmi.sbc.message.storenoticescope.model.root.StoreNoticeScope;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeQueryRequest;
import com.wanmi.sbc.message.bean.vo.StoreNoticeScopeVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import java.util.List;

/**
 * <p>商家公告发送范围业务逻辑</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Service("StoreNoticeScopeService")
public class StoreNoticeScopeService {
	@Autowired
	private StoreNoticeScopeRepository storeNoticeScopeRepository;

	/**
	 * 新增商家公告发送范围
	 * @author 马连峰
	 */
	@Transactional
	public StoreNoticeScope add(StoreNoticeScope entity) {
		storeNoticeScopeRepository.save(entity);
		return entity;
	}

	/**
	 * 修改商家公告发送范围
	 * @author 马连峰
	 */
	@Transactional
	public StoreNoticeScope modify(StoreNoticeScope entity) {
		storeNoticeScopeRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除商家公告发送范围
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteById(Long id) {
		storeNoticeScopeRepository.deleteById(id);
	}

	/**
	 * 批量删除商家公告ID的发送范围
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteByNoticeId(Long noticeId) {
		storeNoticeScopeRepository.deleteByNoticeId(noticeId);
	}

	/**
	 * 批量删除商家公告发送范围
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteByIdList(List<Long> ids) {
		storeNoticeScopeRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询商家公告发送范围
	 * @author 马连峰
	 */
	public StoreNoticeScope getOne(Long id){
		return storeNoticeScopeRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商家公告发送范围不存在"));
	}

	/**
	 * 分页查询商家公告发送范围
	 * @author 马连峰
	 */
	public Page<StoreNoticeScope> page(StoreNoticeScopeQueryRequest queryReq){
		return storeNoticeScopeRepository.findAll(
				StoreNoticeScopeWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询商家公告发送范围
	 * @author 马连峰
	 */
	public List<StoreNoticeScope> list(StoreNoticeScopeQueryRequest queryReq){
		return storeNoticeScopeRepository.findAll(StoreNoticeScopeWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 马连峰
	 */
	public StoreNoticeScopeVO wrapperVo(StoreNoticeScope storeNoticeScope) {
		if (storeNoticeScope != null){
			StoreNoticeScopeVO storeNoticeScopeVO = KsBeanUtil.convert(storeNoticeScope, StoreNoticeScopeVO.class);
			return storeNoticeScopeVO;
		}
		return null;
	}

}

