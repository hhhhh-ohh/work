package com.wanmi.sbc.goods.api.provider.wechatvideo.wechatcateaudit;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.WechatCateCallbackRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>微信类目审核状态保存服务Provider</p>
 * @author 
 * @date 2022-04-09 17:02:02
 */
@FeignClient(value = "${application.goods.name}", contextId = "WechatCateAuditSaveProvider")
public interface WechatCateAuditSaveProvider {


	/**
	 * 处理微信类目审核回调
	 * @param wechatCateCallbackRequest
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/thirdgoodscate/wechatCallback")
	BaseResponse dealCallback(@RequestBody @Valid WechatCateCallbackRequest wechatCateCallbackRequest);

}

