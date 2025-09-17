package com.wanmi.sbc.marketing.communityregionsetting.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionAreaSettingQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionAreaSetting;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionSetting;
import com.wanmi.sbc.marketing.communityregionsetting.repository.CommunityRegionAreaSettingRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>社区拼团区域设置表业务逻辑</p>
 * @author dyt
 * @date 2023-07-20 14:56:35
 */
@Service
public class CommunityRegionAreaSettingService {
	@Autowired
	private CommunityRegionAreaSettingRepository communityRegionAreaSettingRepository;

	/**
	 * 新增社区拼团区域设置表
	 * @author dyt
	 */
	@Transactional
	public void add(List<String> areaIds,List<String> areaNames, CommunityRegionSetting setting) {
		List<CommunityRegionAreaSetting> settings = strToBean(areaIds, areaNames, setting);
		communityRegionAreaSettingRepository.saveAll(settings);
	}

	/**
	 * 修改社区拼团区域设置表
	 * @author dyt
	 */
	@Transactional
	public void modify(List<String> areaIds,List<String> areaNames, CommunityRegionSetting setting) {
		communityRegionAreaSettingRepository.deleteByRegionId(setting.getRegionId());
		if (CollectionUtils.isNotEmpty(areaIds)) {
			this.add(areaIds, areaNames, setting);
		}
	}

	/**
	 * 根据区域删除省市区
	 * @author dyt
	 */
	@Transactional
	public void deleteByRegionId(Long regionId) {
		communityRegionAreaSettingRepository.deleteByRegionId(regionId);
	}

	/**
	 * 验证区域
	 * @param areaIds 地区id
	 * @param regionId 区域id
	 * @param storeId 店铺id
	 */
	public void exists(List<String> areaIds, Long regionId, Long storeId) {
		if(CollectionUtils.isEmpty(areaIds)) {
			return;
		}
		CommunityRegionAreaSettingQueryRequest request = CommunityRegionAreaSettingQueryRequest.builder().areaIds(areaIds).notRegionId(regionId).storeId(storeId).build();
		List<String> names = this.list(request).stream().map(CommunityRegionAreaSetting::getAreaName).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(names)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "所选择的地区已被其他区域使用");
		}
	}

	/**
	 * 列表查询社区拼团商家设置表
	 * @author dyt
	 */
	public List<CommunityRegionAreaSetting> list(CommunityRegionAreaSettingQueryRequest queryReq){
		return communityRegionAreaSettingRepository.findAll(CommunityRegionAreaSettingWhereCriteriaBuilder.build(queryReq));
	}


	/**
	 * 转换实体类
	 * @param areaIds 省市区idList
	 * @param areaNames 省市区名称List
	 * @param regionSetting 区域配置
	 * @return 实体集合
	 */
	public List<CommunityRegionAreaSetting> strToBean(List<String> areaIds, List<String> areaNames, CommunityRegionSetting regionSetting) {
		int len = areaIds.size();
		List<CommunityRegionAreaSetting> settings = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			CommunityRegionAreaSetting setting = new CommunityRegionAreaSetting();
			setting.setAreaId(areaIds.get(i));
			setting.setAreaName(areaNames.get(i));
			setting.setRegionId(regionSetting.getRegionId());
			setting.setStoreId(regionSetting.getStoreId());
			settings.add(setting);
		}
		return settings;
	}

	/**
	 * 填充省市区
	 * @param settingList
	 */
	public void fillRegion(List<CommunityRegionSettingVO> settingList) {
		List<Long> regionIds = settingList.stream().map(CommunityRegionSettingVO::getRegionId).collect(Collectors.toList());
		Map<Long, List<CommunityRegionAreaSetting>> areaSettingMap =
				this.list(CommunityRegionAreaSettingQueryRequest.builder().regionIds(regionIds).build()).stream()
						.collect(Collectors.groupingBy(CommunityRegionAreaSetting::getRegionId));
		List<CommunityRegionAreaSetting> empty = Collections.emptyList();
		settingList.forEach(region -> {
			List<CommunityRegionAreaSetting> areas = areaSettingMap.getOrDefault(region.getRegionId(), empty);
			region.setAreaIdList(areas.stream().map(CommunityRegionAreaSetting::getAreaId).collect(Collectors.toList()));
			region.setAreaNameList(areas.stream().map(CommunityRegionAreaSetting::getAreaName).collect(Collectors.toList()));
		});
	}
}

