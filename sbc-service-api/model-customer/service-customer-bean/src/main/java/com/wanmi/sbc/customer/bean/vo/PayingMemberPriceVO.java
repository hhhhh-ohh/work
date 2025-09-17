package com.wanmi.sbc.customer.bean.vo;

import java.math.BigDecimal;
import lombok.Data;
import java.io.Serializable;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费设置表VO</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Schema
@Data
public class PayingMemberPriceVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 付费设置id
	 */
	@Schema(description = "付费设置id")
	private Integer priceId;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	private Integer levelId;

	/**
	 * 付费设置数量 ，例如3个月
	 */
	@Schema(description = "付费设置数量 ，例如3个月")
	private Integer priceNum;

	/**
	 * 付费设置总金额，例如上述3个月90元
	 */
	@Schema(description = "付费设置总金额，例如上述3个月90元")
	private BigDecimal priceTotal;

	/**
	 * 权益与付费会员等级关联表VO
	 */
	@Schema(description = "权益与付费会员等级关联表VO")
	private List<PayingMemberRightsRelVO> payingMemberRightsRelVOS;

	/**
	 * 权益信息
	 */
	@Schema(description = "权益信息")
	List<CustomerLevelRightsVO> customerLevelRightsVOS;

}