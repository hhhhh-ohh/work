package com.wanmi.sbc.order.api.request.groupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>团明细</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponDetailWithGoodsRequest extends BaseRequest {
	private static final long serialVersionUID = -4493594277885985685L;


	/**
	 * 会员ID
	 */
	@Schema(description = "会员ID")
	private String customerId;

	/**
	 * 团活动
	 */
	@Schema(description = " 团活动信息")
	private GrouponActivityVO grouponActivity;

	@Schema(description = "商品信息", required = true)
	private List<GoodsInfoVO> goodsInfoList;

	@Schema(description = "拼团商品信息", required = true)
	private List<GrouponGoodsInfoVO> grouponGoodsInfoVOList;

}