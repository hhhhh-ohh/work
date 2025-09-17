package com.wanmi.sbc.empower.api.response.ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * 拉卡拉附件上传返回参数
 */
@Schema
@Data
public class UploadResponse extends LakalaBaseResponse{


    /**
     * 附件ID
     */
    @Schema(description = "附件ID")
    private String attFileId;


    /**
     * 附件类型
     */
    @Schema(description = "附件类型")
    private String attType;
}
