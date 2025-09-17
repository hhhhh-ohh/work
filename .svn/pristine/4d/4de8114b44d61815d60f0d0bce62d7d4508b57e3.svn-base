package com.wanmi.sbc.marketing.communitysetting.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderAreaSummaryType;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;
import com.wanmi.sbc.marketing.bean.vo.CommunitySettingVO;
import com.wanmi.sbc.marketing.communitysetting.model.root.CommunitySetting;
import com.wanmi.sbc.marketing.communitysetting.repository.CommunitySettingRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>社区拼团商家设置表业务逻辑</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@Service
public class CommunitySettingService {
	@Autowired
	private CommunitySettingRepository communitySettingRepository;

	/**
	 * 新增社区拼团商家设置表
	 * @author dyt
	 */
	@Transactional
	public CommunitySetting save(CommunitySetting entity) {
		communitySettingRepository.save(entity);
		return entity;
	}


	/**
	 * 单个查询社区拼团商家设置表
	 * @author dyt
	 */
	public CommunitySetting getOne(Long id){
		CommunitySetting setting = new CommunitySetting();
		setting.setStoreId(id);
		setting.setDeliveryOrderType(String.valueOf(DeliveryOrderSummaryType.LEADER.toValue()));
		setting.setDeliveryAreaType(DeliveryOrderAreaSummaryType.PROVINCE);
		return communitySettingRepository.findById(id).orElse(setting);
	}

	/**
	 * 列表查询社区拼团商家设置表
	 * @author dyt
	 */
	public List<CommunitySetting> list(CommunitySettingQueryRequest queryReq){
		return communitySettingRepository.findAll(CommunitySettingWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunitySettingVO wrapperVo(CommunitySetting communitySetting) {
		if (communitySetting != null) {
			CommunitySettingVO communitySettingVO = KsBeanUtil.convert(communitySetting, CommunitySettingVO.class);
			if (communitySettingVO != null && StringUtils.isNotBlank(communitySetting.getDeliveryOrderType())) {
				communitySettingVO.setDeliveryOrderTypes(DeliveryOrderSummaryType.fromValue(communitySetting.getDeliveryOrderType()));
			}
			return communitySettingVO;
		}
		return null;
	}
}

