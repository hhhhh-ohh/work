package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>拼团活动参团信息表VO</p>
 * @author groupon
 * @date 2019-05-17 16:17:44
 */
@Schema
@Data
public class GrouponRecordVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * grouponRecordId
	 */
    @Schema(description = "grouponRecordId")
	private String grouponRecordId;

	/**
	 * 拼团活动ID
	 */
    @Schema(description = "拼团活动ID")
	private String grouponActivityId;

	/**
	 * 会员ID
	 */
    @Schema(description = "会员ID")
	private String customerId;

	/**
	 * SPU编号
	 */
    @Schema(description = "SPU编号")
	private String goodsId;

	/**
	 * sku编号
	 */
    @Schema(description = "sku编号")
	private String goodsInfoId;

	/**
	 * 已购数量
	 */
    @Schema(description = "已购数量")
	private Integer buyNum;

	/**
	 * 限购数量
	 */
    @Schema(description = "限购数量")
	private Integer limitSellingNum;

	/**
	 * createTime
	 */
    @Schema(description = "createTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * updateTime
	 */
    @Schema(description = "updateTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

}