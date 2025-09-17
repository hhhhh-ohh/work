package com.wanmi.sbc.empower.api.request.Ledger.lakala;



import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 拉卡拉附件上传
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema
public class LakalaUploadRequest extends LakalaBaseRequest{


    /**
     * 附件类型
     * 附件类型枚举，ID_CARD_FRONT
     * {@link com.wanmi.sbc.empower.bean.enums.AttType}
     */
    @Schema(description = "附件类型")
    @NotBlank
    @Max(32)
    private String attType;

    /**
     * 附件扩展名称
     * jpg，png，pdf；5M以内
     */
    @Schema(description = "附件扩展名称")
    @NotBlank
    @Max(32)
    private String attExtName;

    /**
     * 附件内容
     * 文件内容 BASE64 【后端使用spring的Base64Utils.decodeFromString() 方法解析，建议前端使用 Base64Utils.encodeToString() 方法编码或者等效工具类】
     */
    @Schema(description = "附件内容")
    @NotBlank
    private String attContext;
}
