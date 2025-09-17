package com.wanmi.sbc.goods.bean.vo.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>微信类目资质VO</p>
 * @author 
 * @date 2022-04-14 10:13:05
 */
@Schema
@Data
public class WechatCateCertificateVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 微信类目id
	 */
	@Schema(description = "微信类目id")
	private Long cateId;

	/**
	 * 图片路径
	 */
	@Schema(description = "图片路径")
	private String certificateUrl;

	/**
	 * createTime
	 */
	@Schema(description = "createTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson")
	private String createPerson;

}