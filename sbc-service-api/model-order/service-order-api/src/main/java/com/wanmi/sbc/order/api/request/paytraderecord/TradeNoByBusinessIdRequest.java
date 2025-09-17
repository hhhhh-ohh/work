package com.wanmi.sbc.order.api.request.paytraderecord;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/***
 * 根据业务ID查询交易流水号请求对象
 * @className TradeNoByBusinessIdRequest
 * @author zhengyang
 * @date 2022/4/22 6:17 下午
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeNoByBusinessIdRequest implements Serializable {

    /**
     * 业务ID集合
     */
    @Schema(description = "业务ID集合")
    @NotEmpty
    private List<String> businessIdList;
}
