package com.wanmi.sbc.setting.api.request.evaluateratio;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import jakarta.validation.constraints.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>商品评价系数设置新增参数</p>
 * @author liutao
 * @date 2019-02-27 15:53:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EvaluateRatioAddRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品评论系数
	 */
	private BigDecimal goodsRatio;

	/**
	 * 服务评论系数
	 */
	private BigDecimal serverRatio;

	/**
	 * 物流评分系数
	 */
	private BigDecimal logisticsRatio;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@NotNull
	@Max(127)
	private Integer delFlag;

	/**
	 * 创建时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Length(max=32)
	private String createPerson;

	/**
	 * 修改时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	@Length(max=32)
	private String updatePerson;

	/**
	 * 删除时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime delTime;

	/**
	 * 删除人
	 */
	@Length(max=32)
	private String delPerson;

}