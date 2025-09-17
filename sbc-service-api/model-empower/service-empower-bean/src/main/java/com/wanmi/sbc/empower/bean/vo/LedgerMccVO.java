package com.wanmi.sbc.empower.bean.vo;

import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>拉卡拉mcc表VO</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Schema
@Data
public class LedgerMccVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * mcc编号
	 */
	@Schema(description = "mcc编号")
	private Long mccId;

	/**
	 * mcc类别
	 */
	@Schema(description = "mcc类别")
	private String mccCate;

	/**
	 * 商户类别名
	 */
	@Schema(description = "商户类别名")
	private String supplierCateName;

}