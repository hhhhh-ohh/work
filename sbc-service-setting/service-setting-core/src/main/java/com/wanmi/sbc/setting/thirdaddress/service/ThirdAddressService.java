package com.wanmi.sbc.setting.thirdaddress.service;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressQueryRequest;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressModifyRequest;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressPageRequest;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressQueryRequest;
import com.wanmi.sbc.setting.bean.dto.ThirdAddressDTO;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.ThirdAddressPageVO;
import com.wanmi.sbc.setting.platformaddress.model.root.PlatformAddress;
import com.wanmi.sbc.setting.platformaddress.service.PlatformAddressService;
import com.wanmi.sbc.setting.thirdaddress.model.root.ThirdAddress;
import com.wanmi.sbc.setting.thirdaddress.repository.ThirdAddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>第三方地址映射表业务逻辑</p>
 * @author dyt
 * @date 2020-08-14 13:41:44
 */
@Slf4j
@Service("ThirdAddressService")
public class ThirdAddressService {
	@Autowired
	private ThirdAddressRepository thirdAddressRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private PlatformAddressService platformAddressService;

	@Autowired
	private ThirdAddressService thirdAddressServiceNew;


	/**
	 * 修改第三方地址映射表
	 * @author dyt
	 */
	@Transactional
	public void modify(ThirdAddressModifyRequest request) {
		//四级街道，判空要对应
		if(StringUtils.isNotBlank(request.getPlatformStreetId()) && StringUtils.isBlank(request.getThirdStreetId())){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070010);
		}else if(StringUtils.isBlank(request.getPlatformStreetId()) && StringUtils.isNotBlank(request.getThirdStreetId())){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070010);
		}

		//验证平台街道是否被映射过
		if(StringUtils.isNotBlank(request.getPlatformStreetId()) && StringUtils.isNotBlank(request.getThirdStreetId())) {
			List<ThirdAddress> thirdAddressList = list(ThirdAddressQueryRequest.builder().platformAddrId(request.getPlatformStreetId()).thirdFlag(request.getThirdFlag()).build());
			if (CollectionUtils.isNotEmpty(thirdAddressList)) {
				if (thirdAddressList.stream().filter(a -> request.getThirdStreetId().equals(a.getThirdAddrId())).count() < 1) {
					throw new SbcRuntimeException(SettingErrorCodeEnum.K070010);
				}
			}
		}

		//提取第三方地址数据
		Map<String, ThirdAddress> thirdAddressMap = list(ThirdAddressQueryRequest.builder().thirdAddrIdList(
				Arrays.asList(request.getThirdProvId(), request.getThirdCityId(), request.getThirdDistrictId(), request.getThirdStreetId()))
				.thirdFlag(request.getThirdFlag())
				.build()).stream().collect(Collectors.toMap(ThirdAddress::getThirdAddrId, Function.identity()));
		if(MapUtils.isEmpty(thirdAddressMap)){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070010);
		}

		//提取平台地址数据
		Map<String, PlatformAddress> platformAddressMap = platformAddressService.list(PlatformAddressQueryRequest.builder().addrIdList(
				Arrays.asList(request.getPlatformProvId(), request.getPlatformCityId(), request.getPlatformDistrictId(), request.getPlatformStreetId()))
				.build()).stream().collect(Collectors.toMap(PlatformAddress::getAddrId, Function.identity()));
		if(MapUtils.isEmpty(platformAddressMap)){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070010);
		}

		ThirdAddress thirdProv = thirdAddressMap.get(request.getThirdProvId());
		ThirdAddress thirdCity = thirdAddressMap.get(request.getThirdCityId());
		ThirdAddress thirdDistrict = thirdAddressMap.get(request.getThirdDistrictId());
		ThirdAddress thirdStreet = null;

		PlatformAddress platformProv = platformAddressMap.get(request.getPlatformProvId());
		PlatformAddress platformCity = platformAddressMap.get(request.getPlatformCityId());
		PlatformAddress platformDistrict = platformAddressMap.get(request.getPlatformDistrictId());
		PlatformAddress platformStreet = null;

		if(thirdProv == null || thirdCity == null || thirdDistrict == null
				|| platformProv == null || platformCity == null || platformDistrict == null ){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070010);
		}

		if(StringUtils.isNotBlank(request.getThirdStreetId())) {
			thirdStreet = thirdAddressMap.get(request.getThirdStreetId());
			if (thirdStreet == null) {
				throw new SbcRuntimeException(SettingErrorCodeEnum.K070010);
			}
		}

		if(StringUtils.isNotBlank(request.getPlatformStreetId())) {
			platformStreet = platformAddressMap.get(request.getPlatformStreetId());
		}

		if ((!this.contains(thirdProv.getAddrName(), platformProv.getAddrName()))
				|| (!(AddrLevel.PROVINCE == platformProv.getAddrLevel()))) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"请选择正确的省份"});
		}

		if ((!this.contains(thirdCity.getAddrName(), platformCity.getAddrName()))
				|| (!platformCity.getAddrParentId().equals(platformProv.getAddrId()))
				|| (!(AddrLevel.CITY == platformCity.getAddrLevel()))) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"请选择正确的城市"});
		}

		if ((!this.contains(thirdDistrict.getAddrName(), platformDistrict.getAddrName()))
				|| (!platformDistrict.getAddrParentId().equals(platformCity.getAddrId()))
				|| (!(AddrLevel.DISTRICT == platformDistrict.getAddrLevel()))) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"请选择正确的区县"});
		}

		if(thirdStreet != null) {
			if (platformStreet == null
					|| (!platformStreet.getAddrParentId().equals(platformDistrict.getAddrId()))
					|| (!(AddrLevel.STREET == platformStreet.getAddrLevel()))) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"请选择正确的街道"});
			}

			thirdStreet.setPlatformAddrId(platformStreet.getAddrId());
			thirdAddressRepository.saveAndFlush(thirdStreet);
		}
		thirdProv.setPlatformAddrId(platformProv.getAddrId());
		thirdCity.setPlatformAddrId(platformCity.getAddrId());
		thirdDistrict.setPlatformAddrId(platformDistrict.getAddrId());
		thirdAddressRepository.saveAndFlush(thirdProv);
		thirdAddressRepository.saveAndFlush(thirdCity);
		thirdAddressRepository.saveAndFlush(thirdDistrict);
	}

	/**
	 * 映射
	 * @param thirdPlatformType 第三方平台类型
	 */
	public void mapping(ThirdPlatformType thirdPlatformType) {
		log.info("第三方平台省市区映射开始");
		ThirdAddressQueryRequest queryRequest = ThirdAddressQueryRequest.builder().thirdFlag(thirdPlatformType).notAddrName("-").emplyPlatformAddrId(Boolean.TRUE).build();
		Long totalCount = thirdAddressRepository.count(ThirdAddressWhereCriteriaBuilder.build(queryRequest));
		if (totalCount > 0) {
			//提取平台地址的模糊名称数据
			List<PlatformAddress> platformAddrList = platformAddressService.list(PlatformAddressQueryRequest.builder().delFlag(DeleteFlag.NO).build());
			Map<String, String> platformAddrName = platformAddrList.stream().collect(Collectors.toMap(PlatformAddress::getAddrId, PlatformAddress::getAddrName));

			Integer pageSize = 2000;
			long pageCount = 0L;
			long m = totalCount % pageSize;
			if (m > 0) {
				pageCount = totalCount / pageSize + 1;
			} else {
				pageCount = totalCount / pageSize;
			}
			queryRequest.setPageSize(pageSize);
			//省市区优先处理
			queryRequest.putSort("level", SortType.ASC.toValue());

			int err = 0;
			for (int i = 0; i <= pageCount; i++) {
				queryRequest.setPageNum(i);
				try {
					//提取第三方地址
					List<ThirdAddress> thirdAddresses = thirdAddressRepository.findAll(ThirdAddressWhereCriteriaBuilder.build(queryRequest), queryRequest.getPageRequest()).getContent();
					//提取第三方父节点的数据
					List<String> parentIds = thirdAddresses.stream().filter(a -> (!(AddrLevel.PROVINCE == a.getLevel())) && StringUtils.isNotBlank(a.getThirdParentId())).map(ThirdAddress::getThirdParentId).collect(Collectors.toList());
					Map<String, ThirdAddress> parentAddrs = this.list(ThirdAddressQueryRequest.builder().thirdFlag(thirdPlatformType)
							.thirdAddrIdList(parentIds).build()).stream().collect(Collectors.toMap(ThirdAddress::getThirdAddrId, Function.identity()));

					thirdAddresses.stream()
							.filter(t -> StringUtils.isBlank(t.getPlatformAddrId()))
							.forEach(thirdAddress -> {
								String tmpName = thirdAddress.getAddrName();
								//根据交叉匹配和等级一致得到平台地址数据
								List<PlatformAddress> platformAddresses = platformAddrList.stream()
										.filter(p -> this.contains(p.getAddrName(), tmpName) && p.getAddrLevel() == thirdAddress.getLevel())
										.sorted(Comparator.comparing((p) -> {
											if(p.getAddrName().equals(tmpName)){
												return -1;
											}else if(this.contains(p.getAddrName(), tmpName)){
												return 0;
											}else {
												return 1;
											}})
										).collect(Collectors.toList());
								if (CollectionUtils.isNotEmpty(platformAddresses)) {
									//省级别
									if (AddrLevel.PROVINCE == thirdAddress.getLevel()) {
										thirdAddress.setPlatformAddrId(platformAddresses.get(0).getAddrId());
									} else {
										//其他级别
										//第三方的父节点
										ThirdAddress thirdParentAddress = parentAddrs.getOrDefault(thirdAddress.getThirdParentId(), new ThirdAddress());
										PlatformAddress platformAddress = null;

										//比较父节点的映射平台地址id
										if(StringUtils.isNotBlank(thirdParentAddress.getPlatformAddrId())){
											platformAddress = platformAddresses.stream()
													.filter(address -> address.getAddrParentId().equals(thirdParentAddress.getPlatformAddrId()))
													.findFirst().orElse(null);
										}

										//比较父节点的名称
										if(platformAddress == null){
											String thirdParentName = thirdParentAddress.getAddrName();
											platformAddress = platformAddresses.stream()
													.filter(p -> this.contains(platformAddrName.get(p.getAddrParentId()), thirdParentName))
													.sorted(Comparator.comparing((p) -> {
														if(platformAddrName.get(p.getAddrParentId()).equals(thirdParentName)){
															return -1;
														}else if(this.contains(platformAddrName.get(p.getAddrParentId()), tmpName)){
															return 0;
														}else {
															return 1;
														}})
													).findFirst().orElse(null);
										}

										if (platformAddress != null) {
											thirdAddress.setPlatformAddrId(platformAddress.getAddrId());
										}
									}
								}
							});
					thirdAddressServiceNew.saveAll(thirdAddresses);
					err = 0;
				} catch (Exception e) {
					log.error("映射异常", e);
					if (err < 3) {
						i--;
					}
					err++;
				}
			}
		}
		log.info("第三方平台省市区映射结束");
	}

	/**
	 * 映射[修正]
	 * @param thirdPlatformType 第三方平台类型
	 */
	public void mappingRevise(ThirdPlatformType thirdPlatformType) {
		log.info("第三方平台省市区映射[修正]开始");
		ThirdAddressQueryRequest queryRequest = ThirdAddressQueryRequest.builder().thirdFlag(thirdPlatformType).notAddrName("-").emplyPlatformAddrId(Boolean.TRUE).build();
		long totalCount = thirdAddressRepository.count(ThirdAddressWhereCriteriaBuilder.build(queryRequest));
		if (totalCount > 0) {
			//提取平台地址的模糊名称数据
			List<PlatformAddress> platformAddrList = platformAddressService.list(PlatformAddressQueryRequest.builder().delFlag(DeleteFlag.NO).build());
			Map<String, PlatformAddress> platformAddrMap = platformAddrList.stream().collect(Collectors.toMap(PlatformAddress::getAddrId, Function.identity()));
			for (int level = 0; level <= AddrLevel.STREET.toValue(); level++) {
				this.runMappingRevise(thirdPlatformType, level, platformAddrList, platformAddrMap);
			}
		}
		log.info("第三方平台省市区映射[修正]结束");
	}

	/**
	 * 执行映射[修正]
	 * @param thirdPlatformType 第三方平台类型
	 */
	public void runMappingRevise(ThirdPlatformType thirdPlatformType, int level,
								 List<PlatformAddress> platformAddrList, Map<String, PlatformAddress> platformAddrMap) {
		ThirdAddressQueryRequest queryRequest =
				ThirdAddressQueryRequest.builder().thirdFlag(thirdPlatformType).emplyPlatformAddrId(Boolean.TRUE).notAddrName("-").build();
		queryRequest.setLevel(AddrLevel.fromValue(level));
		Long totalCount = thirdAddressRepository.count(ThirdAddressWhereCriteriaBuilder.build(queryRequest));
		log.info("第三方平台省市区映射正在修正level:{}，待修正数:{}", level, totalCount);
		AtomicReference<Long> totalMatch = new AtomicReference<>(0L);
		Integer pageSize = 2000;
		long pageCount = 0L;
		long m = totalCount % pageSize;
		if (m > 0) {
			pageCount = totalCount / pageSize + 1;
		} else {
			pageCount = totalCount / pageSize;
		}
		queryRequest.setPageSize(pageSize);
		//省市区优先处理
		queryRequest.putSort("level", SortType.ASC.toValue());

		Map<String, ThirdAddress> newThirdAddressMap = new HashMap<>();

		int err = 0;

		for (int i = 0; i <= pageCount; i++) {
			queryRequest.setPageNum(i);
			try {
				//提取第三方地址
				List<ThirdAddress> thirdAddresses = thirdAddressRepository.findAll(ThirdAddressWhereCriteriaBuilder.build(queryRequest), queryRequest.getPageRequest()).getContent();
				//提取第三方父节点的数据
				List<String> parentIds = thirdAddresses.stream().filter(a -> (!(AddrLevel.PROVINCE == a.getLevel())) && StringUtils.isNotBlank(a.getThirdParentId())).map(ThirdAddress::getThirdParentId).collect(Collectors.toList());
				Map<String, ThirdAddress> thirdParentAddrMap =
						this.list(ThirdAddressQueryRequest.builder().thirdFlag(thirdPlatformType)
						.thirdAddrIdList(parentIds).build()).stream().collect(Collectors.toMap(ThirdAddress::getThirdAddrId, Function.identity()));

				thirdAddresses.stream()
						.filter(t -> StringUtils.isBlank(t.getPlatformAddrId()))
						.forEach(thirdAddress -> {
							AddrLevel thirdAddrLevel = thirdAddress.getLevel();
							String thirdAddName = thirdAddress.getAddrName();
							//根据交叉匹配和等级一致得到平台地址数据
							List<PlatformAddress> platformFilterList;
							if (AddrLevel.PROVINCE == thirdAddrLevel) {
								// 省级匹配
								platformFilterList = platformAddrList
										.stream()
										.filter(p -> AddrLevel.PROVINCE == p.getAddrLevel())
										.filter(p -> this.contains(p.getAddrName(), thirdAddName))
										.collect(Collectors.toList());
							} else {
								// 其他级匹配
								platformFilterList = this.filterAddress(thirdAddress, thirdParentAddrMap, platformAddrList);
							}

							// 目标平台地址
							PlatformAddress targetPlatformAddress = null;

							if (CollectionUtils.isNotEmpty(platformFilterList)) {
								int matchSize = platformFilterList.size();
								if (matchSize == 1) {
									// 唯一匹配，取第一个元素
									targetPlatformAddress = platformFilterList.get(0);
								} else if (matchSize > 1) {
									// 多个匹配，取equals的第一个元素
									targetPlatformAddress =
											platformFilterList.stream().filter(p -> Objects.equals(p.getAddrName(),
													thirdAddName)).findFirst().orElse(null);
								}
							}

							if (Objects.nonNull(targetPlatformAddress) && this.isSpanLevel(targetPlatformAddress, thirdAddress)) {
								int depth = this.getMaxLevel(thirdAddress);
								if (depth != AddrLevel.DISTRICT.toValue()) {
									targetPlatformAddress = null;
								} else {
									// 判断是否已被关联，已被关联的平台地址，不能匹配，防止重复
									PlatformAddress tempPlatformAddress = platformAddrMap.get(targetPlatformAddress.getAddrParentId());
									if (Objects.nonNull(tempPlatformAddress)) {
										String platformAddrId = tempPlatformAddress.getAddrId();
										Optional<ThirdAddress> relatedThirdAddressOpt = thirdAddressRepository.findByPlatformAddrIdAndDelFlag(platformAddrId, DeleteFlag.NO);
										if (relatedThirdAddressOpt.isPresent()) {
											targetPlatformAddress = null;
										}
									}
								}
							}

							if (Objects.nonNull(targetPlatformAddress)) {
								// 跨级处理
								if (this.isSpanLevel(targetPlatformAddress, thirdAddress)) {
									PlatformAddress tempPlatformAddress = platformAddrMap.get(targetPlatformAddress.getAddrParentId());
									if (Objects.nonNull(tempPlatformAddress)) {
										// 构造新地址
										String newAddrId = "S" + targetPlatformAddress.getAddrParentId();
										ThirdAddress newThirdAddress;
										if (newThirdAddressMap.containsKey(newAddrId)) {
											newThirdAddress = newThirdAddressMap.get(newAddrId);
										} else {
											newThirdAddress = new ThirdAddress();
											newThirdAddress.setId(newAddrId);
											newThirdAddress.setThirdAddrId(newAddrId);
											newThirdAddress.setThirdParentId(thirdAddress.getThirdParentId());
											newThirdAddress.setAddrName(tempPlatformAddress.getAddrName());
											newThirdAddress.setPlatformAddrId(tempPlatformAddress.getAddrId());
											newThirdAddress.setThirdFlag(thirdPlatformType);
											newThirdAddress.setLevel(tempPlatformAddress.getAddrLevel());
											newThirdAddress.setDelFlag(DeleteFlag.NO);
											newThirdAddressMap.put(newAddrId, newThirdAddress);
											thirdAddressRepository.save(newThirdAddress);
										}
										// 修改原地址上级和级别
										int thirdAddressLevelVal = newThirdAddress.getLevel().toValue() + 1;
										thirdAddress.setThirdParentId(newThirdAddress.getThirdAddrId());
										thirdAddress.setLevel(AddrLevel.fromValue(thirdAddressLevelVal));
										// 修改其下属地区级别 + 1
										int rows = 0;
										if (AddrLevel.STREET.toValue() != thirdAddressLevelVal) {
											int thirdAddressSubLevelVal = newThirdAddress.getLevel().toValue() + 2;
											rows = thirdAddressServiceNew.updateSubAddLevel(
													thirdAddress.getThirdAddrId(),
													AddrLevel.fromValue(thirdAddressSubLevelVal));
										}
										log.info("add SpanAddr: t[{},{}] p[{},{}] updateSubAddLevel:{} newThirdAddress:{}",
												thirdAddress.getAddrName(), thirdAddress.getThirdAddrId(),
												targetPlatformAddress.getAddrName(), targetPlatformAddress.getAddrId(),
												rows, newThirdAddress);
									}
								}
								// 入库
								thirdAddress.setPlatformAddrId(targetPlatformAddress.getAddrId());
								thirdAddressRepository.save(thirdAddress);
								// 统计数量
								totalMatch.getAndSet(totalMatch.get() + 1);
							}
						});
				err = 0;
			} catch (Exception e) {
				log.error("映射异常", e);
				if (err < 3) {
					i--;
				}
				err++;
			}
		}
		log.info("第三方平台省市区映射修正结束，level:{}，已修正数:{}", level, totalMatch);
	}

	/**
	 * 为 2、3、4级第三方地址，过滤出平台地址
	 * @param thirdAddress 待匹配第三方地址
	 * @param thirdParentAddrMap 第三方地址Map
	 * @param platformList 全量平台地址列表
	 * @return
	 */
	public List<PlatformAddress> filterAddress(ThirdAddress thirdAddress, Map<String, ThirdAddress> thirdParentAddrMap, List<PlatformAddress> platformList) {
		ThirdAddress thirdParent = thirdParentAddrMap.get(thirdAddress.getThirdParentId());
		String thirdAddrName = thirdAddress.getAddrName();
		if (Objects.nonNull(thirdParent) && Objects.nonNull(thirdParent.getPlatformAddrId())) {
			// 排序规则
			Comparator<PlatformAddress> comparingFun = Comparator.comparing((p) -> {
				if (p.getAddrName().equals(thirdAddrName)) {
					return -1;
				} else if (this.contains(p.getAddrName(), thirdAddrName)) {
					return 0;
				} else {
					return 1;
				}
			});
			String platformRootId = thirdParent.getPlatformAddrId();
			// 确定遍历范围
			List<PlatformAddress> childList = this.filterOneByAddrParentId(platformList, platformRootId);
			List<PlatformAddress> sameLevelList =
					childList.stream()
							.filter(p -> {
								// 同级匹配
								return this.isSameLevel(p, thirdAddress) && this.contains(p.getAddrName(), thirdAddress.getAddrName());
							})
							.sorted(comparingFun)
							.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(sameLevelList)) {
				return sameLevelList;
			}
			// 非末级地址，才继续跨级匹配
			if (AddrLevel.STREET != thirdParent.getLevel()) {
				Set<String> grandchildIds = childList.stream().map(PlatformAddress::getAddrId).collect(Collectors.toSet());
				List<PlatformAddress> grandchildList = this.filterOneByAddrParentIds(platformList, grandchildIds);
				return grandchildList.stream()
						.filter(p -> {
							// 跨级匹配
							return this.isSpanLevel(p, thirdAddress) && this.contains(p.getAddrName(), thirdAddress.getAddrName());
						})
						.sorted(comparingFun)
						.collect(Collectors.toList());
			}
		}
		return Collections.emptyList();
	}

	/**
	 * 获取第三方地址的最大级别，即叶子节点的level
	 * @param thirdAddress 获取第三方地址
	 * @return
	 */
	public int getMaxLevel(ThirdAddress thirdAddress) {
		AddrLevel level = thirdAddress.getLevel();
		String thirdAddrId = thirdAddress.getThirdAddrId();
		// 只考虑2、3级
		if (level == AddrLevel.PROVINCE || level == AddrLevel.STREET) {
			return -1;
		}
		// 当前级别
		int currentLevelVal = level.toValue();
		List<String> thirdAddrIds = Collections.singletonList(thirdAddrId);
		while (currentLevelVal < AddrLevel.STREET.toValue()) {
			AddrLevel nextLevel = AddrLevel.fromValue(currentLevelVal + 1);
			thirdAddrIds = thirdAddressRepository.findThirdAddrIdByParentIds(thirdAddrIds, nextLevel);
			if (CollectionUtils.isEmpty(thirdAddrIds)) {
				break;
			}
			// 下级非空，level++
			currentLevelVal++;
		}
		// 最终范围叶子节点的level
		return currentLevelVal;
	}

	/**
	 * 是否同级地址
	 * @param platformAddress 平台地址
	 * @param thirdAddress 第三方地址
	 * @return
	 */
	public boolean isSameLevel(PlatformAddress platformAddress, ThirdAddress thirdAddress) {
		return Objects.equals(platformAddress.getAddrLevel(), thirdAddress.getLevel());
	}

	/**
	 * 是否跨级地址
	 * @param platformAddress 平台地址
	 * @param thirdAddress 第三方地址
	 * @return
	 */
	public boolean isSpanLevel(PlatformAddress platformAddress, ThirdAddress thirdAddress) {
		return Objects.equals(platformAddress.getAddrLevel().toValue(), thirdAddress.getLevel().toValue() + 1);
	}

	/**
	 * 根据父id筛选子平台地址列表
	 * @param platformAddresses 全量平台地址列表
	 * @param addrParentId 父id
	 * @return
	 */
	public List<PlatformAddress> filterOneByAddrParentId(List<PlatformAddress> platformAddresses, String addrParentId) {
		if (Objects.isNull(addrParentId)) {
			return Collections.emptyList();
		}
		return platformAddresses.stream()
				.filter(address -> Objects.equals(address.getAddrParentId(), addrParentId))
				.collect(Collectors.toList());
	}

	/**
	 * 根据父ids筛选子平台地址列表
	 * @param platformAddresses 全量平台地址列表
	 * @param addrParentIds 父ids
	 * @return
	 */
	public List<PlatformAddress> filterOneByAddrParentIds(List<PlatformAddress> platformAddresses, Set<String> addrParentIds) {
		if (CollectionUtils.isEmpty(addrParentIds)) {
			return Collections.emptyList();
		}
		return platformAddresses.stream()
				.filter(address -> addrParentIds.contains(address.getAddrParentId()))
				.collect(Collectors.toList());
	}

	@Transactional
	public void saveAll(List<ThirdAddress> thirdAddressList) {
		thirdAddressRepository.saveAll(thirdAddressList);
	}

	@Transactional
	public int updateSubAddLevel(String thirdParentId, AddrLevel addrLevel) {
		return thirdAddressRepository.updateSubAddLevel(thirdParentId, addrLevel);
	}


	/**
	 * 分页查询第三方地址映射表
	 * @author dyt
	 */
	public MicroServicePage<ThirdAddressPageVO> page(ThirdAddressPageRequest queryReq) {
		StringBuilder sql = new StringBuilder();
		sql.append("select street.third_addr_id streetId, street.addr_name streetName, street.platform_addr_id platformStreetId, ");
		sql.append("area.third_addr_id areaId, area.addr_name areaName, area.platform_addr_id platformAreaId,");
		sql.append("city.third_addr_id cityId, city.addr_name cityName, city.platform_addr_id platformCityId,");
		sql.append("prov.third_addr_id provId, prov.addr_name provName, prov.platform_addr_id platformProvId, ");
		sql.append("street.third_flag thirdFlag ");

		StringBuilder whereSql = new StringBuilder();
		whereSql.append("from third_address street ");
		whereSql.append("INNER JOIN third_address area on area.third_addr_id = street.third_parent_id ");
		whereSql.append("INNER JOIN third_address city on city.third_addr_id = area.third_parent_id ");
		whereSql.append("INNER JOIN third_address prov on prov.third_addr_id = city.third_parent_id ");
		whereSql.append("where street.level = ").append(AddrLevel.STREET.toValue());
		whereSql.append(" and street.third_flag = :thirdFlag");
		if (StringUtils.isNotBlank(queryReq.getStreetName())) {
			whereSql.append(" AND street.addr_name LIKE concat('%', :streetName, '%') ");
		}
		if (StringUtils.isNotBlank(queryReq.getDistrictName())) {
			whereSql.append(" AND area.addr_name LIKE concat('%', :districtName, '%') ");
		}
		if (StringUtils.isNotBlank(queryReq.getCityName())) {
			whereSql.append(" AND city.addr_name LIKE concat('%', :cityName, '%') ");
		}
		if (StringUtils.isNotBlank(queryReq.getProvName())) {
			whereSql.append(" AND prov.addr_name LIKE concat('%', :provName, '%') ");
		}
		//映射状态
		if (Objects.nonNull(queryReq.getMappingFlag())) {
			if (queryReq.getMappingFlag()) {
				whereSql.append(" AND (street.addr_name = '-' or street.platform_addr_id is not null)");
				whereSql.append(" AND area.platform_addr_id is not null");
				whereSql.append(" AND city.platform_addr_id is not null");
				whereSql.append(" AND prov.platform_addr_id is not null");
			} else {
				whereSql.append(" AND ((street.addr_name != '-' and street.platform_addr_id is null)");
				whereSql.append(" OR area.platform_addr_id is null");
				whereSql.append(" OR city.platform_addr_id is null");
				whereSql.append(" OR prov.platform_addr_id is null)");
			}
		}

		Query queryCount = entityManager.createNativeQuery("select count(1) ".concat(whereSql.toString()));
		//组装查询参数
		this.wrapperQueryParam(queryCount, queryReq);
		long count = Long.parseLong(queryCount.getSingleResult().toString());
		if (count > 0) {
			Query query = entityManager.createNativeQuery(sql.append(whereSql).toString());
			//组装查询参数
			this.wrapperQueryParam(query, queryReq);
			query.setFirstResult(queryReq.getPageNum() * queryReq.getPageSize());
			query.setMaxResults(queryReq.getPageSize());
			query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<ThirdAddressPageVO> pageVOList = ThirdAddressWhereCriteriaBuilder.converter(query.getResultList());
			this.fillArea(pageVOList);
			this.fillOther(pageVOList);
			return new MicroServicePage<>(pageVOList, queryReq.getPageable(), count);
		}
		return new MicroServicePage<>(Collections.emptyList(), queryReq.getPageable(), count);
	}

	/**
	 * 列表查询第三方地址映射表
	 * @author dyt
	 */
	public List<ThirdAddress> list(ThirdAddressQueryRequest queryReq){
		return thirdAddressRepository.findAll(ThirdAddressWhereCriteriaBuilder.build(queryReq));
	}


	//合并数据
	@Transactional
	public void batchMerge(List<ThirdAddressDTO> items) {
		if(CollectionUtils.isEmpty(items)){
			return;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("insert INTO third_address(id, third_addr_id, third_parent_id, addr_name, level, third_flag, del_flag, create_time, update_time) values");
		String sqlValue = " (?, ?, ?, ?, ?, ?, 0, now(), now() ) ";
		String empStr = "";
		int len = items.size();
		for (int i = 0; i < len; i++) {
			if (i > 0) {
				sql.append(" , ");
			}
			sql.append(sqlValue);
		}
		sql.append("on duplicate key update addr_name=values(addr_name), update_time=values(update_time)");
		Query query = entityManager.createNativeQuery(sql.toString());
		int paramIndex = 1;
		for (int i = 0; i < len; i++) {
			ThirdAddressDTO item = items.get(i);
			query.setParameter(paramIndex++, item.getThirdAddrId());
			query.setParameter(paramIndex++, item.getThirdAddrId());
			query.setParameter(paramIndex++, Objects.toString(item.getThirdParentId(), empStr));
			query.setParameter(paramIndex++, Objects.toString(item.getAddrName(), empStr));
			query.setParameter(paramIndex++, item.getLevel().toValue());
			query.setParameter(paramIndex++, item.getThirdFlag().toValue());
		}
		query.executeUpdate();
	}

	/**
	 * 针对跨级地址的判等
	 * @param str1
	 * @param str2
	 * @return
	 */
	private boolean equals4SpanLevel(String str1, String str2){
		if(str1 == null || str2 == null){
			return false;
		}
		List<String> replaceWords = Arrays.asList(
				"黎族苗族自治"
		);
		for (String replaceWord : replaceWords) {
			str1 = str1.replace(replaceWord, "");
			str2 = str2.replace(replaceWord, "");
		}
		return Objects.equals(str1, str2);
	}

	/**
	 * 互相包含
	 * @param str1
	 * @param str2
	 * @return
	 */
	private boolean contains(String str1, String str2){
		if(str1 == null || str2 == null){
			return false;
		}
		if (Objects.equals(str1, str2)) {
			return true;
		}
		String replaceStr1 = this.replaceLast(str1);
		String replaceStr2 = this.replaceLast(str2);
		if (StringUtils.isBlank(replaceStr1) || StringUtils.isBlank(replaceStr2)) {
			return false;
		}
		return str1.contains(replaceStr2) || str2.contains(replaceStr1);
	}

    public static void main(String[] args) {
        System.out.println(new ThirdAddressService().contains("自治区直辖县级行政区划", "自治区直辖县级行政区划"));
    }

	//去除尾部字符
	private String replaceLast(String str) {
		if(str == null){
			return null;
		}
		char prov = '省';
		char city = '市';
		String prov1 = "特别行政区";
		String prov2 = "自治区";
		String prov3 = "街道";
		String prov4 = "州";
		String prov5 = "县";
		List<String> endList = Arrays.asList("乡", "镇", "自治州", "自治县");
		int len = str.length() - 1;
		if (str.lastIndexOf(prov) == len) {
			return str.substring(0, str.lastIndexOf(prov));
		} else if (str.lastIndexOf(city) == len) {
			return str.substring(0, str.lastIndexOf(city));
		} else if (str.lastIndexOf(prov1) > -1) {
			return str.substring(0, str.lastIndexOf(prov1));
		} else if (str.lastIndexOf(prov2) > -1) {
			return str.substring(0, str.lastIndexOf(prov2));
		} else if (str.lastIndexOf(prov3) > -1) {
			return str.substring(0, str.lastIndexOf(prov3));
		} else if (str.endsWith(prov4) && str.length() > 2) {
			return str.substring(0, str.lastIndexOf(prov4));
		} else if (str.endsWith(prov5) && str.length() > 2) {
			return str.substring(0, str.lastIndexOf(prov5));
		}
		else {
			for (String item : endList) {
				if (str.endsWith(item)) {
					return str.substring(0, str.lastIndexOf(item));
				}
			}
		}
		return str.trim();
	}

	/**
	 * 填充省市区
	 *
	 * @param pageVOList
	 */
	private void fillArea(List<ThirdAddressPageVO> pageVOList) {
		if (CollectionUtils.isNotEmpty(pageVOList)) {
			List<String> addrIds = new ArrayList<>();
			pageVOList.forEach(detail -> {
				addrIds.add(Objects.toString(detail.getPlatformProvId()));
				addrIds.add(Objects.toString(detail.getPlatformCityId()));
				addrIds.add(Objects.toString(detail.getPlatformDistrictId()));
				addrIds.add(Objects.toString(detail.getPlatformStreetId()));
			});
			if (CollectionUtils.isEmpty(addrIds)) {
				return;
			}
			Map<String, String> platformAddressMap = platformAddressService.list(PlatformAddressQueryRequest.builder().addrIdList(addrIds)
					.delFlag(DeleteFlag.NO)
					.build()).stream().filter(a -> StringUtils.isNotBlank(a.getAddrName()))
					.collect(Collectors.toMap(PlatformAddress::getAddrId, PlatformAddress::getAddrName));
			if (MapUtils.isNotEmpty(platformAddressMap)) {
				pageVOList.forEach(detail -> {
					StringBuilder name = new StringBuilder();
					if (StringUtils.isNotBlank(detail.getPlatformProvId()) && platformAddressMap.containsKey(detail.getPlatformProvId())) {
						name.append(platformAddressMap.get(detail.getPlatformProvId()));
						if (StringUtils.isNotBlank(detail.getPlatformCityId()) && platformAddressMap.containsKey(detail.getPlatformCityId())) {
							name.append(platformAddressMap.get(detail.getPlatformCityId()));
							if (StringUtils.isNotBlank(detail.getPlatformDistrictId()) && platformAddressMap.containsKey(detail.getPlatformDistrictId())) {
								name.append(platformAddressMap.get(detail.getPlatformDistrictId()));
								if (StringUtils.isNotBlank(detail.getPlatformStreetId()) && platformAddressMap.containsKey(detail.getPlatformStreetId())) {
									name.append(platformAddressMap.get(detail.getPlatformStreetId()));
								}
							}
						}
					}
					detail.setPlatformAddress(name.toString());
				});
			}
		}
	}

	/**
	 * 填充其他街道
	 *
	 * @param pageVOList
	 */
	private void fillOther(List<ThirdAddressPageVO> pageVOList) {
		pageVOList.stream().filter(p -> String.valueOf(p.getStreetName()).contains("-")).forEach(p -> p.setPlatformStreetId("0"));
	}

	/**
	 * 组装查询参数
	 *
	 * @param query
	 * @param queryReq
	 */
	private void wrapperQueryParam(Query query, ThirdAddressPageRequest queryReq) {
		query.setParameter("thirdFlag", queryReq.getThirdFlag().toValue());
		if (StringUtils.isNotBlank(queryReq.getStreetName())) {
			query.setParameter("streetName", XssUtils.replaceLikeWildcard(queryReq.getStreetName()));
		}
		if (StringUtils.isNotBlank(queryReq.getDistrictName())) {
			query.setParameter("districtName", XssUtils.replaceLikeWildcard(queryReq.getDistrictName()));
		}
		if (StringUtils.isNotBlank(queryReq.getCityName())) {
			query.setParameter("cityName", XssUtils.replaceLikeWildcard(queryReq.getCityName()));
		}
		if (StringUtils.isNotBlank(queryReq.getProvName())) {
			query.setParameter("provName", XssUtils.replaceLikeWildcard(queryReq.getProvName()));
		}
	}
}

