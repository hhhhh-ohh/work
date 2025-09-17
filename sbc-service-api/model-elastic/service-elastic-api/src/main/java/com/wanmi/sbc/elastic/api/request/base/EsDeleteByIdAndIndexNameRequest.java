package com.wanmi.sbc.elastic.api.request.base;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 请求删除类
 * Created by aqlu on 15/11/30.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsDeleteByIdAndIndexNameRequest extends BaseRequest implements Serializable {

    @NotBlank
    @Schema(description = "es文档id")
    private String id;

    @NotBlank
    @Schema(description = "es索引名称")
    private String indexName;
}
