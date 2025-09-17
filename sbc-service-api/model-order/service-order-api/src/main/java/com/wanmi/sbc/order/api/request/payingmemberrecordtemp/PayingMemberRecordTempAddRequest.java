package com.wanmi.sbc.order.api.request.payingmemberrecordtemp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>付费记录临时表新增参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecordTempAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	@NotNull
	private Integer levelId;

	/**
	 * 付费会员等级名称
	 */
	@Schema(description = "付费会员等级名称")
	private String levelName;

	/**
	 * 付费会员等级昵称
	 */
	@Schema(description = "付费会员等级昵称")
	private String levelNickName;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id", hidden = true)
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称", hidden = true)
	private String customerName;

	/**
	 * 付费设置id
	 */
	@Schema(description = "付费设置id")
	@NotNull
	private Integer priceId;

	/**
	 * 支付数量
	 */
	@Schema(description = "支付数量", hidden = true)
	private Integer payNum;

	/**
	 * 支付金额
	 */
	@Schema(description = "支付金额", hidden = true)
	private BigDecimal payAmount;

	/**
	 * 支付时间
	 */
	@Schema(description = "支付时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime payTime;

	/**
	 * 会员到期时间
	 */
	@Schema(description = "会员到期时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate expirationDate;

	/**
	 * 支付状态：0.未支付，1.已支付
	 */
	@Schema(description = "支付状态：0.未支付，1.已支付", hidden = true)
	private Integer payState;

	/**
	 * 权益id集合
	 */
	@Schema(description = "权益id集合", hidden = true)
	private String rightsIds;

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