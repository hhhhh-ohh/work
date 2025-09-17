package com.wanmi.sbc.order.api.response.payorder;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PayOrderDetailVO;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPayOrderListResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;

    @Schema(description = "支付单集合")
    private List<PayOrderDetailVO> payOrders;

    @Schema(description = "商品idList")
    List<String> goodsIdList;

    @Schema(description = "订单取消时间，如果为空或小于等于0则不提醒")
    private Long cancelTime;

    /**
     * 是否发券
     */
    @Schema(description = "是否发券")
    private Boolean sendCouponFlag = false;

    /**
     * 支付成功广告页
     */
    @Schema(description = "支付成功广告页")
    private List<PayAdvertisementVO> payAdvertisementVOS;

}
