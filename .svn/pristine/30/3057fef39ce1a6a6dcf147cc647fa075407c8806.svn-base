package com.wanmi.sbc.message.api.request.minimsgtempsetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>小程序订阅消息模版配置表修改参数</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgTempSettingBatchModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 更新模板Id，同时清空修改的温馨提示
	 */
	@Schema(description = "更新模板Id，同时清空修改的温馨提示")
	private List<MiniMsgTempSettingBatchModifyData> dataList;

}
