package com.wanmi.sbc.marketing.communityrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityModifyRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityPageRequest;
import com.wanmi.sbc.marketing.api.request.communityrel.CommunityLeaderRelQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CommunityLeaderRangeType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesRangeType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityLeaderRelVO;
import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import com.wanmi.sbc.marketing.communityrel.model.root.CommunityLeaderRel;
import com.wanmi.sbc.marketing.communityrel.repository.CommunityLeaderRelRepository;
import com.wanmi.sbc.marketing.communityrel.utils.CommunityLeaderRelWhereCriteriaBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动团长关联表业务逻辑</p>
 * @author dyt
 * @date 2023-07-24 14:32:15
 */
@Service
public class CommunityLeaderRelService {
	@Autowired
	private CommunityLeaderRelRepository communityLeaderRelRepository;

	/**
	 * 新增社区团购活动团长关联表
	 * @author dyt
	 */
	@Transactional
	public void add(CommunityActivity activity, CommunityActivityAddRequest addRequest) {
		List<CommunityLeaderRel> relList = new ArrayList<>();
		if (CommunitySalesRangeType.CUSTOM.equals(activity.getSalesRange())) {
			addRequest.getSalesRangeContext().forEach(id -> {
				CommunityLeaderRel rel = new CommunityLeaderRel();
				rel.setActivityId(activity.getActivityId());
				rel.setStartTime(activity.getStartTime());
				rel.setEndTime(activity.getEndTime());
				rel.setPickupPointId(id);
				rel.setSalesType(CommunitySalesType.SELF);
				relList.add(rel);
			});
		}
		if (CommunityLeaderRangeType.CUSTOM.equals(activity.getLeaderRange())) {
			addRequest.getLeaderRangeContext().forEach(id -> {
				CommunityLeaderRel rel = new CommunityLeaderRel();
				rel.setActivityId(activity.getActivityId());
				rel.setStartTime(activity.getStartTime());
				rel.setEndTime(activity.getEndTime());
				rel.setPickupPointId(id);
				rel.setSalesType(CommunitySalesType.LEADER);
				relList.add(rel);
			});
		}
		if (CollectionUtils.isNotEmpty(relList)) {
			communityLeaderRelRepository.saveAll(relList);
		}
	}

	/**
	 * 修改社区团购活动团长关联表
	 * @author dyt
	 */
	@Transactional
	public void modify(CommunityActivity activity, CommunityActivityModifyRequest modifyRequest) {
		communityLeaderRelRepository.deleteByActivityId(activity.getActivityId());
		CommunityActivityAddRequest addRequest = CommunityActivityAddRequest.builder().salesRangeContext(modifyRequest.getSalesRangeContext()).leaderRangeContext(modifyRequest.getLeaderRangeContext()).build();
		this.add(activity, addRequest);
	}

	/**
	 * 根据帮卖自提点获取活动ID
	 *
	 * @param request 活动搜索参数
	 * @return 活动ID
	 */
	public List<String> getActivityByAssistLeader(CommunityActivityPageRequest request) {
		return this.list(CommunityLeaderRelQueryRequest.builder()
				.pickupPointId(request.getAssistPickupPointId())
				.salesType(CommunitySalesType.LEADER)
				.tabType(request.getTabType())
				.build()).stream().map(CommunityLeaderRel::getActivityId).collect(Collectors.toList());
	}

	/**
	 * 单个删除社区团购活动团长关联表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(Long id) {
		communityLeaderRelRepository.deleteById(id);
	}

	/**
	 * 批量删除社区团购活动团长关联表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 单个查询社区团购活动团长关联表
	 * @author dyt
	 */
	public CommunityLeaderRel getOne(Long id){
		return communityLeaderRelRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动团长关联表不存在"));
	}

	/**
	 * 分页查询社区团购活动团长关联表
	 * @author dyt
	 */
	public Page<CommunityLeaderRel> page(CommunityLeaderRelQueryRequest queryReq){
		return communityLeaderRelRepository.findAll(
				CommunityLeaderRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购活动团长关联表
	 * @author dyt
	 */
	public List<CommunityLeaderRel> list(CommunityLeaderRelQueryRequest queryReq){
		return communityLeaderRelRepository.findAll(CommunityLeaderRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 活动填充区域
	 * @param voList 活动VO
	 */
	public void fillActivity(List<CommunityActivityVO> voList) {
		List<String> ids = voList.stream()
				.filter(v -> CommunitySalesRangeType.CUSTOM.equals(v.getSalesRange()) || CommunityLeaderRangeType.CUSTOM.equals(v.getLeaderRange()))
				.map(CommunityActivityVO::getActivityId).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		CommunityLeaderRelQueryRequest queryRequest = new CommunityLeaderRelQueryRequest();
		queryRequest.setActivityIdList(ids);
		Map<String, List<CommunityLeaderRelVO>> relMap = this.list(queryRequest).stream().map(this::wrapperVo).collect(Collectors.groupingBy(CommunityLeaderRelVO::getActivityId));
		List<CommunityLeaderRelVO> empty = Collections.emptyList();

		voList.forEach(v -> {
			List<CommunityLeaderRelVO> relList = relMap.getOrDefault(v.getActivityId(), empty);
			if(CommunitySalesRangeType.CUSTOM.equals(v.getSalesRange())) {
				v.setSalesRangeContext(relList.stream().filter(r -> CommunitySalesType.SELF.equals(r.getSalesType())).map(CommunityLeaderRelVO::getPickupPointId).collect(Collectors.toList()));
			}
			if(CommunityLeaderRangeType.CUSTOM.equals(v.getLeaderRange())) {
				v.setLeaderRangeContext(relList.stream().filter(r -> CommunitySalesType.LEADER.equals(r.getSalesType())).map(CommunityLeaderRelVO::getPickupPointId).collect(Collectors.toList()));
			}
			v.setCommunityLeaderRelVOList(relList);
		});
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityLeaderRelVO wrapperVo(CommunityLeaderRel communityLeaderRel) {
		if (communityLeaderRel != null){
			return KsBeanUtil.convert(communityLeaderRel, CommunityLeaderRelVO.class);
		}
		return null;
	}
}

