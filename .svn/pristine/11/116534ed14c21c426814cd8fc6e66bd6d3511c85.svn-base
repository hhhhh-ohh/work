package com.wanmi.sbc.empower.provider.impl.wechatshareset;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.wechatshareset.WechatShareSetSaveProvider;
import com.wanmi.sbc.empower.api.request.wechatshareset.WechatShareSetAddRequest;
import com.wanmi.sbc.empower.wechatshareset.model.root.WechatShareSet;
import com.wanmi.sbc.empower.wechatshareset.service.WechatShareSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>微信分享配置保存服务接口实现</p>
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@RestController
@Validated
public class WechatShareSetSaveController implements WechatShareSetSaveProvider {
	@Autowired
	private WechatShareSetService wechatShareSetService;

	@Override
	public BaseResponse add(@RequestBody @Valid WechatShareSetAddRequest wechatShareSetAddRequest) {
		WechatShareSet wechatShareSet = new WechatShareSet();
		KsBeanUtil.copyPropertiesThird(wechatShareSetAddRequest, wechatShareSet);
		wechatShareSetService.add(wechatShareSet);
		return BaseResponse.SUCCESSFUL();
	}



}

