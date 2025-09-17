package com.wanmi.sbc.order.api.response.paytimeseries;

import com.wanmi.sbc.order.bean.vo.PayTimeSeriesVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）支付流水记录信息response</p>
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTimeSeriesByIdResponse implements Serializable {
    private static final long serialVersionUID = -5480570013720806330L;

    /**
     * 支付流水记录信息
     */
    @Schema(description = "支付流水记录信息")
    private PayTimeSeriesVO payTimeSeriesVO;
}
