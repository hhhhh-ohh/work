package com.wanmi.sbc.crm.api.request.customertagrel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>会员标签关联新增参数</p>
 * @author dyt
 * @date 2019-11-12 14:49:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTagRelAddRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id", required = true)
	@NotBlank
	private String customerId;

	/**
	 * 标签id
	 */
	@Schema(description = "标签id", required = true)
	private List<Long> tagIds;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

}