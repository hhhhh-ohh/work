package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>推荐商品与付费会员等级关联表VO</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@Schema
@Data
public class PayingMemberRecommendRelVO implements Serializable {
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
	 * skuId
	 */
	@Schema(description = "skuId")
	private String goodsInfoId;


	/**
	 * 商品信息
	 */
	@Schema(description = "商品信息")
	private BasicResponse goodsInfoVO;
}