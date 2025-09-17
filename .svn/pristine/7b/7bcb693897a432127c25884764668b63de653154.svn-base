package com.wanmi.sbc.empower.api.response.logisticslogdetail;

import com.wanmi.sbc.empower.bean.vo.LogisticsLogDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）物流记录明细信息response</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogDetailByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物流记录明细信息
     */
    @Schema(description = "物流记录明细信息")
    private LogisticsLogDetailVO logisticsLogDetailVO;
}
