package com.wanmi.sbc.empower.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>达达配送记录VO</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Schema
@Data
public class DeliveryRecordDadaAddVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 配送距离;单位:米
	 */
	@Schema(description = "配送距离;单位:米")
	private BigDecimal distance;

	/**
	 * 实际运费
	 */
	@Schema(description = "实际运费")
	private BigDecimal fee;

	/**
	 * 运费
	 */
	@Schema(description = "运费")
	private BigDecimal deliverFee;
}