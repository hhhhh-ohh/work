package com.wanmi.sbc.empower.provider.impl.wechatloginset;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.wechatloginset.WechatLoginSetSaveProvider;
import com.wanmi.sbc.empower.api.request.wechatloginset.WechatLoginSetAddRequest;
import com.wanmi.sbc.empower.wechatloginset.root.WechatLoginSet;
import com.wanmi.sbc.empower.wechatloginset.service.WechatLoginSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>微信授权登录配置保存服务接口实现</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@RestController
@Validated
public class WechatLoginSetSaveController implements WechatLoginSetSaveProvider {
	@Autowired
	private WechatLoginSetService wechatLoginSetService;

	@Override
	public BaseResponse add(@RequestBody @Valid WechatLoginSetAddRequest wechatLoginSetAddRequest) {
		WechatLoginSet wechatLoginSet = new WechatLoginSet();
		KsBeanUtil.copyPropertiesThird(wechatLoginSetAddRequest, wechatLoginSet);
		wechatLoginSetService.add(wechatLoginSet);

		return BaseResponse.SUCCESSFUL();
	}

}

