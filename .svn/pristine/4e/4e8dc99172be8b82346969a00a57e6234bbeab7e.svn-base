package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.marketing.bean.enums.CardSaleState;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>电子卡券表VO</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Schema
@Data
public class ElectronicCouponVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 电子卡券id
	 */
	@Schema(description = "电子卡券id")
	private Long id;

	/**
	 * 电子卡券名称
	 */
	@Schema(description = "电子卡券名称")
	private String couponName;

	/**
	 * 已发送数
	 */
	@Schema(description = "已发送数")
	private Long sendNum;

	/**
	 * 未发送数
	 */
	@Schema(description = "未发送数")
	private Long notSendNum;

	/**
	 * 失效数
	 */
	@Schema(description = "失效数")
	private Long invalidNum;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 卡券销售状态
	 */
	@Schema(description = "卡券销售状态")
	private CardSaleState cardSaleState;

	/**
	 * 冻结库存
	 */
	@Schema(description = "冻结库存")
	private Long freezeStock;

	/**
	 * 绑定标识 0、未绑定 1、已绑定
	 */
	@Schema(description = "绑定标识 0、未绑定 1、已绑定")
	private Boolean bindingFlag;

}