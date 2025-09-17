package com.wanmi.sbc.empower.api.response.logisticslog;

import com.wanmi.sbc.empower.bean.vo.LogisticsLogVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>物流记录修改结果</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的物流记录信息
     */
    @Schema(description = "已修改的物流记录信息")
    private LogisticsLogVO logisticsLogVO;
}
