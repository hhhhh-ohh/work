package com.wanmi.sbc.elastic.api.request.searchterms;

import com.wanmi.sbc.common.base.EsInitRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author houshuai
 * @date 2020/12/17 14:25
 * @description <p> </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class EsSearchAssociationalWordInitRequest extends EsInitRequest {

    private static final long serialVersionUID = 6875631224899349826L;

    @Schema(description = "批量搜索词id")
    private List<Long> idList;
}
