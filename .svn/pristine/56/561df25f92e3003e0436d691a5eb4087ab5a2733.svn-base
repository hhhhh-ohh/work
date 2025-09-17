package com.wanmi.sbc.marketing.api.request.bookingsale;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className BookingSaleCloseRequest
 * @description 活动关闭请求体
 * @date 2021/6/24 3:30 下午
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class BookingSaleCloseRequest extends BaseRequest {

    private static final long serialVersionUID = -2434675763057405755L;

    @Schema(description = "活动id")
    @NotNull
    private Long id;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
