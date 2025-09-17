package com.wanmi.sbc.empower.api.response.logisticslog;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.empower.bean.vo.LogisticsLogVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>物流记录分页结果</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物流记录分页结果
     */
    @Schema(description = "物流记录分页结果")
    private MicroServicePage<LogisticsLogVO> logisticsLogVOPage;
}
