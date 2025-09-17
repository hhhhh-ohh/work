package com.wanmi.sbc.setting.api.request.yunservice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSentinelConfigRequest extends BaseRequest {

    @Schema(description = "接口集合")
    private List<String> interfaceList;

    @Schema(description = "接口对应并发数量")
    private List<String> countList;

    @Schema(description = "flowId集合-自增")
    private List<String> flowIdList;
}