package com.wanmi.sbc.vas.provider.impl.iep.iepsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.iep.iepsetting.IepSettingProvider;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingAddRequest;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingDelByIdListRequest;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingDelByIdRequest;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingModifyRequest;
import com.wanmi.sbc.vas.api.response.iep.iepsetting.IepSettingAddResponse;
import com.wanmi.sbc.vas.api.response.iep.iepsetting.IepSettingModifyResponse;
import com.wanmi.sbc.vas.iep.iepsetting.model.root.IepSetting;
import com.wanmi.sbc.vas.iep.iepsetting.service.IepSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>企业购设置保存服务接口实现</p>
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@RestController
@Validated
public class IepSettingController implements IepSettingProvider {
	@Autowired
	private IepSettingService iepSettingService;

	@Override
	public BaseResponse<IepSettingAddResponse> add(@RequestBody @Valid IepSettingAddRequest iepSettingAddRequest) {
		IepSetting iepSetting = KsBeanUtil.convert(iepSettingAddRequest, IepSetting.class);
		return BaseResponse.success(new IepSettingAddResponse(
				iepSettingService.wrapperVo(iepSettingService.add(iepSetting))));
	}

	@Override
	public BaseResponse<IepSettingModifyResponse> modify(@RequestBody @Valid IepSettingModifyRequest iepSettingModifyRequest) {
		IepSetting iepSetting = KsBeanUtil.convert(iepSettingModifyRequest, IepSetting.class);
		return BaseResponse.success(new IepSettingModifyResponse(
				iepSettingService.wrapperVo(iepSettingService.modify(iepSetting))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid IepSettingDelByIdRequest iepSettingDelByIdRequest) {
		IepSetting iepSetting = KsBeanUtil.convert(iepSettingDelByIdRequest, IepSetting.class);
		iepSetting.setDelFlag(DeleteFlag.YES);
		iepSettingService.deleteById(iepSetting);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid IepSettingDelByIdListRequest iepSettingDelByIdListRequest) {
		List<IepSetting> iepSettingList = iepSettingDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				IepSetting iepSetting = KsBeanUtil.convert(Id, IepSetting.class);
				iepSetting.setDelFlag(DeleteFlag.YES);
				return iepSetting;
			}).collect(Collectors.toList());
		iepSettingService.deleteByIdList(iepSettingList);
		return BaseResponse.SUCCESSFUL();
	}

}

