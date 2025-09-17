package com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>手动推荐商品管理新增参数</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	@Length(max=32)
	private String goodsId;

	/**
	 * 权重
	 */
	@Schema(description = "权重")
	private BigDecimal weight;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}