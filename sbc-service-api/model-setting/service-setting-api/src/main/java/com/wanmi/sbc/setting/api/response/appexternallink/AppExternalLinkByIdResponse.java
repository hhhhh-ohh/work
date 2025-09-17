package com.wanmi.sbc.setting.api.response.appexternallink;

import com.wanmi.sbc.setting.bean.vo.AppExternalLinkVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）AppExternalLink信息response</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalLinkByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * AppExternalLink信息
     */
    @Schema(description = "AppExternalLink信息")
    private AppExternalLinkVO appExternalLinkVO;
}
