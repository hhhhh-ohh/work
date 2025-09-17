package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.message.bean.enums.StoreNoticeReceiveScope;
import com.wanmi.sbc.message.bean.enums.StoreNoticeTargetScope;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家公告发送范围VO</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Schema
@Data
public class StoreNoticeScopeVO implements Serializable {
	private static final long serialVersionUID = 1L;

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