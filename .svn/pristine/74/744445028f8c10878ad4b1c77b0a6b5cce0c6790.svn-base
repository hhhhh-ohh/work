package com.wanmi.sbc.order.api.response.leadertradedetail;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>成团团长人数结果</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderTradeDetailTradeCountLeaderResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分组成团团长人数
     */
    @Schema(description = "成团团长人数 <活动id, 成团团长人数>")
    private Map<String, Long> result;
}
