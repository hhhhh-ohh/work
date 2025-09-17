package com.wanmi.sbc.setting.provider.impl.platformaddress;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressSaveProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.*;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressAddResponse;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressListResponse;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressModifyResponse;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.setting.platformaddress.model.root.PlatformAddress;
import com.wanmi.sbc.setting.platformaddress.service.PlatformAddressService;
import com.wanmi.sbc.setting.thirdaddress.service.ThirdAddressService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * <p>平台地址信息保存服务接口实现</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@RestController
@Validated
public class PlatformAddressSaveController implements PlatformAddressSaveProvider {
	@Autowired
	private PlatformAddressService platformAddressService;

	@Autowired
	private ThirdAddressService thirdAddressService;

	@Override
	public BaseResponse<PlatformAddressAddResponse> add(@RequestBody @Valid PlatformAddressAddRequest platformAddressAddRequest) {
		this.validate(platformAddressAddRequest.getAddrId());
		this.validateName(platformAddressAddRequest.getAddrName(), null, platformAddressAddRequest.getAddrLevel(), platformAddressAddRequest.getAddrParentId());
		PlatformAddress platformAddress = KsBeanUtil.convert(platformAddressAddRequest, PlatformAddress.class);
		return BaseResponse.success(new PlatformAddressAddResponse(
				platformAddressService.wrapperVo(platformAddressService.add(platformAddress))));
	}

	@Override
	public BaseResponse<PlatformAddressModifyResponse> modify(@RequestBody @Valid PlatformAddressModifyRequest platformAddressModifyRequest) {
		String id = platformAddressModifyRequest.getId();
		String oldAddrId = platformAddressModifyRequest.getOldAddrId();
		if(!StringUtils.equals(id,oldAddrId)){
			PlatformAddress newPlatformAddress = platformAddressService.findByIdAndDelFlag(id);
			if (Objects.nonNull(newPlatformAddress)) {
				throw new SbcRuntimeException(SettingErrorCodeEnum.K070004);
			}
			PlatformAddress platformAddress = platformAddressService.getOne(oldAddrId);
			this.validateName(platformAddressModifyRequest.getAddrName(), platformAddress.getId(), platformAddress.getAddrLevel(), platformAddress.getAddrParentId());
			KsBeanUtil.copyPropertiesThird(platformAddressModifyRequest, platformAddress);
			platformAddress.setId(id);
            platformAddress.setAddrId(id);
			//因需要修改id 所以不能在当前do实体中直接修改
            PlatformAddressVO platformAddressVO =  new PlatformAddressVO();
            BeanUtils.copyProperties(platformAddress,platformAddressVO);
			return BaseResponse.success(new PlatformAddressModifyResponse(
					platformAddressService.modifyPlatformAddress(platformAddressVO,oldAddrId)));
		}
		PlatformAddress platformAddress = platformAddressService.getOne(platformAddressModifyRequest.getId());
		this.validateName(platformAddressModifyRequest.getAddrName(), platformAddress.getId(), platformAddress.getAddrLevel(), platformAddress.getAddrParentId());
		KsBeanUtil.copyPropertiesThird(platformAddressModifyRequest, platformAddress);
		return BaseResponse.success(new PlatformAddressModifyResponse(
				platformAddressService.wrapperVo(platformAddressService.modify(platformAddress))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PlatformAddressDelByIdRequest platformAddressDelByIdRequest) {
		PlatformAddress platformAddress = platformAddressService.getOne(platformAddressDelByIdRequest.getId());
		/*if (Integer.valueOf(1).equals(platformAddress.getDataType())) {
			if (platformAddressService.countNumByParentId(platformAddress.getAddrId()) > 0) {
				throw new SbcRuntimeException(SettingErrorCode.ADDRESS_DELETE_ERROR_CHILD);
			} else if (thirdAddressService.count(
					ThirdAddressQueryRequest.builder().platformAddrId(platformAddress.getAddrId()).delFlag(DeleteFlag.NO).build()) > 0) {
				throw new SbcRuntimeException(SettingErrorCode.ADDRESS_DELETE_ERROR_MAPPING);
			}
		} else {
			throw new SbcRuntimeException(SettingErrorCode.ADDRESS_DELETE_ERROR_INIT);
		}*/
		platformAddressService.deleteById(platformAddressDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PlatformAddressDelByIdListRequest platformAddressDelByIdListRequest) {
		platformAddressService.deleteByIdList(platformAddressDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	private void validate(String code){
		List<PlatformAddress> addresses = platformAddressService.list(
				PlatformAddressQueryRequest.builder().addrId(code).delFlag(DeleteFlag.NO).build());
		if (CollectionUtils.isNotEmpty(addresses)) {
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070004);
		}
	}

	private void validateName(String name, String id, AddrLevel addrLevel, String parentId) {
		if(AddrLevel.PROVINCE == addrLevel){
			parentId = "0";
		}
		List<PlatformAddress> addresses = platformAddressService.list(
				PlatformAddressQueryRequest.builder().addrName(name).addrParentId(parentId).delFlag(DeleteFlag.NO).build());
		if (CollectionUtils.isNotEmpty(addresses)) {
			String addrName ="";
			switch (addrLevel){
				case PROVINCE:addrName="省份";break;
				case CITY:addrName="城市";break;
				case DISTRICT:addrName="区县";break;
				case STREET:addrName="街道";break;
				default:break;
			}
			if (StringUtils.isBlank(id)) {
				throw new SbcRuntimeException(SettingErrorCodeEnum.K070005, new Object[]{addrName});
			} else {
				if (!addresses.get(0).getId().equals(id)) {
					throw new SbcRuntimeException(SettingErrorCodeEnum.K070005, new Object[]{addrName});
				}
			}
		}
	}

	/**
	 * @description 初始化地区拼音值
	 * @author 张文昌
	 * @date   2021/9/3 16:16
	 * @return
	 */
	@Override
	public BaseResponse<PlatformAddressListResponse> initPinYin() {
		return BaseResponse.success(PlatformAddressListResponse.builder().platformAddressVOList(platformAddressService.initPinyin()).build());
	}

	@Override
	public BaseResponse batchAdd(@RequestBody PlatformAddressBatchAddRequest platformAddressBatchAddRequest) {
		List<PlatformAddressAddRequest> platformAddressAddRequests =
				platformAddressBatchAddRequest.getPlatformAddressAddRequests();
		List<PlatformAddress> platformAddressList = KsBeanUtil.copyListProperties(platformAddressAddRequests,
				PlatformAddress.class);
		platformAddressService.batchAdd(platformAddressList);
		return BaseResponse.SUCCESSFUL();
	}
}

