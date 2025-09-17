package com.wanmi.sbc.order.api.response.payorder;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class DeleteByPayOrderIdResponse extends BasicResponse {

    @Schema(description = "删除结果")
    String value;
}