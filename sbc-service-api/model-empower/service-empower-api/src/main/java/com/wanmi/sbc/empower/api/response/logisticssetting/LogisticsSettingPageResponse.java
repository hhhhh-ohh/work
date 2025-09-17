package com.wanmi.sbc.empower.api.response.logisticssetting;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.empower.bean.vo.LogisticsSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>物流配置分页结果</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsSettingPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物流配置分页结果
     */
    @Schema(description = "物流配置分页结果")
    private MicroServicePage<LogisticsSettingVO> logisticsSettingVOPage;
}
