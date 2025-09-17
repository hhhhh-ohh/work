package com.wanmi.sbc.elastic.api.request.groupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2020/12/12 18:50
 * @description <p> </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class EsGrouponActivityDelByIdRequest extends BaseRequest {


    @NotBlank
    @Schema(description = "拼团活动id")
    private String id;
}
