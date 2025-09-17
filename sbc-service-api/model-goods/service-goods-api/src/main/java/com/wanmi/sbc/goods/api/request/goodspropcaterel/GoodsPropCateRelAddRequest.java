package com.wanmi.sbc.goods.api.request.goodspropcaterel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>商品类目与属性关联新增参数</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropCateRelAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	@Schema(description = "属性id")
	@NotNull
	@Max(9223372036854775807L)
	private Long propId;

	/**
	 * 商品分类id
	 */
	@Schema(description = "商品分类id")
	@NotNull
	@Max(9223372036854775807L)
	private Long cateId;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	@Max(9999999999L)
	private Integer relSort;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

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