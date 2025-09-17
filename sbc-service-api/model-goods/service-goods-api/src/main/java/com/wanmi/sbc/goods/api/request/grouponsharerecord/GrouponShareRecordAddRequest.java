package com.wanmi.sbc.goods.api.request.grouponsharerecord;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ShareChannel;
import com.wanmi.sbc.common.enums.TerminalSource;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>拼团分享访问记录新增参数</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponShareRecordAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 拼团活动ID
	 */
	@Schema(description = "拼团活动ID")
	@NotBlank
	private String grouponActivityId;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	private String customerId;

	/**
	 * SPU id
	 */
	@Schema(description = "SPU id")
	private String goodsId;

	/**
	 * SKU id
	 */
	@Schema(description = "SKU id")
	private String goodsInfoId;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	private Long storeId;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID")
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
	 * 分享人，通过分享链接访问的时候
	 */
	@Schema(description = "分享人，通过分享链接访问的时候")
	private String shareCustomerId;

	/**
	 * 0分享拼团，1通过分享链接访问拼团
	 */
	@Schema(description = "0分享拼团，1通过分享链接访问拼团")
	private Integer type;

	@Schema(description = "团号")
	private String grouponNo;
}