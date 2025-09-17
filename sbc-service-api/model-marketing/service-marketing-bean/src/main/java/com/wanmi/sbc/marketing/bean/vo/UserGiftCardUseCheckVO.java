package com.wanmi.sbc.marketing.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>用户礼品卡使用验证实体类</p>
 * @author 吴瑞
 * @date 2022-12-09 14:02:19
 */
@Data
@Schema
public class UserGiftCardUseCheckVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "用户礼品卡Id")
	@NotNull
	private Long  userGiftCardId;

	@Schema(description = "预估使用余额")
	@NotNull
	private BigDecimal usePrice;

}
