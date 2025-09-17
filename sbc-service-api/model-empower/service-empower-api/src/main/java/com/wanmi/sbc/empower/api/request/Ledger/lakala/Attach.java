package com.wanmi.sbc.empower.api.request.Ledger.lakala;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接收方附件资料
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attach {

    /**
     * 附件类型编码
     */
    @Schema(description = "附件类型编码")
    String attachType;

    /**
     * 附件名称
     */
    @Schema(description = "附件名称")
    String attachName;

    /**
     * 附件名称
     */
    @Schema(description = "附件名称")
    String attachStorePath;
}
