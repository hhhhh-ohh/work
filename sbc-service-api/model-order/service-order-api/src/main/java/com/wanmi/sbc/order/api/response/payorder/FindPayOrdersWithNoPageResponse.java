package com.wanmi.sbc.order.api.response.payorder;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PayOrderResponseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@Schema
public class FindPayOrdersWithNoPageResponse extends BasicResponse {

    @Schema(description = "支付单列表")
    private List<PayOrderResponseVO> payOrderResponses;

    @Schema(description = "当前页",example = "0")
    private Integer currentPage;

    @Schema(description = "每页记录数",example = "0")
    private Integer pageSize;

    @Schema(description = "总数",example = "0")
    private Long total;
}

