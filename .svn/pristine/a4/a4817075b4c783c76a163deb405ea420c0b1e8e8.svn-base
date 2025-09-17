package com.wanmi.sbc.order.payorder.response;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 付款单
 * Created by zhangjin on 2017/4/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayOrderPageResponse extends BasicResponse {

    private List<PayOrderResponse> payOrderResponses;

    private Integer currentPage;

    private Integer pageSize;

    private Long total;
}
