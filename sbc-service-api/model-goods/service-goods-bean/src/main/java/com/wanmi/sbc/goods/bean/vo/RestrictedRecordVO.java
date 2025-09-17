package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;

/**
 * <p>限售VO</p>
 * @author 限售记录
 * @date 2020-04-11 15:59:01
 */
@Schema
@Data
public class RestrictedRecordVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录主键
	 */
	@Schema(description = "记录主键")
	private Long recordId;

	/***
	 * 门店ID
	 */
	@Schema(description = "门店ID")
	private Long storeId;

	/**
	 * 会员的主键
	 */
	@Schema(description = "会员的主键")
	private String customerId;

	/**
	 * 货品主键
	 */
	@Schema(description = "货品主键")
	private String goodsInfoId;

	/**
	 * 购买的数量
	 */
	@Schema(description = "购买的数量")
	private Long purchaseNum;

	/**
	 * 周期类型（0: 终生，1:周  2:月  3:年）
	 */
	@Schema(description = "周期类型（0: 终生，1:周  2:月  3:年）")
	private Integer restrictedCycleType;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate startDate;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate endDate;

}