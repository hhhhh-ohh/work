package com.wanmi.sbc.customer.communitypickup.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderQueryRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointAddRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointModifyRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointQueryRequest;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.communityleader.model.root.CommunityLeader;
import com.wanmi.sbc.customer.communitypickup.model.root.CommunityLeaderPickupPoint;
import com.wanmi.sbc.customer.communitypickup.repository.CommunityLeaderPickupPointRepository;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>团长自提点表业务逻辑</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Service
public class CommunityLeaderPickupPointService {
	@Autowired
	private CommunityLeaderPickupPointRepository communityLeaderPickupPointRepository;

	@Autowired
	private PlatformAddressQueryProvider platformAddressQueryProvider;

	/**
	 * 新增团长自提点表
	 * @author dyt
	 */
	@Transactional
	public void add(List<CommunityLeaderPickupPointAddRequest> requestList, CommunityLeader leader) {
		List<CommunityLeaderPickupPoint> pickupPoints = requestList.stream().map(s -> {
			CommunityLeaderPickupPoint point = KsBeanUtil.convert(s, CommunityLeaderPickupPoint.class);
			if (point == null) {
				return point;
			}
			point.setCustomerId(leader.getCustomerId());
			point.setLeaderId(leader.getLeaderId());
			point.setLeaderAccount(leader.getLeaderAccount());
			point.setLeaderName(leader.getLeaderName());
			point.setCheckStatus(leader.getCheckStatus());
			point.setCreateTime(LocalDateTime.now());
			point.setUpdateTime(LocalDateTime.now());
			point.setDelFlag(DeleteFlag.NO);
			if (StringUtils.isNotBlank(s.getContactCode())) {
				point.setContactNumber(s.getContactCode().concat("-").concat(point.getContactNumber()));
			}
			return point;
		}).filter(Objects::nonNull).collect(Collectors.toList());
		this.fillFullAddress(pickupPoints);
		communityLeaderPickupPointRepository.saveAll(pickupPoints);
	}

	/**
	 * 修改团长自提点表
	 * @author dyt
	 */
	@Transactional
	public void modify(List<CommunityLeaderPickupPointModifyRequest> requestList, CommunityLeader leader) {
		Map<String, CommunityLeaderPickupPointModifyRequest> pickupMap = requestList.stream()
				.collect(Collectors.toMap(CommunityLeaderPickupPointModifyRequest::getPickupPointId, Function.identity()));
		CommunityLeaderPickupPointQueryRequest queryRequest = new CommunityLeaderPickupPointQueryRequest();
		queryRequest.setLeaderId(leader.getLeaderId());
		List<CommunityLeaderPickupPoint> pickupPoints = this.list(queryRequest);
		pickupPoints.stream()
				.filter(s -> pickupMap.containsKey(s.getPickupPointId()))
				.forEach(s -> {
					CommunityLeaderPickupPointModifyRequest modifyInfo = pickupMap.get(s.getPickupPointId());
					if (StringUtils.isNotBlank(modifyInfo.getContactCode())) {
						modifyInfo.setContactNumber(modifyInfo.getContactCode().concat("-").concat(modifyInfo.getContactNumber()));
					}
					KsBeanUtil.copyPropertiesThird(modifyInfo, s);
					s.setCheckStatus(leader.getCheckStatus());
					s.setLeaderAccount(leader.getLeaderAccount());
					s.setLeaderName(leader.getLeaderName());
					s.setUpdateTime(LocalDateTime.now());
				});
		this.fillFullAddress(pickupPoints);
		communityLeaderPickupPointRepository.saveAll(pickupPoints);

	}

	/**
	 * 更新审核状态
	 * @author dyt
	 */
	@Transactional
	public void updateCheckStatusByLeaderId(LeaderCheckStatus checkStatus, String leaderId) {
		communityLeaderPickupPointRepository.updateCheckStatusByLeaderId(checkStatus, leaderId);
	}

	/**
	 * 单个删除团长自提点表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(CommunityLeaderPickupPoint entity) {
		communityLeaderPickupPointRepository.save(entity);
	}

	/**
	 * 批量删除团长自提点表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<CommunityLeaderPickupPoint> infos) {
		communityLeaderPickupPointRepository.saveAll(infos);
	}

	/**
	 * 单个查询团长自提点表
	 * @author dyt
	 */
	public CommunityLeaderPickupPoint getOne(String id){
		return communityLeaderPickupPointRepository.findByPickupPointIdAndDelFlag(id, DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "团长自提点表不存在"));
	}

	/**
	 * 分页查询团长自提点表
	 * @author dyt
	 */
	public Page<CommunityLeaderPickupPoint> page(CommunityLeaderPickupPointQueryRequest queryReq){
		return communityLeaderPickupPointRepository.findAll(
				CommunityLeaderPickupPointWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询团长自提点表
	 * @author dyt
	 */
	public List<CommunityLeaderPickupPoint> list(CommunityLeaderPickupPointQueryRequest queryReq){
		return communityLeaderPickupPointRepository.findAll(CommunityLeaderPickupPointWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 根据自提点地址获取id
	 * @param leaderQueryRequest 团长查询条件
	 * @return 团长id
	 */
	public List<String> getLeaderByAreaIds(CommunityLeaderQueryRequest leaderQueryRequest) {
		if (CollectionUtils.isEmpty(leaderQueryRequest.getAreaIds())) {
			return Collections.emptyList();
		}
		return this.list(CommunityLeaderPickupPointQueryRequest.builder()
						.likeLeaderName(leaderQueryRequest.getLikeLeaderName())
						.likeLeaderAccount(leaderQueryRequest.getLikeLeaderAccount())
						.checkStatus(leaderQueryRequest.getCheckStatus())
						.areaIds(leaderQueryRequest.getAreaIds())
						.leaderIds(leaderQueryRequest.getLeaderIdList())
						.leaderId(leaderQueryRequest.getLeaderId())
						.delFlag(DeleteFlag.NO)
						.build())
				.stream()
				.map(CommunityLeaderPickupPoint::getLeaderId).collect(Collectors.toList());
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityLeaderPickupPointVO wrapperVo(CommunityLeaderPickupPoint communityLeaderPickupPoint) {
		if (communityLeaderPickupPoint != null){
			return KsBeanUtil.convert(communityLeaderPickupPoint, CommunityLeaderPickupPointVO.class);
		}
		return null;
	}

	/**
	 * 填充全地址
	 * @param points
	 */
	private void fillFullAddress(List<CommunityLeaderPickupPoint> points) {
		List<String> aresIds = points.stream()
				.flatMap(s -> Stream.of(s.getPickupProvinceId(), s.getPickupCityId(), s.getPickupAreaId(), s.getPickupStreetId()).filter(Objects::nonNull))
				.map(Objects::toString)
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(aresIds)) {
			return;
		}
		PlatformAddressListRequest listRequest = PlatformAddressListRequest.builder().addrIdList(aresIds).delFlag(DeleteFlag.NO).build();
		Map<String, String> addressMap = platformAddressQueryProvider.list(listRequest).getContext().getPlatformAddressVOList().stream()
				.filter(o -> Objects.nonNull(o.getAddrName()))
				.collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName, (a,b) -> a));
		points.forEach(p -> {
			StringBuffer buffer = new StringBuffer();
			buffer.append(addressMap.getOrDefault(Objects.toString(p.getPickupProvinceId()), StringUtils.EMPTY));
			buffer.append(addressMap.getOrDefault(Objects.toString(p.getPickupCityId()), StringUtils.EMPTY));
			buffer.append(addressMap.getOrDefault(Objects.toString(p.getPickupAreaId()), StringUtils.EMPTY));
			buffer.append(addressMap.getOrDefault(Objects.toString(p.getPickupStreetId()), StringUtils.EMPTY));
			buffer.append(Objects.toString(p.getAddress(), StringUtils.EMPTY));
			p.setFullAddress(buffer.toString());
		});
	}

	public static void main(String[] args) {
		System.out.println(Stream.of(null,null,null).collect(Collectors.toList()));
	}
}

