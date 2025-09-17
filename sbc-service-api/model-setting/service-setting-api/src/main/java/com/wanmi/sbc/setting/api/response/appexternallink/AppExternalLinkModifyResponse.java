package com.wanmi.sbc.setting.api.response.appexternallink;

import com.wanmi.sbc.setting.bean.vo.AppExternalLinkVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>AppExternalLink修改结果</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalLinkModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的AppExternalLink信息
     */
    @Schema(description = "已修改的AppExternalLink信息")
    private AppExternalLinkVO appExternalLinkVO;
}
