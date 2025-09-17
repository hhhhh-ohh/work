package com.wanmi.sbc.goods.api.request.grouponsharerecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ShareChannel;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>拼团分享访问记录修改参数</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponShareRecordModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 拼团活动ID
	 */
	@Schema(description = "拼团活动ID")
	@NotBlank
	@Length(max=32)
	private String grouponActivityId;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	@Length(max=32)
	private String customerId;

	/**
	 * SPU id
	 */
	@Schema(description = "SPU id")
	@NotBlank
	@Length(max=32)
	private String goodsId;

	/**
	 * SKU id
	 */
	@Schema(description = "SKU id")
	@NotBlank
	@Length(max=32)
	private String goodsInfoId;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	@NotNull
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID")
	@NotNull
	@Max(9223372036854775807L)
	private Long companyInfoId;

	/**
	 * 终端：1 H5，2pc，3APP，4小程序
	 */
	@Schema(description = "终端：1 H5，2pc，3APP，4小程序")
	private TerminalSource terminalSource;

	/**
	 * 分享渠道：0微信，1朋友圈，2QQ，3QQ空间，4微博，5复制链接，6保存图片
	 */
	@Schema(description = "分享渠道：0微信，1朋友圈，2QQ，3QQ空间，4微博，5复制链接，6保存图片")
	private ShareChannel shareChannel;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 分享人，通过分享链接访问的时候
	 */
	@Schema(description = "分享人，通过分享链接访问的时候")
	@Length(max=31)
	private String shareCustomerId;

	/**
	 * 0分享拼团，1通过分享链接访问拼团
	 */
	@Schema(description = "0分享拼团，1通过分享链接访问拼团")
	@Max(127)
	private Integer type;

}