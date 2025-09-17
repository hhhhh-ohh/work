package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yangzhen
 * @Description  批量查找
 * @Date 18:15 2020/11/28
 * @Param
 * @return
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeGetByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private List<String> tid;
}
