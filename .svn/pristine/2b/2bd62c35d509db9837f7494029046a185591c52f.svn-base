package com.wanmi.sbc.vas.api.request.commodityscoringalgorithm;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>商品评分算法新增参数</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommodityScoringAlgorithmAddRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 列名
	 */
	@Schema(description = "列名")
	@Length(max=50)
	private String keyType;

	/**
	 * 权值
	 */
	@Schema(description = "权值")
	private BigDecimal weight;

	/**
	 * 标签ID
	 */
	@Schema(description = "标签ID")
	private String tagId;

	/**
	 * 0: 否 1：是
	 */
	@Schema(description = "0: 否 1：是")
	private DefaultFlag isSelected;

	/**
	 * 0:否 1:是
	 */
	@Schema(description = "0:否 1:是", hidden = true)
	private DeleteFlag delFlag;


	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}