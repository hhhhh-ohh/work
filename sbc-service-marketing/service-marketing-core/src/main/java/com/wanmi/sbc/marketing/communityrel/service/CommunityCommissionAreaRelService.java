package com.wanmi.sbc.marketing.communityrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityModifyRequest;
import com.wanmi.sbc.marketing.api.request.communityrel.CommunityCommissionAreaRelQueryRequest;
import com.wanmi.sbc.marketing.bean.dto.CommunityCommissionAreaRelDTO;
import com.wanmi.sbc.marketing.bean.enums.CommunityCommissionFlag;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityCommissionAreaRelVO;
import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import com.wanmi.sbc.marketing.communityrel.model.root.CommunityCommissionAreaRel;
import com.wanmi.sbc.marketing.communityrel.repository.CommunityCommissionAreaRelRepository;
import com.wanmi.sbc.marketing.communityrel.utils.CommunityCommissionAreaRelWhereCriteriaBuilder;
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
 * <p>社区团购活动佣金区域关联表业务逻辑</p>
 * @author dyt
 * @date 2023-07-24 14:40:22
 */
@Service
public class CommunityCommissionAreaRelService {
	@Autowired
	private CommunityCommissionAreaRelRepository communityCommissionAreaRelRepository;

	/**
	 * 新增社区团购活动佣金区域关联表
	 * @author dyt
	 */
	@Transactional
	public void add(CommunityActivity activity, CommunityActivityAddRequest addRequest) {
		if (CommunityCommissionFlag.PICKUP.equals(activity.getCommissionFlag())
				&& CollectionUtils.isNotEmpty(addRequest.getCommissionAreaList())) {
			for (int i = 0; i < addRequest.getCommissionAreaList().size(); i++) {
				CommunityCommissionAreaRelDTO rel = addRequest.getCommissionAreaList().get(i);
				List<CommunityCommissionAreaRel> areaList = new ArrayList<>();
				for (int j = 0; j < rel.getAreaId().size(); j++) {
					CommunityCommissionAreaRel areaRel = new CommunityCommissionAreaRel();
					areaRel.setAreaId(rel.getAreaId().get(j));
					areaRel.setAreaName(rel.getAreaName().get(j));
					areaRel.setAssistCommission(rel.getAssistCommission());
					areaRel.setPickupCommission(rel.getPickupCommission());
					areaRel.setActivityId(activity.getActivityId());
					areaRel.setGroupNo(i);
					areaList.add(areaRel);
				}
				communityCommissionAreaRelRepository.saveAll(areaList);
			}
		}
	}

	/**
	 * 修改社区团购活动佣金区域关联表
	 * @author dyt
	 */
	@Transactional
	public void modify(CommunityActivity activity, CommunityActivityModifyRequest modifyRequest) {
		communityCommissionAreaRelRepository.deleteByActivityId(activity.getActivityId());
		CommunityActivityAddRequest addRequest = CommunityActivityAddRequest.builder()
				.commissionAreaList(modifyRequest.getCommissionAreaList()).build();
		this.add(activity, addRequest);
	}

	/**
	 * 单个删除社区团购活动佣金区域关联表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(Long id) {
		communityCommissionAreaRelRepository.deleteById(id);
	}

	/**
	 * 批量删除社区团购活动佣金区域关联表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 单个查询社区团购活动佣金区域关联表
	 * @author dyt
	 */
	public CommunityCommissionAreaRel getOne(Long id){
		return communityCommissionAreaRelRepository.findById(id)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动佣金区域关联表不存在"));
	}

	/**
	 * 分页查询社区团购活动佣金区域关联表
	 * @author dyt
	 */
	public Page<CommunityCommissionAreaRel> page(CommunityCommissionAreaRelQueryRequest queryReq){
		return communityCommissionAreaRelRepository.findAll(
				CommunityCommissionAreaRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购活动佣金区域关联表
	 * @author dyt
	 */
	public List<CommunityCommissionAreaRel> list(CommunityCommissionAreaRelQueryRequest queryReq){
		return communityCommissionAreaRelRepository.findAll(CommunityCommissionAreaRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 活动填充佣金区域
	 * @param voList 活动VO
	 */
	public void fillActivity(List<CommunityActivityVO> voList) {
		List<String> ids = voList.stream()
				.filter(v -> CommunityCommissionFlag.PICKUP.equals(v.getCommissionFlag()))
				.map(CommunityActivityVO::getActivityId).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		CommunityCommissionAreaRelQueryRequest queryRequest = new CommunityCommissionAreaRelQueryRequest();
		queryRequest.setActivityIdList(ids);
		Map<String, List<CommunityCommissionAreaRel>> relMap = this.list(queryRequest).stream().collect(Collectors.groupingBy(CommunityCommissionAreaRel::getActivityId));
		List<CommunityCommissionAreaRel> empty = Collections.emptyList();
		voList.stream()
				.filter(v -> CommunityCommissionFlag.PICKUP.equals(v.getCommissionFlag()))
				.forEach(v -> {
					List<CommunityCommissionAreaRel> relList = relMap.getOrDefault(v.getActivityId(), empty);
					v.setCommissionAreaList(this.wrapperVo(relList));
				});
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public List<CommunityCommissionAreaRelVO> wrapperVo(List<CommunityCommissionAreaRel> communityCommissionAreaRel) {
		List<CommunityCommissionAreaRelVO> voList = new ArrayList<>();
		if (communityCommissionAreaRel != null){
			Map<Integer, List<CommunityCommissionAreaRel>>  relMap = communityCommissionAreaRel.stream().collect(Collectors.groupingBy(CommunityCommissionAreaRel::getGroupNo));
			relMap.forEach((key,relList) -> {
				CommunityCommissionAreaRelVO vo = new CommunityCommissionAreaRelVO();
				List<String> areaIds = new ArrayList<>();
				List<String> areaNames = new ArrayList<>();
				relList.forEach(r -> {
					areaIds.add(r.getAreaId());
					areaNames.add(r.getAreaName());
					vo.setPickupCommission(r.getPickupCommission());
					vo.setAssistCommission(r.getAssistCommission());
					vo.setActivityId(r.getActivityId());
					vo.setId(r.getId());
				});
				vo.setAreaId(areaIds);
				vo.setAreaName(areaNames);
				voList.add(vo);
			});
		}
		return voList;
	}
}

