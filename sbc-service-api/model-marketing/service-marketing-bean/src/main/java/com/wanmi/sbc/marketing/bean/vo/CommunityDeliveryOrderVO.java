package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;
import lombok.Data;
import java.io.Serializable;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购发货单VO</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Schema
@Data
public class CommunityDeliveryOrderVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	private String activityId;

	/**
	 * 团长Id
	 */
	@Schema(description = "团长Id")
	private String leaderId;

	/**
	 * 区域名称
	 */
	@Schema(description = "区域名称")
	private String areaName;

	/**
	 * 商品json内容
	 */
	@Schema(description = "商品内容")
	private List<CommunityDeliveryItemVO> goodsList;

	/**
	 * 发货状态 0:未发货 1:已发货
	 */
	@Schema(description = "发货状态 0:未发货 1:已发货")
	private Integer deliveryStatus;

	/**
	 * 汇总类型 0:按团长 1:按区域
	 */
	@Schema(description = "汇总类型 0:按团长 1:按区域")
	private DeliveryOrderSummaryType type;

	/**
	 * 团长名称
	 */
	@SensitiveWordsField(signType = SignWordType.NAME)
	@Schema(description = "团长名称")
	private String leaderName;

	/**
	 * 团长账号
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	@Schema(description = "团长账号")
	private String leaderAccount;

	/**
	 * 自提点名称
	 */
	@Schema(description = "自提点名称")
	private String pickupPointName;

	/**
	 * 联系电话
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	@Schema(description = "联系电话")
	private String contactNumber;

	/**
	 * 全详细地址
	 */
	@SensitiveWordsField(signType = SignWordType.ADDRESS)
	@Schema(description = "全详细地址")
	private String fullAddress;

}