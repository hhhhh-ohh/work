package com.wanmi.sbc.goods.api.request.goodspropertydetail;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>商品属性值修改参数</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性值id
	 */
	@Schema(description = "属性值id")
	@Max(9223372036854775807L)
	private Long detailId;

	/**
	 * 属性id外键
	 */
	@Schema(description = "属性id外键")
	@NotNull
	@Max(9223372036854775807L)
	private Long propId;

	/**
	 * 属性值
	 */
	@Schema(description = "属性值")
	@Length(max=45)
	private String detailName;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	@Max(9999999999L)
	private Integer detailSort;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	@Length(max=36)
	private String deletePerson;

}
