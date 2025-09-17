package com.wanmi.sbc.goods.api.request.flashsaleactivity;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>抢购商品表通用查询请求参数</p>
 * @author xufeng
 * @date 2022-02-14 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashPromotionActivityQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	private Long storeId;

	/**
	 * 删除标志，0:未删除 1:已删除
	 */
	@Schema(description = "删除标志，0:未删除 1:已删除")
	private DeleteFlag delFlag;

	/**
	 * 查询时间段 0：未开始 1：正在进行 2：已结束 3：未开始与正在进行 4：暂停
	 */
	@Schema(description = "查询时间段，0：未开始 1：正在进行 2：已结束 3：未开始与正在进行 4：暂停")
	private Integer queryDataType;

	/**
	 * 多个店铺名称
	 */
	@Schema(description = "多个店铺id")
	private List<Long> storeIds;

	/**
	 * 活动名称
	 */
	@Schema(description = "activityName")
	private String activityName;

	/**
	 * 活动开始时间
	 */
	@Schema(description = "startTimeStr")
	private String startTimeStr;

	/**
	 * 活动结束时间
	 */
	@Schema(description = "endTimeStr")
	private String endTimeStr;

}