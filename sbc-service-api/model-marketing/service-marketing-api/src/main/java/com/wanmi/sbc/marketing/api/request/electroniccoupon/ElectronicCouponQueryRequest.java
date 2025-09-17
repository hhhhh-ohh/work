package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>电子卡券表通用查询请求参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-电子卡券idList
	 */
	@Schema(description = "批量查询-电子卡券idList")
	private List<Long> idList;

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
	 * 是否删除 0 否  1 是
	 */
	@Schema(description = "是否删除 0 否  1 是")
	private DeleteFlag delFlag;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 包含的卡券id
	 */
	@Schema(description = "包含的卡券id")
	private List<Long> includeIds;

	@Schema(description = "绑定标识")
	private Boolean bindingFlag;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;


}