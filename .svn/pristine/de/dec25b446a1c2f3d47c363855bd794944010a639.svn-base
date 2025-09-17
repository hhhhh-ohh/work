package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.order.bean.dto.TradeAddDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeAddBatchRequest extends BaseRequest {

    /**
     * 交易单
     */
    @Schema(description = "交易单")
    private List<TradeAddDTO> tradeDTOList;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

    /**
     * 会员信息
     */
    @Schema(description = "会员信息")
    private CustomerVO customerVO;

    /**
     * skuIds
     */
    @Schema(description = "skuIds")
    List<String> goodsInfoIds;
}
