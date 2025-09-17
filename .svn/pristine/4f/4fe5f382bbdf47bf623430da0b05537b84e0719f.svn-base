package com.wanmi.sbc.marketing.communityregionsetting.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingAddRequest;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingModifyRequest;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionSetting;
import com.wanmi.sbc.marketing.communityregionsetting.repository.CommunityRegionSettingRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>社区拼团区域设置表业务逻辑</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Service
public class CommunityRegionSettingService {
	@Autowired
	private CommunityRegionSettingRepository communityRegionSettingRepository;

	@Autowired
	private CommunityRegionLeaderSettingService communityRegionLeaderSettingService;

	@Autowired
	private CommunityRegionAreaSettingService communityRegionAreaSettingService;

	/**
	 * 新增社区拼团区域设置表
	 * @author dyt
	 */
	@Transactional
	public void add(CommunityRegionSettingAddRequest request) {
		CommunityRegionSetting setting = KsBeanUtil.convert(request, CommunityRegionSetting.class);
		if(setting == null){
			return;
		}
		setting = communityRegionSettingRepository.save(setting);

		if(CollectionUtils.isNotEmpty(request.getAreaIdList())){
			communityRegionAreaSettingService.add(request.getAreaIdList(), request.getAreaNameList(), setting);
		}

		if(CollectionUtils.isNotEmpty(request.getPickupPointIdList())){
			communityRegionLeaderSettingService.add(request.getPickupPointIdList(), setting);
		}
	}

	/**
	 * 修改社区拼团区域设置表
	 * @author dyt
	 */
	@Transactional
	public void modify(CommunityRegionSettingModifyRequest request) {
		CommunityRegionSetting setting = KsBeanUtil.convert(request, CommunityRegionSetting.class);
		if(setting == null){
			return;
		}
		communityRegionSettingRepository.save(setting);
		communityRegionAreaSettingService.modify(request.getAreaIdList(), request.getAreaNameList(), setting);
		communityRegionLeaderSettingService.modify(request.getPickupPointIdList(), setting);
	}

	public void exists(String name, Long notRegionId, Long storeId){
		CommunityRegionSettingQueryRequest queryRequest = new CommunityRegionSettingQueryRequest();
		queryRequest.setStoreId(storeId);
		queryRequest.setRegionName(name);
		queryRequest.setNotRegionId(notRegionId);
		if(communityRegionSettingRepository.count(CommunityRegionSettingWhereCriteriaBuilder.build(queryRequest)) > 0) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "区域名称已存在");
		}
	}

	/**
	 * 单个删除社区拼团区域设置表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(Long id) {
		communityRegionSettingRepository.deleteById(id);
		communityRegionAreaSettingService.deleteByRegionId(id);
		communityRegionLeaderSettingService.deleteByRegionId(id);
	}

	/**
	 * 批量删除社区拼团区域设置表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ids.forEach(id -> {
			communityRegionSettingRepository.deleteById(id);
		});
	}

	/**
	 * 单个查询社区拼团区域设置表
	 * @author dyt
	 */
	public CommunityRegionSetting getOne(Long id, Long storeId){
		return communityRegionSettingRepository.findByRegionIdAndStoreId(id, storeId)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购区域设置表不存在"));
	}

	/**
	 * 分页查询社区拼团区域设置表
	 * @author dyt
	 */
	public Page<CommunityRegionSetting> page(CommunityRegionSettingQueryRequest queryReq){
		return communityRegionSettingRepository.findAll(
				CommunityRegionSettingWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区拼团区域设置表
	 * @author dyt
	 */
	public List<CommunityRegionSetting> list(CommunityRegionSettingQueryRequest queryReq){
		return communityRegionSettingRepository.findAll(CommunityRegionSettingWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityRegionSettingVO wrapperVo(CommunityRegionSetting communityRegionSetting) {
		if (communityRegionSetting != null){
			return KsBeanUtil.convert(communityRegionSetting, CommunityRegionSettingVO.class);
		}
		return null;
	}
}

