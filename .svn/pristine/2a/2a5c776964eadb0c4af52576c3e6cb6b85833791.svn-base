package com.wanmi.sbc.customer.api.request.payingmembercustomerrel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>客户与付费会员等级关联表新增参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberCustomerRelAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户与付费会员等级关联id
	 */
	@Schema(description = "客户与付费会员等级关联id")
	@Max(9999999999L)
	private Integer levelId;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	@Length(max=32)
	private String customerId;

	/**
	 * 开通时间
	 */
	@Schema(description = "开通时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime openTime;

	/**
	 * 会员到期时间
	 */
	@Schema(description = "会员到期时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate expirationDate;

	/**
	 * 总共优惠金额
	 */
	@Schema(description = "总共优惠金额")
	private BigDecimal discountAmount;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

}