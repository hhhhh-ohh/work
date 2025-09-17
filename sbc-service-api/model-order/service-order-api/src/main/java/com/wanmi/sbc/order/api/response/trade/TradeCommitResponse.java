package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.TradeCommitResultVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

/**
 * @author: gaomuwei
 * @createDate: 2018/12/18 10:06
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeCommitResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 订单提交结果
     */
    @Schema(description = "订单提交结果列表")
    private List<TradeCommitResultVO> tradeCommitResults;

}
