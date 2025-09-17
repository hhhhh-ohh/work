package com.wanmi.sbc.order.api.response.paytimeseries;

import com.wanmi.sbc.order.bean.vo.PayTimeSeriesVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>支付流水记录新增结果</p>
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTimeSeriesAddResponse implements Serializable {
    private static final long serialVersionUID = 2504509629972519374L;

    /**
     * 已新增的支付流水记录信息
     */
    @Schema(description = "已新增的支付流水记录信息")
    private PayTimeSeriesVO payTimeSeriesVO;
}
