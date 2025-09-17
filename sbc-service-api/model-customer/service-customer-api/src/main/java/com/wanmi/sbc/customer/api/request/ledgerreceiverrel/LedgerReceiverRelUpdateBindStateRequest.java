package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>分账绑定关系修改参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelUpdateBindStateRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Length(max=32)
	private String id;


	/**
	 * 绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
	 */
	@Schema(description = "绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败")
	@Max(127)
	private Integer bindState;

	/**
	 * 绑定时间
	 */
	@Schema(description = "绑定时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bindTime;

	/**
	 * 驳回原因
	 */
	@Schema(description = "驳回原因")
	private String rejectReason;


}
