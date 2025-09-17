package com.wanmi.sbc.marketing.api.request.grouponsetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

/**
 * Created by feitingting on 2019/5/24.
 */
@Schema
@Data
public class GrouponPosterSaveRequest extends BaseRequest {

    @Schema(description = "拼团广告，json字符串")
    private String poster;
}
