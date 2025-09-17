package com.wanmi.sbc.setting.provider.impl.payadvertisement;

import com.wanmi.sbc.setting.api.request.payadvertisement.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.payadvertisement.PayAdvertisementProvider;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementAddResponse;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementModifyResponse;
import com.wanmi.sbc.setting.payadvertisement.service.PayAdvertisementService;
import com.wanmi.sbc.setting.payadvertisement.model.root.PayAdvertisement;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>支付广告页配置保存服务接口实现</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@RestController
@Validated
public class PayAdvertisementController implements PayAdvertisementProvider {
	@Autowired
	private PayAdvertisementService payAdvertisementService;

	@Override
	public BaseResponse add(@RequestBody @Valid PayAdvertisementAddRequest payAdvertisementAddRequest) {
		payAdvertisementService.add(payAdvertisementAddRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid PayAdvertisementModifyRequest payAdvertisementModifyRequest) {
		payAdvertisementService.modify(payAdvertisementModifyRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayAdvertisementByIdRequest payAdvertisementByIdRequest) {
		payAdvertisementService.deleteById(payAdvertisementByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse pause(PayAdvertisementByIdRequest payAdvertisementByIdRequest) {
		payAdvertisementService.pauseById(payAdvertisementByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse start(PayAdvertisementByIdRequest payAdvertisementByIdRequest) {
		payAdvertisementService.startById(payAdvertisementByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse close(PayAdvertisementByIdRequest payAdvertisementByIdRequest) {
		payAdvertisementService.closeById(payAdvertisementByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

}

