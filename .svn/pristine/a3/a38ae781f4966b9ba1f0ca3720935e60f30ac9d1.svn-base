package com.wanmi.sbc.marketing.api.request.drawprize;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.vo.DrawPrizeVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>抽奖活动奖品表新增参数</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawPrizeAddRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 抽奖活动id
	 */
	@Schema(description = "抽奖活动id")
	@NotNull
	@Max(9223372036854775807L)
	private Long activityId;

	/**
	 * 奖品名称
	 */
	@Schema(description = "奖品名称")
	@NotBlank
	@Length(max=20)
	private String prizeName;

	/**
	 * 奖品图片
	 */
	@Schema(description = "奖品图片")
	@NotBlank
	@Length(max=1024)
	private String prizeUrl;

	/**
	 * 商品总量（1-99999999）
	 */
	@Schema(description = "商品总量（1-99999999）")
	@NotNull
	@Max(9999999999L)
	private Integer prizeNum;

	/**
	 * 中奖概率0.01-100之间的数字
	 */
	@Schema(description = "中奖概率0.01-100之间的数字")
	@NotNull
	private BigDecimal winPercent;

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
	@Schema(description = "创建人")
	@Length(max=32)
	private String createPerson;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	@Length(max=32)
	private String updatePerson;

	/**
	 * 删除标识
	 */
	@Schema(description = "删除标识")
	private DeleteFlag delFlag;

	/**
	 * 活动奖品集合
	 */
	@Schema(description = "活动奖品集合")
	private List<DrawPrizeVO> drawPrizeVOList;


}