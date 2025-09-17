package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.enums.BoolFlag;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家与付费会员等级关联表VO</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@Schema
@Data
public class PayingMemberStoreRelVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	private Integer levelId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 公司编码
	 */
	@Schema(description = "公司编码")
	private String companyCode;


	/**
	 * 商家类型 0、平台自营 1、第三方商家
	 */
	@Schema(description = "商家类型 0、平台自营 1、第三方商家")
	private BoolFlag companyType;

	/**
	 * 公司名称
	 */
	@Schema(description = "公司名称")
	private String supplierName;
}