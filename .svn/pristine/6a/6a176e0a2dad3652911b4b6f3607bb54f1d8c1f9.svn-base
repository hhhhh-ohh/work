package com.wanmi.sbc.empower.provider.impl.miniprogramset;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetAddRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetDelByIdListRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetDelByIdRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetModifyRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetAddResponse;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetModifyResponse;
import com.wanmi.sbc.empower.miniprogramset.model.root.MiniProgramSet;
import com.wanmi.sbc.empower.miniprogramset.service.MiniProgramSetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>小程序配置保存服务接口实现</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@RestController
@Validated
public class MiniProgramSetController implements MiniProgramSetProvider {
	@Autowired
	private MiniProgramSetService miniProgramSetService;

	@Override
	public BaseResponse<MiniProgramSetAddResponse> add(@RequestBody @Valid MiniProgramSetAddRequest miniProgramSetAddRequest) {
		MiniProgramSet miniProgramSet = KsBeanUtil.convert(miniProgramSetAddRequest, MiniProgramSet.class);
		return BaseResponse.success(new MiniProgramSetAddResponse(
				miniProgramSetService.wrapperVo(miniProgramSetService.add(miniProgramSet))));
	}

	@Override
	public BaseResponse<MiniProgramSetModifyResponse> modify(@RequestBody @Valid MiniProgramSetModifyRequest miniProgramSetModifyRequest) {
		if (miniProgramSetModifyRequest.getStatus().equals(DefaultFlag.YES.toValue()) && (StringUtils.isBlank(miniProgramSetModifyRequest.getAppId())
				|| miniProgramSetModifyRequest.getAppId().length() > 50)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		if (miniProgramSetModifyRequest.getStatus().equals(DefaultFlag.YES.toValue()) && (StringUtils.isBlank(miniProgramSetModifyRequest.getAppSecret())
				|| miniProgramSetModifyRequest.getAppSecret().length() > 50)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		MiniProgramSet miniProgramSet = KsBeanUtil.convert(miniProgramSetModifyRequest, MiniProgramSet.class);
		return BaseResponse.success(new MiniProgramSetModifyResponse(
				miniProgramSetService.wrapperVo(miniProgramSetService.modify(miniProgramSet))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid MiniProgramSetDelByIdRequest miniProgramSetDelByIdRequest) {
		MiniProgramSet miniProgramSet = KsBeanUtil.convert(miniProgramSetDelByIdRequest, MiniProgramSet.class);
		miniProgramSet.setDelFlag(DeleteFlag.YES);
		miniProgramSetService.deleteById(miniProgramSet);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid MiniProgramSetDelByIdListRequest miniProgramSetDelByIdListRequest) {
		List<MiniProgramSet> miniProgramSetList = miniProgramSetDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				MiniProgramSet miniProgramSet = KsBeanUtil.convert(Id, MiniProgramSet.class);
				miniProgramSet.setDelFlag(DeleteFlag.YES);
				return miniProgramSet;
			}).collect(Collectors.toList());
		miniProgramSetService.deleteByIdList(miniProgramSetList);
		return BaseResponse.SUCCESSFUL();
	}

}

