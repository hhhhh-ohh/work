package com.wanmi.sbc.goods.api.request.priceadjustmentrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>调价记录表新增参数</p>
 * @author chenli
 * @date 2020-12-09 19:57:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
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
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 调价商品数
	 */
	@Schema(description = "调价商品数")
	@Max(9223372036854775807L)
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
	@Length(max=20)
	private String creatorName;

	/**
	 * 制单人账户
	 */
	@Schema(description = "制单人账户")
	@Length(max=20)
	private String creatorAccount;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 是否确认：0 未确认、1 已确认
	 */
	@Schema(description = "是否确认：0 未确认、1 已确认")
	private Integer confirmFlag;

}