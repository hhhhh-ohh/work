package com.wanmi.sbc.customer.api.request.storelevel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>商户客户等级表新增参数</p>
 * @author yang
 * @date 2019-02-27 19:51:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StoreLevelAddRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺编号
	 */
	private Long storeId;

	/**
	 * 等级名称
	 */
	@NotBlank
	@Length(max=32)
	private String levelName;

	/**
	 * 折扣率
	 */
	@NotNull
	private BigDecimal discountRate;

	/**
	 * 客户升级所需累积支付金额
	 */
	private BigDecimal amountConditions;

	/**
	 * 客户升级所需累积支付订单笔数
	 */
	private Integer orderConditions;

	/**
	 * 创建时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	private String createPerson;

}