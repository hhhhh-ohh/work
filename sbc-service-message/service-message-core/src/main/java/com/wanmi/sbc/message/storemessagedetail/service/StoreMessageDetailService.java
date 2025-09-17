package com.wanmi.sbc.message.storemessagedetail.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationQueryProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyPageRequest;
import com.wanmi.sbc.elastic.bean.vo.storeInformation.EsCompanyInfoVO;
import com.wanmi.sbc.message.api.request.storemessagedetail.StoreMessageDetailQueryRequest;
import com.wanmi.sbc.message.bean.enums.ReadFlag;
import com.wanmi.sbc.message.bean.enums.StoreMessageType;
import com.wanmi.sbc.message.bean.enums.StoreNoticeSendStatus;
import com.wanmi.sbc.message.bean.enums.StoreNoticeTargetScope;
import com.wanmi.sbc.message.bean.vo.StoreMessageDetailVO;
import com.wanmi.sbc.message.bean.vo.StoreNoticeSendVO;
import com.wanmi.sbc.message.storemessagedetail.model.root.StoreMessageDetail;
import com.wanmi.sbc.message.storemessagedetail.repository.StoreMessageDetailRepository;
import com.wanmi.sbc.message.storenoticesend.repository.StoreNoticeSendRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>商家消息/公告业务逻辑</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Service("StoreMessageDetailService")
public class StoreMessageDetailService {

	@Autowired private StoreMessageDetailRepository storeMessageDetailRepository;

	@Autowired private StoreNoticeSendRepository storeNoticeSendRepository;

	@Autowired private EsStoreInformationQueryProvider esStoreInformationQueryProvider;

	@Autowired private EntityManager entityManager;

	/**
	 * 新增商家消息/公告
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public StoreMessageDetail add(StoreMessageDetail entity) {
		storeMessageDetailRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除商家消息/公告
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteById(String id, Long storeId) {
		storeMessageDetailRepository.deleteById(id, storeId);
	}

	/**
	 * 单个已读商家消息/公告
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void readById(String id, Long storeId) {
		storeMessageDetailRepository.readById(id, storeId);
	}

	/**
	 * 批量已读商家消息/公告
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void batchRead(StoreMessageDetailQueryRequest request) {
		StringBuilder updateSql = new StringBuilder("update store_message_detail detail");
		StringBuilder whereSql = new StringBuilder();
		updateSql.append(" set is_read = 1, update_time = now() ");
		whereSql.append(" where 1 = 1");
		if (CollectionUtils.isNotEmpty(request.getIdList())) {
			whereSql.append(" and detail.id in (:idList)");
		}
		if (CollectionUtils.isNotEmpty(request.getJoinIdList())) {
			whereSql.append(" and detail.join_id in (:joinIdList)");
		}
		if (Objects.nonNull(request.getJoinId())) {
			whereSql.append(" and detail.join_id = :joinId");
		}
		if (Objects.nonNull(request.getStoreId())) {
			whereSql.append(" and detail.store_id = :storeId");
		}
		if (Objects.nonNull(request.getIsRead())) {
			whereSql.append(" and detail.is_read = :isRead");
		}
		if (Objects.nonNull(request.getMessageType())) {
			whereSql.append(" and detail.message_type = :messageType");
		}
		if (StringUtils.isNotBlank(request.getTitle())) {
			whereSql.append(" and detail.title like concat('%', :title,'%')");
		}
		if (Objects.nonNull(request.getSendTimeBegin())) {
			whereSql.append(" and detail.send_time >= :sendTimeBegin");
		}
		if (Objects.nonNull(request.getSendTimeEnd())) {
			whereSql.append(" and detail.send_time <= :sendTimeEnd");
		}
		Query query = entityManager.createNativeQuery(updateSql.append(whereSql).toString());
		this.wrapperQueryParam(query, request);
		int rows = query.executeUpdate();
	}
	/**
	 * 组装查询参数
	 *
	 * @param query
	 * @param request
	 */
	private void wrapperQueryParam(Query query, StoreMessageDetailQueryRequest request) {
		if (CollectionUtils.isNotEmpty(request.getIdList())) {
			query.setParameter("idList", request.getIdList());
		}
		if (CollectionUtils.isNotEmpty(request.getJoinIdList())) {
			query.setParameter("joinIdList", request.getJoinIdList());
		}
		if (Objects.nonNull(request.getJoinId())) {
			query.setParameter("joinId", request.getJoinId());
		}
		if (Objects.nonNull(request.getStoreId())) {
			query.setParameter("storeId", request.getStoreId());
		}
		if (Objects.nonNull(request.getIsRead())) {
			query.setParameter("isRead", request.getIsRead().toValue());
		}
		if (Objects.nonNull(request.getMessageType())) {
			query.setParameter("messageType", request.getMessageType().toValue());
		}
		if (StringUtils.isNotBlank(request.getTitle())) {
			query.setParameter("title", request.getTitle());
		}
		if (Objects.nonNull(request.getSendTimeBegin())) {
			query.setParameter("sendTimeBegin", request.getSendTimeBegin());
		}
		if (Objects.nonNull(request.getSendTimeEnd())) {
			query.setParameter("sendTimeEnd", request.getSendTimeEnd());
		}
	}

	/**
	 * 批量删除商家消息/公告
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteByIdList(List<String> ids, Long storeId) {
		storeMessageDetailRepository.deleteByIdList(ids, storeId);
	}

	/**
	 * 单个查询商家消息/公告
	 * @author 马连峰
	 */
	public StoreMessageDetail getOne(String id, Long storeId){
		return storeMessageDetailRepository.findByIdAndStoreIdAndDelFlag(id, storeId, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商家消息/公告不存在"));
	}

	/**
	 * 分页查询商家消息/公告
	 * @author 马连峰
	 */
	public Page<StoreMessageDetail> page(StoreMessageDetailQueryRequest queryReq){
		return storeMessageDetailRepository.findAll(
				StoreMessageDetailWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询商家消息/公告
	 * @author 马连峰
	 */
	public List<StoreMessageDetail> list(StoreMessageDetailQueryRequest queryReq){
		return storeMessageDetailRepository.findAll(StoreMessageDetailWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 马连峰
	 */
	public StoreMessageDetailVO wrapperVo(StoreMessageDetail storeMessageDetail) {
		if (storeMessageDetail != null){
			StoreMessageDetailVO storeMessageDetailVO = KsBeanUtil.convert(storeMessageDetail, StoreMessageDetailVO.class);
			return storeMessageDetailVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 马连峰
	 */
	public Long count(StoreMessageDetailQueryRequest queryReq) {
		return storeMessageDetailRepository.count(StoreMessageDetailWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 批量删除节点ID或公告ID的消息
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteByJoinId(Long joinId) {
		storeMessageDetailRepository.deleteByJoinId(joinId);
	}

	/**
	 * 为公告构造消息实体列表
	 * @param noticeSendVO 公告
	 * @param storeIds 店铺ids
	 * @return
	 */
	private List<StoreMessageDetail> generateEntitiesForNotice(StoreNoticeSendVO noticeSendVO, List<Long> storeIds) {
		String title = "【平台公告】" + noticeSendVO.getTitle();
		List<StoreMessageDetail> messageDetails = new ArrayList<>();
		for (Long storeId : storeIds) {
			// 构造消息实体列表
			StoreMessageDetail detail = new StoreMessageDetail();
			detail.setStoreId(storeId);
			detail.setJoinId(noticeSendVO.getId());
			detail.setTitle(title);
			detail.setMessageType(StoreMessageType.NOTICE);
			detail.setIsRead(ReadFlag.NO);
			detail.setSendTime(noticeSendVO.getSendTime());
			detail.setDelFlag(DeleteFlag.NO);
			detail.setCreateTime(LocalDateTime.now());
			detail.setCreatePerson(noticeSendVO.getCreatePerson());
			messageDetails.add(detail);
		}
		return messageDetails;
	}

	/**
	 * 处理消息发送
	 * @param noticeSendVO
	 */
	@Transactional(rollbackFor = Exception.class)
	public void handleSendNotice(StoreNoticeSendVO noticeSendVO) {

		// 0. 分批保存，每页最大数量
		int maxPageSize = 1000;

		// 1. 店铺查询公共条件，过滤掉禁用、关店的记录
		EsCompanyPageRequest companyPageRequest = new EsCompanyPageRequest();
		companyPageRequest.setPageSize(maxPageSize);
		companyPageRequest.setAccountState(AccountState.ENABLE.toValue());
		companyPageRequest.setStoreState(StoreState.OPENING.toValue());
		companyPageRequest.setAuditState(CheckState.CHECKED.toValue());
		companyPageRequest.setDeleteFlag(DeleteFlag.NO);

		// 2. 商家范围为全部或自定义时，需要查询店铺列表，填充目标范围ids
		if (StoreNoticeTargetScope.ALL == noticeSendVO.getSupplierScope() || StoreNoticeTargetScope.CUSTOMIZE == noticeSendVO.getSupplierScope()) {
			companyPageRequest.setStoreIds(noticeSendVO.getSupplierScopeIds());
			companyPageRequest.setStoreType(StoreType.SUPPLIER);
			companyPageRequest.setPageNum(0);
			List<EsCompanyInfoVO> companyInfos;
			do {
				companyInfos = esStoreInformationQueryProvider.companyInfoPage(companyPageRequest).getContext().getEsCompanyAccountPage().getContent();
				if (CollectionUtils.isNotEmpty(companyInfos)) {
					// 构造实体列表，分页批量保存商家对应的消息
					List<Long> targetSupplierStoreIds = companyInfos.stream().map(EsCompanyInfoVO::getStoreId).collect(Collectors.toList());
					List<StoreMessageDetail> messageDetails = this.generateEntitiesForNotice(noticeSendVO, targetSupplierStoreIds);
					storeMessageDetailRepository.saveAll(messageDetails);
				}
				companyPageRequest.setPageNum(companyPageRequest.getPageNum() + 1);
			} while (companyInfos.size() == maxPageSize);
		}

		// 3. 供应商范围为全部或自定义时，需要查询店铺列表，填充目标范围ids
		if (StoreNoticeTargetScope.ALL == noticeSendVO.getProviderScope() || StoreNoticeTargetScope.CUSTOMIZE == noticeSendVO.getProviderScope()) {
			companyPageRequest.setStoreIds(noticeSendVO.getProviderScopeIds());
			companyPageRequest.setStoreType(StoreType.PROVIDER);
			companyPageRequest.setPageNum(0);
			List<EsCompanyInfoVO> companyInfos;
			do {
				companyInfos = esStoreInformationQueryProvider.companyInfoPage(companyPageRequest).getContext().getEsCompanyAccountPage().getContent();
				if (CollectionUtils.isNotEmpty(companyInfos)) {
					// 构造实体列表，分页批量保存供应商对应的消息
					List<Long> targetSupplierStoreIds = companyInfos.stream().map(EsCompanyInfoVO::getStoreId).collect(Collectors.toList());
					List<StoreMessageDetail> messageDetails = this.generateEntitiesForNotice(noticeSendVO, targetSupplierStoreIds);
					storeMessageDetailRepository.saveAll(messageDetails);
				}
				companyPageRequest.setPageNum(companyPageRequest.getPageNum() + 1);
			} while (companyInfos.size() == maxPageSize);
		}

		// 4. 全部处理完成，将状态置为已发送
		storeNoticeSendRepository.modifySendStatusById(noticeSendVO.getId(), StoreNoticeSendStatus.SENT);
	}
}

