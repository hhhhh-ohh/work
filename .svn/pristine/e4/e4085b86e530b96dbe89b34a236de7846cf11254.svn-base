package com.wanmi.sbc.setting.api.request.wechatvideo;


import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>视频直播带货应用设置修改参数</p>
 * @author zhaiqiankun
 * @date 2022-04-02 10:54:44
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoSettingSaveOrderRequest extends BaseRequest {

	private static final long serialVersionUID = 6985484246154389955L;

	/**
	 * 设置数据，json格式 consigneeName:收件人姓名,contactMobile:联系方式,addressDetail:详细地址,tid:订单id,returnTradeId:退单id
	 */
	@Schema(description = "设置数据，json格式 consigneeName:收件人姓名,contactMobile:联系方式,addressDetail:详细地址,tid:订单id,returnTradeId:退单id")
	@NotBlank
	private String context;
}
