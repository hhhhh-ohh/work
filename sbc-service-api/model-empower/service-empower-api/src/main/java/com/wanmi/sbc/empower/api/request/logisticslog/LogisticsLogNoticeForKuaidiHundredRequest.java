package com.wanmi.sbc.empower.api.request.logisticslog;

import com.wanmi.sbc.empower.bean.dto.KuaidiHundredNoticeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据快递100的回调通知请求参数</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogNoticeForKuaidiHundredRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "快递100的回调参数")
    private KuaidiHundredNoticeDTO kuaidiHundredNoticeDTO;
}
