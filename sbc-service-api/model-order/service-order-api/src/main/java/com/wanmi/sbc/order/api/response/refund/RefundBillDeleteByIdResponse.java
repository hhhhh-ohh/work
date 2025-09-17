package com.wanmi.sbc.order.api.response.refund;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 流水单
 * Created by zhangjin on 2017/4/21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class RefundBillDeleteByIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "删除结果")
    private Integer result;
}
