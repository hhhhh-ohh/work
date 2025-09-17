package com.wanmi.sbc.setting.api.response.appexternalconfig;

import com.wanmi.sbc.setting.bean.vo.AppExternalConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>AppExternalConfig修改结果</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalConfigModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的AppExternalConfig信息
     */
    @Schema(description = "已修改的AppExternalConfig信息")
    private AppExternalConfigVO appExternalConfigVO;
}
