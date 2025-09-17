package com.wanmi.sbc.message.api.request.storenoticescope;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.message.bean.enums.StoreNoticeReceiveScope;
import com.wanmi.sbc.message.bean.enums.StoreNoticeTargetScope;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家公告发送范围列表查询请求参数</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeScopeListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键idList
	 */
	@Schema(description = "批量查询-主键idList")
	private List<Long> idList;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 公告id
	 */
	@Schema(description = "公告id")
	private Long noticeId;

	/**
	 * 范围分类 1：商家 2：供应商
	 */
	@Schema(description = "范围分类 1：商家 2：供应商")
	private StoreNoticeReceiveScope scopeCate;

	/**
	 * 0：自定义
	 */
	@Schema(description = "0：自定义")
	private StoreNoticeTargetScope scopeType;

	/**
	 * 目标id
	 */
	@Schema(description = "目标id")
	private Long scopeId;

}