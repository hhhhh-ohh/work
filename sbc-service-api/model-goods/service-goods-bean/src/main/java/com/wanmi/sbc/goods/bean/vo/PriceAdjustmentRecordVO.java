package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>调价记录表VO</p>
 * @author chenli
 * @date 2020-12-09 19:57:21
 */
@Schema
@Data
public class PriceAdjustmentRecordVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

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
	 * 生效时间
	 */
	@Schema(description = "生效时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime effectiveTime;

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
	 * 是否确认：0 未确认、1 已确认
	 */
	@Schema(description = "是否确认：0 未确认、1 已确认")
	private DefaultFlag confirmFlag;

	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}