package com.wanmi.sbc.order.api.response.paytimeseries;

import com.wanmi.sbc.order.bean.vo.PayTimeSeriesVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 支付流水记录修改结果
 *
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTimeSeriesModifyResponse implements Serializable {
    private static final long serialVersionUID = -168755470902417223L;

    /** 已修改的支付流水记录信息 */
    @Schema(description = "已修改的支付流水记录信息")
    private PayTimeSeriesVO payTimeSeriesVO;
}
