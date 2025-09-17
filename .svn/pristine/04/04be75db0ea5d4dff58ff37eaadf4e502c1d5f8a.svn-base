package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className OrderReturnItemNumRequest
 * @description
 * @date 2021/11/17 3:10 下午
 **/
@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReturnItemNumRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1271779373990823371L;

    @Schema(description = "订单号")
    @NotBlank
    private String id;
}
