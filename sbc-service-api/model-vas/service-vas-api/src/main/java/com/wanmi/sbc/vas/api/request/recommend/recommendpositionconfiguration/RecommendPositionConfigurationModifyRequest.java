package com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionOpenFlag;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionType;
import com.wanmi.sbc.vas.bean.enums.recommen.TacticsType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>推荐坑位设置修改参数</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:04:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPositionConfigurationModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 坑位名称
	 */
	@Schema(description = "坑位名称")
	@Length(max=255)
	@NotBlank
	private String name;

	/**
	 * 坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类
	 */
	@Schema(description = "坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类")
	private PositionType type;

	/**
	 * 坑位标题
	 */
	@Schema(description = "坑位标题")
	@Length(min=1,max=20)
	@NotBlank
	private String title;

	/**
	 * 推荐内容
	 */
	@Schema(description = "推荐内容")
	@NotEmpty
	private List<String> content;

	/**
	 * 推荐策略类型，0：热门推荐；1：基于商品相关性推荐
	 */
	@Schema(description = "推荐策略类型，0：热门推荐；1：基于商品相关性推荐")
	@NotNull
	private TacticsType tacticsType;

	/**
	 * 推荐上限
	 */
	@Schema(description = "推荐上限")
	@Max(9999999999L)
	private Integer upperLimit;

	/**
	 * 坑位开关，0：关闭；1：开启
	 */
	@Schema(description = "坑位开关，0：关闭；1：开启")
	private PositionOpenFlag isOpen;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

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