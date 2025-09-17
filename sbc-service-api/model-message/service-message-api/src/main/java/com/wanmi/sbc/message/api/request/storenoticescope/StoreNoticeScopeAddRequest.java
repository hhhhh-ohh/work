package com.wanmi.sbc.message.api.request.storenoticescope;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.message.bean.enums.StoreNoticeReceiveScope;
import com.wanmi.sbc.message.bean.enums.StoreNoticeTargetScope;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>商家公告发送范围新增参数</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeScopeAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 公告id
	 */
	@Schema(description = "公告id")
	@NotNull
	@Max(9223372036854775807L)
	private Long noticeId;

	/**
	 * 范围分类 1：商家 2：供应商
	 */
	@Schema(description = "范围分类 1：商家 2：供应商")
	@Max(127)
	private StoreNoticeReceiveScope scopeCate;

	/**
	 * 0：自定义
	 */
	@Schema(description = "0：自定义")
	@Max(127)
	private StoreNoticeTargetScope scopeType;

	/**
	 * 目标id
	 */
	@Schema(description = "目标id")
	@NotNull
	@Max(9223372036854775807L)
	private Long scopeId;

}