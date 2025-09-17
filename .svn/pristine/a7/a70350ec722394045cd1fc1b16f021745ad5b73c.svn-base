package com.wanmi.sbc.message.api.request.minimsgtempsetting;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * <p>小程序订阅消息模版配置表修改参数</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgTempSettingBatchModifyData {
	private static final long serialVersionUID = 2000344092227453309L;

	/**
	 * 模版ID
	 */
	@Schema(description = "模版ID")
	@Length(max=50)
	private String priTmplId;

	/**
	 * 模版标题ID
	 */
	@Schema(description = "模版标题ID")
	@Length(max=50)
	private String tid;

}
