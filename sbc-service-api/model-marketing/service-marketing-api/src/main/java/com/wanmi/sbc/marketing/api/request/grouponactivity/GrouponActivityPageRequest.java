package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import com.wanmi.sbc.marketing.bean.enums.GrouponTabTypeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>拼团活动信息表分页查询请求参数</p>
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponActivityPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = -2986900069552897623L;

	/**
	 * 批量查询-活动IDList
	 */
	@Schema(description = "批量查询-活动IDList")
	private List<String> grouponActivityIdList;

	/**
	 * 活动ID
	 */
	@Schema(description = "是否包邮，0：否，1：是")
	private String grouponActivityId;

	/**
	 * 拼团人数
	 */
	@Schema(description = "拼团人数")
	private Integer grouponNum;

	/**
	 * 搜索条件:开始时间开始
	 */
	@Schema(description = "搜索条件:开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeBegin;
	/**
	 * 搜索条件:开始时间截止
	 */
	@Schema(description = "搜索条件:开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeEnd;

	/**
	 * 搜索条件:结束时间开始
	 */
	@Schema(description = "搜索条件:结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeBegin;
	/**
	 * 搜索条件:结束时间截止
	 */
	@Schema(description = "搜索条件:结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeEnd;


	/**
	 * 搜索条件:开始时间开始
	 */
	@Schema(description = "搜索条件:开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;
	/**
	 * 搜索条件:开始时间截止
	 */
	@Schema(description = "搜索条件:开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;
	/**
	 * 拼团分类ID
	 */
	@Schema(description = "拼团分类ID")
	private String grouponCateId;

	/**
	 * 是否自动成团
	 */
	@Schema(description = "是否自动成团")
	private Boolean autoGroupon;

	/**
	 * 是否包邮
	 */
	@Schema(description = "是否包邮")
	private Boolean freeDelivery;

	/**
	 * spu编号
	 */
	@Schema(description = "spu编号")
	private String goodsId;

	/**
	 * spu编码
	 */
	@Schema(description = "spu编码")
	private String goodsNo;

	/**
	 * spu商品名称
	 */
	@Schema(description = "spu商品名称")
	private String goodsName;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	private String storeId;

    /**
     * 是否精选
     */
    @Schema(description = "是否精选")
	private Boolean sticky;

	/**
	 * 是否审核通过，0：待审核，1：审核通过，2：审核不通过
	 */
	@Schema(description = "是否审核通过")
	private AuditStatus auditStatus;


	/**
	 * 删除标记 0未删除 1已删除
	 */
	@Schema(description = "删除标记")
	private DeleteFlag delFlag = DeleteFlag.NO;


	/**
	 *  页面tab类型，0: 即将开始, 1: 进行中, 2: 已结束，3：待审核，4：审核失败
	 */
	@Schema(description = " 页面tab类型")
	private GrouponTabTypeStatus tabType;

	/**
	 * 最小市场价
	 */
	@Schema(description = "最小市场价")
	private BigDecimal minMarketPrice;

	/**
	 * 最大市场价
	 */
	@Schema(description = "最大市场价")
	private BigDecimal maxMarketPrice;

	/**
	 * 最小实际销量
	 */
	@Schema(description = "实际销量查询区间：最小实际销量")
	private Long minGoodsSalesNum;

	/**
	 * 最大实际销量
	 */
	@Schema(description = "实际销量查询区间：最大实际销量")
	private Long maxGoodsSalesNum;

	/**
	 * 最小库存
	 */
	@Schema(description = "库存查询区间：最小库存")
	private Long minStock;

	/**
	 * 最大库存
	 */
	@Schema(description = "库存查询区间：最大库存")
	private Long maxStock;


	/**
	 * 商品id集合
	 */
	@Schema(description = "商品id集合")
	private List<String> spuIdList;
}