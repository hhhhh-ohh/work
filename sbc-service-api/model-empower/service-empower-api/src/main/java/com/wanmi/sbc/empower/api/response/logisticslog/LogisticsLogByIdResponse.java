package com.wanmi.sbc.empower.api.response.logisticslog;

import com.wanmi.sbc.empower.bean.vo.LogisticsLogVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）物流记录信息response</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物流记录信息
     */
    @Schema(description = "物流记录信息")
    private LogisticsLogVO logisticsLogVO;
}
