package com.wanmi.sbc.setting.provider.impl.platformaddress;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.*;
import com.wanmi.sbc.setting.api.response.platformaddress.*;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;
import com.wanmi.sbc.setting.bean.vo.AddressJsonInfoVO;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.setting.platformaddress.model.root.PlatformAddress;
import com.wanmi.sbc.setting.platformaddress.service.PlatformAddressService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>平台地址信息查询服务接口实现</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@RestController
@Validated
public class PlatformAddressQueryController implements PlatformAddressQueryProvider {
	@Autowired
	private PlatformAddressService platformAddressService;

	@Override
	public BaseResponse<PlatformAddressPageResponse> page(@RequestBody @Valid PlatformAddressPageRequest platformAddressPageReq) {
		PlatformAddressQueryRequest queryReq = KsBeanUtil.convert(platformAddressPageReq, PlatformAddressQueryRequest.class);
		Page<PlatformAddress> platformAddressPage = platformAddressService.page(queryReq);
		Page<PlatformAddressVO> newPage = platformAddressPage.map(entity -> platformAddressService.wrapperVo(entity));
		MicroServicePage<PlatformAddressVO> microPage = new MicroServicePage<>(newPage, platformAddressPageReq.getPageable());
		PlatformAddressPageResponse finalRes = new PlatformAddressPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PlatformAddressListResponse> list(@RequestBody @Valid PlatformAddressListRequest platformAddressListReq) {
		PlatformAddressQueryRequest queryReq = KsBeanUtil.convert(platformAddressListReq, PlatformAddressQueryRequest.class);
		List<PlatformAddress> platformAddressList = platformAddressService.list(queryReq);
        Set<String> parenAddIds = new HashSet<>();
        //填充是否叶子节点标识
		if(Boolean.TRUE.equals(platformAddressListReq.getLeafFlag()) && CollectionUtils.isNotEmpty(platformAddressList)){
            List<String> addrIds = platformAddressList.stream().map(PlatformAddress::getAddrId).collect(Collectors.toList());
            List<PlatformAddress> childList = platformAddressService.list(PlatformAddressQueryRequest.builder().addrParentIdList(addrIds).delFlag(DeleteFlag.NO).build());
            if(CollectionUtils.isNotEmpty(childList)){
				parenAddIds.addAll(childList.stream().map(PlatformAddress::getAddrParentId).distinct().collect(Collectors.toSet()));
			}
        }
		List<PlatformAddressVO> newList = platformAddressList.stream().map(entity -> {
            PlatformAddressVO vo = platformAddressService.wrapperVo(entity);
            if(parenAddIds.contains(vo.getAddrId())){
                vo.setLeafFlag(Boolean.FALSE);
            }
            return vo;
		}).collect(Collectors.toList());
		return BaseResponse.success(new PlatformAddressListResponse(newList));
	}

	@Override
	public BaseResponse<AddressJsonResponse> addressJsonList(@RequestBody @Valid PlatformAddressListRequest platformAddressListReq) {
		List<PlatformAddress> provinceList = platformAddressService.list(PlatformAddressQueryRequest.builder().addrLevel(AddrLevel.PROVINCE).delFlag(DeleteFlag.NO).build());
		List<PlatformAddress> cityList = platformAddressService.list(PlatformAddressQueryRequest.builder().addrLevel(AddrLevel.CITY).delFlag(DeleteFlag.NO).build());
		ArrayList<AddressJsonInfoVO> provinces = new ArrayList();
		Map<String,List<AddressJsonInfoVO>> cityJson = new HashMap();
		provinceList.forEach(province->{
			List<PlatformAddress> cityJsonList = cityList.stream().filter(city->city.getAddrParentId().equals(province.getId())).collect(Collectors.toList());
			AddressJsonInfoVO addressJsonInfoVO = new AddressJsonInfoVO();
			addressJsonInfoVO.setId(province.getId());
			addressJsonInfoVO.setName(province.getAddrName());
			provinces.add(addressJsonInfoVO);
			List<AddressJsonInfoVO> cityDataList = cityJsonList.stream().map(cityJsonInfo ->{
				AddressJsonInfoVO cityJsonData = new AddressJsonInfoVO();
				cityJsonData.setProvince(province.getAddrName());
				cityJsonData.setId(cityJsonInfo.getId());
				cityJsonData.setName(cityJsonInfo.getAddrName());
				return cityJsonData;
			}).collect(Collectors.toList());
			cityJson.put(province.getId(),cityDataList);
		});
		List<PlatformAddress> aresList = platformAddressService.list(PlatformAddressQueryRequest.builder().addrLevel(AddrLevel.DISTRICT).delFlag(DeleteFlag.NO).build());
		Map<String,List<AddressJsonInfoVO>> cityAresJson = new HashMap();
		cityList.forEach(city->{
			List<PlatformAddress> cityAresList = aresList.stream().filter(ares->ares.getAddrParentId().equals(city.getId())).collect(Collectors.toList());

			List<AddressJsonInfoVO> cityDataList = cityAresList.stream().map(cityAres->{
				AddressJsonInfoVO addressAresJsonInfoVO = new AddressJsonInfoVO();
				addressAresJsonInfoVO.setId(cityAres.getId());
				addressAresJsonInfoVO.setName(cityAres.getAddrName());
				addressAresJsonInfoVO.setCity(city.getAddrName());
				return addressAresJsonInfoVO;
			}).collect(Collectors.toList());
			cityAresJson.put(city.getId(),cityDataList);
		});
		AddressJsonResponse addressJsonResponse = new AddressJsonResponse();
		addressJsonResponse.setProvinces(provinces);
		addressJsonResponse.setCities(cityJson);
		addressJsonResponse.setAreas(cityAresJson);
		return BaseResponse.success(addressJsonResponse);
	}

	@Override
	public BaseResponse<PlatformAddressByIdResponse> getById(@RequestBody @Valid PlatformAddressByIdRequest platformAddressByIdRequest) {
		PlatformAddress platformAddress =
		platformAddressService.getOne(platformAddressByIdRequest.getId());
		return BaseResponse.success(new PlatformAddressByIdResponse(platformAddressService.wrapperVo(platformAddress)));
	}

	@Override
	public BaseResponse<PlatformAddressListResponse> provinceOrCitylist(@RequestBody @Valid PlatformAddressListRequest platformAddressListReq) {
		List<PlatformAddress> platformAddressList = platformAddressService.provinceCityList(platformAddressListReq.getAddrIdList());
		List<PlatformAddressVO> newList = platformAddressList.stream().map(entity -> {
			PlatformAddressVO vo = platformAddressService.wrapperVo(entity);
			return vo;
		}).collect(Collectors.toList());
		return BaseResponse.success(new PlatformAddressListResponse(newList));
	}

	/**
	 * 校验是否需要完善地址,true表示需要完善，false表示不需要完善
	 * @param request
	 * @return
	 */
	@Override
	public BaseResponse<Boolean> verifyAddress(@RequestBody @Valid PlatformAddressVerifyRequest request) {
		String provinceId = request.getProvinceId();
		String cityId = request.getCityId();
		String areaId = request.getAreaId();
		String streetId = request.getStreetId();

		// 省、市、区id任一个为null，都需要完善
		if(Objects.isNull(provinceId) || Objects.isNull(cityId) || Objects.isNull(areaId)) {
			return BaseResponse.success(Boolean.TRUE);
		}
		//		验证一下省市是否错位
		if (!platformAddressService.isAreaProvince(provinceId)) {
			return BaseResponse.success(Boolean.TRUE);
		}
		// 根据省市区街道id 统计数据库是否有对应数据，没有则需要完善
		int provinceNum = platformAddressService.countNum(provinceId);
		int cityNum = platformAddressService.countNum(cityId);
		int areaNum = platformAddressService.countNum(areaId);
		/*int streetNum = 1;
		// 如果有街道id，需统计街道id 数据库是否有对应数据（null、0、-1都表示没有）
		if(Objects.nonNull(streetId) && !("0".equals(streetId) || "-1".equals(streetId))) {
			streetNum = platformAddressService.countNum(streetId);
		}*/
		if(provinceNum == 0 || cityNum == 0 || areaNum == 0){
			return BaseResponse.success(Boolean.TRUE);
		}
		// 通过省、市父子id 统计市级地址数量，数量小于1 需要完善
		//不校验街道，剔除校验街道逻辑

		return BaseResponse.success(Boolean.FALSE);
	}

	@Override
	public BaseResponse<PlatformAddressListGroupResponse> listGroupByPinYin(PlatformAddressQueryRequest request) {
        return BaseResponse.success(
                PlatformAddressListGroupResponse.builder()
                        .platformAddressVOGroup(platformAddressService.GroupByPinYin(request))
                        .build());
	}

	@Override
	public BaseResponse<Boolean> verifyAddressSimple(@RequestBody @Valid PlatformAddressVerifyRequest request) {
		return BaseResponse.success(platformAddressService.verifyAddress(request));
	}

	@Override
	public BaseResponse<PlatformAddressListResponse> batchQuery(@RequestBody @Valid PlatformAddressBatchAddRequest platformAddressBatchAddRequest) {
		PlatformAddressListResponse platformAddressListResponse = new PlatformAddressListResponse();
		List<PlatformAddressVO> platformAddressVOList = Lists.newArrayList();
		platformAddressBatchAddRequest.getPlatformAddressAddRequests().forEach(platformAddressAddRequest -> {
			platformAddressVOList.add(platformAddressService.wrapperVo(platformAddressService.findByAddrNameAndAddrParentIdAndDelFlag(platformAddressAddRequest.getAddrName(),
					platformAddressAddRequest.getAddrParentId())));
		});
		platformAddressListResponse.setPlatformAddressVOList(platformAddressVOList);
		return BaseResponse.success(platformAddressListResponse);
	}
	@Override
	public BaseResponse<PlatformAddressVO> findByAddrNameAndDelFlag(@RequestBody @Valid PlatformAddressListRequest platformAddressListRequest) {
		PlatformAddressVO platformAddressVO = platformAddressService.wrapperVo(platformAddressService.findByAddrNameAndDelFlag(platformAddressListRequest.getAddrName()));
		return BaseResponse.success(platformAddressVO);
	}
}

