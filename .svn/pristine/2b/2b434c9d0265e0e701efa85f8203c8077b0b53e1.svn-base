package com.wanmi.sbc.elastic.api.request.sensitivewords;

import com.wanmi.sbc.common.base.EsInitRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author houshuai
 * 敏感词查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsSensitiveWordsInitRequest extends EsInitRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "批量敏感词id")
    private List<Long> idList;

}