package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import org.checkerframework.common.aliasing.qual.Unique;

/**
 * <p>直播商家VO</p>
 * @author zwb
 * @date 2020-06-06 18:06:59
 */
@Schema
@Data
public class LiveCompanyVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 提交审核时间
	 */
	@Schema(description = "提交审核时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime submitTime;

	/**
	 * 直播状态 0未开通，1待审核，2已开通，3审核未通过，4禁用中
	 */
	@Schema(description = "直播状态 0未开通，1待审核，2已开通，3审核未通过，4禁用中")
	private Integer liveBroadcastStatus;

	/**
	 * 直播审核原因
	 */
	@Schema(description = "直播审核原因")
	private String auditReason;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID")
	private Long companyInfoId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	@Unique
	private Long storeId;

}