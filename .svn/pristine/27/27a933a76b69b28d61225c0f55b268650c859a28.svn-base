package com.wanmi.sbc.goods.bean.vo.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.vo.ThirdGoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>微信类目审核状态VO</p>
 * @author 
 * @date 2022-04-09 17:02:02
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WechatCateAuditVO extends ThirdGoodsCateVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 微信返回的审核id
	 */
	private String auditId;


	/**
	 * 微信类目id
	 */
	private Long wechatCateId;

	/**
	 * 映射的平台类目
	 */
	private String cateIds;


	/**
	 * 审核状态，0：待审核，1：审核通过，2：审核不通过
	 */
	@Schema(description = "审核状态，0：待审核，1：审核通过，2：审核不通过")
	private AuditStatus auditStatus;

	/**
	 * 审核不通过原因
	 */
	@Schema(description = "审核不通过原因")
	private String rejectReason;

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

	/**
	 * updateTime
	 */
	@Schema(description = "updateTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;


	/**
	 * 商品资质
	 */
	private String productQualificationUrls;

}