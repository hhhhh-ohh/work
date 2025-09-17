package com.wanmi.sbc.elastic.api.request.ledger;

import com.wanmi.sbc.common.base.EsInitRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoRequest
 * @description
 * @date 2022/7/13 3:13 PM
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsLedgerBindInfoInitRequest extends EsInitRequest {
    private static final long serialVersionUID = 4489038089958996531L;

    @Schema(description = "ids")
    private List<String> idList;
}
