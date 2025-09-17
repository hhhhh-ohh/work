package com.wanmi.sbc.setting.provider.impl.pickupsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingProvider;
import com.wanmi.sbc.setting.api.request.ConfigRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingAddRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingAuditRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingDefaultAddressRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingDelByIdRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingModifyRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.pickupemployeerela.model.root.PickupEmployeeRela;
import com.wanmi.sbc.setting.pickupemployeerela.service.PickupEmployeeRelaService;
import com.wanmi.sbc.setting.pickupsetting.model.root.PickupSetting;
import com.wanmi.sbc.setting.pickupsetting.service.PickupSettingService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>pickup_setting保存服务接口实现</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@RestController
@Validated
public class PickupSettingController implements PickupSettingProvider {

	@Autowired
	private PickupSettingService pickupSettingService;

	@Autowired
	private PickupEmployeeRelaService pickupEmployeeRelaService;

	@Override
	@Transactional
	public BaseResponse add(@RequestBody @Valid PickupSettingAddRequest pickupSettingAddRequest) {
		PickupSetting pickupSetting = KsBeanUtil.convert(pickupSettingAddRequest, PickupSetting.class);
		List<PickupSetting> pickupSettings = pickupSettingService.getPickupIdsByName(pickupSettingAddRequest.getName());
		if (CollectionUtils.isNotEmpty(pickupSettings)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		// 保存自提配置
		pickupSetting.setIsDefaultAddress(0);
		pickupSetting.setAuditStatus(0);
		pickupSetting.setEnableStatus(0);
		pickupSetting.setDelFlag(DeleteFlag.NO);
		PickupSetting returnPickupSetting = pickupSettingService.add(pickupSetting);
		List<String> employeeIds = pickupSettingAddRequest.getEmployeeIds();
		batchSavePickupEmployeeRel(returnPickupSetting, employeeIds);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	@Transactional
	public BaseResponse modify(@RequestBody @Valid PickupSettingModifyRequest pickupSettingModifyRequest) {
		Long pickupId = pickupSettingModifyRequest.getId();
		PickupSetting pickupSetting = pickupSettingService.getOne(pickupId);
		if (!Objects.equals(pickupSettingModifyRequest.getStoreId(), pickupSetting.getStoreId())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
		}
		List<PickupSetting> pickupSettings = pickupSettingService.getPickupIdsByName(pickupSettingModifyRequest.getName());
		List<PickupSetting> itemList =
				pickupSettings.stream().filter(g -> !Objects.equals(g.getId(), pickupId)).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(itemList)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		//已删除的
		if (Objects.isNull(pickupSetting) || DeleteFlag.YES.equals(pickupSetting.getDelFlag())) {
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070023);
		}
		KsBeanUtil.copyPropertiesThird(pickupSettingModifyRequest, pickupSetting);
		pickupEmployeeRelaService.deleteByPickupId(pickupId);
		List<String> employeeIds = pickupSettingModifyRequest.getEmployeeIds();
		batchSavePickupEmployeeRel(pickupSetting, employeeIds);
		if (AuditStatus.NOT_PASS.toValue().equals(pickupSetting.getAuditStatus().toString())){
			//已驳回编辑后重置自提点状态
			pickupSetting.setEnableStatus(EnableStatus.DISABLE.toValue());
			pickupSetting.setAuditStatus(Integer.valueOf(AuditStatus.WAIT_CHECK.toValue()));
			pickupSetting.setAuditReason(null);
		}
		pickupSettingService.modify(pickupSetting);
		return BaseResponse.SUCCESSFUL();
	}

	private void batchSavePickupEmployeeRel(PickupSetting pickupSetting, List<String> employeeIds) {
		if (CollectionUtils.isNotEmpty(employeeIds)) {
			List<PickupEmployeeRela> pickupEmployeeRelas =
					employeeIds.stream().map(employee -> {
						PickupEmployeeRela rela = new PickupEmployeeRela();
						rela.setPickupId(pickupSetting.getId());
						rela.setEmployeeId(employee);
						return rela;
					})
							.collect(Collectors.toList());
			// 自提员工关系
			pickupEmployeeRelaService.save(pickupEmployeeRelas);
		}
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PickupSettingDelByIdRequest pickupSettingDelByIdRequest) {
		PickupSetting pickupSetting = pickupSettingService.getOne(pickupSettingDelByIdRequest.getId());
		if (!Objects.equals(pickupSettingDelByIdRequest.getStoreId(), pickupSetting.getStoreId())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
		}
		if (Constants.ONE==pickupSetting.getIsDefaultAddress()){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070024);
		}
		// 只存在一条不允许删除
		if (EnableStatus.ENABLE.toValue() == pickupSetting.getEnableStatus()){
			verifyEnableNum(pickupSettingDelByIdRequest.getStoreId());
		}
		KsBeanUtil.copyPropertiesThird(pickupSettingDelByIdRequest, pickupSetting);
		pickupSetting.setDelFlag(DeleteFlag.YES);
		pickupSettingService.deleteById(pickupSetting);
		return BaseResponse.SUCCESSFUL();
	}

	private void verifyEnableNum(Long storeId) {
		// 查询未删除且启用自提点数量
		PickupSettingQueryRequest queryReq = new PickupSettingQueryRequest();
		queryReq.setDelFlag(DeleteFlag.NO);
		queryReq.setEnableStatus(1);
		queryReq.setStoreId(storeId);
		Long total = pickupSettingService.total(queryReq);
		// 只存在一条不允许删除
		if (1L == total){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070022);
		}
	}

	@Override
	public BaseResponse pickupSettingAudit(@RequestBody @Valid PickupSettingAuditRequest request) {
		PickupSetting pickupSetting = pickupSettingService.getOne(request.getId());
		if (Objects.nonNull(request.getStoreId()) && !Objects.equals(request.getStoreId(), pickupSetting.getStoreId())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
		}
		if (Constants.ONE==pickupSetting.getIsDefaultAddress()){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070024);
		}
		// 只存在一条不允许禁用
		if (Objects.nonNull(request.getEnableStatus()) && EnableStatus.DISABLE.toValue() == request.getEnableStatus()) {
			verifyEnableNum(request.getStoreId());
		}
		pickupSettingService.pickupSettingAudit(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse pickupSettingConfig(@RequestBody @Valid ConfigRequest request) {
		request.setConfigKey(ConfigKey.PICKUP_SETTING);
		pickupSettingService.pickupSettingConfig(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse pickupSettingDefaultAddress(@RequestBody @Valid PickupSettingDefaultAddressRequest request) {
		PickupSetting pickupSetting = pickupSettingService.getOne(request.getId());
		if (!Objects.equals(request.getBaseStoreId(), pickupSetting.getStoreId())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
		}
		if(EnableStatus.ENABLE.toValue() != pickupSetting.getEnableStatus()){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070024);
		}
		pickupSettingService.pickupSettingDefaultAddress(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modifyMapSetting(@RequestBody @Valid ConfigRequest request) {
		request.setConfigKey(ConfigKey.PICKUP_SETTING);
		pickupSettingService.modifyMapSetting(request);
		return BaseResponse.SUCCESSFUL();
	}

}

