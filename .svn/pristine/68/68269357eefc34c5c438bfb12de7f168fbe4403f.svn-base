package com.wanmi.sbc.marketing.communityregionsetting.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionLeaderSettingQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionLeaderSetting;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionSetting;
import com.wanmi.sbc.marketing.communityregionsetting.repository.CommunityRegionLeaderSettingRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>社区拼团区域设置表业务逻辑</p>
 * @author dyt
 * @date 2023-07-20 14:55:16
 */
@Service
public class CommunityRegionLeaderSettingService {
	@Autowired
	private CommunityRegionLeaderSettingRepository communityRegionLeaderSettingRepository;

	/**
	 * 新增社区拼团区域设置表
	 *
	 * @author dyt
	 */
	@Transactional
	public void add(List<String> pickupPointIdList, CommunityRegionSetting regionSetting) {
		communityRegionLeaderSettingRepository.saveAll(strToBean(pickupPointIdList, regionSetting));
	}

	/**
	 * 修改社区拼团区域设置表
	 *
	 * @author dyt
	 */
	@Transactional
	public void modify(List<String> pickupPointIdList, CommunityRegionSetting regionSetting) {
		communityRegionLeaderSettingRepository.deleteByRegionId(regionSetting.getRegionId());
		if (CollectionUtils.isNotEmpty(pickupPointIdList)) {
			this.add(pickupPointIdList, regionSetting);
		}
	}

	/**
	 * 根据区域删除省市区
	 * @author dyt
	 */
	@Transactional
	public void deleteByRegionId(Long regionId) {
		communityRegionLeaderSettingRepository.deleteByRegionId(regionId);
	}

	/**
	 * 验证区域
	 * @param pickupPointIdList 地区id
	 * @param regionId 区域id
	 * @param storeId 店铺id
	 */
	public void exists(List<String> pickupPointIdList, Long regionId, Long storeId){
		if(CollectionUtils.isEmpty(pickupPointIdList)){
			return;
		}
		CommunityRegionLeaderSettingQueryRequest request = CommunityRegionLeaderSettingQueryRequest.builder().pickupPointIds(pickupPointIdList).notRegionId(regionId).storeId(storeId).build();
		List<String> names = this.list(request).stream().map(CommunityRegionLeaderSetting::getPickupPointId).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(names)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "所选择的团长/自提点已被其他区域使用");
		}
	}

	/**
	 * 列表查询社区拼团区域设置表
	 *
	 * @author dyt
	 */
	public List<CommunityRegionLeaderSetting> list(CommunityRegionLeaderSettingQueryRequest queryReq){
		return communityRegionLeaderSettingRepository.findAll(CommunityRegionLeaderSettingWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 转换实体类
	 * @param pickupPointIdList 自提点idList
	 * @param regionSetting 区域配置
	 * @return 实体集合
	 */
	public List<CommunityRegionLeaderSetting> strToBean(List<String> pickupPointIdList, CommunityRegionSetting regionSetting) {
		return pickupPointIdList.stream().map(id -> {
			CommunityRegionLeaderSetting setting = new CommunityRegionLeaderSetting();
			setting.setPickupPointId(id);
			setting.setRegionId(regionSetting.getRegionId());
			setting.setStoreId(regionSetting.getStoreId());
			return setting;
		}).collect(Collectors.toList());
	}

	/**
	 * 填充自提点
	 * @param settingList
	 */
	public void fillRegion(List<CommunityRegionSettingVO> settingList) {
		List<Long> regionIds = settingList.stream().map(CommunityRegionSettingVO::getRegionId).collect(Collectors.toList());
		Map<Long, List<CommunityRegionLeaderSetting>> leaderSetings =
				this.list(CommunityRegionLeaderSettingQueryRequest.builder().regionIds(regionIds).build()).stream()
						.collect(Collectors.groupingBy(CommunityRegionLeaderSetting::getRegionId));
		List<CommunityRegionLeaderSetting> empty = Collections.emptyList();
		settingList.forEach(region -> {
			List<CommunityRegionLeaderSetting> areas = leaderSetings.getOrDefault(region.getRegionId(), empty);
			region.setPickupPointIdList(areas.stream().map(CommunityRegionLeaderSetting::getPickupPointId).collect(Collectors.toList()));
		});
	}
}

