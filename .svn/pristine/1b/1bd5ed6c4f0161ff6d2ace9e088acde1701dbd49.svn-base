package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 标准库导出模板响应结构
 * @author daiyitian
 * @dateTime 2018/11/6 下午2:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardExcelExportTemplateResponse extends BasicResponse {

    private static final long serialVersionUID = 4987645947155837293L;

    /**
     * base64位字符串形式的文件流
     */
    @Schema(description = "base64位字符串形式的文件流")
    private String file;
}
