package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.dto.GrouponGoodsInfoForEditDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>拼团活动信息表修改参数</p>
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@Data
public class GrouponActivityModifyRequest extends BaseRequest {

	private static final long serialVersionUID = -5880357234975551464L;

	/**
	 * 活动ID
	 */
	@Schema(description = "活动ID")
	@NotBlank
	private String grouponActivityId;

	/**
	 * 拼团人数
	 */
	@Schema(description = "拼团人数")
	@NotNull
	private Integer grouponNum;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@NotNull
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@NotNull
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 拼团分类ID
	 */
	@Schema(description = "拼团分类ID")
	private String grouponCateId;

	/**
	 * 是否自动成团
	 */
	@Schema(description = "是否自动成团")
	@NotNull
	private boolean autoGroupon;

	/**
	 * 是否包邮，0：否，1：是
	 */
	@Schema(description = "是否包邮")
	@NotNull
	private boolean freeDelivery;

	/**
	 * 拼团活动单品列表
	 */
	@Schema(description = "拼团活动单品列表")
	@NotEmpty
	private List<GrouponGoodsInfoForEditDTO> goodsInfos;

	/**
	 * 预热时间
	 */
	@Schema(description = "预热时间")
	private Integer preTime;

	@Override
	public void checkParam() {

		// 结束时间必须在开始时间之后
		if (!endTime.isAfter(startTime)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

	}

}