package com.wanmi.sbc.setting.api.response.yunservice;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>获取文件response</p>
 *
 * @author yang
 * @date 2019-11-05 18:33:04
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YunGetResourceResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 文件内容
     */
    @Schema(description = "文件内容")
    private byte[] content;
}
