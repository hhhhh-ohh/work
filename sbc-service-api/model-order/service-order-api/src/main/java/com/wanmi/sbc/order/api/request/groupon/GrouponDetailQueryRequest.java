package com.wanmi.sbc.order.api.request.groupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.bean.enums.GrouponDetailOptType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>团明细</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponDetailQueryRequest extends BaseRequest {
	private static final long serialVersionUID = -4493594277885985685L;


	/**
	 * 会员ID
	 */
	@Schema(description = "会员ID")
	private String customerId;


	/**
	 * sku编号
	 */
	@Schema(description = "sku编号")
	private String goodsInfoId;

	/**
	 * spu编号
	 */
	@Schema(description = "spu编号")
	private String goodsId;

	/**
	 * skus编号
	 */
	@Schema(description = "skus编号")
	private List<String> goodsInfoIds ;

	/**
	 * 是否团长
	 */
	@Schema(description = "是：开团 否：参团")
	private Boolean leader;


	/**
	 * 团号
	 */
	@Schema(description = "团号")
	private String grouponNo;


	/**
	 * 业务入口
	 */
	@Schema(description = "业务入口")
	private GrouponDetailOptType optType;




	@Override
	public void checkParam() {
        //商品详情页
		if (GrouponDetailOptType.GROUPON_GOODS_DETAIL.equals(optType)) {
			if(Objects.isNull(goodsInfoId)){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}

		}
        //拼团页面
		if (GrouponDetailOptType.GROUPON_JOIN.equals(optType)){
			if(Objects.isNull(grouponNo)){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
        //提交订单
		if (GrouponDetailOptType.GROUPON_SUBMIT.equals(optType)){
			if(Objects.isNull(customerId)||Objects.isNull(leader)){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}

	}

}