package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>拼团活动信息表VO</p>
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@Schema
@Data
public class GrouponActivityVO extends BasicResponse {

	private static final long serialVersionUID = -5758613804083118625L;

	/**
	 * 活动ID
	 */
    @Schema(description = "活动ID")
	private String grouponActivityId;

	/**
	 * 拼团人数
	 */
    @Schema(description = "拼团人数")
	private Integer grouponNum;

	/**
	 * 开始时间
	 */
    @Schema(description = "开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
    @Schema(description = "结束时间")
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
	private boolean autoGroupon;

	/**
	 * 是否包邮
	 */
    @Schema(description = "是否包邮")
	private boolean freeDelivery;

	/**
	 * spu编号
	 */
    @Schema(description = "spu编号")
	private String goodsId;

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
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 已成团人数
	 */
    @Schema(description = "已成团人数")
	private Integer alreadyGrouponNum;

	/**
	 * 待成团人数
	 */
    @Schema(description = "待成团人数")
	private Integer waitGrouponNum;

	/**
	 * 团失败人数
	 */
    @Schema(description = "团失败人数")
	private Integer failGrouponNum;

	/**
	 * 审核不通过原因
	 */
	@Schema(description = "审核不通过原因")
	private String auditFailReason;

	/**
	 * 审核状态
	 */
	@Schema(description = "活动审核状态，0：待审核，1：审核通过，2：审核不通过")
	private AuditStatus auditStatus;


	private DeleteFlag delFlag = DeleteFlag.NO;


	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;


	private String goodsNo;

	/**
	 * 预热时间
	 */
	@Schema(description = "预热时间")
	private Integer preTime;

	/**
	 * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
	 */
	@Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
	private Integer goodsType;

}