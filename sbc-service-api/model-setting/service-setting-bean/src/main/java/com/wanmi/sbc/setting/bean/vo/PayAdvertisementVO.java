package com.wanmi.sbc.setting.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.Data;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>支付广告页配置VO</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Schema
@Data
public class PayAdvertisementVO implements Serializable {

	/**
	 * 支付广告id
	 */
	@Schema(description = "支付广告id")
	private Long id;

	/**
	 * 广告名称
	 */
	@Schema(description = "广告名称")
	private String advertisementName;

	/**
	 * 投放开始时间
	 */
	@Schema(description = "投放开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 投放结束时间
	 */
	@Schema(description = "投放结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 1:全部店铺  2:部分店铺
	 */
	@Schema(description = "1:全部店铺  2:部分店铺")
	private Integer storeType;

	/**
	 * 参与门店
	 */
	@Schema(description = "参与门店")
	private String participateStoreStr;

	/**
	 * 订单金额
	 */
	@Schema(description = "订单金额")
	private BigDecimal orderPrice;

	/**
	 * 广告图片
	 */
	@Schema(description = "广告图片")
	private String advertisementImg;

	/**
	 * 目标客户 1:全平台客户 -1:指定客户 other:部分客户
	 */
	@Schema(description = "目标客户 1:全平台客户 -1:指定客户 other:部分客户")
	private String joinLevel;

	/**
	 * 目标客户
	 */
	@Schema(description = "目标客户")
	private String joinLevelName;

	/**
	 * 是否暂停（1：暂停，0：正常）
	 */
	@Schema(description = "是否暂停（1：暂停，0：正常）")
	private Integer isPause;

	/**
	 * 活动状态 (1:进行中 2:暂停中 3.未开始 4.已结束)
	 */
	@Schema(description = "活动状态 (1:进行中 2:暂停中 3.未开始 4.已结束)")
	private Integer advertisementStatus;

	/**
	 * 支付广告页关联店铺
	 */
	@Schema(description = "支付广告页关联店铺")
	private List<PayAdvertisementStoreVO> payAdvertisementStore;


	/**
	 * 获取活动状态
	 *
	 * @return
	 */
	public Integer getAdvertisementStatus() {
		if (startTime != null && endTime != null) {
			if (LocalDateTime.now().isBefore(startTime)) {
				return Constants.THREE;
			} else if (LocalDateTime.now().isAfter(endTime)) {
				return Constants.FOUR;
			} else if (isPause == Constants.ONE) {
				return Constants.TWO;
			} else {
				return Constants.ONE;
			}
		}
		return null;
	}

	/**
	 * 获取参与门店字段
	 * @return
	 */
	public String getParticipateStoreStr(){
		String participateStoreStr = null;
			if(storeType != null){
				switch (storeType){
					case Constants.ONE:
						participateStoreStr = "全部门店";
						break;
					case Constants.TWO:
						participateStoreStr = "部分门店";
						break;
					default:
						break;
				}
			}
		return participateStoreStr;
	}

	public List<Long> getJoinLevelList() {
		return Arrays.stream(getJoinLevel().split(",")).map(Long::parseLong).collect(Collectors.toList());
	}

}