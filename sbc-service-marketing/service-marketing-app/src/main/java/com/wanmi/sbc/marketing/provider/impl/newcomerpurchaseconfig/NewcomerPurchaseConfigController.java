package com.wanmi.sbc.marketing.provider.impl.newcomerpurchaseconfig;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchaseconfig.NewcomerPurchaseConfigProvider;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigAddRequest;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseConfigAddResponse;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigModifyRequest;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseConfigModifyResponse;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigDelByIdRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigDelByIdListRequest;
import com.wanmi.sbc.marketing.newcomerpurchaseconfig.service.NewcomerPurchaseConfigService;
import com.wanmi.sbc.marketing.newcomerpurchaseconfig.model.root.NewcomerPurchaseConfig;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>新人专享设置保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@RestController
@Validated
public class NewcomerPurchaseConfigController implements NewcomerPurchaseConfigProvider {
	@Autowired
	private NewcomerPurchaseConfigService newcomerPurchaseConfigService;

	@Override
	public BaseResponse<NewcomerPurchaseConfigAddResponse> add(@RequestBody @Valid NewcomerPurchaseConfigAddRequest newcomerPurchaseConfigAddRequest) {
		NewcomerPurchaseConfig newcomerPurchaseConfig = KsBeanUtil.convert(newcomerPurchaseConfigAddRequest, NewcomerPurchaseConfig.class);
		return BaseResponse.success(new NewcomerPurchaseConfigAddResponse(
				newcomerPurchaseConfigService.wrapperVo(newcomerPurchaseConfigService.add(newcomerPurchaseConfig))));
	}

	@Override
	public BaseResponse<NewcomerPurchaseConfigModifyResponse> modify(@RequestBody @Valid NewcomerPurchaseConfigModifyRequest newcomerPurchaseConfigModifyRequest) {
		NewcomerPurchaseConfig newcomerPurchaseConfig = KsBeanUtil.convert(newcomerPurchaseConfigModifyRequest, NewcomerPurchaseConfig.class);
		return BaseResponse.success(new NewcomerPurchaseConfigModifyResponse(
				newcomerPurchaseConfigService.wrapperVo(newcomerPurchaseConfigService.modify(newcomerPurchaseConfig))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid NewcomerPurchaseConfigDelByIdRequest newcomerPurchaseConfigDelByIdRequest) {
		NewcomerPurchaseConfig newcomerPurchaseConfig = KsBeanUtil.convert(newcomerPurchaseConfigDelByIdRequest, NewcomerPurchaseConfig.class);
		newcomerPurchaseConfig.setDelFlag(DeleteFlag.YES);
		newcomerPurchaseConfigService.deleteById(newcomerPurchaseConfig);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid NewcomerPurchaseConfigDelByIdListRequest newcomerPurchaseConfigDelByIdListRequest) {
		newcomerPurchaseConfigService.deleteByIdList(newcomerPurchaseConfigDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse save(@RequestBody @Valid NewcomerPurchaseConfigModifyRequest newcomerPurchaseConfigModifyRequest) {
		newcomerPurchaseConfigService.save(newcomerPurchaseConfigModifyRequest);
		return BaseResponse.SUCCESSFUL();
	}

}

