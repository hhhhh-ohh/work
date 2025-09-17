package com.wanmi.sbc.goods.api.request.goodssharerecord;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.ShareChannel;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品分享列表查询请求参数</p>
 * @author zhangwenchang
 * @date 2020-03-06 13:46:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsShareRecordListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-shareIdList
	 */
	@Schema(description = "批量查询-shareIdList")
	private List<Long> shareIdList;

	/**
	 * shareId
	 */
	@Schema(description = "shareId")
	private Long shareId;

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

}