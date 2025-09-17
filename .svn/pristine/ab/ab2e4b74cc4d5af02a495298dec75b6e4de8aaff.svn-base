package com.wanmi.sbc.marketing.communityrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityModifyRequest;
import com.wanmi.sbc.marketing.api.request.communityrel.CommunityCommissionLeaderRelQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CommunityCommissionFlag;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityCommissionLeaderRelVO;
import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import com.wanmi.sbc.marketing.communityrel.model.root.CommunityCommissionLeaderRel;
import com.wanmi.sbc.marketing.communityrel.repository.CommunityCommissionLeaderRelRepository;
import com.wanmi.sbc.marketing.communityrel.utils.CommunityCommissionLeaderRelWhereCriteriaBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动佣金团长关联表业务逻辑</p>
 * @author dyt
 * @date 2023-07-24 14:43:24
 */
@Service
public class CommunityCommissionLeaderRelService {
	@Autowired
	private CommunityCommissionLeaderRelRepository communityCommissionLeaderRelRepository;

	/**
	 * 新增社区团购活动佣金团长关联表
	 * @author dyt
	 */
	@Transactional
	public void add(CommunityActivity activity, CommunityActivityAddRequest addRequest) {
		if (CommunityCommissionFlag.PICKUP.equals(activity.getCommissionFlag())
				&& CollectionUtils.isNotEmpty(addRequest.getCommissionLeaderList())) {
			List<CommunityCommissionLeaderRel> relList = addRequest.getCommissionLeaderList().stream().map(a -> {
				CommunityCommissionLeaderRel rel = new CommunityCommissionLeaderRel();
				rel.setLeaderId(a.getLeaderId());
				rel.setPickupPointId(a.getPickupPointId());
				rel.setAssistCommission(a.getAssistCommission());
				rel.setPickupCommission(a.getPickupCommission());
				rel.setActivityId(activity.getActivityId());
				return rel;
			}).collect(Collectors.toList());
			communityCommissionLeaderRelRepository.saveAll(relList);
		}
	}

	/**
	 * 修改社区团购活动佣金团长关联表
	 * @author dyt
	 */
	@Transactional
	public void modify(CommunityActivity activity, CommunityActivityModifyRequest modifyRequest) {
		communityCommissionLeaderRelRepository.deleteByActivityId(activity.getActivityId());
		CommunityActivityAddRequest addRequest = CommunityActivityAddRequest.builder()
				.commissionLeaderList(modifyRequest.getCommissionLeaderList()).build();
		this.add(activity, addRequest);
	}

	/**
	 * 单个删除社区团购活动佣金团长关联表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(Long id) {
		communityCommissionLeaderRelRepository.deleteById(id);
	}

	/**
	 * 批量删除社区团购活动佣金团长关联表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 单个查询社区团购活动佣金团长关联表
	 * @author dyt
	 */
	public CommunityCommissionLeaderRel getOne(Long id){
		return communityCommissionLeaderRelRepository.findById(id)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动佣金团长关联表不存在"));
	}

	/**
	 * 分页查询社区团购活动佣金团长关联表
	 * @author dyt
	 */
	public Page<CommunityCommissionLeaderRel> page(CommunityCommissionLeaderRelQueryRequest queryReq){
		return communityCommissionLeaderRelRepository.findAll(
				CommunityCommissionLeaderRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购活动佣金团长关联表
	 * @author dyt
	 */
	public List<CommunityCommissionLeaderRel> list(CommunityCommissionLeaderRelQueryRequest queryReq){
		return communityCommissionLeaderRelRepository.findAll(CommunityCommissionLeaderRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 活动填充佣金自提点
	 * @param voList 活动VO
	 */
	public void fillActivity(List<CommunityActivityVO> voList, String leaderId) {
		List<String> ids = voList.stream()
				.filter(v -> CommunityCommissionFlag.PICKUP.equals(v.getCommissionFlag()))
				.map(CommunityActivityVO::getActivityId).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		CommunityCommissionLeaderRelQueryRequest queryRequest = new CommunityCommissionLeaderRelQueryRequest();
		queryRequest.setActivityIdList(ids);
		queryRequest.setLeaderId(leaderId);
		Map<String, List<CommunityCommissionLeaderRelVO>> relMap = this.list(queryRequest).stream().map(this::wrapperVo).collect(Collectors.groupingBy(CommunityCommissionLeaderRelVO::getActivityId));
		List<CommunityCommissionLeaderRelVO> empty = Collections.emptyList();
		voList.stream()
				.filter(v -> CommunityCommissionFlag.PICKUP.equals(v.getCommissionFlag()))
				.forEach(v -> v.setCommissionLeaderList(relMap.getOrDefault(v.getActivityId(), empty)));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityCommissionLeaderRelVO wrapperVo(CommunityCommissionLeaderRel communityCommissionLeaderRel) {
		if (communityCommissionLeaderRel != null){
			CommunityCommissionLeaderRelVO communityCommissionLeaderRelVO = KsBeanUtil.convert(communityCommissionLeaderRel, CommunityCommissionLeaderRelVO.class);
			return communityCommissionLeaderRelVO;
		}
		return null;
	}
}

