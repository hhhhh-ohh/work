package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.enums.NoPushType;
import com.wanmi.sbc.crm.bean.enums.TacticsType;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分类推荐管理VO</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@Schema
@Data
public class RecommendCateManageVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 类目id
	 */
	@Schema(description = "类目id")
	private Long cateId;

	/**
	 * 权重
	 */
	@Schema(description = "权重")
	private Integer weight;

	/**
	 * 禁推标识 0：可推送；1:禁推
	 */
	@Schema(description = "禁推标识 0：可推送；1:禁推")
	private NoPushType noPushType;

}