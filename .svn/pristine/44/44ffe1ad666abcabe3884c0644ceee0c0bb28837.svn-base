package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author edz
 * @className GrouponGoodsInfoCloseRequest
 * @description 拼团商品关闭请求体
 * @date 2021/6/24 10:13 上午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class GrouponGoodsInfoCloseRequest extends BaseRequest {

    private static final long serialVersionUID = -2213100649412493123L;

    @Schema(description = "拼团活动id")
    @NotBlank
    private String grouponActivityId;
}
