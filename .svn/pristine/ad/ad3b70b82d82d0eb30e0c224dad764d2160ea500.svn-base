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
 * <p>卡密导入记录表VO</p>
 * @author 许云鹏
 * @date 2022-01-26 17:36:55
 */
@Schema
@Data
public class ElectronicImportRecordVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 批次id
	 */
	@Schema(description = "批次id")
	private String id;

	/**
	 * 卡券id
	 */
	@Schema(description = "卡券id")
	private Long couponId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 销售开始时间
	 */
	@Schema(description = "销售开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleStartTime;

	/**
	 * 销售结束时间
	 */
	@Schema(description = "销售结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleEndTime;

}