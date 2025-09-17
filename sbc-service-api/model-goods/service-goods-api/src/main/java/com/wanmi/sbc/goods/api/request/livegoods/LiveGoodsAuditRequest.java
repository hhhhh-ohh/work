package com.wanmi.sbc.goods.api.request.livegoods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.LiveGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * <p>直播商品提审参数</p>
 * @author zwb
 * @date 2020-06-06 18:49:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsAuditRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品列表
	 */
	@Schema(description = "商品列表")
	private List<LiveGoodsVO> goodsInfoVOList;

	/**
	 * accessToken
	 */
	@Schema(description = "accessToken")
	@Length(max=255)
	private String accessToken;
}