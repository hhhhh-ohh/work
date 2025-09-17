package com.wanmi.sbc.crm.api.request.recommendgoodsmanage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.bean.enums.NoPushType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>商品推荐管理新增参数</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendGoodsManageAddRequest extends BaseRequest {
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
	private Integer weight;

	/**
	 * 禁推标识 0：可推送；1:禁推
	 */
	@Schema(description = "禁推标识 0：可推送；1:禁推")
	private NoPushType noPushType;

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