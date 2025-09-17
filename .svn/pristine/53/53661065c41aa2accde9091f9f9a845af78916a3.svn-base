package com.wanmi.sbc.setting.api.request.wechatvideo;


import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.enums.SettingType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

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
public class WechatVideoSettingModifyStatusRequest extends BaseRequest {

	private static final long serialVersionUID = 6985484246154389955L;

	@Schema(description = "设置类型")
	@NotNull
	private SettingType settingType;

	/**
	 * 设置数据，json格式 consigneeName:收件人姓名,contactMobile:联系方式,addressDetail:详细地址,tradeId:订单id,returnTradeId:退单id
	 */
	@Schema(description = "设置数据，json格式 consigneeName:收件人姓名,contactMobile:联系方式,addressDetail:详细地址,tradeId:订单id,returnTradeId:退单id")
	private String context;

	@Schema(description = "是否是保存订单标识")
	private boolean saveOrderFlag = false;

	@Schema(description = "是否是保存退货地址标识")
	private boolean saveReturnAddressFlag = false;
}
