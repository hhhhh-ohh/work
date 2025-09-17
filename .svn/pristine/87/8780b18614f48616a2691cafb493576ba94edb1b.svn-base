package com.wanmi.sbc.message.storenoticesend.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeQueryRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendAddRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendModifyRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendQueryRequest;
import com.wanmi.sbc.message.bean.enums.StoreNoticeReceiveScope;
import com.wanmi.sbc.message.bean.enums.StoreNoticeSendStatus;
import com.wanmi.sbc.message.bean.vo.StoreNoticeSendVO;
import com.wanmi.sbc.message.storenoticescope.model.root.StoreNoticeScope;
import com.wanmi.sbc.message.storenoticescope.repository.StoreNoticeScopeRepository;
import com.wanmi.sbc.message.storenoticescope.service.StoreNoticeScopeService;
import com.wanmi.sbc.message.storenoticesend.model.root.StoreNoticeSend;
import com.wanmi.sbc.message.storenoticesend.repository.StoreNoticeSendRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>商家公告业务逻辑</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Service("StoreNoticeSendService")
public class StoreNoticeSendService {

	@Autowired private StoreNoticeScopeService storeNoticeScopeService;

	@Autowired private StoreNoticeSendRepository storeNoticeSendRepository;

	@Autowired private StoreNoticeScopeRepository storeNoticeScopeRepository;

	/**
	 * 新增商家公告
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public StoreNoticeSend add(StoreNoticeSendAddRequest addRequest) {
		StoreNoticeSend noticeSend = KsBeanUtil.convert(addRequest, StoreNoticeSend.class);
		// 0. 填充默认字段，状态默认为未发送，未扫描
		noticeSend.setScanFlag(BoolFlag.NO);
		noticeSend.setSendStatus(StoreNoticeSendStatus.NOT_SENT);
		noticeSend.setCreateTime(LocalDateTime.now());
		noticeSend.setDelFlag(DeleteFlag.NO);
		// 1. 保存公告
		storeNoticeSendRepository.saveAndFlush(noticeSend);
		// 2. 保存公告范围
		saveScopeList(addRequest, noticeSend.getId());
		return noticeSend;
	}

	/**
	 * 修改商家公告
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public StoreNoticeSend modify(StoreNoticeSend noticeSend, StoreNoticeSendModifyRequest modifyRequest) {
		// 0. 填充默认字段
		noticeSend.setTitle(modifyRequest.getTitle());
		noticeSend.setContent(modifyRequest.getContent());
		noticeSend.setSendTimeType(modifyRequest.getSendTimeType());
		noticeSend.setSendTime(modifyRequest.getSendTime());
		noticeSend.setProviderScope(modifyRequest.getProviderScope());
		noticeSend.setSupplierScope(modifyRequest.getSupplierScope());
		noticeSend.setReceiveScope(modifyRequest.getReceiveScope());
		noticeSend.setUpdatePerson(modifyRequest.getUpdatePerson());
		// 状态默认为未发送，未扫描
		noticeSend.setSendStatus(StoreNoticeSendStatus.NOT_SENT);
		noticeSend.setScanFlag(BoolFlag.NO);
		noticeSend.setUpdateTime(LocalDateTime.now());
		noticeSend.setDelFlag(DeleteFlag.NO);
		// 1. 保存公告
		storeNoticeSendRepository.saveAndFlush(noticeSend);
		// 2. 删除旧发送范围
		storeNoticeScopeRepository.deleteByNoticeId(noticeSend.getId());
		// 3. 保存公告范围
		saveScopeList(modifyRequest, noticeSend.getId());
		return noticeSend;
	}

	/**
	 * 修改公告发送状态
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void modifySendStatusById(Long id, StoreNoticeSendStatus sendStatus) {
		storeNoticeSendRepository.modifySendStatusById(id, sendStatus);
	}


	/**
	 * 修改公告扫描标识
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void modifyScanFlag(List<Long> noticeIds, BoolFlag scanFlag) {
		storeNoticeSendRepository.modifyScanFlag(noticeIds, scanFlag);
	}

	/**
	 * 单个删除商家公告
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteById(Long id) {
		storeNoticeSendRepository.deleteById(id);
	}

	/**
	 * 单个查询商家公告
	 * @author 马连峰
	 */
	public StoreNoticeSend getOne(Long id){
		return storeNoticeSendRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商家公告不存在"));
	}

	/**
	 * 单个查询商家公告带出发送范围
	 * @author 马连峰
	 */
	public StoreNoticeSendVO getOneWithScope(Long id){
		StoreNoticeSend storeNoticeSend = this.getOne(id);
		StoreNoticeSendVO storeNoticeSendVO = this.wrapperVo(storeNoticeSend);
		// 查找并填充发送范围
		List<StoreNoticeScope> scopeList = storeNoticeScopeService.list(StoreNoticeScopeQueryRequest.builder().noticeId(id).build());
		scopeList.forEach(item -> {
			Long scopeId = item.getScopeId();
			if (StoreNoticeReceiveScope.SUPPLIER == item.getScopeCate()) {
				storeNoticeSendVO.getSupplierScopeIds().add(scopeId);
			} else if (StoreNoticeReceiveScope.PROVIDER == item.getScopeCate()) {
				storeNoticeSendVO.getProviderScopeIds().add(scopeId);
			}
		});
		return storeNoticeSendVO;
	}

	/**
	 * 分页查询商家公告
	 * @author 马连峰
	 */
	public Page<StoreNoticeSend> page(StoreNoticeSendQueryRequest queryReq){
		return storeNoticeSendRepository.findAll(
				StoreNoticeSendWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询商家公告
	 * @author 马连峰
	 */
	public List<StoreNoticeSend> list(StoreNoticeSendQueryRequest queryReq){
		return storeNoticeSendRepository.findAll(StoreNoticeSendWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 马连峰
	 */
	public StoreNoticeSendVO wrapperVo(StoreNoticeSend storeNoticeSend) {
		if (storeNoticeSend != null){
			StoreNoticeSendVO storeNoticeSendVO = KsBeanUtil.convert(storeNoticeSend, StoreNoticeSendVO.class);
			return storeNoticeSendVO;
		}
		return null;
	}

	/**
	 * 组装并保存发送范围实体列表
	 * @param addRequest
	 * @param noticeId
	 * @return
	 */
	public void saveScopeList(StoreNoticeSendAddRequest addRequest, Long noticeId) {
		List<StoreNoticeScope> scopeList = new ArrayList<>();
		List<Long> supplierScopeIds = addRequest.getSupplierScopeIds();
		List<Long> providerScopeIds = addRequest.getProviderScopeIds();
		// 1. 组装商家接收范围
		if (CollectionUtils.isNotEmpty(supplierScopeIds)) {
			supplierScopeIds.forEach(scopeId -> {
				StoreNoticeScope scope = new StoreNoticeScope();
				scope.setScopeId(scopeId);
				scope.setNoticeId(noticeId);
				scope.setScopeType(addRequest.getSupplierScope());
				scope.setScopeCate(StoreNoticeReceiveScope.SUPPLIER);
				scopeList.add(scope);
			});
		}
		// 2. 组装供应商接收范围
		if (CollectionUtils.isNotEmpty(providerScopeIds)) {
			providerScopeIds.forEach(scopeId -> {
				StoreNoticeScope scope = new StoreNoticeScope();
				scope.setScopeId(scopeId);
				scope.setNoticeId(noticeId);
				scope.setScopeType(addRequest.getProviderScope());
				scope.setScopeCate(StoreNoticeReceiveScope.PROVIDER);
				scopeList.add(scope);
			});
		}
		// 3. 保存发送范围
		if (CollectionUtils.isNotEmpty(scopeList)) {
			storeNoticeScopeRepository.saveAll(scopeList);
		}
	}

}

