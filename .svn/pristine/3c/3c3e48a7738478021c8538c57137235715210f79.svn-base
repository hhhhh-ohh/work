package com.wanmi.sbc.setting.api.response;

import com.alibaba.fastjson2.JSONArray;
import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSentinelConfigResponse extends BasicResponse {

    private static final long serialVersionUID = 482786291371551111L;

    @Schema(description = "配置文件输出结果")
    private JSONArray configInfo;
}