package com.wanmi.sbc.order.api.response.paytimeseries;

import com.wanmi.sbc.order.bean.vo.PayTimeSeriesVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>支付流水记录列表结果</p>
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTimeSeriesListResponse implements Serializable {
    private static final long serialVersionUID = -7037657620430843257L;

    /**
     * 支付流水记录列表结果
     */
    @Schema(description = "支付流水记录列表结果")
    private List<PayTimeSeriesVO> payTimeSeriesVOList;
}
