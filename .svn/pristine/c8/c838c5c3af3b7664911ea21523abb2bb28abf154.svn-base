package com.wanmi.sbc.order.api.request.payorder;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className UpdatePayOrderNo
 * @description TODO
 * @date 2022/7/12 11:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class UpdatePayOrderNoRequest implements Serializable {
    @Schema(description = "财务支付单ID")
    private List<String> payOrderIds;

    @Schema(description = "支付单ID")
    private String payOrderNo;
}
