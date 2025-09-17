package com.wanmi.sbc.goods.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 搜索历史参数
 * Created by dyt on 2017/8/8.
 */
@Schema
@Data
public class SearchHistoryRequest extends BaseRequest {

    /**
     * 关键字
     */
    @Schema(description = "搜索历史关键字")
    @NotBlank
    private String keyword;
}
