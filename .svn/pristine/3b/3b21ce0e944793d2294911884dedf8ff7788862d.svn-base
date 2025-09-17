package com.wanmi.sbc.customer.api.request.livecompany;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>直播商家修改参数</p>
 * @author zwb
 * @date 2020-06-06 18:06:59
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveCompanyModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 提交审核时间
	 */
	@Schema(description = "提交审核时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime submitTime;

	/**
	 * 直播状态 0未开通，1待审核，2已开通，3审核未通过，4禁用中
	 */
	@Schema(description = "直播状态 0未开通，1待审核，2已开通，3审核未通过，4禁用中")
	@Max(127)
	private Integer liveBroadcastStatus;

	/**
	 * 直播审核原因
	 */
	@Schema(description = "直播审核原因")
	@Length(max=255)
	private String auditReason;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	@Length(max=45)
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	@Length(max=255)
	private String deletePerson;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID")
	@Max(9223372036854775807L)
	private Long companyInfoId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

}