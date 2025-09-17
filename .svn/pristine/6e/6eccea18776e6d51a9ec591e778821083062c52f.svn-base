package com.wanmi.sbc.setting.api.request.wechatvideo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.enums.SettingType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * <p>单个查询视频直播带货应用设置请求参数</p>
 * @author zhaiqiankun
 * @date 2022-04-07 14:54:30
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoSettingByTypeRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull
	private SettingType settingType;

	/**
	 * 查询指定字段
	 */
	@Schema(description = "查询指定字段", hidden = true)
	private List<String> findFields;

}