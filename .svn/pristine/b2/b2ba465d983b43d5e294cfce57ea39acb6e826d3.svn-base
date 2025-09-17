package com.wanmi.sbc.empower.api.response.logisticssetting;

import com.wanmi.sbc.empower.bean.vo.LogisticsSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）物流配置信息response</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsSettingByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物流配置信息
     */
    @Schema(description = "物流配置信息")
    private LogisticsSettingVO logisticsSettingVO;
}
