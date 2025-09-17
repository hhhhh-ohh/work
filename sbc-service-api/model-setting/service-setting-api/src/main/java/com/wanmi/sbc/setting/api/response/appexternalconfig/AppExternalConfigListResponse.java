package com.wanmi.sbc.setting.api.response.appexternalconfig;

import com.wanmi.sbc.setting.bean.vo.AppExternalConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>AppExternalConfig列表结果</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalConfigListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * AppExternalConfig列表结果
     */
    @Schema(description = "AppExternalConfig列表结果")
    private List<AppExternalConfigVO> appExternalConfigVOList;
}
