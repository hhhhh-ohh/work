package com.wanmi.sbc.goods.api.response.excel;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 导出商品模板响应结构
 *
 * @author daiyitian
 * @dateTime 2018/11/6 下午2:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsExcelExportTemplateResponse extends BasicResponse {

    private static final long serialVersionUID = -190690499581926021L;

    /**
     * base64位字符串形式的文件流
     */
    @Schema(description = "base64位字符串形式的文件流")
    private String file;
}
