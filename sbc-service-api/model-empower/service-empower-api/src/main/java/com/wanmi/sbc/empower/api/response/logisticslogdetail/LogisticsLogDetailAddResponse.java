package com.wanmi.sbc.empower.api.response.logisticslogdetail;

import com.wanmi.sbc.empower.bean.vo.LogisticsLogDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>物流记录明细新增结果</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogDetailAddResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的物流记录明细信息
     */
    @Schema(description = "已新增的物流记录明细信息")
    private LogisticsLogDetailVO logisticsLogDetailVO;
}
