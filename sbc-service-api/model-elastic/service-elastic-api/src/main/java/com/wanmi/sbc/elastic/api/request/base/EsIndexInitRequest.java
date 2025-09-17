package com.wanmi.sbc.elastic.api.request.base;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * es索引初始化请求
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsIndexInitRequest extends BaseRequest implements Serializable {

    /**
     * 索引类型
     */
    @Schema(description = "索引类型")
    private String indexType;
}
