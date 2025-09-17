package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.VideoCheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>视频带货申请修改参数</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9999999999L)
	private Integer id;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	@NotNull
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 申请开通状态，0：未审核，1：审核通过，2：驳回，3：禁用
	 */
	@Schema(description = "申请开通状态，0：未审核，1：审核通过，2：驳回，3：禁用")
	@NotNull
	private VideoCheckStatus status;

	@Schema(description = "审核时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime auditTime;

	/**
	 * 审核不通过原因
	 */
	@Schema(description = "审核不通过原因")
	@Length(max=512)
	private String auditReason;


	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;


	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;


}
