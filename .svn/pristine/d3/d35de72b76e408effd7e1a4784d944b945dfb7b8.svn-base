package com.wanmi.sbc.marketing.communityrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityModifyRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityPageRequest;
import com.wanmi.sbc.marketing.api.request.communityrel.CommunityAreaRelQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CommunityLeaderRangeType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesRangeType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityAreaRelVO;
import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import com.wanmi.sbc.marketing.communityrel.model.root.CommunityAreaRel;
import com.wanmi.sbc.marketing.communityrel.repository.CommunityAreaRelRepository;
import com.wanmi.sbc.marketing.communityrel.utils.CommunityAreaRelWhereCriteriaBuilder;
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
 * <p>社区团购活动区域关联表业务逻辑</p>
 * @author dyt
 * @date 2023-07-24 14:38:10
 */
@Service
public class CommunityAreaRelService {
	@Autowired
	private CommunityAreaRelRepository communityAreaRelRepository;

	/**
	 * 新增社区团购活动区域关联表
	 * @author dyt
	 */
	@Transactional
	public void add(CommunityActivity activity, CommunityActivityAddRequest addRequest) {
		List<CommunityAreaRel> relList = new ArrayList<>();
		if (CommunitySalesRangeType.AREA.equals(activity.getSalesRange())) {
			for (int i = 0; i < addRequest.getSalesRangeContext().size(); i++) {
				CommunityAreaRel rel = new CommunityAreaRel();
				rel.setActivityId(activity.getActivityId());
				rel.setAreaId(addRequest.getSalesRangeContext().get(i));
//				rel.setAreaName(addRequest.getSalesRangeAreaNames().get(i));
				rel.setStartTime(activity.getStartTime());
				rel.setEndTime(activity.getEndTime());
				rel.setSalesType(CommunitySalesType.SELF);
				relList.add(rel);
			}
		}
		if (CommunityLeaderRangeType.AREA.equals(activity.getLeaderRange())) {
			for (int i = 0; i < addRequest.getLeaderRangeContext().size(); i++) {
				CommunityAreaRel rel = new CommunityAreaRel();
				rel.setActivityId(activity.getActivityId());
				rel.setAreaId(addRequest.getLeaderRangeContext().get(i));
//					rel.setAreaName(addRequest.getLeaderRangeAreaNames().get(i));
				rel.setStartTime(activity.getStartTime());
				rel.setEndTime(activity.getEndTime());
				rel.setSalesType(CommunitySalesType.LEADER);
				relList.add(rel);
			}
		}
		if (CollectionUtils.isNotEmpty(relList)) {
			communityAreaRelRepository.saveAll(relList);
		}
	}

	/**
	 * 修改社区团购活动区域关联表
	 * @author dyt
	 */
	@Transactional
	public void modify(CommunityActivity activity, CommunityActivityModifyRequest modifyRequest) {
		communityAreaRelRepository.deleteByActivityId(activity.getActivityId());
		CommunityActivityAddRequest addRequest = CommunityActivityAddRequest.builder().salesRangeContext(modifyRequest.getSalesRangeContext()).leaderRangeContext(modifyRequest.getLeaderRangeContext()).build();
		this.add(activity, addRequest);
	}

	/**
	 * 活动填充区域
	 * @param voList 活动VO
	 */
	public void fillActivity(List<CommunityActivityVO> voList) {
		List<String> ids = voList.stream()
				.filter(v -> CommunitySalesRangeType.AREA.equals(v.getSalesRange()) || CommunityLeaderRangeType.AREA.equals(v.getLeaderRange()))
				.map(CommunityActivityVO::getActivityId).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		CommunityAreaRelQueryRequest queryRequest = new CommunityAreaRelQueryRequest();
		queryRequest.setActivityIdList(ids);
		Map<String, List<CommunityAreaRelVO>> relMap = this.list(queryRequest).stream().map(this::wrapperVo).collect(Collectors.groupingBy(CommunityAreaRelVO::getActivityId));
		List<CommunityAreaRelVO> empty = Collections.emptyList();

		voList.forEach(v -> {
			List<CommunityAreaRelVO> relList = relMap.getOrDefault(v.getActivityId(), empty);
			if (CommunitySalesRangeType.AREA.equals(v.getSalesRange())) {
				v.setSalesRangeContext(relList.stream().filter(r -> CommunitySalesType.SELF.equals(r.getSalesType())).map(CommunityAreaRelVO::getAreaId).collect(Collectors.toList()));
				v.setSalesRangeAreaNames(relList.stream().filter(r -> CommunitySalesType.SELF.equals(r.getSalesType())).map(CommunityAreaRelVO::getAreaName).collect(Collectors.toList()));
			}
			if (CommunityLeaderRangeType.AREA.equals(v.getLeaderRange())) {
				v.setLeaderRangeContext(relList.stream().filter(r -> CommunitySalesType.LEADER.equals(r.getSalesType())).map(CommunityAreaRelVO::getAreaId).collect(Collectors.toList()));
				v.setLeaderRangeAreaNames(relList.stream().filter(r -> CommunitySalesType.LEADER.equals(r.getSalesType())).map(CommunityAreaRelVO::getAreaName).collect(Collectors.toList()));
			}
		});
	}

	/**
	 * 根据帮卖区域获取活动ID
	 *
	 * @param request 活动搜索参数
	 * @return 活动ID
	 */
	public List<String> getActivityByAssistLeader(CommunityActivityPageRequest request) {
		return this.list(CommunityAreaRelQueryRequest.builder()
				.areaIdList(request.getAssistAreaIds())
				.salesType(CommunitySalesType.LEADER)
				.tabType(request.getTabType())
				.build()).stream().map(CommunityAreaRel::getActivityId).collect(Collectors.toList());
	}

	/**
	 * 单个删除社区团购活动区域关联表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(Long id) {
		communityAreaRelRepository.deleteById(id);
	}

	/**
	 * 批量删除社区团购活动区域关联表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 单个查询社区团购活动区域关联表
	 * @author dyt
	 */
	public CommunityAreaRel getOne(Long id){
		return communityAreaRelRepository.findById(id).orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动区域关联表不存在"));
	}

	/**
	 * 分页查询社区团购活动区域关联表
	 * @author dyt
	 */
	public Page<CommunityAreaRel> page(CommunityAreaRelQueryRequest queryReq){
		return communityAreaRelRepository.findAll(CommunityAreaRelWhereCriteriaBuilder.build(queryReq), queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购活动区域关联表
	 * @author dyt
	 */
	public List<CommunityAreaRel> list(CommunityAreaRelQueryRequest queryReq){
		return communityAreaRelRepository.findAll(CommunityAreaRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityAreaRelVO wrapperVo(CommunityAreaRel communityAreaRel) {
		if (communityAreaRel != null){
			return KsBeanUtil.convert(communityAreaRel, CommunityAreaRelVO.class);
		}
		return null;
	}
}

