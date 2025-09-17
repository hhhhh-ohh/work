package com.wanmi.sbc.goods.api.request.wechatvideo.wechatcateaudit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>微信类目审核状态通用查询请求参数</p>
 * @author 
 * @date 2022-04-09 17:02:02
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatCateAuditQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 微信返回的审核id
	 */
	@Schema(description = "微信返回的审核id")
	private String auditId;

	/**
	 * 微信类目id
	 */
	@Schema(description = "微信类目id")
	private Long wechatCateId;

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
	 * 搜索条件:createTime开始
	 */
	@Schema(description = "搜索条件:createTime开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:createTime截止
	 */
	@Schema(description = "搜索条件:createTime截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson")
	private String createPerson;

	/**
	 * 搜索条件:updateTime开始
	 */
	@Schema(description = "搜索条件:updateTime开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:updateTime截止
	 */
	@Schema(description = "搜索条件:updateTime截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

}