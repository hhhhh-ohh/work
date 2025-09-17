package com.wanmi.sbc.crm.config.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmFlagGetResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * Crm标记 true:是false:否
     */
    @Schema(description = "Crm标记 true:是false:否")
    private Boolean crmFlag;

}
