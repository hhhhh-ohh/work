package com.wanmi.sbc.elastic.api.request.groupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

/**
 * @className EsGrouponActivityModifyCateIdRequest
 * @description TODO
 * @author 黄昭
 * @date 2021/11/5 10:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class EsGrouponActivityModifyCateIdRequest extends BaseRequest {

    private static final long serialVersionUID = -4097969615272707842L;

    /**
     * 拼团分类Id
     */
    @Schema(description = "拼团分类Id")
    @NotBlank
    @Length(max = 32)
    private String grouponCateId;
}
