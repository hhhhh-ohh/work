package com.wanmi.sbc.setting.api.request.operatedatalog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>系统日志新增参数</p>
 * @author guanfl
 * @date 2020-04-21 14:57:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperateDataLogAddRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 操作内容
	 */
	@Schema(description = "操作内容")
	@Length(max=255)
	private String operateContent;

	/**
	 * 操作标识
	 */
	@Schema(description = "操作标识")
	@Length(max=255)
	private String operateId;

	/**
	 * 操作前数据
	 */
	@Schema(description = "操作前数据")
	private String operateBeforeData;

	/**
	 * 操作后数据
	 */
	@Schema(description = "操作后数据")
	private String operateAfterData;

	/**
	 * 操作人账号
	 */
	@Schema(description = "操作人账号")
	@Length(max=50)
	private String operateAccount;

	/**
	 * 操作人名称
	 */
	@Schema(description = "操作人名称")
	@Length(max=128)
	private String operateName;

	/**
	 * 操作时间
	 */
	@Schema(description = "操作时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime operateTime;

	/**
	 * 删除标记
	 */
	@Schema(description = "删除标记")
	private DeleteFlag delFlag;

}