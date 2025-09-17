package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className ElectronicExportTemplateResponse
 * @description 卡券模版
 * @date 2022/2/3 10:03 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicExportTemplateResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * base64位字符串形式的文件流
     */
    @Schema(description = "base64位字符串形式的文件流")
    private String templateFile;
}
