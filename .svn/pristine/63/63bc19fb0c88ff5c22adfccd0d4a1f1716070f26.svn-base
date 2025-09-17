package com.wanmi.sbc.vas.bean.vo.recommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.enums.recommen.NoPushType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>商品推荐管理VO</p>
 * @author lvzhenweirecommend
 * @date 2020-11-18 14:07:44
 */
@Schema
@Data
public class RecommendGoodsManageVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	private String goodsId;

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