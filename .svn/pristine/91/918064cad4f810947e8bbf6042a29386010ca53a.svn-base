package com.wanmi.sbc.goods.api.request.grouponsharerecord;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.ShareChannel;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>拼团分享访问记录列表查询请求参数</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponShareRecordListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 拼团活动ID
	 */
	@Schema(description = "拼团活动ID")
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
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

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

}