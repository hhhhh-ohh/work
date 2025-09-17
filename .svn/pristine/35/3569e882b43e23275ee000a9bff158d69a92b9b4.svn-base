package com.wanmi.sbc.communitypickup.service;

import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>团长自提点表业务逻辑</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Service
public class CommunityLeaderPickupPointService {

	@Autowired
	private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

	/**
	 * 列表查询团长自提点表
	 * @author dyt
	 */
	public List<CommunityLeaderPickupPointVO> list(CommunityLeaderPickupPointListRequest queryReq){
		return communityLeaderPickupPointQueryProvider.list(queryReq).getContext().getCommunityLeaderPickupPointList();
	}


	/**
	 * 商家设置-区域设置列表填充自提点名称
	 * @param settingList
	 */
	public void fillPointsName(List<CommunityRegionSettingVO> settingList) {
		List<String> ids = settingList.stream().filter(s -> CollectionUtils.isNotEmpty(s.getPickupPointIdList()))
				.flatMap(s -> s.getPickupPointIdList().stream()).collect(Collectors.toList());
		Map<String, String> pointsNameMap = this.list(
						CommunityLeaderPickupPointListRequest.builder().pickupPointIdList(ids).build()).stream()
				.collect(Collectors.toMap(CommunityLeaderPickupPointVO::getPickupPointId, CommunityLeaderPickupPointVO::getPickupPointName));
		settingList.stream().filter(s -> CollectionUtils.isNotEmpty(s.getPickupPointIdList()))
				.forEach(s -> s.setPickupPointNameList(
						s.getPickupPointIdList().stream().map(pointsNameMap::get).filter(Objects::nonNull)
								.collect(Collectors.toList())));
	}
}

