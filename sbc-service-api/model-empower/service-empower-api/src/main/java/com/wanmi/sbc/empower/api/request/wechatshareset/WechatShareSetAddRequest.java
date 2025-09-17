package com.wanmi.sbc.empower.api.request.wechatshareset;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>微信分享配置新增参数</p>
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatShareSetAddRequest extends EmpowerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 微信公众号App ID
	 */
	@Schema(description = "微信公众号App ID")
	private String shareAppId;

	/**
	 * 微信公众号 App Secret
	 */
	@Schema(description = "微信公众号 App Secret")
	private String shareAppSecret;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 操作人
	 */
	@Schema(description = "操作人")
	@Length(max=45)
	private String operatePerson;

	@Schema(description = "门店id")
	private Long storeId;

}