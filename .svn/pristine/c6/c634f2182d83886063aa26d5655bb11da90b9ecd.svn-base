package com.wanmi.sbc.setting.platformaddress.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Pinyin4jUtil;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressQueryRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressVerifyRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.setting.platformaddress.model.root.PlatformAddress;
import com.wanmi.sbc.setting.platformaddress.repository.PlatformAddressRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>平台地址信息业务逻辑</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@Service("PlatformAddressService")
public class PlatformAddressService {
	@Autowired
	private PlatformAddressRepository platformAddressRepository;

	@Autowired
	private EntityManager entityManager;

	/**
	 * 新增平台地址信息
	 * @author dyt
	 */
	@Transactional
	public PlatformAddress add(PlatformAddress entity) {
		entity.setId(entity.getAddrId());
        entity.setDelFlag(DeleteFlag.NO);
		entity.setDataType(1);
		entity.setCreateTime(LocalDateTime.now());
		entity.setUpdateTime(LocalDateTime.now());
		entity.setPinYin(Pinyin4jUtil.getPinyin(entity.getAddrName()));
		platformAddressRepository.save(entity);
		return entity;
	}

	/**
	 * 修改平台地址信息
	 * @author dyt
	 */
	@Transactional
	public PlatformAddress modify(PlatformAddress entity) {
		entity.setPinYin(Pinyin4jUtil.getPinyin(entity.getAddrName()));
		platformAddressRepository.save(entity);
		return entity;
	}

	/**
	 * 修改平台地址
	 * @param entity
	 * @return
	 */
	@Transactional
	public PlatformAddressVO modifyPlatformAddress(PlatformAddressVO entity,String oldAddrId) {
		entityManager.clear();
		entity.setPinYin(Pinyin4jUtil.getPinyin(entity.getAddrName()));
		platformAddressRepository.modifyPlatformAddress(entity.getAddrId(),entity.getAddrName(),entity.getPinYin(),oldAddrId);
		return entity;
	}

	/**
	 * 单个删除平台地址信息
	 * @author dyt
	 */
	@Transactional
	public void deleteById(String id) {
		platformAddressRepository.deleteById(id);
	}

	/**
	 * 批量删除平台地址信息
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		platformAddressRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询平台地址信息
	 * @author dyt
	 */
	public PlatformAddress getOne(String id){
		return platformAddressRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "平台地址信息不存在"));
	}

	/**
	 * 单个查询平台地址信息
	 * @author dyt
	 */
	public PlatformAddress findByIdAndDelFlag(String id){
		return platformAddressRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
				.orElse(null);
	}

	/**
	 * 单个查询平台地址信息
	 * @author xufeng
	 */
	public PlatformAddress findByAddrNameAndAddrParentIdAndDelFlag(String addrName, String addrParentId){
		// 先精确查找，查不到再模糊查，取一条数据
		PlatformAddress platformAddress = platformAddressRepository.findByEqual(addrName, addrParentId);
		if (Objects.isNull(platformAddress)){
			List<PlatformAddress> platformAddressList = platformAddressRepository.findByLike(addrName, addrParentId);
			if (CollectionUtils.isNotEmpty(platformAddressList)){
				platformAddress = platformAddressList.get(0);
			}
		}
		return platformAddress;
	}
	public PlatformAddress findByAddrNameAndDelFlag(String addrName){
		// 先精确查找，查不到再模糊查，取一条数据
		PlatformAddress platformAddress = platformAddressRepository.findByEqualAddrName(addrName);
		return platformAddress;
	}
	/**
	 * 分页查询平台地址信息
	 * @author dyt
	 */
	public Page<PlatformAddress> page(PlatformAddressQueryRequest queryReq){
		return platformAddressRepository.findAll(
				PlatformAddressWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询平台地址信息
	 * @author dyt
	 */
	public List<PlatformAddress> list(PlatformAddressQueryRequest queryReq){
		return platformAddressRepository.findAll(PlatformAddressWhereCriteriaBuilder.build(queryReq), Sort.by(Arrays.asList(Sort.Order.desc("dataType"), Sort.Order.asc("sortNo"), Sort.Order.desc("createTime"))));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public PlatformAddressVO wrapperVo(PlatformAddress platformAddress) {
		if (platformAddress != null){
			PlatformAddressVO platformAddressVO = KsBeanUtil.convert(platformAddress, PlatformAddressVO.class);
            platformAddressVO.setLeafFlag(Boolean.TRUE);
			return platformAddressVO;
		}
		return null;
	}

	/**
	 * 列表查询平台省市地址信息
	 * @author dyt
	 */
	public List<PlatformAddress> provinceCityList(List<String> addrIds){
		return platformAddressRepository.findAllProvinceAndCity(addrIds);
	}

	/**
	 * 统计数量
	 * @author yhy
	 */
	public int countNum(String id){
		return platformAddressRepository.countById(id);
	}

	/**
	 * 统计数量
	 * 按照省市区进行数量统计
	 */
	public int countNumByIdAndParentId(String province,String city,String area){
		return platformAddressRepository.countByIdAndParentId(province,city,area);
	}

	/** 当前区域是否为省
	 * @param addrId 区域id
	 * @return ture 是，false 不是
	 */
	public Boolean isAreaProvince(String addrId) {
		return platformAddressRepository.isAreaProvince(addrId) > 0;
	}

	/**
	 * 列表查询平台地址信息，根据首字母聚合
	 * @author dyt
	 */
	public Map<String,List<PlatformAddressVO>> GroupByPinYin(PlatformAddressQueryRequest queryReq){
		List<PlatformAddress> platformAddressList = platformAddressRepository.findAll(PlatformAddressWhereCriteriaBuilder.build(queryReq), Sort.by(Arrays.asList(Sort.Order.desc("dataType"), Sort.Order.asc("sortNo"), Sort.Order.desc("createTime"))));
		if(CollectionUtils.isEmpty(platformAddressList)){
			return null;
		}
		List<PlatformAddressVO> platformAddressVOList =
				platformAddressList.stream()
						.map(entity -> this.wrapperVo(entity))
						.sorted(Comparator.comparing(PlatformAddressVO::getPinYin))
						.collect(Collectors.toList());
		//分组后的城市集合
		Map<String, List<PlatformAddressVO>> map = new TreeMap<>();
		String[] arr ={"A","B","C","D","E","F","G","H","I","J","K","L","M",
				"N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

		//创建不同key的map集合, Map("A",list1), Map("B",list2)
		for(int i = 0; i < arr.length; i++){
			map.put(arr[i], new ArrayList<>());
		}

		for (PlatformAddressVO c : platformAddressVOList) {
			String pinyin = c.getPinYin().substring(0, 1).toUpperCase();
			if (map.containsKey(pinyin)) {
				map.get(pinyin).add(c);
			}
		}
		return map;
	}

	/**
	 * @description 初始化拼音字段
	 * @author 张文昌
	 * @date   2021/9/3 16:13
	 * @return
	 */
	public List<PlatformAddressVO> initPinyin(){
		//查询源数据
		List<PlatformAddress> source =
				platformAddressRepository.findAll();
		if(CollectionUtils.isEmpty(source)){
			return Collections.emptyList();
		}
		source =
				source.stream().filter(platformAddress -> StringUtils.isBlank(platformAddress.getPinYin())).map(platformAddress -> {
					platformAddress.setPinYin(Pinyin4jUtil.getPinyin(platformAddress.getAddrName()));
			return platformAddress;
		}).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(source)){
			//更新数据
			platformAddressRepository.saveAll(source);
		}
		return KsBeanUtil.copyListProperties(source, PlatformAddressVO.class);
	}

    /**
     * 校验是否需要完善地址,true表示需要完善，false表示不需要完善
     *
     * @param request
     * @return
     */
    public Boolean verifyAddress(PlatformAddressVerifyRequest request) {
        String provinceId = request.getProvinceId();
        String cityId = request.getCityId();
        String areaId = request.getAreaId();
        String streetId = request.getStreetId();

        // 省、市、区id任一个为null，都需要完善
        if (Objects.isNull(provinceId) || Objects.isNull(cityId) || Objects.isNull(areaId)) {
            return Boolean.TRUE;
        }

        Boolean flag = Boolean.TRUE;

        // 通过省、市父子 区id 统计市、区级地址数量，数量小于2 需要完善
        int num = this.countNumByIdAndParentId(provinceId, cityId, areaId);

//        if (num >= 2
//                && Objects.nonNull(streetId)
//                && !("0".equals(streetId) || "-1".equals(streetId))) {
//            // 有街道id：通过区、街道父子id 统计街道地址数量，数量小于1 则需要完善
//            int areaStreetNum = this.countNumByIdAndParentId(streetId, areaId);
//            flag = areaStreetNum > 0 ? Boolean.FALSE : Boolean.TRUE;
//        } else if (num >= 2) {
//            // 没有街道id：通过区id 统计街道地址数量，数量大于0 则需要完善
//            int areaStreetNum = this.countNumByParentId(areaId);
//            flag = areaStreetNum > 0 ? Boolean.TRUE : Boolean.FALSE;
//        }
		//广东省东莞市、中山市，海南省儋州市、三沙市以及甘肃省嘉峪关市没有区
		List<String> cityNoAreaList = new ArrayList<>();
		cityNoAreaList.add("441900");
		cityNoAreaList.add("442000");
		cityNoAreaList.add("460400");
		cityNoAreaList.add("460300");
		cityNoAreaList.add("620200");
		if(num>=2 || (cityNoAreaList.contains(cityId) && num>=1)){
			flag = false;
		}

        return flag;
    }

	@Transactional
	public void batchAdd(List<PlatformAddress> platformAddressList) {
		platformAddressList.forEach(entity -> {
			entity.setId(entity.getAddrId());
			entity.setSortNo(Integer.parseInt(entity.getAddrId()));
			entity.setDelFlag(DeleteFlag.NO);
			entity.setDataType(0);
			entity.setCreateTime(LocalDateTime.now());
			entity.setUpdateTime(LocalDateTime.now());
			entity.setPinYin(Pinyin4jUtil.getPinyin(entity.getAddrName()));
		});
		platformAddressRepository.saveAll(platformAddressList);
	}
}

