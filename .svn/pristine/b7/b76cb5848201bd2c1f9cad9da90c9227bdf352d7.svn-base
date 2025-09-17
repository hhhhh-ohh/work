package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>店铺快递公司关联表VO</p>
 * @author lq
 * @date 2019-11-05 16:12:13
 */
@Schema
@Data
public class StoreExpressCompanyRelaVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键UUID
	 */
	@Schema(description = "主键UUID")
	private Long id;

	/**
	 * 主键ID,自增
	 */
	@Schema(description = "主键ID,自增")
	private Long expressCompanyId;

	/**
	 * 快递公司信息
	 */
	@Schema(description = "快递公司信息")
	private ExpressCompanyVO expressCompany;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	private Long storeId;

	/**
	 * 商家标识
	 */
	@Schema(description = "商家标识")
	private Integer companyInfoId;
}