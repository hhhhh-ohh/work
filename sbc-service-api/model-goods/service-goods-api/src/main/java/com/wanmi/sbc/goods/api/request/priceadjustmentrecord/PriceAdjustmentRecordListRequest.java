package com.wanmi.sbc.goods.api.request.priceadjustmentrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>调价记录表列表查询请求参数</p>
 * @author chenli
 * @date 2020-12-09 19:57:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-调价单号List
	 */
	@Schema(description = "批量查询-调价单号List")
	private List<String> idList;

	/**
	 * 调价单号
	 */
	@Schema(description = "调价单号")
	private String id;

	/**
	 * 调价类型：0 市场价、 1 等级价、2 阶梯价、3 供货价
	 */
	@Schema(description = "调价类型：0 市场价、 1 等级价、 2 阶梯价、 3 供货价")
	private PriceAdjustmentType priceAdjustmentType;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 调价商品数
	 */
	@Schema(description = "调价商品数")
	private Long goodsNum;

	/**
	 * 搜索条件:生效时间开始
	 */
	@Schema(description = "搜索条件:生效时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime effectiveTimeBegin;
	/**
	 * 搜索条件:生效时间截止
	 */
	@Schema(description = "搜索条件:生效时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime effectiveTimeEnd;

	/**
	 * 制单人名称
	 */
	@Schema(description = "制单人名称")
	private String creatorName;

	/**
	 * 制单人账户
	 */
	@Schema(description = "制单人账户")
	private String creatorAccount;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 是否确认：0 未确认、1 已确认
	 */
	@Schema(description = "是否确认：0 未确认、1 已确认")
	private Integer confirmFlag;

}